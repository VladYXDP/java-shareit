package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final ItemStorage itemStorage;

    public User add(User user) {
        return userStorage.add(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User get(long id) {
        return userStorage.get(id);
    }

    public User delete(long id) {
        List<Item> itemIds = itemStorage.getAllByUser(id);
        itemIds.forEach(item -> itemStorage.delete(item.getId()));
        return userStorage.delete(id);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }
}
