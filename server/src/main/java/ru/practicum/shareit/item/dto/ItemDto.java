package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.dataTransferObject.UserDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {
    Long id;

    @NotEmpty(message = "Item name can't be Empty")
    @Size(max = 100, message = "Text cannot exceed 100 characters.")
    String name;

    @NotEmpty(message = "Item description can't be Empty")
    @Size(max = 1000, message = "Text cannot exceed 1000 characters.")
    String description;

    @NotNull(message = "Item availability can't be Null")
    Boolean available;

    UserDto owner;

    Long requestId;

    List<CommentDto> comments;

    public boolean isAvailable() {
        return available;
    }
}
