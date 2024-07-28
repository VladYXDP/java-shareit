package ru.practicum.shareit.booking;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

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
