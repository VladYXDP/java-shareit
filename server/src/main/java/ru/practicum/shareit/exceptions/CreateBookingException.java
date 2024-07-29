package ru.practicum.shareit.exceptions;

public class CreateBookingException extends RuntimeException {
    public CreateBookingException(String message) {
        super(message);
    }
}
