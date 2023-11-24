package ru.practicum.item.service;

import ru.practicum.item.dto.CommentDto;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ItemDtoDate;
import ru.practicum.item.object.Item;

import java.util.List;

public interface ItemService {
    List<ItemDto> getItemsByRequestId(Long requestId);

    ItemDto addItem(Long userId, ItemDto itemDto);

    List<ItemDtoDate> getItemsByUser(Long userId, Integer from, Integer size);

    ItemDtoDate findItemById(Long userId, Long itemId);

    ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    List<ItemDto> searchItems(Long userId, String text, Integer from, Integer size);

    Item findItem(Long itemId);

    CommentDto addComment(Long userId, Long itemId, CommentDto commentDto);
}
