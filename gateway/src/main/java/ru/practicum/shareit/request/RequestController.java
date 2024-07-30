package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody RequestDto dto) {
        return requestClient.add(dto);
    }

    @GetMapping
    public ResponseEntity<Object> getMyAll(@Positive @RequestHeader(value = "X-Sharer-User-Id") Long userId) {
        return requestClient.get(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllAnotherUser(@Positive @RequestHeader(value = "X-Sharer-User-Id") Long userId,
                                                    @RequestParam(name = "from", required = true, defaultValue = "0") Long from,
                                                    @RequestParam(name = "size", required = false) Long size) {
        return requestClient.getAllAnotherUser(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getById(@Positive @PathVariable Long requestId) {
        return requestClient.getById(requestId);
    }
}
