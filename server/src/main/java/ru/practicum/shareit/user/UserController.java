package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserTransfer userTransfer;
    private final UserService userService;

    @PostMapping
    public UserDto add(@RequestBody UserDto dto) {
        if (dto.getEmail() == null) {
            throw new ValidationException("Поле email обязательно для заполнения!");
        }
        User user = userService.add(userTransfer.toUserCreate(dto));
        return userTransfer.toDto(user);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody UserDto userDto,
                          @PathVariable Long userId) {
        userDto.setId(userId);
        return userTransfer.toDto(userService.update(userTransfer.toUser(userDto)));
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable Long userId) {
        return userTransfer.toDto(userService.get(userId));
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.findAll().stream()
                .map(userTransfer::toDto)
                .collect(Collectors.toList());
    }
}