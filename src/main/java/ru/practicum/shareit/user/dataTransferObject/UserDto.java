package ru.practicum.shareit.user.dataTransferObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
@FieldDefaults(level = AccessLevel.PRIVATE)
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
