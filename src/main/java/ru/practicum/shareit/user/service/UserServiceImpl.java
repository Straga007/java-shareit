package ru.practicum.shareit.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.user.dataTransferObject.UserDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private Long id = 0L;
    public final Map<Long, UserDto> users = new HashMap<>();


    @Override
    public List<UserDto> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        if (users.containsValue(userDto)) {
            throw new IllegalArgumentException(String.format("User with email %s already exists", userDto.getEmail()));
        }
        id++;
        userDto.setId(id);
        UserDto userNew = new UserDto(userDto.getId(), userDto.getName(), userDto.getEmail());
        users.put(id, userNew);
        return userNew;
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        UserDto updatedUser = findUserById(userId);

        String name = userDto.getName();
        String email = userDto.getEmail();

        if (name != null && !name.isBlank()) {
            updatedUser.setName(name);
        }

        if (email != null && !email.isBlank()) {
            if (emailExistsForOtherUser(userId, email)) {
                throw new IllegalArgumentException(String.format("User with email %s already exists", email));
            }
            updatedUser.setEmail(email);
        }

        return updatedUser;
    }

    private boolean emailExistsForOtherUser(Long userId, String email) {
        return users.values().stream()
                .anyMatch(user -> !user.getId().equals(userId) && email.equals(user.getEmail()));
    }


    @Override
    public UserDto findUserById(Long userId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundException("User not found.");
        }
        return users.get(userId);
    }

    @Override
    public void deleteUserById(Long userId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundException("User not found.");
        }
        users.remove(userId);
    }
}
