package ru.practicum.shareit.item;

public class ItemValidate {

    public static Item validate(Item item, Item currentItem) {
        if (item.getName() == null) {
            item.setName(currentItem.getName());
        }
        if (item.getDescription() == null) {
            item.setDescription(currentItem.getDescription());
        }
        if (item.getAvailable() == null) {
            item.setAvailable(currentItem.getAvailable());
        }
        return item;
    }
}
