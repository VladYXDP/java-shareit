package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;

@Component
public class ItemTransfer {

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
}
