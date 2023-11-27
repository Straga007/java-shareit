package ru.practicum.shareit.user;

import org.junit.Test;
import ru.practicum.shareit.user.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDtoTest {

    @Test
    public void testAllArgsConstructor() {
        Long id = 1L;
        String name = "John";
        String email = "john@example.com";

        UserDto userDto = new UserDto(id, name, email);

        assertThat(userDto.getId()).isEqualTo(id);
        assertThat(userDto.getName()).isEqualTo(name);
        assertThat(userDto.getEmail()).isEqualTo(email);
    }

    @Test
    public void testRequiredArgsConstructor() {
        String name = "John";
        String email = "john@example.com";

        UserDto userDto = new UserDto(1L, name, email);

        assertThat(userDto.getName()).isEqualTo(name);
        assertThat(userDto.getEmail()).isEqualTo(email);
    }

    @Test
    public void testEqualsAndHashCode() {
        String email = "john@example.com";
        UserDto userDto1 = new UserDto(1L, "John", email);
        UserDto userDto2 = new UserDto(2L, "Jane", email);

        assertThat(userDto1).isEqualTo(userDto2);
        assertThat(userDto1.hashCode()).isEqualTo(userDto2.hashCode());
    }
}
