package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RequestController.class)
@AutoConfigureMockMvc
public class RequestControllerTest {

    @MockBean
    private RequestService requestService;
    @MockBean
    private RequestTransfer requestTransfer;
    @Autowired
    private ObjectMapper parser;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void add() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder().id(1L).build();
        RequestDto requestDtoReq = RequestDto.builder()
                .description("desc")
                .created(now)
                .requestorId(1L)
                .build();
        RequestDto requestDtoResp = RequestDto.builder()
                .id(1L)
                .created(now)
                .items(Collections.emptyList())
                .description("desc")
                .requestorId(1L)
                .build();
        Request requestReq = Request.builder()
                .created(now)
                .description("desc")
                .requestorId(user)
                .build();
        Request requestResp = Request.builder()
                .id(1L)
                .description("desc")
                .created(now)
                .item(Collections.emptyList())
                .requestorId(user)
                .build();
        when(requestService.add(any(Request.class))).thenReturn(requestResp);
        when(requestTransfer.toDto(any(Request.class))).thenReturn(requestDtoResp);
        when(requestTransfer.toEntity(any(RequestDto.class))).thenReturn(requestReq);
        mockMvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(parser.writeValueAsString(requestDtoReq)))
                .andExpect(status().isOk())
                .andExpect(content().json(parser.writeValueAsString(requestDtoResp)));
    }

    @Test
    void getMyAll() throws Exception {
        when(requestService.get(any(Long.class))).thenReturn(Collections.emptyList());
        when(requestTransfer.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getById() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder().id(1L).build();
        RequestDto requestDtoResp = RequestDto.builder()
                .id(1L)
                .created(now)
                .items(Collections.emptyList())
                .description("desc")
                .requestorId(1L)
                .build();
        Request requestResp = Request.builder()
                .id(1L)
                .description("desc")
                .created(now)
                .item(Collections.emptyList())
                .requestorId(user)
                .build();
        when(requestService.getById(any(Long.class))).thenReturn(requestResp);
        when(requestTransfer.toDto(any(Request.class))).thenReturn(requestDtoResp);
        mockMvc.perform(get("/requests/{requestId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(parser.writeValueAsString(requestDtoResp)));
    }
}
