package ru.practicum.shareit.item;

import jakarta.validation.constraints.Size;
import lombok.*;

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
    private Item item;
    private String authorName;
    private LocalDateTime created;
}
