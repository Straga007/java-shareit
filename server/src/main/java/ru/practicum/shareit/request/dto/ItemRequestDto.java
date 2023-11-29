package ru.practicum.shareit.request.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dataTransferObject.UserDto;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ItemRequestDto {
    Long id;

    @Size(max = 1000, message = "Text cannot exceed 1000 characters.")
    String description;

    UserDto requester;

    LocalDateTime created;

    List<ItemDto> items;
}
