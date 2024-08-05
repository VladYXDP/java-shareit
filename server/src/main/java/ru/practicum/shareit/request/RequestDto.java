package ru.practicum.shareit.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private Long id;
    private String description;
    private LocalDateTime created;
    private Long requestorId;

    private List<ItemsRequestDto> items;
}
