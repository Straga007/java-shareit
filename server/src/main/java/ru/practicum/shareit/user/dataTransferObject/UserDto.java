package ru.practicum.shareit.user.dataTransferObject;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "email")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;

    @NotBlank(message = "User name can't be Blank")
    String name;

    @NotBlank(message = "user email can't be Blank")
    @Email(message = "Invalid email format")
    String email;
}
