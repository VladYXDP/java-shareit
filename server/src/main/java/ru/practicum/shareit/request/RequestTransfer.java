package ru.practicum.shareit.request;

import org.springframework.stereotype.Component;

@Component
public class RequestTransfer {

    public RequestDto toDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .build();
    }

    public Request toEntity(RequestDto dto) {
        return Request.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .build();
    }
}
