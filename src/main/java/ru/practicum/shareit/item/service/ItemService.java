package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoDate;
import ru.practicum.shareit.item.object.Item;

import java.util.List;

public interface ItemService {
    List<ItemDto> getAllItems();

    ItemDto addItem(Long userId, ItemDto itemDto);

    List<ItemDtoDate> getItemsByUser(Long userId);

    ItemDtoDate findItemById(Long userId, Long itemId);

    ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    List<ItemDto> searchItems(Long userId, String text);

    void removeItem(Long userId, Long itemId);

    Item findItem(Long itemId);

    CommentDto addComment(Long userId, Long itemId, CommentDto commentDto);

    List<CommentDto> getAllComments();
}
