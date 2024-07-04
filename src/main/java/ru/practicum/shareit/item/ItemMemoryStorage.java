package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ItemMemoryStorage implements ItemStorage {

    private long id = 1;
    private final Map<Long, List<Long>> owners = new HashMap<>();
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item add(Item item) {
        if (!owners.containsKey(item.getOwner())) {
            owners.put(item.getOwner(), new ArrayList<>());
        }
        item.setId(id++);
        owners.get(item.getOwner()).add(item.getId());
        items.put(item.getId(), item);
        return get(item.getOwner(), item.getId());
    }

    @Override
    public Item update(Item item) {
        Item validatedItem = validateForUpdate(item);
        items.put(validatedItem.getId(), validatedItem);
        return get(validatedItem.getOwner(), item.getId());
    }

    @Override
    public Item get(Long userId, Long itemId) {
        if (items.containsKey(itemId)) {
            return items.get(itemId);
        }
        throw new NotFoundException("Предмет с id " + id + " не найден!");
    }

    @Override
    public Item delete(long itemId) {
        if (items.containsKey(itemId)) {
            Item item = items.get(itemId);
            owners.get(item.getOwner()).remove(item.getId());
            if (owners.get(item.getOwner()).isEmpty()) {
                owners.remove(item.getOwner());
            }
            return item;
        }
        throw new NotFoundException("Ошибка удаления предмета " + id + "!");
    }

    @Override
    public List<Item> getAllByUser(long userId) {
        if (owners.containsKey(userId)) {
            return owners.get(userId).stream()
                    .map(items::get)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Item> getAll() {
        return new ArrayList<>(items.values());
    }

    private Item validateForUpdate(Item item) {
        Item currentItem = get(item.getOwner(), item.getId());
        if (!currentItem.getOwner().equals(item.getOwner())) {
            throw new NotFoundException("Не найден пользователь с id " + item.getOwner() + " для вещи " + item.getId());
        }
        if (item.getName() == null) {
            item.setName(currentItem.getName());
        }
        if (item.getDesc() == null) {
            item.setDesc(currentItem.getDesc());
        }
        if (item.getAvailable() == null) {
            item.setAvailable(currentItem.getAvailable());
        }
        return item;
    }
}
