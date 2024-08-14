package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final UserService userService;

    public Request add(Request request) {
        request.setCreated(LocalDateTime.now());
        return requestRepository.save(request);
    }

    public List<Request> get(Long userId) {
        User user = userService.get(userId);
        return requestRepository.findAllByRequestorId(user);
    }

    public Request getById(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() -> {
            throw new NotFoundException("Запроса с id " + requestId + " не существует!");
        });
    }
}
