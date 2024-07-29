package ru.practicum.shareit.booking;

public enum BookingControllerStates {
    ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED;

    public static BookingControllerStates getState(String state) {
        for (BookingControllerStates tmp : values()) {
            if (tmp.name().equals(state)) {
                return tmp;
            }
        }
        throw new IllegalStateException("Unknown state: " + state);
    }
}
