package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.exception.ValidationException;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemClient itemClient;

    @Valid
    @PostMapping
    public ResponseEntity<Object> add(@Positive @RequestHeader(value = "X-Sharer-User-Id") Long userId,
                                      @RequestBody ItemDto dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new ValidationException("Ошибка добавление предмета! Недопустимое значение наименования!");
        }
        if (dto.getAvailable() == null) {
            throw new ValidationException("Ошибка добавление предмета! Недопустимое значение доступности!");
        }
        if (dto.getDescription() == null || dto.getDescription().isEmpty()) {
            throw new ValidationException("Ошибка добавление предмета! Недопустимое значение описания!");
        }
        return itemClient.add(dto, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@Positive @PathVariable Long itemId,
                                         @Valid @RequestBody ItemDto dto,
                                         @Positive @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        dto.setId(itemId);
        return itemClient.update(dto, userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> get(@Positive @PathVariable("itemId") Long itemId,
                                      @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.get(itemId, userId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@Positive @PathVariable("itemId") Long itemId,
                                             @Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                             @Valid @RequestBody CommentDto body) {
        return itemClient.addComment(body, userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllByUser(@Positive @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.getAllByUser(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@PathParam("text") String text) {
        return itemClient.search(text);
    }
}