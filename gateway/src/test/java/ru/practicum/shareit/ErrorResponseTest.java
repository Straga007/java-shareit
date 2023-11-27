package ru.practicum.shareit;

import org.junit.Test;
import ru.practicum.shareit.exception.ErrorResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorResponseTest {

    @Test
    public void testGetError() {
        String errorMessage = "error message";
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);

        String actualError = errorResponse.getError();

        assertThat(actualError).isEqualTo(errorMessage);
    }
}
