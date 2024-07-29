package ru.practicum.shareit.comment;

import jakarta.validation.constraints.Size;
import lombok.*;
import ru.practicum.shareit.item.ItemDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private long id;
    @Size(min = 1)
    private String text;
    private ItemDto item;
    private String authorName;
    private LocalDateTime created;
}
