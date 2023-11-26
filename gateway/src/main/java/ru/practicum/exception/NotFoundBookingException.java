package ru.practicum.exception;

public class NotFoundBookingException extends RuntimeException {
    public NotFoundBookingException(String message) {
        super(message);
    }
}
