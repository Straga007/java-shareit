package ru.practicum.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ItemRequestDto;
import ru.practicum.request.service.RequestService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private static final String header = "X-Sharer-User-Id";

    private final RequestService requestService;

    @GetMapping
    public List<ItemRequestDto> getAllOwnRequests(@RequestHeader(header) Long userId) {
        return requestService.getAllOwnRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllOthersRequests(@RequestHeader(header) Long userId,
                                                     @RequestParam(value = "from", required = false) @Min(0) Integer from,
                                                     @RequestParam(value = "size", required = false) @Positive Integer size) {
        return requestService.getAllOthersRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequest(@RequestHeader(header) Long userId,
                                     @PathVariable("requestId") Long requestId) {
        return requestService.findItemRequestById(userId, requestId);
    }

    @PostMapping
    public ItemRequestDto addRequest(@RequestHeader(header) Long userId,
                                     @RequestBody ItemRequestDto itemRequestDto) {
        return requestService.addRequest(userId, itemRequestDto);
    }
}
