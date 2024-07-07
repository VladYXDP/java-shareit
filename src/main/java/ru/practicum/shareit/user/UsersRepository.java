package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User,Long> {

    User getUserById(Long id);

    User save(User user);

    User findUserByEmail(String email);

    Boolean existsByEmail(String email);
}
