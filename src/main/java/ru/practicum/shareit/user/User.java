package ru.practicum.shareit.user;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.Item;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name")
    private String name;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "owner")
    private Set<Item> items;

    @OneToMany(mappedBy = "booker")
    private List<Booking> booking;
}
