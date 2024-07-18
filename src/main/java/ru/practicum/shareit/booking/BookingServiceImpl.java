package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ItemAvailableException;
import ru.practicum.shareit.exceptions.NotFoundException;
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
        userService.get(userId);
        Booking booking = get(bookingId, userId);
        booking.setStatus(BookingStatus.APPROVED);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking get(Long bookingId, Long userId) {
        userService.get(userId);
        Optional<Booking> bookingOpt = bookingRepository.getBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            return bookingOpt.get();
        } else {
            throw new NotFoundException("Ошибка получения бронирования с id " + bookingId + "!");
        }
    }

    @Override
    public List<Booking> getByOwner(Long userId) {
        userService.get(userId);
        List<Booking> bookings = bookingRepository.findAll().stream()
                .sorted(Comparator.comparing(Booking::getId).reversed())
                .toList();
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
            case PAST -> bookings = bookingRepository.getBookingById(userId).stream()
                    .filter(it -> it.getEndDate().isBefore(LocalDateTime.now()))
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .collect(Collectors.toList());
            case FUTURE -> bookings = bookingRepository.findAllByBookerId(userId).stream()
                    .filter(it -> it.getStartDate().isAfter(LocalDateTime.now()))
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .toList();
            case CURRENT -> bookings = bookingRepository.getBookingById(userId).stream()
                    .filter(it -> it.getStatus().equals(BookingStatus.APPROVED))
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .collect(Collectors.toList());
            case WAITING -> bookings = bookingRepository.getBookingById(userId).stream()
                    .filter(it -> it.getStatus().equals(BookingStatus.WAITING))
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .collect(Collectors.toList());
            case REJECTED -> bookings = bookingRepository.getBookingById(userId).stream()
                    .filter(it -> it.getStatus().equals(BookingStatus.REJECTED))
                    .sorted(Comparator.comparing(Booking::getId).reversed())
                    .collect(Collectors.toList());
        }
        return bookings;
    }
}
