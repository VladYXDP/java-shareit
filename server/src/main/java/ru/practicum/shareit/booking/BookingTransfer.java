package ru.practicum.shareit.booking;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemBookingDto;
import ru.practicum.shareit.user.UserBookingDto;

import java.util.List;

@Component
public class BookingTransfer {

    public Booking toEntity(BookingDto dto) {
        return Booking.builder()
                .userId(dto.getUserId())
                .itemId(dto.getItemId())
                .startDate(dto.getStart())
                .endDate(dto.getEnd())
                .build();
    }

    public BookingDto toDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStartDate())
                .end(booking.getEndDate())
                .status(booking.getStatus())
                .build();
    }

    public List<CreateBookingDto> toListCreateDto(List<Booking> bookings) {
        return bookings.stream().map(this::toCreateDto).toList();
    }

    public CreateBookingDto toCreateDto(Booking booking) {
        return CreateBookingDto.builder()
                .id(booking.getId())
                .start(booking.getStartDate())
                .end(booking.getEndDate())
                .status(booking.getStatus())
                .itemBookingDto(new ItemBookingDto(
                        booking.getItem().getId(),
                        booking.getItem().getName()
                ))
                .userBookingDto(new UserBookingDto(booking.getBooker().getId()))
                .build();
    }

    public ApprovedBookingDto toApprovedDto(Booking booking) {
        return ApprovedBookingDto.builder()
                .id(booking.getId())
                .status(booking.getStatus())
                .itemBookingDto(new ItemBookingDto(
                        booking.getItem().getId(),
                        booking.getItem().getName()
                ))
                .userBookingDto(new UserBookingDto(
                        booking.getBooker().getId()
                ))
                .build();
    }
}
