package ru.practicum.shareit.item;

import java.util.List;

public interface ItemStorage {

    Item add(Item item);

    Item update(Item item);

    Item get(Long userId, Long itemId);

    List<Item> getAllByUser(long id);

    Item delete(long id);

    List<Item> getAll();
}
