package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.comment.CommentDto;

import java.util.Map;

@Component
public class ItemClient extends BaseClient {

    private static final String API_PREFIX = "/items";

    public ItemClient(@Value("${shareit-server.url}") String serverUrl,
                      RestTemplateBuilder restBuilder) {
        super(restBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build());
    }

    public ResponseEntity<Object> add(ItemDto dto, Long userId) {
        return post("", userId, dto);
    }

    public ResponseEntity<Object> get(Long itemId, Long userId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> update(ItemDto dto, Long userId) {
        return patch("/" + dto.getId(), 1L, dto);
    }

    public ResponseEntity<Object> addComment(CommentDto dto, Long userId, Long itemId) {
        return post("/" + itemId + "/comment", userId, dto);
    }

    public ResponseEntity<Object> getAllByUser(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> search(String text) {
        return get("/search?text={text}", null, Map.of("text", text));
    }
}
