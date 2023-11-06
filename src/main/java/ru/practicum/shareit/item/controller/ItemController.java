package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.DtoTime;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemServiceImpl;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemServiceImpl itemServiceImpl;
    private static final String header = "X-Sharer-User-Id";


    @GetMapping
    public List<DtoTime> getItemsByUser(@RequestHeader(header) Long userId) {
        return itemServiceImpl.getItemsByUser(userId);
    }

    @GetMapping("/{itemId}")
    public DtoTime findItemById(@RequestHeader(header) Long userId,
                                @PathVariable("itemId") Long itemId) {
        return itemServiceImpl.findItemById(userId, itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestHeader(header) Long userId,
                                     @RequestParam String text) {
        return itemServiceImpl.searchItems(userId, text);
    }

    @PostMapping
    public ItemDto addItem(@Valid @RequestHeader(header) Long userId,
                           @Valid @RequestBody ItemDto itemDto) {
        return itemServiceImpl.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader(header) Long userId,
                              @PathVariable("itemId") Long itemId,
                              @RequestBody ItemDto itemDto) {
        return itemServiceImpl.updateItem(userId, itemId, itemDto);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(header) Long userId,
                                 @PathVariable("itemId") Long itemId,
                                 @Valid @RequestBody CommentDto commentDto) {
        return itemServiceImpl.addComment(userId, itemId, commentDto);
    }

    @GetMapping("/comments")
    public List<CommentDto> getAllComments() {
        return itemServiceImpl.getAllComments();
    }
}
