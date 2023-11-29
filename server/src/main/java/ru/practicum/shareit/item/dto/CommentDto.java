package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {

    Long id;

    @NotEmpty(message = "Text can't be Empty.")
    @Size(max = 1000, message = "Text cannot exceed 1000 characters.")
    String text;

    ItemDto itemDto;

    String authorName;

    LocalDateTime created;
}
