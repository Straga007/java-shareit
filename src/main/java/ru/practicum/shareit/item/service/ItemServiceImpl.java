package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dataTransferObject.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final UserServiceImpl userServiceImpl;
    private Long id = 0L;
    private final Map<Long, ItemDto> items = new HashMap<>();


    @Override
    public ItemDto addItem(@Positive Long userId, @Valid ItemDto itemDto) {
        itemDto.setOwnerId(userServiceImpl.findUserById(userId).getId());
        id++;
        itemDto.setId(id);
        items.put(itemDto.getId(), itemDto);
        return itemDto;
    }

    @Override
    public List<ItemDto> getItemsByUser(@Positive Long userId) {
        UserDto user = userServiceImpl.findUserById(userId);
        return items.values().stream()
                .filter(item -> item.getOwnerId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto findItemById(@Positive Long userId, @Positive Long itemId) {
        userServiceImpl.findUserById(userId);
        if (!items.containsKey(itemId)) {
            throw new NotFoundException("Item not found.");
        }
        return items.get(itemId);
    }

    @Override
    public ItemDto updateItem(@Positive Long userId, @Positive Long itemId, @Valid ItemDto itemDto) {
        userServiceImpl.findUserById(userId);
        String name = itemDto.getName();
        String description = itemDto.getDescription();
        Boolean available = itemDto.getAvailable();
        ItemDto updatedItem = findItemById(userId, itemId);
        if (!updatedItem.getOwnerId().equals(userId)) {
            throw new NotFoundException("Item not found.");
        }
        updatedItem.setName(name != null && !name.isBlank() ? name : updatedItem.getName());
        updatedItem.setDescription(description != null && !description.isBlank() ? description : updatedItem.getDescription());
        updatedItem.setAvailable(available != null ? available : updatedItem.getAvailable());
        return updatedItem;
    }

    @Override
    public List<ItemDto> searchItems(@Positive Long userId, @NotBlank String text) {
        userServiceImpl.findUserById(userId);
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }
        return items.values().stream()
                .filter(item -> item.isAvailable() &&
                        (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                                item.getDescription().toLowerCase().contains(text.toLowerCase())))
                .collect(Collectors.toList());
    }

}
