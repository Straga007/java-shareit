package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.dataTransferObject.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    Long id;

    @NotBlank(message = "Item cant be blank")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    String name;

    @NotBlank(message = "Item cant be blank")
    @Size(min = 1, max = 50, message = "description must be between 1 and 50 characters")
    String description;

    @NotNull(message = "Item cant be null")
    Boolean available;

    UserDto owner;
    ItemRequest request;

    public boolean isAvailable() {
        return available;
    }
}
