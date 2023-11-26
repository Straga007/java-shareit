package ru.practicum.exception;

public class NotFoundItemException extends RuntimeException {
    public NotFoundItemException(String message) {
        super(message);
    }
}
