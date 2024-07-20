package ru.practicum.shareit.exceptions;

public class ApprovedStatusAlreadyExistsException extends RuntimeException {
    public ApprovedStatusAlreadyExistsException(String message) {
        super(message);
    }
}
