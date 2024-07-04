package ru.practicum.shareit.user;

import java.util.List;

public interface UserStorage {

    User add(User user);

    User update(User dto);

    User get(long id);

    User delete(long id);

    List<User> getAll();
}
