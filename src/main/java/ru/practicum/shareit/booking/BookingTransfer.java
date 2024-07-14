package ru.practicum.shareit.booking;

import org.springframework.stereotype.Component;

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
                .build();
    }
}
