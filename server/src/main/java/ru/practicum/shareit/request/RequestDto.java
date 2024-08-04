package ru.practicum.shareit.request;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
public class RequestDto {
    private Long id;
    private String description;
    private Long requestorId;
}
