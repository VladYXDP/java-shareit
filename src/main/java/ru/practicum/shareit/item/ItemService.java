package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public Item add(Item item) {
        userStorage.get(item.getOwner());
        return itemStorage.add(item);
    }

    public Item update(Item item) {
        return itemStorage.update(item);
    }

    public Item get(long userId, long itemId) {
        return itemStorage.get(userId, itemId);
    }

    public List<Item> getAllByUser(long userId) {
        userStorage.get(userId);
        return itemStorage.getAllByUser(userId);
    }

    public List<Item> search(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        return itemStorage.getAll().stream()
                .filter(item -> item.getDesc().toLowerCase().contains(text.toLowerCase())
                        && item.getAvailable().equals(Boolean.TRUE))
                .toList();
    }
}
