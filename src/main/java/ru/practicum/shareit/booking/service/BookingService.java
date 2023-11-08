package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> findBookingByItemIdOrderByStartAsc(Long itemId);

    Optional<Booking> findTopByItemOwnerIdAndStatusAndStartBeforeOrderByEndDesc(Long ownerId, Status status, LocalDateTime start);

    Optional<Booking> findTopByItemOwnerIdAndStatusAndStartAfterOrderByStartAsc(Long ownerId, Status status, LocalDateTime start);

    List<Booking> findBookingByItemIdAndBookerIdAndStatusAndEndBefore(Long itemId, Long userId, Status status, LocalDateTime end);

    BookingDto addBooking(Long userId, BookingRequestDto bookingRequestDto);

    List<BookingDto> getBookingByOwner(Long userId, String state);

    BookingDto findBookingById(Long userId, Long bookingId);

    void removeBooking(Long userId, Long bookingId);

    BookingDto bookingApprove(Long ownerId, Long bookingId, boolean approved);

    List<BookingDto> getAllBookingsWithState(Long userId, String state);

    List<BookingDto> getBookingByOwnerId(Long ownerId);
}
