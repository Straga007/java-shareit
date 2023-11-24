package ru.practicum.request.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.exeptions.BadRequestException;
import ru.practicum.exeptions.NotFoundException;
import ru.practicum.item.ItemRepository;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.mapper.ItemMapper;
import ru.practicum.request.dto.ItemRequestDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.object.ItemRequest;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.dataTransferObject.UserDto;
import ru.practicum.user.service.UserService;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestServiceImpl implements RequestService {

    UserService userService;

    RequestRepository requestRepository;

    ItemRepository itemRepository;
    //ItemService itemService;

    @Override
    public ItemRequest findItemRequestById(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("Request %s not found.", requestId)));
    }

    @Override
    public ItemRequestDto addRequest(Long userId, ItemRequestDto requestDto) {
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        if (requestDto.getDescription() == null) {
            throw new BadRequestException("Wrong description.");
        }
        UserDto requesterDto = userService.findUserById(userId);
        requestDto.setRequester(requesterDto);
        requestDto.setCreated(now);
        ItemRequest request = requestRepository.save(RequestMapper.toItemRequest(requestDto));
        return RequestMapper.toItemRequestDto(request);
    }

    @Override
    public List<ItemRequestDto> getAllOwnRequests(Long userId) {
        userService.findUserById(userId);
        return requestRepository.findAllByRequesterIdOrderByCreatedAsc(userId).stream()
                .map(RequestMapper::toItemRequestDto)
                .peek(request -> {
                    List<ItemDto> items = itemRepository.findAllByRequestId(request.getId()).stream()
                            .map(ItemMapper::toItemDto)
                            .collect(Collectors.toList());
                    request.setItems(items);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestDto> getAllOthersRequests(Long userId, Integer from, Integer size) {
        if (from != null && size != null) {
            if (from < 0 || size <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong request.");
            }
            int pageNumber = (int) Math.ceil((double) from / size);
            Pageable pageable = PageRequest.of(pageNumber, size);
            return requestRepository.findByRequesterIdNot(userId, pageable).stream()
                    .map(RequestMapper::toItemRequestDto)
                    .peek(request -> {
                        List<ItemDto> items = itemRepository.findAllByRequestId(request.getId()).stream()
                                .map(ItemMapper::toItemDto)
                                .collect(Collectors.toList());
                        request.setItems(items);
                    })
                    .collect(Collectors.toList());
        }
        return requestRepository.findByRequesterIdNot(userId).stream()
                .map(RequestMapper::toItemRequestDto)
                .peek(request -> {
                    List<ItemDto> items = itemRepository.findAllByRequestId(request.getId()).stream()
                            .map(ItemMapper::toItemDto)
                            .collect(Collectors.toList());
                    request.setItems(items);
                })
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto findItemRequestById(Long userId, Long requestId) {
        userService.findUserById(userId);
        ItemRequestDto itemRequestDto = RequestMapper.toItemRequestDto(requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("Request %s not found.", requestId))));
        List<ItemDto> listItemDto = itemRepository.findAllByRequestId(requestId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
        itemRequestDto.setItems(listItemDto);
        return itemRequestDto;
    }
}
