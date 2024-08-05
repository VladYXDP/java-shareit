package ru.practicum.shareit.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.user.UserDto;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private UserDto owner;
    private Boolean available;
    private String requestId;
    private String itemName;

    private ItemBookingDto lastBooking;
    private ItemBookingDto nextBooking;
    private List<CommentDto> comments;
}
