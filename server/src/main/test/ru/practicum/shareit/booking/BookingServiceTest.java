package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    private BookingServiceImpl bookingService;
    private BookingRepository bookingRepository;
    private ItemService itemService;
    private UserService userService;

    static User user;
    static User owner;
    static Booking bookingResp;
    static Booking bookingReq;
    static Item itemWithId;
    static Item availableItem;

    @BeforeAll
    static void init() {
        LocalDateTime now = LocalDateTime.now();
        owner = User.builder()
                .id(2L)
                .name("name")
                .build();
        availableItem = Item.builder()
                .id(1L)
                .description("desc")
                .owner(owner)
                .available(true)
                .build();
        itemWithId = Item.builder()
                .id(1L)
                .description("desc")
                .owner(owner)
                .available(false)
                .build();
        user = User.builder()
                .id(1L)
                .name("name")
                .items(Set.of(itemWithId))
                .build();
        bookingResp = Booking.builder()
                .id(1L)
                .startDate(now)
                .endDate(now.plusDays(2))
                .status(BookingStatus.WAITING)
                .item(itemWithId)
                .booker(owner)
                .build();
        bookingReq = Booking.builder()
                .startDate(now)
                .endDate(now.plusDays(2))
                .item(itemWithId)
                .booker(user)
                .itemId(1L)
                .userId(1L)
                .build();
    }

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        when(userService.get(anyLong())).thenReturn(user);

        itemService = Mockito.mock(ItemService.class);
        when(itemService.get(anyLong(), anyLong())).thenReturn(availableItem);
        when(itemService.findAllByUserId(anyLong())).thenReturn(Collections.emptySet());

        bookingRepository = Mockito.mock(BookingRepository.class);
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingResp);
        when(bookingRepository.getBookingById(anyLong())).thenReturn(Optional.of(bookingResp));
        when(bookingRepository.findAll()).thenReturn(Collections.emptyList());
        when(bookingRepository.findAllByBookerId(anyLong())).thenReturn(Collections.emptyList());
        when(bookingRepository.findAllByItem(any(Item.class))).thenReturn(Collections.emptyList());

        bookingService = new BookingServiceImpl(bookingRepository, itemService, userService);
    }

    @Test
    void add() {
        Booking booking = bookingService.add(bookingReq);
        Assertions.assertNotNull(booking);
        verify(bookingRepository, times(1)).save(bookingReq);
    }

    @Test
    void approved() {
        Booking booking = bookingService.approved(1L, 1L, true);
        Assertions.assertNotNull(booking);
        verify(bookingRepository, times(1)).save(bookingResp);
    }

    @Test
    void get() {
        Booking booking = bookingService.get(1L, 1L);
        Assertions.assertNotNull(booking);
        verify(bookingRepository, times(1)).getBookingById(1L);
    }

    @Test
    void getByOwner() {
        List<Booking> bookings = bookingService.getByOwner(1L, BookingControllerStates.CURRENT);
        Assertions.assertTrue(bookings.isEmpty());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void getAllByUser() {
        List<Booking> bookings = bookingService.getByOwner(1L, BookingControllerStates.CURRENT);
        Assertions.assertTrue(bookings.isEmpty());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void getAllByBookingItem() {
        List<Booking> bookings = bookingService.getAllBookingByItem(itemWithId);
        Assertions.assertTrue(bookings.isEmpty());
        verify(bookingRepository, times(1)).findAllByItem(itemWithId);
    }
}
