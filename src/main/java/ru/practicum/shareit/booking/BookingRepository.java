package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.Item;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking save(Booking booking);

    Optional<Booking> getBookingById(Long bookingId);

    Optional<Booking> getBookingByItem(Item item);

    List<Booking> findAllByBookerId(Long userId);
}
