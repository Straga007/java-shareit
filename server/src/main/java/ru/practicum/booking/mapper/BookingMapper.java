package ru.practicum.booking.mapper;


import ru.practicum.booking.dto.BookingDto;
import ru.practicum.booking.dto.BookingRequestDto;
import ru.practicum.booking.object.Booking;
import ru.practicum.booking.object.Status;
import ru.practicum.item.mapper.ItemMapper;
import ru.practicum.item.object.Item;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.object.User;

public class BookingMapper {

    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                UserMapper.toUserDto(booking.getBooker()),
                ItemMapper.toItemDto(booking.getItem()),
                booking.getStatus().toString()
        );
    }

    public static Booking toBooking(BookingDto bookingDto, Item item, User booker) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStatus(Status.valueOf(bookingDto.getStatus()));
        return booking;
    }

    public static BookingRequestDto toBookingRequestDto(Booking booking) {
        return new BookingRequestDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getBooker().getId(),
                booking.getItem().getId(),
                booking.getStatus().toString()
        );
    }
}