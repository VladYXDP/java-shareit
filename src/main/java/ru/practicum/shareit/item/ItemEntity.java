package ru.practicum.shareit.item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.UserEntity;

@Entity
@Table(name = "item")
@Setter
@Getter
@Builder
public class ItemEntity {

    public ItemEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private UserEntity owner;

    @Column
    private Boolean available;
}
