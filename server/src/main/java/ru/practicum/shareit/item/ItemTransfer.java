package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.ItemBookingDto;

@Component
@Primary
@RequiredArgsConstructor
public class ItemTransfer {

    private final CommentTransfer commentTransfer;

    public ItemDto toDto(Item entity) {
        return ItemDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .available(entity.getAvailable())
                .lastBooking(entity.getLastBooking() != null
                        ? new ItemBookingDto(entity.getLastBooking().getId(), entity.getLastBooking().getBooker().getId())
                        : null)
                .nextBooking(entity.getNextBooking() != null
                        ? new ItemBookingDto(entity.getNextBooking().getId(), entity.getNextBooking().getBooker().getId())
                        : null)
                .comments(commentTransfer.toDtoList(entity.getComments()))
                .build();
    }

    public Item toItemCreate(ItemDto dto) {
        return Item.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .ownerId(dto.getOwnerId())
                .available(dto.getAvailable())
                .requestId(dto.getRequestId())
                .build();
    }

    public Item toItem(ItemDto dto) {
        return Item.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .ownerId(dto.getOwnerId())
                .build();
    }
}
