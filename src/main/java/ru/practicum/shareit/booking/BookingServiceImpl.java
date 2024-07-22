package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.*;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public Booking add(Booking booking) {
        User user = userService.get(booking.getUserId());
        Item item = itemService.get(booking.getItemId(), booking.getUserId());
        if (item.getOwner().getId() == user.getId()) {
            throw new CreateBookingException("Владелец не может создать бронь для своей вещи!");
        }
        if (item.getAvailable()) {
            booking.setItem(item);
            booking.setBooker(user);
            booking.setStatus(BookingStatus.WAITING);
            return bookingRepository.save(booking);
        } else {
            throw new ItemAvailableException("Предмет с id " + item.getId() + " недоступен для бронирования!");
        }
    }

    @Override
    public Booking approved(Long bookingId, Long userId, Boolean approved) {
        User user = userService.get(userId);
        Optional<Booking> bookingOpt = bookingRepository.getBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            if (user.equals(booking.getBooker()) && booking.getStatus().equals(BookingStatus.WAITING) && approved) {
                throw new UpdateStatusException("Попытка бронирования уже существует " + bookingId);
            } else if (user.getItems().contains(booking.getItem())) {
                if (approved) {
                    if (booking.getStatus().equals(BookingStatus.APPROVED)) {
                        throw new ApprovedStatusAlreadyExistsException("Ошибка обновления статуса!");
                    }
                    booking.setStatus(BookingStatus.APPROVED);
                } else {
                    booking.setStatus(BookingStatus.REJECTED);
                }
                return bookingRepository.save(booking);
            } else {
                throw new UpdateException("Ошибка обновления бронирования " + bookingId + " пользователем " + userId);
            }
        } else {
            throw new NotFoundException("Ошибка обновления бронирования с id " + bookingId + "!");
        }
    }

    @Override
    public Booking get(Long bookingId, Long userId) {
        User user = userService.get(userId);
        Optional<Booking> bookingOpt = bookingRepository.getBookingById(bookingId);
        Booking booking;
        if (bookingOpt.isPresent()) {
            booking = bookingOpt.get();
            if (user.getItems().contains(booking.getItem())) {
                return booking;
            } else if (booking.getBooker().equals(user)) {
                return booking;
            }
        }
        throw new NotFoundException("Ошибка получения бронирования с id " + bookingId + "!");
    }

    @Override
    public List<Booking> getByOwner(Long userId, BookingControllerStates state) {
        List<Booking> bookings = new ArrayList<>();
        User user = userService.get(userId);
        switch (state) {
            case ALL -> bookings = bookingRepository.findAll().stream()
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .toList();
            case PAST -> bookings = bookingRepository.findAll().stream()
                    .filter(it -> it.getEndDate().isBefore(LocalDateTime.now()) && it.getBooker().getId() != userId)
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .collect(Collectors.toList());
            case FUTURE -> bookings = bookingRepository.findAll().stream()
                    .filter(it -> it.getStartDate().isAfter(LocalDateTime.now()) && it.getBooker().getId() != userId)
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .toList();
            case CURRENT -> bookings = bookingRepository.findAll().stream()
                    .filter(it -> it.getStatus().equals(BookingStatus.APPROVED) && it.getBooker().getId() != userId)
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .collect(Collectors.toList());
            case WAITING -> bookings = bookingRepository.findAll().stream()
                    .filter(it -> it.getStatus().equals(BookingStatus.WAITING) && it.getBooker().getId() != userId)
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .collect(Collectors.toList());
            case REJECTED -> bookings = bookingRepository.findAll().stream()
                    .filter(it -> it.getStatus().equals(BookingStatus.REJECTED) && it.getBooker().getId() != userId)
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .collect(Collectors.toList());
        }
        return bookings;
    }

    @Override
    public List<Booking> getAllByUser(Long userId, BookingControllerStates state) {
        List<Booking> bookings = new ArrayList<>();
        userService.get(userId);
        switch (state) {
            case ALL -> bookings = bookingRepository.findAllByBookerId(userId).stream()
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .toList();
            case PAST -> bookings = bookingRepository.findAllByBookerId(userId).stream()
                    .filter(it -> it.getEndDate().isBefore(LocalDateTime.now()))
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .collect(Collectors.toList());
            case FUTURE -> bookings = bookingRepository.findAllByBookerId(userId).stream()
                    .filter(it -> it.getStartDate().isAfter(LocalDateTime.now()))
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .toList();
            case CURRENT -> bookings = bookingRepository.findAllByBookerId(userId).stream()
                    .filter(it -> it.getStatus().equals(BookingStatus.APPROVED))
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .collect(Collectors.toList());
            case WAITING -> bookings = bookingRepository.findAllByBookerId(userId).stream()
                    .filter(it -> it.getStatus().equals(BookingStatus.WAITING))
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .collect(Collectors.toList());
            case REJECTED -> bookings = bookingRepository.findAllByBookerId(userId).stream()
                    .filter(it -> it.getStatus().equals(BookingStatus.REJECTED))
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .collect(Collectors.toList());
        }
        return bookings;
    }

    @Override
    public List<Booking> getAllBookingByItem(Item item) {
        return bookingRepository.findAllByItem(item);
    }
}
