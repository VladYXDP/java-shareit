package ru.practicum.shareit.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.UserEntityDto;

@Getter
@Setter
@Builder
public class ItemDtoEntity {

    private Long id;
    private String name;
    private String description;
    private UserEntityDto owner;
    private Boolean available;
}
