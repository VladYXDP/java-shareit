package ru.practicum.shareit.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.practicum.shareit.item.ItemBookingDto;
import ru.practicum.shareit.user.UserBookingDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private BookingStatus status;
    @JsonProperty(value = "item")
    private ItemBookingDto itemBookingDto;
    @JsonProperty(value = "booker")
    private UserBookingDto userBookingDto;
}
