package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        dto.setUserId(userId);
        return bookingTransfer.toDto(bookingService.add(bookingTransfer.toEntity(dto)));
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
