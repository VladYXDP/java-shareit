package ru.practicum.shareit.booking;

import java.util.List;

public interface BookingService {

    Booking add(Booking booking);

    Booking approved(Long bookingId, Long userId, Boolean approved);

    Booking get(Long bookingId, Long userId);

    List<Booking> getByOwner(Long userId);

    List<Booking> getAllByUser(Long userId, BookingControllerStates state);
}
