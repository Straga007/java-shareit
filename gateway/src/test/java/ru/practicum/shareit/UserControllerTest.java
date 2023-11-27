package ru.practicum.shareit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.UserClient;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserDto;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserClient userClient;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUsers() {
        ResponseEntity<Object> mockResponse = new ResponseEntity<>("Mocked response", HttpStatus.OK);

        when(userClient.getUsers()).thenReturn(mockResponse);

        ResponseEntity<Object> responseEntity = userController.getUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Mocked response", responseEntity.getBody());

        verify(userClient, times(1)).getUsers();
    }

    @Test
    public void testAddUser() {
        UserDto userDto = new UserDto(1L, "John Doe", "john.doe@example.com");

        ResponseEntity<Object> mockResponse = new ResponseEntity<>("Mocked response", HttpStatus.CREATED);

        when(userClient.addUser(userDto)).thenReturn(mockResponse);

        ResponseEntity<Object> responseEntity = userController.addUser(userDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Mocked response", responseEntity.getBody());

        verify(userClient, times(1)).addUser(userDto);
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        ResponseEntity<Object> mockResponse = new ResponseEntity<>("Mocked response", HttpStatus.OK);

        when(userClient.getUserById(userId)).thenReturn(mockResponse);

        ResponseEntity<Object> responseEntity = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Mocked response", responseEntity.getBody());

        verify(userClient, times(1)).getUserById(userId);
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "John Doe", "john.doe@example.com");

        ResponseEntity<Object> mockResponse = new ResponseEntity<>("Mocked response", HttpStatus.OK);

        when(userClient.updateUser(userId, userDto)).thenReturn(mockResponse);

        ResponseEntity<Object> responseEntity = userController.updateUser(userId, userDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Mocked response", responseEntity.getBody());

        verify(userClient, times(1)).updateUser(userId, userDto);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        ResponseEntity<Object> mockResponse = new ResponseEntity<>("Mocked response", HttpStatus.NO_CONTENT);

        when(userClient.deleteUser(userId)).thenReturn(mockResponse);

        ResponseEntity<Object> responseEntity = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals("Mocked response", responseEntity.getBody());

        verify(userClient, times(1)).deleteUser(userId);
    }
}
