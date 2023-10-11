package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.dataTransferObject.UserDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    Long id;

    @NotEmpty(message = "Item cant be empty")
    String name;

    @NotEmpty(message = "Item cant be empty")
    String description;

    @NotNull(message = "Item cant be null")
    Boolean available;

    UserDto owner;
    ItemRequest request;

    public boolean isAvailable() {
        return available;
    }
}
