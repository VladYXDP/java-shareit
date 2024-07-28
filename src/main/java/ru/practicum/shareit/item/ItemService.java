package ru.practicum.shareit.item;

import java.util.Set;

public interface ItemService {

    Item add(Item item);

    Item update(Item item);

    Item get(Long itemId, Long userId);

    Item delete();

    Set<Item> search(String search);

    Comment addComment(Comment comment, Long userId, Long itemId);

    Set<Item> findAllByUserId(Long userId);
}
