package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.request.RequestService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ItemServiceTest {

    private ItemServiceImpl itemService;
    private UserServiceImpl userService;
    private ItemsRepository itemsRepository;
    private BookingRepository bookingRepository;
    private CommentRepository commentRepository;
    private RequestService requestService;

    static User user;
    static Item itemWithoutId;
    static Item itemWithId;
    static Comment commentWithoutId;
    static Comment commentWithId;
    static Booking booking;
    static Item itemWithIdUser2;

    @BeforeAll
    static void init() {
        LocalDateTime now = LocalDateTime.now();
        user = User.builder()
                .id(1L)
                .name("name")
                .items(Collections.emptySet())
                .email("test@email.ru")
                .build();
        itemWithoutId = Item.builder()
                .description("desc")
                .name("name")
                .ownerId(1L)
                .available(true)
                .build();
        itemWithId = Item.builder()
                .id(1L)
                .description("desc")
                .name("name")
                .ownerId(1L)
                .owner(user)
                .available(true)
                .build();
        itemWithIdUser2 = Item.builder()
                .id(2L)
                .description("desc")
                .name("name")
                .ownerId(2L)
                .owner(user)
                .available(true)
                .build();
        commentWithoutId = Comment.builder()
                .text("text")
                .created(now)
                .item(itemWithId)
                .author(user)
                .build();
        commentWithId = Comment.builder()
                .id(1L)
                .created(now)
                .item(itemWithId)
                .author(user)
                .text("text")
                .build();
        booking = Booking.builder()
                .id(1L)
                .itemId(2L)
                .status(BookingStatus.APPROVED)
                .startDate(now)
                .endDate(now.plusDays(1))
                .userId(2L)
                .item(itemWithIdUser2)
                .booker(user)
                .build();

    }

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserServiceImpl.class);
        when(userService.get(anyLong())).thenReturn(user);

        itemsRepository = Mockito.mock(ItemsRepository.class);
        when(itemsRepository.save(any(Item.class))).thenReturn(itemWithId);
        when(itemsRepository.getItemById(anyLong())).thenReturn(itemWithId);
        when(itemsRepository.search(anyString(), anyString())).thenReturn(Collections.emptySet());

        bookingRepository = Mockito.mock(BookingRepository.class);
        when(bookingRepository.findAllByItem(itemWithId)).thenReturn(Collections.emptyList());
        when(bookingRepository.findAllByBookerIdAndItem(anyLong(), any(Item.class))).thenReturn(List.of(booking));
        when(bookingRepository.findLastBooking(any(LocalDateTime.class), anyLong())).thenReturn(Optional.empty());
        when(bookingRepository.findNextBooking(any(LocalDateTime.class), anyLong())).thenReturn(Optional.empty());


        commentRepository = Mockito.mock(CommentRepository.class);
        when(commentRepository.findAllByItemId(anyLong())).thenReturn(Collections.emptyList());
        when(commentRepository.save(any(Comment.class))).thenReturn(commentWithId);

        requestService = Mockito.mock(RequestService.class);
        itemService = new ItemServiceImpl(userService, itemsRepository, bookingRepository, commentRepository, requestService);
    }

    @Test
    void add() {
        Item item = itemService.add(itemWithoutId);
        Assertions.assertNotNull(item);
        verify(itemsRepository, times(1)).save(itemWithoutId);
    }

    @Test
    void update() {
        Item item = itemService.update(itemWithId);
        Assertions.assertNotNull(item);
        verify(itemsRepository, times(1)).getItemById(1L);
    }

    @Test
    void get() {
        Item item = itemService.get(1L, 1L);
        Assertions.assertNotNull(item);
        verify(itemsRepository, times(1)).getItemById(1L);
    }

    @Test
    void search() {
        Set<Item> items = itemService.search("Test");
        Assertions.assertTrue(items.isEmpty());
        verify(itemsRepository, times(1)).search("%Test%", "%Test%");
    }

    @Test
    void addComment() {
        Comment comment = itemService.addComment(commentWithoutId, 1L, 1L);
        Assertions.assertNotNull(comment);
        verify(commentRepository, times(1)).save(commentWithoutId);
    }

    @Test
    void findAllByUserId() {
        Set<Item> items = itemService.findAllByUserId(1L);
        Assertions.assertTrue(items.isEmpty());
        verify(userService, times(1)).get(1L);
    }
}
