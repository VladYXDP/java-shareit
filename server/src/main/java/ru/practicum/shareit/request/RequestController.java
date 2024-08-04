package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final RequestTransfer requestTransfer;

    @PostMapping
    public RequestDto add(@RequestBody RequestDto dto) {
        return requestTransfer.toDto(requestService.add(requestTransfer.toEntity(dto)));
    }

//    @GetMapping
//    public ResponseEntity<Object> getMyAll(@RequestHeader(value = "X-Sharer-User-Id") Long userId) {
//        return requestService.get(userId);
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<Object> getAllAnotherUser(@RequestHeader(value = "X-Sharer-User-Id") Long userId,
//                                                    @RequestParam(name = "from", required = true, defaultValue = "0") Long from,
//                                                    @RequestParam(name = "size", required = false) Long size) {
//        return requestService.getAllAnotherUser(userId, from, size);
//    }
//
//    @GetMapping("/{requestId}")
//    public ResponseEntity<Object> getById(@PathVariable Long requestId) {
//        return requestService.getById(requestId);
//    }
}
