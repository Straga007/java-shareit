package ru.practicum.item.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.booking.dto.BookingRequestDto;
import ru.practicum.user.dataTransferObject.UserDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "Item availability can't be Null")
    Boolean available;

    UserDto owner;

    Long requestId;

    BookingRequestDto lastBooking;

    BookingRequestDto nextBooking;

    List<CommentDto> comments;

    public boolean isAvailable() {
        return available;
    }
}