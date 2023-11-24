package ru.practicum.request.mapper;


import ru.practicum.request.dto.ItemRequestDto;
import ru.practicum.request.object.ItemRequest;
import ru.practicum.user.mapper.UserMapper;

import java.util.ArrayList;

public class RequestMapper {
    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto) {
        return new ItemRequest(
                itemRequestDto.getId(),
                itemRequestDto.getDescription(),
                UserMapper.toUser(itemRequestDto.getRequester()),
                itemRequestDto.getCreated()
        );
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return new ItemRequestDto(
                itemRequest.getId(),
                itemRequest.getDescription(),
                UserMapper.toUserDto(itemRequest.getRequester()),
                itemRequest.getCreated(),
                new ArrayList<>()
        );
    }
}
