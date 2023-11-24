package ru.practicum.booking.service;

import ru.practicum.booking.dto.BookingDto;
import ru.practicum.booking.dto.BookingRequestDto;

import java.util.List;

public interface BookingService {

    BookingDto addBooking(Long userId, BookingRequestDto bookingRequestDto);

    List<BookingDto> getBookingByOwner(Long userId, String state, Integer from, Integer size);

    BookingDto findBookingById(Long userId, Long bookingId);

    BookingDto bookingApprove(Long ownerId, Long bookingId, boolean approved);

    List<BookingDto> getAllBookingsWithState(Long userId, String state, Integer from, Integer size);
}
