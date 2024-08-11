package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mockito;
import ru.practicum.shareit.exceptions.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@Transactional
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@TestPropertySource(properties = "spring.datasource.url=jdbc:postgresql://localhost:5432/test")
//@SpringJUnitConfig(UserServiceImpl.class)
public class UserServiceTest {

    private UserServiceImpl userService;
    private UsersRepository usersRepository;

    static User userWithoutId;
    static User userWithId;

    @BeforeAll
    static void initUser() {
        userWithoutId = User.builder().email("test@email.ru").name("test").build();
        userWithId = User.builder().id(1L).email("test@email.ru").name("test").build();
    }

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        when(usersRepository.save(any(User.class))).thenReturn(userWithId);
        when(usersRepository.getUserById(anyLong())).thenReturn(Optional.of(userWithId));
        when(usersRepository.findAll()).thenReturn(Collections.emptyList());
        wh
        userService = new UserServiceImpl(usersRepository);
    }

    @Test
    void add() {
        User user = userService.add(userWithoutId);
        Assertions.assertNotNull(user);
        verify(usersRepository, times(1)).save(userWithoutId);
    }

    @Test
    void update() {
        User user = userService.update(userWithId);
        Assertions.assertNotNull(user);
        verify(usersRepository, times(1)).save(userWithId);
    }

    @Test
    void get() {
        User user = userService.get(1L);
        Assertions.assertNotNull(user);
        verify(usersRepository, times(1)).getUserById(1L);
    }

    @Test
    void delete() {
        userService.delete(1L);
        verify(usersRepository, times(1)).delete(userWithId);
    }

    @Test
    void findAll() {
        List<User> user = userService.findAll();
        Assertions.assertNotNull(user);
        verify(usersRepository, times(1)).findAll();
    }
}
