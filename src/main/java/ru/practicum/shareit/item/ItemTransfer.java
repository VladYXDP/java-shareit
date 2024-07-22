package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.ItemBookingDto;
import ru.practicum.shareit.user.UserTransfer;

@Component
@Primary
@RequiredArgsConstructor
public class ItemTransfer {

    private final UserTransfer userTransfer;

    public ItemDto toDto(Item entity) {
        if (entity.getLastBooking() != null && entity.getNextBooking() != null) {
            return ItemDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .description(entity.getDescription())
//                .ownerId(entity.getOwnerId())
//                .owner(userTransfer.toDto(entity.getOwner()))
                    .available(entity.getAvailable())
                    .lastBooking(new ItemBookingDto(entity.getLastBooking().getId(), entity.getLastBooking().getBooker().getId()))
                    .nextBooking(new ItemBookingDto(entity.getNextBooking().getId(), entity.getNextBooking().getBooker().getId()))
                    .build();
        } else {
            return ItemDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .description(entity.getDescription())
//                .ownerId(entity.getOwnerId())
//                .owner(userTransfer.toDto(entity.getOwner()))
                    .available(entity.getAvailable())
                    .build();
        }
    }

    public Item toItemCreate(ItemDto dto) {
        return Item.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .ownerId(dto.getOwnerId())
                .available(dto.getAvailable())
                .build();
    }

    public Item toItem(ItemDto dto) {
        return Item.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .ownerId(dto.getOwnerId())
//                .owner(userTransfer.toUser(entityDto.getOwner()))
                .build();
    }
}
