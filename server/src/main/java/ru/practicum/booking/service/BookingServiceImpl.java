package ru.practicum.booking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.booking.dto.BookingDto;
import ru.practicum.booking.dto.BookingRequestDto;
import ru.practicum.booking.mapper.BookingMapper;
import ru.practicum.booking.object.Booking;
import ru.practicum.booking.object.Status;
import ru.practicum.booking.repository.BookingRepository;
import ru.practicum.exeptions.NotFoundException;
import ru.practicum.exeptions.UnsupportedStateException;
import ru.practicum.item.object.Item;
import ru.practicum.item.service.ItemService;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.object.User;
import ru.practicum.user.service.UserService;


import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingServiceImpl implements BookingService {

    @Autowired
    Clock clock;
    BookingRepository bookingRepository;
    UserService userService;
    ItemService itemService;

    private Pageable createPageable(Integer from, Integer size) {
        if (from != null && size != null) {
            if (from < 0 || size <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong request.");
            }
            int pageNumber = (int) Math.ceil((double) from / size);
            return PageRequest.of(pageNumber, size);
        }
        return Pageable.unpaged();
    }

    @Override
    public List<BookingDto> getAllBookingsWithState(Long userId, String state, Integer from, Integer size) {
        userService.findUserById(userId);
        LocalDateTime now = LocalDateTime.now(clock.withZone(ZoneId.systemDefault()));
        Pageable pageable = createPageable(from, size);

        switch (state) {
            case "WAITING":
                return bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.WAITING, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "CURRENT":
                return bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByIdAsc(userId, now, now, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "REJECTED":
                return bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.REJECTED, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "CANCELED":
                return bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.CANCELED, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "FUTURE":
                return bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(userId, now, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "PAST":
                return bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(userId, now, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "UNSUPPORTED_STATUS":
                throw new UnsupportedStateException("Unknown state: " + state);
            default:
                return bookingRepository.findAllByBookerIdOrderByStartDesc(userId, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
        }
    }

    @Override
    public List<BookingDto> getBookingByOwner(Long userId, String state, Integer from, Integer size) {
        userService.findUserById(userId);
        LocalDateTime now = LocalDateTime.now(clock.withZone(ZoneId.systemDefault()));
        Pageable pageable = createPageable(from, size);

        switch (state) {
            case "WAITING":
                return bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.WAITING, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "CURRENT":
                return bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByIdAsc(userId, now, now, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "REJECTED":
                return bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.REJECTED, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "CANCELED":
                return bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.CANCELED, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "FUTURE":
                return bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(userId, now, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "PAST":
                return bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(userId, now, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "UNSUPPORTED_STATUS":
                throw new UnsupportedStateException("Unknown state: " + state);
            default:
                return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId, pageable).stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
        }
    }

    @Override
    public BookingDto addBooking(Long userId, BookingRequestDto bookingRequestDto) {
        User booker = UserMapper.toUser(userService.findUserById(userId));
        Item item = itemService.findItem(bookingRequestDto.getItemId());
        if (!item.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item is not available.");
        }
        if (bookingRequestDto.getStart() == null || bookingRequestDto.getEnd() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Timestamps");
        }
        if (bookingRequestDto.getStart().isAfter(bookingRequestDto.getEnd())
                || bookingRequestDto.getEnd().isBefore(bookingRequestDto.getStart())
                || bookingRequestDto.getStart().equals(bookingRequestDto.getEnd())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Timestamps");
        }
        if (item.getOwner().equals(booker)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are owner!");
        }
        Booking booking = new Booking(
                bookingRequestDto.getId(),
                bookingRequestDto.getStart(),
                bookingRequestDto.getEnd(),
                item,
                booker,
                Status.WAITING);
        bookingRepository.save(booking);
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public BookingDto findBookingById(Long userId, Long bookingId) {
        userService.findUserById(userId);
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException(String.format("Booking %s not found.", bookingId)));
        if (booking.getBooker().getId().equals(userId) || booking.getItem().getOwner().getId().equals(userId)) {
            return BookingMapper.toBookingDto(booking);
        } else {
            throw new NotFoundException(String.format("Booking %s not found.", bookingId));
        }
    }

    @Override
    public BookingDto bookingApprove(Long ownerId, Long bookingId, boolean approved) {
        userService.findUserById(ownerId);
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException(String.format("Booking %s not found.", bookingId)));
        if (!booking.getItem().getOwner().getId().equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You are not owner!");
        }
        if (booking.getStatus().equals(Status.APPROVED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "It already approved!");
        }
        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        bookingRepository.save(booking);
        return BookingMapper.toBookingDto(booking);
    }
}