package ru.practicum.shareit.user.object;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
public class User {
    Long id;
    String name;
    String email;
}
