package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Set;

public interface ItemsRepository extends JpaRepository<Item, Long> {

    Item save(Item item);

    Set<Item> findAllByDescriptionLikeIgnoreCaseOrNameLikeIgnoreCase(
            String DescriptionSearch, String nameSearch);

    Item getItemById(Long id);
}
