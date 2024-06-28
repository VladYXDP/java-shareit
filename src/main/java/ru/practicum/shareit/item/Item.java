package ru.practicum.shareit.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Item {
    private Long id;
    private String name;
    private String desc;
    private Long owner;
    private Boolean available;
}