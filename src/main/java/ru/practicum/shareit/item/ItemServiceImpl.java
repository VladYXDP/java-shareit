package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exceptions.NotFoundException;
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
public class ItemServiceImpl implements ItemService {

    private final UserServiceImpl userService;
    private final ItemsRepository itemsRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Item add(Item item) {
        User user = userService.get(item.getOwnerId());
        item.setOwner(user);
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
    public Item get(Long itemId, Long userId) {
        User user = userService.get(userId);
        Item item = itemsRepository.getItemById(itemId);
        List<Booking> bookings = bookingRepository.findAllByItemOrderByStartDateDesc(item);
        if (item != null) {
            if (!bookings.isEmpty()) {
                LocalDateTime now = LocalDateTime.now();
                Optional<Booking> lastBookingOpt = bookingRepository.findLastBooking(now, item.getId());
                Optional<Booking> nextBookingOpt = bookingRepository.findNextBooking(now, item.getId());
                if (lastBookingOpt.isPresent()) {
                    Booking lastBooking = lastBookingOpt.get();
                    if (lastBooking.getBooker().getId() != user.getId()) {
                        item.setLastBooking(lastBookingOpt.get());
                    }
                }
                if (nextBookingOpt.isPresent()) {
                    Booking nextBooking = nextBookingOpt.get();
                    if (nextBooking.getBooker().getId() != user.getId()) {
                        item.setNextBooking(nextBookingOpt.get());
                    }
                }
            }
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
    public Set<Item> search(String search) {
        if (search.isEmpty()) {
            return new HashSet<>();
        }
        Set<Item> items = itemsRepository.search("%" + search + "%", "%" + search + "%");
        return items;
    }

    @Override
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
                            item.setLastBooking(lastBookingOpt.get());
                        }
                    }
                    if (nextBookingOpt.isPresent()) {
                        Booking nextBooking = nextBookingOpt.get();
                        if (nextBooking.getBooker().getId() != userId) {
                            item.setNextBooking(nextBookingOpt.get());
                        }
                    }
                    return item;
                })
                .collect(Collectors.toSet());
    }
}
