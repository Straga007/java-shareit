/*
package shareit.Ex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dataTransferObject.UserDto;
import ru.practicum.shareit.user.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.Cont.BookingControllerTest.toJson;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ErrorHandlerTest {
    @Mock
    private UnsupportedStateException ex;

    @InjectMocks
    private ErrorHandler errorHandler;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void userCreateFailNoEmail() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("user");

        when(userService.addUser(any(UserDto.class))).thenReturn(null);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userDto)))
                .andExpect(status().isBadRequest());
    }


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
}*/
