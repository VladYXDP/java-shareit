package ru.practicum.shareit.booking;

import ru.practicum.shareit.item.Item;

import java.util.List;

public interface BookingService {

    Booking add(Booking booking);

    Booking approved(Long bookingId, Long userId, Boolean approved);

    Booking get(Long bookingId, Long userId);

    List<Booking> getByOwner(Long userId, BookingControllerStates state);

    List<Booking> getAllBookingByItem(Item item);

    List<Booking> getAllByUser(Long userId, BookingControllerStates state);
}
