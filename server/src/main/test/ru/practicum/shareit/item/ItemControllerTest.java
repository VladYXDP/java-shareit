package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc
public class ItemControllerTest {

    @MockBean
    private ItemService itemService;
    @MockBean
    private ItemTransfer itemTransfer;
    @MockBean
    private CommentTransfer commentTransfer;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper parser;

    @Test
    void add() throws Exception {
        Item itemReq = Item.builder()
                .description("desc test")
                .name("test")
                .available(false)
                .build();
        Item itemResp = Item.builder()
                .id(1L)
                .description("desc test")
                .name("test")
                .available(false)
                .build();
        ItemDto dtoReq = ItemDto.builder()
                .description("desc test")
                .name("test")
                .build();
        ItemDto dtoResp = ItemDto.builder()
                .id(1L)
                .description("desc test")
                .name("test")
                .available(false)
                .build();

        when(itemService.add(any(Item.class))).thenReturn(itemResp);
        when(itemTransfer.toItemCreate(any(ItemDto.class))).thenReturn(itemReq);
        when(itemTransfer.toDto(any(Item.class))).thenReturn(dtoResp);

        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .content(parser.writeValueAsString(dtoReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(parser.writeValueAsString(itemTransfer.toDto(itemResp))));
    }

    @Test
    void update() throws Exception {
        Item itemReq = Item.builder()
                .id(1L)
                .description("desc update test")
                .name("update test")
                .available(false)
                .build();
        Item itemResp = Item.builder()
                .id(1L)
                .description("desc update test")
                .name("update test")
                .available(false)
                .build();
        ItemDto dtoReq = ItemDto.builder()
                .description("desc update test")
                .name("update test")
                .build();
        ItemDto dtoResp = ItemDto.builder()
                .id(1L)
                .description("desc update test")
                .name("update test")
                .available(false)
                .build();

        when(itemService.update(any(Item.class))).thenReturn(itemResp);
        when(itemTransfer.toItem(any(ItemDto.class))).thenReturn(itemReq);
        when(itemTransfer.toDto(any(Item.class))).thenReturn(dtoResp);

        mockMvc.perform(patch("/items/{itemId}", 1L)
                        .header("X-Sharer-User-Id", 1L)
                        .content(parser.writeValueAsString(dtoReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(parser.writeValueAsString(itemTransfer.toDto(itemResp))));
    }

    @Test
    void get() throws Exception {
        Item itemResp = Item.builder()
                .id(1L)
                .description("desc test")
                .name("test")
                .available(false)
                .build();
        ItemDto dtoResp = ItemDto.builder()
                .id(1L)
                .description("desc test")
                .name("test")
                .available(false)
                .build();

        when(itemService.get(anyLong(), anyLong())).thenReturn(itemResp);
        when(itemTransfer.toDto(any(Item.class))).thenReturn(dtoResp);
        mockMvc.perform(MockMvcRequestBuilders.get( "/items/{itemId}", 1L)
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(parser.writeValueAsString(itemTransfer.toDto(itemResp))));
    }

    @Test
    void addComment() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        CommentDto commentDtoReq = CommentDto.builder()
                .created(LocalDateTime.now())
                .authorName("author")
                .text("text")
                .build();
        User user = User.builder()
                .id(1L)
                .email("test@email.ru")
                .name("author")
                .build();
        Item item = Item.builder()
                .id(1L)
                .ownerId(1L)
                .name("item")
                .description("desc")
                .build();
        Comment comment = Comment.builder()
                .id(1L)
                .text("text")
                .item(item)
                .created(now)
                .author(user)
                .build();
        CommentDto commentDtoResp = CommentDto.builder()
                .id(1L)
                .item(item)
                .created(now)
                .created(LocalDateTime.now())
                .authorName("author")
                .text("text")
                .build();

        when(itemService.addComment(any(Comment.class) ,anyLong(), anyLong())).thenReturn(comment);
        when(commentTransfer.toComment(any(CommentDto.class))).thenReturn(comment);
        when(commentTransfer.toDto(any(Comment.class))).thenReturn(commentDtoResp);
        mockMvc.perform(post( "/items/{itemId}/comment", 1L)
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(parser.writeValueAsString(commentDtoReq)))
                .andExpect(status().isOk())
                .andExpect(content().json(parser.writeValueAsString(commentTransfer.toDto(comment))));
    }

    @Test
    void getAllByUser() throws Exception {
        when(itemService.findAllByUserId(any(Long.class))).thenReturn(Collections.emptySet());
        when(itemTransfer.toDto(any(Item.class))).thenReturn(any(ItemDto.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/items")
                .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void search() throws Exception {
        when(itemService.search(any(String.class))).thenReturn(Collections.emptySet());
        when(itemTransfer.toDto(any(Item.class))).thenReturn(any(ItemDto.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/items/search")
                        .header("X-Sharer-User-Id", 1L)
                        .queryParam("test", "акк"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
