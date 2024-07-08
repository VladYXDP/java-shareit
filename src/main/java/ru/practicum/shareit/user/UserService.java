package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {

    User add(User user);

    User update(User user);

    User get(Long userId);

    void delete(Long userId);

    List<User> getAll();
}
