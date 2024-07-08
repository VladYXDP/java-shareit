package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User,Long> {

    Optional<User> getUserById(Long id);

    User save(User user);

    User findUserByEmail(String email);

    Boolean existsByEmail(String email);
}
