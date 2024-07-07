package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {

    private Long id;
    private String name;
    @Email(message = "Поле email недопустимого формата!")
    private String email;
}
