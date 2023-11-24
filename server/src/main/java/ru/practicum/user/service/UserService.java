package ru.practicum.user.service;

import ru.practicum.user.dataTransferObject.UserDto;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto addUser(@Valid UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto);

    UserDto findUserById(Long userId);

    void deleteUserById(Long userId);
}
