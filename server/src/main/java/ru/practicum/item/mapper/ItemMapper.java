package ru.practicum.item.mapper;

import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ItemDtoDate;
import ru.practicum.item.object.Item;
import ru.practicum.user.mapper.UserMapper;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                UserMapper.toUserDto(item.getOwner()),
                item.getRequest() != null ? item.getRequest().getId() : null,
                null
        );
    }

    public static Item toItem(ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.isAvailable(),
                UserMapper.toUser(itemDto.getOwner()),
                null
        );
    }

    public static ItemDtoDate toItemDtoDate(Item item) {
        return new ItemDtoDate(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                UserMapper.toUserDto(item.getOwner()),
                item.getRequest() != null ? item.getRequest().getId() : null,
                null,
                null,
                null
        );
    }
}