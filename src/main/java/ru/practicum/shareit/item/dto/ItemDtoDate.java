package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dataTransferObject.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDtoDate {
    Long id;

    @NotEmpty(message = "Item name can't be Empty")
    String name;

    @NotEmpty(message = "Item description can't be Empty")
    String description;

    @NotBlank(message = "Item availability can't be Null")
    Boolean available;

    UserDto owner;

    ItemRequestDto request;

    BookingRequestDto lastBooking;

    BookingRequestDto nextBooking;

    List<CommentDto> comments;

    public boolean isAvailable() {
        return available;
    }
}
