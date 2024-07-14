package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserServiceImpl;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final UserServiceImpl userService;
    private final ItemsRepository itemsRepository;

    @Override
    public Item add(Item item) {
        User user = userService.get(item.getOwnerId());
        item.setOwner(user);
        item = itemsRepository.save(item);
        return item;
    }

    @Override
    public Item update(Item item) {
        Item currentItem = itemsRepository.getItemById(item.getId());
        User user = userService.get(item.getOwnerId());
        if (user.getId() == currentItem.getOwner().getId()) {
            item.setOwner(currentItem.getOwner());
            item = itemsRepository.save(ItemValidate.validate(item, currentItem));
            return item;
        } else {
            throw new NotFoundException("Ошибка обновления предмета! Не совпадает пользователь!");
        }
    }

    @Override
    public Item get(Long itemId, Long userId) {
        userService.get(userId);
        Item item = itemsRepository.getItemById(itemId);
        if (item != null) {
            return item;
        } else {
            throw new NotFoundException("Предмет с id " + itemId + " не найден!");
        }
    }

    @Override
    public Item delete() {
        return null;
    }

    @Override
    public Set<Item> search(String search) {
        if(search.isEmpty()) {
            return new HashSet<>();
        }
        Set<Item> items = itemsRepository.search("%" + search + "%","%" + search + "%");
        return items;
    }

    @Override
    public Set<Item> findAllByUserId(Long userId) {
        return userService.get(userId).getItems();
    }
}
