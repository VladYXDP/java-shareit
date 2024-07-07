package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.AlreadyExistException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Override
    public User add(User user) {
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistException("Ошибка создания пользователя с email " + user.getEmail());
        }
        return usersRepository.save(user);
    }

    @Override
    public User update(User user) {
        User currentUser = usersRepository.getUserById(user.getId());
        if (user.getEmail() == null) {
            user.setEmail(currentUser.getEmail());
        } else {
            if (usersRepository.existsByEmail(user.getEmail()) && user.getEmail().equals(currentUser.getEmail())) {
                throw new AlreadyExistException("Ошибка обновления пользователя с email " + user.getEmail());
            }
        }
        if (user.getName() == null) {
            user.setName(currentUser.getName());
        }
        return usersRepository.save(user);
    }

    @Override
    public User get() {
        return null;
    }

    @Override
    public User delete() {
        return null;
    }
}
