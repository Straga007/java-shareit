package ru.practicum.shareit.user.dataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
public class UserDto {
    Long id;

    @NotNull(message = "User cant be null")
    @NotBlank(message = "User cant be blank")
    String name;

    @NotNull(message = "User cant be null")
    @NotBlank(message = "user cant be blank")
    @Email(message = "Invalid email")
    String email;
}
