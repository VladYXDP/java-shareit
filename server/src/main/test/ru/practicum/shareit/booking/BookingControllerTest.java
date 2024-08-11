package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.item.ItemBookingDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserBookingDto;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc
public class BookingControllerTest {

    @MockBean
    private BookingService bookingService;
    @MockBean
    private BookingTransfer bookingTransfer;
    @Autowired
    private ObjectMapper parser;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void add() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        BookingDto dtoReq = BookingDto.builder()
                .itemId(1L)
                .start(now)
                .end(now.plusDays(2))
                .build();
        CreateBookingDto createBookingDto = CreateBookingDto.builder()
                .id(1L)
                .start(now)
                .end(now.plusDays(2))
                .status(BookingStatus.APPROVED)
                .itemBookingDto(new ItemBookingDto(1L, "test"))
                .userBookingDto(new UserBookingDto(1L))
                .build();
        Booking bookingResp = Booking.builder()
                .id(1L)
                .startDate(now)
                .endDate(now.plusDays(2))
                .status(BookingStatus.APPROVED)
                .booker(User.builder().id(2L).name("name").email("1@email.ru").build())
                .build();

        when(bookingService.add(any(Booking.class))).thenReturn(bookingResp);
        when(bookingTransfer.toCreateDto(any(Booking.class))).thenReturn(createBookingDto);
        when(bookingTransfer.toEntity(any(BookingDto.class))).thenReturn(bookingResp);
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .content(parser.writeValueAsString(dtoReq)))
                .andExpect(status().isOk())
                .andExpect(content().json(parser.writeValueAsString(createBookingDto)));
    }

    @Test
    void approved() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        BookingDto dtoReq = BookingDto.builder()
                .itemId(1L)
                .start(now)
                .end(now.plusDays(2))
                .build();
        CreateBookingDto createBookingDto = CreateBookingDto.builder()
                .id(1L)
                .start(now)
                .end(now.plusDays(2))
                .status(BookingStatus.APPROVED)
                .itemBookingDto(new ItemBookingDto(1L, "test"))
                .userBookingDto(new UserBookingDto(1L))
                .build();
        Booking bookingResp = Booking.builder()
                .id(1L)
                .startDate(now)
                .endDate(now.plusDays(2))
                .status(BookingStatus.APPROVED)
                .booker(User.builder().id(2L).name("name").email("1@email.ru").build())
                .build();

        when(bookingService.approved(anyLong(), anyLong(), any(Boolean.class))).thenReturn(bookingResp);
        when(bookingTransfer.toCreateDto(any(Booking.class))).thenReturn(createBookingDto);
        when(bookingTransfer.toEntity(any(BookingDto.class))).thenReturn(bookingResp);
        mockMvc.perform(patch("/bookings/{bookingId}", 1L)
                        .queryParam("approved", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .content(parser.writeValueAsString(dtoReq)))
                .andExpect(status().isOk())
                .andExpect(content().json(parser.writeValueAsString(createBookingDto)));
    }


    @Test
    void get() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        BookingDto dtoReq = BookingDto.builder()
                .itemId(1L)
                .start(now)
                .end(now.plusDays(2))
                .build();
        CreateBookingDto createBookingDto = CreateBookingDto.builder()
                .id(1L)
                .start(now)
                .end(now.plusDays(2))
                .status(BookingStatus.APPROVED)
                .itemBookingDto(new ItemBookingDto(1L, "test"))
                .userBookingDto(new UserBookingDto(1L))
                .build();
        Booking bookingResp = Booking.builder()
                .id(1L)
                .startDate(now)
                .endDate(now.plusDays(2))
                .status(BookingStatus.APPROVED)
                .booker(User.builder().id(2L).name("name").email("1@email.ru").build())
                .build();

        when(bookingService.get(anyLong(), anyLong())).thenReturn(bookingResp);
        when(bookingTransfer.toCreateDto(any(Booking.class))).thenReturn(createBookingDto);
        when(bookingTransfer.toEntity(any(BookingDto.class))).thenReturn(bookingResp);
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/{bookingId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1L)
                        .content(parser.writeValueAsString(dtoReq)))
                .andExpect(status().isOk())
                .andExpect(content().json(parser.writeValueAsString(createBookingDto)));
    }

    @Test
    void getByOwner() throws Exception {
        when(bookingService.getByOwner(anyLong(), eq(BookingControllerStates.CURRENT))).thenReturn(Collections.emptyList());
        when(bookingTransfer.toListCreateDto(Collections.emptyList())).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/owner", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("state", "CURRENT")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllByUser() throws Exception {
        when(bookingService.getAllByUser(anyLong(), eq(BookingControllerStates.CURRENT))).thenReturn(Collections.emptyList());
        when(bookingTransfer.toListCreateDto(Collections.emptyList())).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("state", "CURRENT")
                        .header("X-Sharer-User-Id", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
