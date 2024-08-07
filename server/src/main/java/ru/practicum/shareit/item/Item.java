package ru.practicum.shareit.item;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "items")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "item_name")
    private String name;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requests_id")
    private Request request;

    @OneToMany(mappedBy = "item")
    private Set<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private List<Comment> comments;

    @Column
    private Boolean available;

    @Transient
    private Booking lastBooking;

    @Transient
    private Booking nextBooking;

    @Transient
    private Long requestId;

    @Transient
    private Long ownerId;
}
