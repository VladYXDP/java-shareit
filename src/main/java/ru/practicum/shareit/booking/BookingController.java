package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.BookingDateException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingTransfer bookingTransfer;

    //Бронирование
    @PostMapping
    public CreateBookingDto add(@Valid @RequestBody BookingDto dto,
                                @Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        if (dto.getEnd().isAfter(LocalDateTime.now())) {
            if (dto.getEnd().isAfter(dto.getStart())) {
                if (!dto.getStart().equals(dto.getEnd())) {
                    if (dto.getStart().isAfter(LocalDateTime.now())) {
                        dto.setUserId(userId);
                        return bookingTransfer.toCreateDto(bookingService.add(bookingTransfer.toEntity(dto)));
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
    public CreateBookingDto approved(@Positive @PathVariable("bookingId") Long bookingId,
                                     @RequestParam(value = "approved", required = false, defaultValue = "false") Boolean approved,
                                     @Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingTransfer.toCreateDto(bookingService.approved(bookingId, userId, approved));
    }

    @GetMapping(value = "/{bookingId}")
    public CreateBookingDto get(@Positive @PathVariable("bookingId") Long bookingId,
                                @Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingTransfer.toCreateDto(bookingService.get(bookingId, userId));
    }

    @GetMapping(value = "/owner")
    public List<CreateBookingDto> getByOwner(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                             @RequestParam(value = "state", required = false, defaultValue = "ALL") String state) {
        BookingControllerStates.getState(state);
        return bookingTransfer.toListCreateDto(bookingService.getByOwner(userId));
    }

    @GetMapping
    public List<CreateBookingDto> getAllByUser(@Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                               @RequestParam(value = "state", required = false, defaultValue = "ALL") String state) {
        return bookingTransfer.toListCreateDto(bookingService.getAllByUser(userId, BookingControllerStates.getState(state)));
    }
}
