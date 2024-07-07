package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.UserTransfer;

@Component
@Primary
@RequiredArgsConstructor
public class ItemTransfer {

    private final UserTransfer userTransfer;

    public ItemDto toDto(Item entity) {
        return ItemDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .owner(userTransfer.toDto(entity.getOwner()))
                .available(entity.getAvailable())
                .build();
    }

    public Item toEntity(ItemDto entityDto) {
        return Item.builder()
                .id(entityDto.getId())
                .name(entityDto.getName())
                .description(entityDto.getDescription())
                .available(entityDto.getAvailable())
                .owner(userTransfer.toUser(entityDto.getOwner()))
                .build();
    }
}
