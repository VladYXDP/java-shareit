package ru.practicum.shareit.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Дата начала бронирования обязательна для заполнения!")
    private LocalDateTime start;
    @NotNull(message = "Дата окончания бронирования обязательна для заполнения!")
    private LocalDateTime end;
    private BookingStatus status;
    @JsonProperty(value = "item")
    private ItemBookingDto itemBookingDto;
    @JsonProperty(value = "booker")
    private UserBookingDto userBookingDto;
}
