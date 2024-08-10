package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserServiceTest {

    @MockBean
    private UserService userService;
    @MockBean
    private UserTransfer userTransfer;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper parser;

    @Test
    void add() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("main@email.ru")
                .name("main")
                .build();
        UserDto userDtoReq = UserDto.builder()
                .email("main@email.ru")
                .name("main")
                .build();
        UserDto userDtoResp = UserDto.builder()
                .id(1L)
                .email("main@email.ru")
                .name("main")
                .build();
        when(userService.add(any(User.class))).thenReturn(user);
        when(userTransfer.toUserCreate(any(UserDto.class))).thenReturn(User.builder().name(user.getName()).email(user.getEmail()).build());
        when(userTransfer.toDto(any(User.class))).thenReturn(userDtoResp);
        mockMvc.perform(post("/users")
                        .content(parser.writeValueAsString(userDtoReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(parser.writeValueAsString(userTransfer.toDto(user))));
    }

    @Test
    void update() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("main-update@email.ru")
                .name("main-update")
                .build();
        UserDto userDtoReq = UserDto.builder()
                .email("main@email.ru")
                .name("main")
                .build();
        UserDto userDtoResp = UserDto.builder()
                .id(1L)
                .email("main-update@email.ru")
                .name("main-update")
                .build();
        when(userService.update(any(User.class))).thenReturn(user);
        when(userTransfer.toUser(any(UserDto.class))).thenReturn(User.builder().name(user.getName()).email(user.getEmail()).build());
        when(userTransfer.toDto(any(User.class))).thenReturn(userDtoResp);
        mockMvc.perform(patch("/users/{userId}", 1)
                        .content(parser.writeValueAsString(userDtoReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(parser.writeValueAsString(userTransfer.toDto(user))));
    }

    @Test
    void get() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("main@email.ru")
                .name("main")
                .build();
        UserDto userDtoResp = UserDto.builder()
                .id(1L)
                .email("main@email.ru")
                .name("main")
                .build();
        when(userService.get(1L)).thenReturn(user);
        when(userTransfer.toUser(any(UserDto.class))).thenReturn(User.builder().name(user.getName()).email(user.getEmail()).build());
        when(userTransfer.toDto(any(User.class))).thenReturn(userDtoResp);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(parser.writeValueAsString(userTransfer.toDto(user))));
    }

    @Test
    void getAll() throws Exception {
        when(userService.findAll()).thenReturn(Collections.emptyList());
        when(userTransfer.toDto(any(User.class))).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
