package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.BookingDateException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingTransfer bookingTransfer;

    //Бронирование
    @PostMapping
    public BookingDto add(@Valid @RequestBody BookingDto dto,
                          @Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        if (dto.getEnd().isAfter(LocalDateTime.now())) {
            if (dto.getEnd().isAfter(dto.getStart())) {
                if (!dto.getStart().equals(dto.getEnd())) {
                    if (dto.getStart().isAfter(LocalDateTime.now())) {
                        dto.setUserId(userId);
                        return bookingTransfer.toDto(bookingService.add(bookingTransfer.toEntity(dto)));
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

    //Подтверждение бронирования
    @PostMapping(value = "/{bookingId}")
    public void complete() {

    }

    @GetMapping(value = "/{bookingId}")
    public void get() {

    }

    @GetMapping
    public void getAllCurrentUser() {

    }
}
