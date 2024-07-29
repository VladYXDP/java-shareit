package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody UserDto dto) {
        if (dto.getEmail() == null) {
            throw new ValidationException("Поле email обязательно для заполнения!");
        }
        return userClient.add(dto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> update(@Valid @RequestBody UserDto userDto,
                                         @Positive @PathVariable Long userId) {
        userDto.setId(userId);
        return userClient.update(userDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> get(@Positive @PathVariable Long userId) {
        return userClient.get(userId);
    }

    @DeleteMapping("/{userId}")
    public void delete(@Positive @PathVariable Long userId) {
        userClient.delete(userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return userClient.getAll();
    }
}
