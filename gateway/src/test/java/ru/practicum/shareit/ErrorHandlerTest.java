package ru.practicum.shareit;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.exception.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorHandlerTest {

    private final ErrorHandler errorHandler = new ErrorHandler();

    @Test
    void handleUnsupportedStateException() {
        UnsupportedStateException exception = new UnsupportedStateException("Unsupported State");
        ResponseEntity<ErrorResponse> responseEntity = errorHandler.handleUnsupportedStateException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void handleRequestNotFoundException() {
        RequestNotFoundException exception = new RequestNotFoundException("Request Not Found");
        ResponseEntity<ErrorResponse> responseEntity = errorHandler.handleRequestNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void handleNotFoundBookingException() {
        NotFoundBookingException exception = new NotFoundBookingException("Booking Not Found");
        ResponseEntity<ErrorResponse> responseEntity = errorHandler.handleNotFoundBookingException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void handleNotFoundItemException() {
        NotFoundItemException exception = new NotFoundItemException("Item Not Found");
        ResponseEntity<ErrorResponse> responseEntity = errorHandler.handleNotFoundItemException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void handleNotFoundUserException() {
        NotFoundUserException exception = new NotFoundUserException("User Not Found");
        ResponseEntity<ErrorResponse> responseEntity = errorHandler.handleNotFoundUserException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
