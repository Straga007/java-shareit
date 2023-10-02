package ru.practicum.shareit.user.dataTransferObject;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDto {
    Long id;

    @NotNull(message = "User name can't be Null")
    @NotBlank(message = "User name can't be Blank")
    String name;

    @NotNull(message = "User email can't be Null")
    @NotBlank(message = "user email can't be Blank")
    @Email(message = "Invalid email format")
    String email;
}
