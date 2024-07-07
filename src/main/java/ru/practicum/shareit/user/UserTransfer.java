package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemTransfer;

@Component
public class UserTransfer {

    private ItemTransfer itemTransfer;

    public User toUser(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserEntityDto toDtoEntity(UserEntity user) {
        return UserEntityDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserEntity toEntity(UserEntityDto dtoEntity) {
        return UserEntity.builder()
                .id(dtoEntity.getId())
                .name(dtoEntity.getName())
                .email(dtoEntity.getEmail())
                .build();
    }


    @Autowired
    @Lazy
    public void setItemTransfer(ItemTransfer itemTransfer) {
        this.itemTransfer = itemTransfer;
    }
}
