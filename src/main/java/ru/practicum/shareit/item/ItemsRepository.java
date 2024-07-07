package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<ItemEntity, Long> {
}
