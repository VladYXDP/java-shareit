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

    public Item toItem(ItemDto dto) {
        return Item.builder()
                .id(dto.getId())
                .name(dto.getName())
                .desc(dto.getDescription())
                .owner(dto.getOwner())
                .available(dto.getAvailable())
                .build();
    }

    public ItemDto toDto(Item dto) {
        return ItemDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDesc())
                .owner(dto.getOwner())
                .available(dto.getAvailable())
                .build();
    }

    public ItemDtoEntity toDto(ItemEntity entity) {
        return ItemDtoEntity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .owner(userTransfer.toDtoEntity(entity.getOwner()))
                .available(entity.getAvailable())
                .build();
    }

    public ItemEntity toEntity(ItemDtoEntity entityDto) {
        return ItemEntity.builder()
                .id(entityDto.getId())
                .name(entityDto.getName())
                .description(entityDto.getDescription())
                .available(entityDto.getAvailable())
                .owner(userTransfer.toEntity(entityDto.getOwner()))
                .build();
    }
}
