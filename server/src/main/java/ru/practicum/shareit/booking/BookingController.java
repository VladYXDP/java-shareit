package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingTransfer bookingTransfer;

    //Бронирование
    @PostMapping
    public CreateBookingDto add(@RequestBody BookingDto dto,
                                @RequestHeader("X-Sharer-User-Id") Long userId) {
        dto.setUserId(userId);
        return bookingTransfer.toCreateDto(bookingService.add(bookingTransfer.toEntity(dto)));
    }

    @PatchMapping("/{bookingId}")
    public CreateBookingDto approved(@PathVariable("bookingId") Long bookingId,
                                     @RequestParam(value = "approved", required = false, defaultValue = "false") Boolean approved,
                                     @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingTransfer.toCreateDto(bookingService.approved(bookingId, userId, approved));
    }

    @GetMapping(value = "/{bookingId}")
    public CreateBookingDto get(@PathVariable("bookingId") Long bookingId,
                                @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingTransfer.toCreateDto(bookingService.get(bookingId, userId));
    }

    @GetMapping(value = "/owner")
    public List<CreateBookingDto> getByOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @RequestParam(value = "state", required = false, defaultValue = "ALL") String state) {
        return bookingTransfer.toListCreateDto(bookingService.getByOwner(userId, BookingControllerStates.getState(state)));
    }

    @GetMapping
    public List<CreateBookingDto> getAllByUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                               @RequestParam(value = "state", required = false, defaultValue = "ALL") String state) {
        return bookingTransfer.toListCreateDto(bookingService.getAllByUser(userId, BookingControllerStates.getState(state)));
    }
}
