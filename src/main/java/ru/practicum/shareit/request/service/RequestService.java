package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.object.ItemRequest;

import java.util.List;

public interface RequestService {
    ItemRequest findItemRequestById(Long requestId);

    ItemRequestDto addRequest(Long userId, ItemRequestDto requestDto);

    List<ItemRequestDto> getAllOwnRequests(Long userId);

    List<ItemRequestDto> getAllOthersRequests(Long userId, Integer from, Integer size);

    ItemRequestDto findItemRequestById(Long userId, Long requestId);
}