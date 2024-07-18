package ru.practicum.shareit.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemBookingDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserBookingDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    @NotNull(message = "Дата начала бронирования обязательна для заполнения!")
    private LocalDateTime start;
    @NotNull(message = "Дата окончания бронирования обязательна для заполнения!")
    private LocalDateTime end;
    private BookingStatus status;
    private User booker;
    private Item item;
    @Positive
    private Long itemId;
    @Positive
    private Long userId;
}
