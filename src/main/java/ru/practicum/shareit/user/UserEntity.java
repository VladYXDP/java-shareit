package ru.practicum.shareit.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.ItemEntity;

import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
public class UserEntity {

    public UserEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<ItemEntity> items;
}
