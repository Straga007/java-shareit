package ru.practicum.shareit.item;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ItemControllerTest {

    @Mock
    private ItemClient itemClient;

    @InjectMocks
    private ItemController itemController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetItemsByUser() {
        Long userId = 1L;
        Integer from = 0;
        Integer size = 10;

        ResponseEntity<Object> mockResponse = new ResponseEntity<>("Mocked response", HttpStatus.OK);

        when(itemClient.getItemsByUser(userId, from, size)).thenReturn(mockResponse);

        ResponseEntity<Object> responseEntity = itemController.getItemsByUser(userId, from, size);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Mocked response", responseEntity.getBody());

        verify(itemClient, times(1)).getItemsByUser(userId, from, size);
    }

    @Test
    public void testFindItemById() {
        Long userId = 1L;
        Long itemId = 1L;

        ResponseEntity<Object> mockResponse = new ResponseEntity<>("Mocked response", HttpStatus.OK);

        when(itemClient.findItemById(userId, itemId)).thenReturn(mockResponse);

        ResponseEntity<Object> responseEntity = itemController.findItemById(userId, itemId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Mocked response", responseEntity.getBody());

        verify(itemClient, times(1)).findItemById(userId, itemId);
    }

    @Test
    public void testSearchItems() {
        Long userId = 1L;
        String text = "searchText";
        Integer from = 0;
        Integer size = 10;

        ResponseEntity<Object> mockResponse = new ResponseEntity<>("Mocked response", HttpStatus.OK);

        when(itemClient.searchItems(userId, text, from, size)).thenReturn(mockResponse);

        ResponseEntity<Object> responseEntity = itemController.searchItems(userId, text, from, size);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Mocked response", responseEntity.getBody());

        verify(itemClient, times(1)).searchItems(userId, text, from, size);
    }

    @Test
    public void testAddItem() {
        Long userId = 1L;
        ItemDto itemDto = new ItemDto(1L, "ItemName", "ItemDescription", true, null, null, new ArrayList<>());

        ResponseEntity<Object> mockResponse = new ResponseEntity<>("Mocked response", HttpStatus.CREATED);

        when(itemClient.addItem(userId, itemDto)).thenReturn(mockResponse);

        ResponseEntity<Object> responseEntity = itemController.addItem(userId, itemDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Mocked response", responseEntity.getBody());

        verify(itemClient, times(1)).addItem(userId, itemDto);
    }

    @Test
    public void testUpdateItem() {
        Long userId = 1L;
        Long itemId = 1L;
        ItemDto itemDto = new ItemDto(1L, "ItemName", "ItemDescription", true, null, null, new ArrayList<>());

        ResponseEntity<Object> mockResponse = new ResponseEntity<>("Mocked response", HttpStatus.OK);

        when(itemClient.updateItem(userId, itemId, itemDto)).thenReturn(mockResponse);

        ResponseEntity<Object> responseEntity = itemController.updateItem(userId, itemId, itemDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Mocked response", responseEntity.getBody());

        verify(itemClient, times(1)).updateItem(userId, itemId, itemDto);
    }

    @Test
    public void testAddComment() {
        Long userId = 1L;
        Long itemId = 1L;
        CommentDto commentDto = new CommentDto(1L, "CommentText", null, "AuthorName", null);

        ResponseEntity<Object> mockResponse = new ResponseEntity<>("Mocked response", HttpStatus.CREATED);

        when(itemClient.addComment(userId, itemId, commentDto)).thenReturn(mockResponse);

        ResponseEntity<Object> responseEntity = itemController.addComment(userId, itemId, commentDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Mocked response", responseEntity.getBody());

        verify(itemClient, times(1)).addComment(userId, itemId, commentDto);
    }
}
