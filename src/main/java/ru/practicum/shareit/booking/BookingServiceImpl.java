package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ItemAvailableException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public Booking add(Booking booking) {
        User user = userService.get(booking.getUserId());
        Item item = itemService.get(booking.getItemId(), booking.getUserId());
        if (item.getAvailable()) {
            booking.setItem(item);
            booking.setUser(user);
            return bookingRepository.save(booking);
        } else {
            throw new ItemAvailableException("Предмет с id " + item.getId() + " недоступен для бронирования!");
        }
    }
}
