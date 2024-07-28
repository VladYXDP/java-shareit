package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ItemsRepository extends JpaRepository<Item, Long> {

    Item save(Item item);

    //Для постгрес
//    @Query(value = "SELECT i.id, i.name, i.description, i.available, i.users_id FROM items AS i " +
//            "WHERE (UPPER(i.description COLLATE \"en_US\") LIKE UPPER(?1) " +
//            "OR UPPER(i.name COLLATE \"en_US\") LIKE UPPER(?2)) AND i.available = true", nativeQuery = true)
//    Set<Item> search(String description, String name);

    @Query(value = "SELECT i.id, i.item_name, i.description, i.available, i.users_id FROM items AS i " +
            "WHERE (UPPER(i.description) LIKE UPPER(?1) " +
            "OR UPPER(i.item_name) LIKE UPPER(?2)) AND i.available = true", nativeQuery = true)
    Set<Item> search(String description, String name);

    Item getItemById(Long id);
}
