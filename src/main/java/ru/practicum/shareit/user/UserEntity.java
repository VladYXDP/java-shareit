package ru.practicum.shareit.user;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.item.ItemEntity;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String email;

    @OneToMany(mappedBy = "owner")
    private Set<ItemEntity> items;
}
