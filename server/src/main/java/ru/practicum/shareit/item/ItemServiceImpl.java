package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.exceptions.CreateCommentException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.request.RequestService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final UserServiceImpl userService;
    private final ItemsRepository itemsRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final RequestService requestService;

    @Override
    public Item add(Item item) {
        User user = userService.get(item.getOwnerId());
        item.setOwner(user);
        if (item.getRequestId() != null) {
            Request request = requestService.getById(item.getRequestId());
            item.setRequest(request);
        }
        item = itemsRepository.save(item);
        return item;
    }

    @Override
    public Item update(Item item) {
        Item currentItem = itemsRepository.getItemById(item.getId());
        User user = userService.get(item.getOwnerId());
        if (user.getId() == currentItem.getOwner().getId()) {
            item.setOwner(currentItem.getOwner());
            item = itemsRepository.save(ItemValidate.validate(item, currentItem));
            return item;
        } else {
            throw new NotFoundException("Ошибка обновления предмета! Не совпадает пользователь!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Item get(Long itemId, Long userId) {
        User user = userService.get(userId);
        Item item = itemsRepository.getItemById(itemId);
        List<Booking> bookings = bookingRepository.findAllByItem(item);
        if (item != null) {
            boolean isOwner = item.getOwner().getId() == userId;
            if (!bookings.isEmpty()) {
                LocalDateTime now = LocalDateTime.now();
                Optional<Booking> lastBookingOpt = bookingRepository.findLastBooking(now, item.getId());
                Optional<Booking> nextBookingOpt = bookingRepository.findNextBooking(now, item.getId());
                if (lastBookingOpt.isPresent()) {
                    Booking lastBooking = lastBookingOpt.get();
                    if (lastBooking.getBooker().getId() != user.getId()) {
                        item.setLastBooking(lastBooking);
                    }
                }
                if (nextBookingOpt.isPresent()) {
                    Booking nextBooking = nextBookingOpt.get();
                    if (nextBooking.getBooker().getId() != user.getId() && !nextBooking.getStatus().equals(BookingStatus.REJECTED)) {
                        item.setNextBooking(nextBooking);
                    }
                }
            }
            if (!isOwner) {
                item.setLastBooking(null);
                item.setNextBooking(null);
            }
            item.setComments(commentRepository.findAllByItemId(itemId));
            return item;
        } else {
            throw new NotFoundException("Предмет с id " + itemId + " не найден!");
        }
    }

    @Override
    public Item delete() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Item> search(String search) {
        if (search.isEmpty()) {
            return new HashSet<>();
        }
        Set<Item> items = itemsRepository.search("%" + search + "%", "%" + search + "%");
        return items;
    }

    @Override
    public Comment addComment(Comment comment, Long userId, Long itemId) {
        User user = userService.get(userId);
        Item item = itemsRepository.getItemById(itemId);
        List<Booking> booking = bookingRepository.findAllByBookerIdAndItem(userId, item);
        boolean check = booking.stream().anyMatch(it -> it.getStatus().equals(BookingStatus.APPROVED)
                && it.getStartDate().isBefore(LocalDateTime.now()));
        if (!check) {
            throw new CreateCommentException("Пользователь " + userId + " не пользовался предметом " + itemId);
        }
        comment.setItem(item);
        comment.setAuthor(user);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Item> findAllByUserId(Long userId) {
        Set<Item> items = userService.get(userId).getItems();
        LocalDateTime now = LocalDateTime.now();
        return items.stream()
                .map(item -> {
                    Optional<Booking> lastBookingOpt = bookingRepository.findLastBooking(now, item.getId());
                    Optional<Booking> nextBookingOpt = bookingRepository.findNextBooking(now, item.getId());
                    if (lastBookingOpt.isPresent()) {
                        Booking lastBooking = lastBookingOpt.get();
                        if (lastBooking.getBooker().getId() != userId) {
                            item.setLastBooking(lastBooking);
                        }
                    }
                    if (nextBookingOpt.isPresent()) {
                        Booking nextBooking = nextBookingOpt.get();
                        if (nextBooking.getBooker().getId() != userId) {
                            item.setNextBooking(nextBooking);
                        }
                    }
                    return item;
                })
                .collect(Collectors.toSet());
    }
}
