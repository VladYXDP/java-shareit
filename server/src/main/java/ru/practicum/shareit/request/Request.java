package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private LocalDateTime created;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private User requestorId;
    @OneToMany(mappedBy = "request", fetch = FetchType.EAGER)
    private List<Item> item;
}
