package ru.practicum.shareit.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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
    public UserDto add(@Valid @RequestBody UserDto dto) {
        if (dto.getEmail() == null) {
            throw new ValidationException("Поле email обязательно для заполнения!");
        }
        User user = userService.add(userTransfer.toUserCreate(dto));
        return userTransfer.toDto(user);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@Valid @RequestBody UserDto userDto,
                          @Positive @PathVariable Long userId) {
        userDto.setId(userId);
        return userTransfer.toDto(userService.update(userTransfer.toUser(userDto)));
    }

 /*   @GetMapping("/{userId}")
    public UserDto get(@Positive @PathVariable Long userId) {
        return userTransfer.toDto(userService.get(userId));
    }

    @DeleteMapping("/{userId}")
    public UserDto delete(@Positive @PathVariable Long userId) {
        return userTransfer.toDto(userService.delete(userId));
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll().stream()
                .map(userTransfer::toDto)
                .collect(Collectors.toList());
    }*/
}