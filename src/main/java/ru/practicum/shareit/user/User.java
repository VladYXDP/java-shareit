package ru.practicum.shareit.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.Item;

import java.util.Set;

@Getter
@Setter
@Builder
public class User {
    private Long id;
    private String name;
    private String email;
    private Set<Item> items;
}