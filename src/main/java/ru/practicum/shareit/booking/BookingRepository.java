package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.Item;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking save(Booking booking);

    Optional<Booking> getBookingById(Long bookingId);

    Optional<Booking> getBookingByItem(Item item);

    List<Booking> findAllByItem(Item item);

    List<Booking> findAllByItemOrderByStartDateDesc(Item item);

    @Query(value = "SELECT b.id, b.start_date, b.end_date, b.items_id, b.booker_id, b.status, u.id AS user_id, u.email, u.user_name " +
            "FROM bookings AS b INNER JOIN users AS u ON b.booker_id = u.id " +
            "WHERE b.start_date < ?1 AND b.items_id = ?2 " +
            "ORDER BY b.start_date " +
            "DESC LIMIT 1", nativeQuery = true)
    Optional<Booking> findLastBooking(LocalDateTime startDate, Long itemId);

    @Query(value = "SELECT b.id, b.start_date, b.end_date, b.items_id, b.booker_id, b.status, u.id AS user_id, u.email, u.user_name " +
            "FROM bookings AS b INNER JOIN users AS u ON b.booker_id = u.id " +
            "WHERE b.start_date > ?1 AND b.items_id = ?2 " +
            "ORDER BY b.start_date " +
            "ASC LIMIT 1", nativeQuery = true)
    Optional<Booking> findNextBooking(LocalDateTime startDate, Long itemId);

    @Query(value = "SELECT b.id, b.start_date, b.end_date, b.items_id, b.booker_id, b.status, u.id AS user_id, u.email, u.user_name " +
            "FROM bookings AS b INNER JOIN users AS u ON b.booker_id = u.id " +
            "WHERE b.start_date < ?1 AND b.end_date > ?1 AND b.items_id = ?2 " +
            "ORDER BY b.start_date " +
            "ASC LIMIT 1", nativeQuery = true)
    Optional<Booking> findCurrent(LocalDateTime startDate, Long itemId);

    List<Booking> findAllByStatus(BookingControllerStates state);

    List<Booking> findAllByBookerIdAndItem(Long userId, Item item);

    List<Booking> findAllByBookerId(Long userId);
}
