package ru.practicum.shareit.exceptions;

public class ApprovedForUserException extends RuntimeException {
    public ApprovedForUserException(String message) {
        super(message);
    }
}
