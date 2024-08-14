package ru.practicum.shareit.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RequestServiceTest {

    private UserService userService;
    private RequestService requestService;
    private RequestRepository requestRepository;

    static Request reqWithId;
    static Request reqWithoutId;
    static User user;

    @BeforeAll
    static void init() {
        LocalDateTime now = LocalDateTime.now();
        user = User.builder()
                .id(1L)
                .email("test@emial.ru")
                .name("name")
                .build();
        reqWithId = Request.builder()
                .id(1L)
                .description("desc")
                .created(now)
                .requestorId(user)
                .item(Collections.emptyList())
                .build();
        reqWithoutId = Request.builder()
                .description("desc")
                .created(now)
                .requestorId(user)
                .build();
    }

    @BeforeEach
    void setUp() {
        requestRepository = Mockito.mock(RequestRepository.class);
        when(requestRepository.save(any(Request.class))).thenReturn(reqWithId);
        when(requestRepository.findAllByRequestorId(any(User.class))).thenReturn(Collections.emptyList());
        when(requestRepository.findById(anyLong())).thenReturn(Optional.of(reqWithId));
        userService = Mockito.mock(UserService.class);
        when(userService.get(anyLong())).thenReturn(user);
        requestService = new RequestService(requestRepository, userService);
    }

    @Test
    void add() {
        Request request = requestService.add(reqWithoutId);
        Assertions.assertNotNull(request);
        verify(requestRepository, times(1)).save(reqWithoutId);
    }

    @Test
    void get() {
        List<Request> requests = requestService.get(1L);
        Assertions.assertNotNull(requests);
        verify(requestRepository, times(1)).findAllByRequestorId(user);
    }

    @Test
    void getById() {
        Request request = requestService.getById(1L);
        Assertions.assertNotNull(request);
        verify(requestRepository, times(1)).findById(1L);
    }
}
