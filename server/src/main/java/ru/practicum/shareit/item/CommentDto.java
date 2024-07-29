package ru.practicum.shareit.item;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private long id;
    private String text;
    private Item item;
    private String authorName;
    private LocalDateTime created;
}
