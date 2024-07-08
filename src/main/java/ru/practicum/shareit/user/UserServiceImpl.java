package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.AlreadyExistException;
import ru.practicum.shareit.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Override
    public User add(User user) {
//        if (usersRepository.existsByEmail(user.getEmail())) {
//            throw new AlreadyExistException("Ошибка создания пользователя с email " + user.getEmail());
//        }
        try {
            User user1 = usersRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
        }
        return usersRepository.save(user);
    }

    @Override
    public User update(User user) {
        User currentUser = get(user.getId());
        if (user.getEmail() == null) {
            user.setEmail(currentUser.getEmail());
        } else {
            if (usersRepository.existsByEmail(user.getEmail()) && !user.getEmail().equals(currentUser.getEmail())) {
                throw new AlreadyExistException("Ошибка обновления пользователя с email " + user.getEmail());
            }
        }
        if (user.getName() == null) {
            user.setName(currentUser.getName());
        }
        return usersRepository.save(user);
    }

    @Override
    public User get(Long userId) {
        Optional<User> userOpt = usersRepository.getUserById(userId);
        if (userOpt.isEmpty()) {
            throw new NotFoundException("Ошибка получения пользователя с id " + userId);
        }
        return userOpt.get();
    }

    @Override
    public void delete(Long userId) {
        usersRepository.deleteById(userId);
    }

    @Override
    public List<User> getAll() {
        return usersRepository.findAll();
    }
}
