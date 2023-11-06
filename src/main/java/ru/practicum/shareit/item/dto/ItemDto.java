package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    Long id;

    @NotEmpty(message = "Item name can't be Empty")
    String name;

    @NotEmpty(message = "Item description can't be Empty")
    String description;

    @NotNull(message = "Item availability can't be Null")
    Boolean available;

    Long ownerId;
    Long requestId;

    public boolean isAvailable() {
        return available;
    }
}
