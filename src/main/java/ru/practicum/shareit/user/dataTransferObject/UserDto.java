package ru.practicum.shareit.user.dataTransferObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;

    @NotBlank(message = "User cant be blank")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    String name;

    @NotBlank(message = "user cant be blank")
    @Email(message = "Invalid email")
    @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters")
    String email;
}
