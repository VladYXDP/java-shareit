package ru.practicum.shareit.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestDto {
    @Positive
    private Long id;
    @Size(min = 1)
    private String description;
    @Positive
    private Long requestorId;
}
