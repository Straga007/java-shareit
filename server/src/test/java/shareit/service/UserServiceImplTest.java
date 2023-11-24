/*
package shareit.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dataTransferObject.UserDto;
import ru.practicum.shareit.user.object.User;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser() {
        UserDto userDto = new UserDto(1L, "John", "john@example.com");
        User user = new User(1L, "John", "john@example.com");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserDto result = userService.addUser(userDto);

        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void addUser_EmailAlreadyExists_ThrowException() {
        UserDto userDto = new UserDto(1L, "John", "john@example.com");

        when(userRepository.save(any(User.class)))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(IllegalArgumentException.class, () -> userService.addUser(userDto));
    }

    @Test
    void getUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "John", "john@example.com"));
        userList.add(new User(2L, "Jane", "jane@example.com"));

        when(userRepository.findAll())
                .thenReturn(userList);

        List<UserDto> result = userService.getUsers();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("john@example.com", result.get(0).getEmail());
        assertEquals("Jane", result.get(1).getName());
        assertEquals("jane@example.com", result.get(1).getEmail());
    }

    @Test
    void updateUser() {
        User existingUser = new User(1L, "John", "john@example.com");
        UserDto userDto = new UserDto(1L, "Jane", "jane@example.com");
        User updatedUser = new User(1L, "Jane", "jane@example.com");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class)))
                .thenReturn(updatedUser);

        UserDto result = userService.updateUser(1L, userDto);

        assertEquals(1L, result.getId());
        assertEquals("Jane", result.getName());
        assertEquals("jane@example.com", result.getEmail());
    }

    @Test
    void updateUser_InvalidUserId_ThrowsException() {
        UserDto userDto = new UserDto(1L, "Jane", "jane@example.com");

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.updateUser(1L, userDto));
    }

    @Test
    void updateUser_EmailAlreadyExists_ThrowsIllegalArgumentException() {
        Long userId = 1L;
        UserDto userDto = new UserDto(1L, "John", "john@example.com");
        User existingUser = new User(2L, "Jane", "jane@example.com");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(existingUser));
        when(userRepository.findByEmail(userDto.getEmail()))
                .thenReturn(Optional.of(existingUser));

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userId, userDto));
    }

    @Test
    void findUserById() {
        User user = new User(1L, "John", "john@example.com");
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserDto result = userService.findUserById(1L);

        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void findUserById_InvalidUserId_ThrowsException() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findUserById(1L));
    }

    @Test
    void deleteUser() {
        User existingUser = new User(1L, "John", "john@example.com");
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(existingUser));

        userService.deleteUserById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_InvalidUserId_ThrowsException() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deleteUserById(1L));
    }
}*/
