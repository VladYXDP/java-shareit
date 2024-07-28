package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.ValidationException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemTransfer itemTransfer;
    private final CommentTransfer commentTransfer;

    @Valid
    @PostMapping
    public ItemDto add(@Positive @RequestHeader(value = "X-Sharer-User-Id") Long userId,
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
        dto.setOwnerId(userId);
        return itemTransfer.toDto(itemService.add(itemTransfer.toItemCreate(dto)));
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@Positive @PathVariable Long itemId,
                          @Valid @RequestBody ItemDto dto,
                          @Positive @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        dto.setId(itemId);
        dto.setOwnerId(userId);
        return itemTransfer.toDto(itemService.update(itemTransfer.toItem(dto)));
    }

    @GetMapping("/{itemId}")
    public ItemDto get(@Positive @PathVariable("itemId") Long itemId,
                       @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemTransfer.toDto(itemService.get(itemId, userId));
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@Positive @PathVariable("itemId") Long itemId,
                                 @Positive @RequestHeader("X-Sharer-User-Id") Long userId,
                                 @Valid @RequestBody CommentDto body) {
        return commentTransfer.toDto(itemService.addComment(commentTransfer.toComment(body), userId, itemId));
    }

    @GetMapping
    public List<ItemDto> getAllByUser(@Positive @RequestHeader("X-Sharer-User-Id") Long id) {
        return itemService.findAllByUserId(id).stream()
                .map(itemTransfer::toDto)
                .sorted(Comparator.comparing(ItemDto::getId))
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<ItemDto> search(@PathParam("text") String text) {
        return itemService.search(text).stream()
                .map(itemTransfer::toDto)
                .collect(Collectors.toList());
    }
}