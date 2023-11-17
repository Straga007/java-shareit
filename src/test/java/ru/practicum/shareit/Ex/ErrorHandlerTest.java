package ru.practicum.shareit.Ex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.exeptions.*;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ErrorHandlerTest {
    @Mock
    private UnsupportedStateException ex;

    @InjectMocks
    private ErrorHandler errorHandler;


    @Test
    public void handleUnsupportedStateException() {
        when(ex.getMessage()).thenReturn("Unsupported state");

        ResponseEntity<ErrorResponse> responseEntity = errorHandler.handleUnsupportedStateException(ex);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertEquals("Unsupported state", responseEntity.getBody().getError());
    }

    @Test
    public void handleUnsupportedStateException_ReturnsErrorResponseWithErrorMessage() {
        UnsupportedStateException ex = new UnsupportedStateException("Unsupported state");
        ResponseEntity<ErrorResponse> responseEntity = errorHandler.handleUnsupportedStateException(ex);
        ErrorResponse errorResponse = responseEntity.getBody();

        Assertions.assertNotNull(errorResponse);
        Assertions.assertEquals("Unsupported state", errorResponse.getError());
    }

    @Test
    public void createErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse("Error message");

        Assertions.assertEquals("Error message", errorResponse.getError());
    }

    @Test
    public void createNotFoundBookingException_WithMessage() {
        String message = "Booking not found";
        NotFoundException exception = new NotFoundException(message);

        Assertions.assertEquals(message, exception.getMessage());
    }


    @Test
    public void createNotFoundException_WithMessage() {
        String message = "Request not found";
        NotFoundException exception = new NotFoundException(message);

        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    public void notFoundException_HasResponseStatusNotFound() {
        NotFoundException ex = new NotFoundException("Item not found");
        ResponseEntity<ErrorResponse> responseEntity = errorHandler.handleNotFoundException(ex);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void createBadRequestException_WithMessage() {
        String message = "Wrong description.";
        BadRequestException exception = new BadRequestException(message);

        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    public void createUnsupportedStateException_WithMessage() {
        String message = "Unsupported state";
        UnsupportedStateException exception = new UnsupportedStateException(message);

        Assertions.assertEquals(message, exception.getMessage());
    }
}