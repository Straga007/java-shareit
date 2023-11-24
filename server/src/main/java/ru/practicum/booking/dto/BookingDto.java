package ru.practicum.booking.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.user.dataTransferObject.UserDto;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDto {
    Long id;

    LocalDateTime start;

    LocalDateTime end;

    UserDto booker;

    ItemDto item;

    String status;
}
