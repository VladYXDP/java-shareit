package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;

import java.util.Map;

@Component
public class RequestClient extends BaseClient {

    private static final String API_PREFIX = "/requests";

    public RequestClient(@Value("${shareit-server.url}") String serverUrl,
                         RestTemplateBuilder restBuilder) {
        super(restBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build());
    }

    public ResponseEntity<Object> add(RequestDto dto) {
        return post("", dto.getRequestorId());
    }

    public ResponseEntity<Object> get(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getAllAnotherUser(Long userId, Long from, Long size) {
        return get("/all", userId, Map.of("from", from, "size", size));
    }

    public ResponseEntity<Object> getById(Long requestId) {
        return get("/" + requestId);
    }
}
