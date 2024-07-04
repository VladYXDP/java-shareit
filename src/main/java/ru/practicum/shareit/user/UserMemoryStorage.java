package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.AlreadyExistException;
import ru.practicum.shareit.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserMemoryStorage implements UserStorage {

    private long id = 1;
    private final Map<Long, User> users = new HashMap<>();
    private final List<String> emails = new ArrayList<>();

    @Override
    public User add(User user) {
        if (emails.contains(user.getEmail())) {
            throw new AlreadyExistException("Пользователь с email " + user.getEmail() + " уже существует!");
        }
        user.setId(id++);
        emails.add(user.getEmail());
        users.put(user.getId(), user);
        return get(user.getId());
    }

    @Override
    public User update(User user) {
        User oldUser = get(user.getId());
        if (user.getEmail() != null) {
            if (!oldUser.getEmail().equals(user.getEmail())) {
                if (emails.contains(user.getEmail())) {
                    throw new AlreadyExistException("Ошибка обновления email пользователя " + oldUser.getName() + " " +
                            "на " + user.getEmail());
                }
                emails.remove(oldUser.getEmail());
                emails.add(user.getEmail());
            }
        } else {
            user.setEmail(oldUser.getEmail());
        }
        if (user.getName() == null) {
            user.setName(oldUser.getName());
        }
        users.put(user.getId(), user);
        return get(user.getId());
    }

    @Override
    public User get(long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        throw new NotFoundException("Пользователь с id " + id + " не найден!");
    }

    @Override
    public User delete(long id) {
        emails.remove(get(id).getEmail());
        return users.remove(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }
}
