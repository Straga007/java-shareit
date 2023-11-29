package ru.practicum.shareit.user.mapper;


import ru.practicum.shareit.user.dataTransferObject.UserDto;
import ru.practicum.shareit.user.object.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail()
        );
    }
}
