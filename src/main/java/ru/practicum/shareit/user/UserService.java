package ru.practicum.shareit.user;

public interface UserService {

    User add(User user);

    User update(User user);

    User get(Long userId);

    User delete();
}
