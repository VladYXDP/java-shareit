package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;

@Component
public class UserClient extends BaseClient {

    private static final String API_PREFIX = "/users";

    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder restBuilder) {
        super(restBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build()
        );
    }

    public ResponseEntity<Object> add(UserDto dto) {
        return post("", dto);
    }

    public ResponseEntity<Object> update(UserDto dto) {
        return patch("/" + dto.getId(), dto);
    }

    public ResponseEntity<Object> get(Long userId) {
        return get("/" + userId);
    }

    public ResponseEntity<Object> delete(Long userId) {
        return delete("/" + userId);
    }

    public ResponseEntity<Object> getAll() {
        return get("");
    }
}
