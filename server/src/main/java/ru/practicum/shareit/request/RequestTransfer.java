package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserTransfer;

import java.util.List;

@Component
public class RequestTransfer {

    public List<RequestDto> toDto(List<Request> requests) {
        return requests.stream().map(this::toDto).toList();
    }

    public RequestDto toDto(Request request) {
        if (request.getItem() == null) {
            return RequestDto.builder()
                    .id(request.getId())
                    .description(request.getDescription())
                    .created(request.getCreated())
                    .build();
        } else {
            return RequestDto.builder()
                    .id(request.getId())
                    .description(request.getDescription())
                    .created(request.getCreated())
                    .items(request.getItem().stream()
                            .map(it -> new ItemsRequestDto(it.getName()))
                            .toList())
                    .build();
        }
    }

    public Request toEntity(RequestDto dto) {
        return Request.builder()
                .description(dto.getDescription())
                .requestorId(User.builder().id(dto.getRequestorId()).build())
                .build();
    }
}
