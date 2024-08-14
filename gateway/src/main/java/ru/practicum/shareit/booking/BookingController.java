package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.BookingDateException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody BookingDto dto,
                                      @Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        if (dto.getEnd().isAfter(LocalDateTime.now())) {
            if (dto.getEnd().isAfter(dto.getStart())) {
                if (!dto.getStart().equals(dto.getEnd())) {
                    if (dto.getStart().isAfter(LocalDateTime.now())) {
                        dto.setUserId(userId);
                        return bookingClient.add(dto);
                    } else {
                        throw new BookingDateException("Ошибка валидации данных! Дата начала должна быть позже текущей даты - ! " + LocalDateTime.now());
                    }
                } else {
                    throw new BookingDateException("Ошибка валидации данных! Дата начала и окончания не должны совпадать!");
                }
            } else {
                throw new BookingDateException("Ошибка валидации данных! Окончание даты бронирования должно быть позже даты начала - " + dto.getStart());
            }
        } else {
            throw new BookingDateException("Ошибка валидации данных! Окончание даты бронирования должно быть позже - " + LocalDateTime.now());
        }
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approved(@Positive @PathVariable("bookingId") Long bookingId,
                                           @RequestParam(value = "approved", required = false, defaultValue = "false") Boolean approved,
                                           @Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingClient.approved(bookingId, approved, userId);
    }

    @GetMapping(value = "/{bookingId}")
    public ResponseEntity<Object> get(@Positive @PathVariable("bookingId") Long bookingId,
                                      @Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingClient.get(bookingId, userId);
    }

    @GetMapping(value = "/owner")
    public ResponseEntity<Object> getByOwner(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                             @RequestParam(value = "state", required = false, defaultValue = "ALL") String state) {
        return bookingClient.getByOwner(userId, state);
    }

    @GetMapping
    public ResponseEntity<Object> getAllByUser(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                               @RequestParam(value = "state", required = false, defaultValue = "ALL") String state) {
        return bookingClient.getAllByUser(userId, state);
    }
}
