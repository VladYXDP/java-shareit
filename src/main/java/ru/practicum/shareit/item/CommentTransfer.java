package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentTransfer {

    public List<CommentDto> toDtoList(List<Comment> comments) {
        if (comments == null) {
            return new ArrayList<>();
        }
        return comments.stream().map(this::toDto).toList();
    }

    public Comment toComment(CommentDto dto) {
        return Comment.builder()
                .text(dto.getText())
                .created(LocalDateTime.now())
                .build();
    }

    public CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }
}
