package ru.practicum.shareit.item.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repositry.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exeptions.NotFoundItemException;
import ru.practicum.shareit.item.CommentRepository;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.DtoTime;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dataTransferObject.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.object.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemServiceImpl implements ItemService {

    UserService userService;

    BookingRepository bookingRepository;

    ItemRepository itemRepository;

    CommentRepository commentRepository;

    @Override
    public List<ItemDto> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        User owner = UserMapper.toUser(userService.findUserById(userId));
        itemDto.setOwner(UserMapper.toUserDto(owner));
        Item item = itemRepository.save(ItemMapper.toItem(itemDto));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public List<DtoTime> getItemsByUser(Long userId) {
        User owner = UserMapper.toUser(userService.findUserById(userId));
        List<Item> items = itemRepository.findByOwner(owner);

        return items.stream()
                .map(ItemMapper::toItemDtoWithDate)
                .peek(itemDto -> {
                    List<Booking> bookings = bookingRepository.findBookingByItemIdOrderByStartAsc(itemDto.getId());
                    LocalDateTime now = LocalDateTime.now();
                    BookingRequestDto lastBooking = null;
                    BookingRequestDto nextBooking = null;

                    for (Booking booking : bookings) {
                        if (booking.getEnd().isBefore(now)) {
                            lastBooking = BookingMapper.toBookingRequestDto(booking);
                        } else if (booking.getStart().isAfter(now)) {
                            nextBooking = BookingMapper.toBookingRequestDto(booking);
                            break;
                        }
                    }

                    itemDto.setLastBooking(lastBooking);
                    itemDto.setNextBooking(nextBooking);
                })
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        User owner = UserMapper.toUser(userService.findUserById(userId));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundItemException("Item not found."));
        if (!item.getOwner().equals(owner)) {
            throw new NotFoundItemException("Item not found.");
        }
        String name = itemDto.getName();
        String description = itemDto.getDescription();
        Boolean available = itemDto.getAvailable();
        if (name != null && !name.isBlank()) {
            item.setName(name);
        }
        if (description != null && !description.isBlank()) {
            item.setDescription(description);
        }
        if (available != null) {
            item.setAvailable(available);
        }
        itemRepository.save(item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public DtoTime findItemById(Long userId, Long itemId) {
        userService.findUserById(userId);
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundItemException("Item not found."));

        LocalDateTime now = LocalDateTime.now();
        BookingRequestDto lastBooking = bookingRepository.findTopByItemOwnerIdAndStatusAndStartBeforeOrderByEndDesc(userId, Status.APPROVED, now)
                .map(BookingMapper::toBookingRequestDto)
                .orElse(null);

        BookingRequestDto nextBooking = bookingRepository.findTopByItemOwnerIdAndStatusAndStartAfterOrderByStartAsc(userId, Status.APPROVED, now)
                .map(BookingMapper::toBookingRequestDto)
                .orElse(null);

        List<CommentDto> comments = commentRepository.findAllByItemId(itemId).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());

        DtoTime dtoTime = ItemMapper.toItemDtoWithDate(item);
        dtoTime.setLastBooking(lastBooking);
        dtoTime.setNextBooking(nextBooking);
        dtoTime.setComments(comments);

        return dtoTime;
    }

    @Override
    public List<ItemDto> searchItems(Long userId, String text) {
        userService.findUserById(userId);
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.searchItems(text)
                .stream()
                .filter(Item::isAvailable)
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public void removeItem(Long userId, Long itemId) {
        findItemById(userId, itemId);
        itemRepository.deleteById(itemId);
    }

    @Override
    public Item findItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new NotFoundItemException("Item not found."));
    }

    @Override
    public CommentDto addComment(Long userId, Long itemId, CommentDto commentDto) {
        UserDto author = userService.findUserById(userId);
        ItemDto item = ItemMapper.toItemDto(findItem(itemId));
        Comment existingComment = commentRepository.findByAuthorIdAndItemId(userId, itemId);
        if (existingComment != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already commented this item.");
        }
        List<Booking> bookings = bookingRepository.findBookingByItemIdAndBookerIdAndStatusAndEndBefore(itemId, userId, Status.APPROVED, LocalDateTime.now());

        if (bookings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't comment.");
        }
        commentDto.setItemDto(item);
        commentDto.setAuthorName(author.getName());
        commentDto.setCreated(LocalDateTime.now());
        Comment comment = commentRepository.save(CommentMapper.toComment(commentDto, UserMapper.toUser(author)));
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
