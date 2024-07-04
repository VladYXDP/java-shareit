package ru.practicum.shareit.item;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemTransfer itemTransfer;

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
        dto.setOwner(userId);
        return itemTransfer.toDto(itemService.add(itemTransfer.toItem(dto)));
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@PathVariable Long itemId,
                          @Valid @RequestBody ItemDto dto,
                          @Positive @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        dto.setId(itemId);
        dto.setOwner(userId);
        return itemTransfer.toDto(itemService.update(itemTransfer.toItem(dto)));
    }

    @GetMapping("/{itemId}")
    public ItemDto get(@Positive @PathVariable("itemId") Long itemId,
                       @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemTransfer.toDto(itemService.get(userId, itemId));
    }

    @GetMapping
    public List<ItemDto> getAllByUser(@Positive @RequestHeader("X-Sharer-User-Id") Long id) {
        return itemService.getAllByUser(id).stream()
                .map(itemTransfer::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<ItemDto> search(@PathParam("text") String text,
                                @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.search(text).stream()
                .map(itemTransfer::toDto)
                .collect(Collectors.toList());
    }
}