package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking save(Booking booking);

    Optional<Booking> getBookingById(Long bookingId);

    List<Booking> findAllByBookerId(Long userId);
}
