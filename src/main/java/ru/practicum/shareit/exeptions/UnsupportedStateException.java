package ru.practicum.shareit.exeptions;

public class UnsupportedStateException extends RuntimeException {

    public UnsupportedStateException(String message) {
        super(message);
    }
}