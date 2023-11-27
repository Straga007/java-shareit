package ru.practicum.shareit;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.item.ItemClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class ItemClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);

    private ItemClient itemClient;

    @Before
    public void setup() {
        String baseUrl = "http://localhost:9090";
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        itemClient = new ItemClient(baseUrl, restTemplateBuilder);
    }

    @Test
    public void testAddItem() {
        Long userId = 1L;
        Long itemId = 1L;
        UserDto userDto = new UserDto(1L, "Ivan", "ivan@bik.com");
        ItemDto item = new ItemDto(itemId, "Fork", "Thing for eat", true, userDto, null, null);

        stubFor(post(urlEqualTo("/items"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 12," +
                                "\"name\": \"Fork\"," +
                                "\"description\": \"Thing for eat\"," +
                                "\"available\": true," +
                                "\"owner\": {" +
                                "\"id\": 1," +
                                "\"name\": \"Ivan\"," +
                                "\"email\": \"ivan@bik.com\"" +
                                "}," +
                                "\"requestId\": null," +
                                "\"comments\": null" +
                                "}")));

        ResponseEntity<Object> responseEntity = itemClient.addItem(1L, item);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("{id=12, " +
                "name=Fork, " +
                "description=Thing for eat, " +
                "available=true, " +
                "owner={" +
                "id=1, " +
                "name=Ivan, " +
                "email=ivan@bik.com" +
                "}, " +
                "requestId=null, " +
                "comments=null" +
                "}", responseEntity.getBody().toString());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testGetItemsByUser() {
        Long userId = 1L;

        stubFor(get(urlPathEqualTo("/items"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1," +
                                "\"name\": \"Fork\"," +
                                "\"description\": \"Thing for eat\"," +
                                "\"available\": true," +
                                "\"owner\": {" +
                                "\"id\": 1," +
                                "\"name\": \"Ivan\"," +
                                "\"email\": \"ivan@bik.com\"" +
                                "}," +
                                "\"requestId\": null," +
                                "\"comments\": null" +
                                "}]")));

        // Вызов метода getItemsByUser
        ResponseEntity<Object> responseEntity = itemClient.getItemsByUser(userId, 0, 10);

        // Проверка, что запрос был отправлен
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("[{id=1, name=Fork, description=Thing for eat, available=true, owner={id=1, name=Ivan, email=ivan@bik.com}, requestId=null, comments=null}]", responseEntity.getBody().toString());
    }
    @Test
    public void testFindItemById() {
        Long userId = 1L;
        Long itemId = 1L;

        stubFor(get(urlPathEqualTo("/items/" + itemId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1," +
                                "\"name\": \"Fork\"," +
                                "\"description\": \"Thing for eat\"," +
                                "\"available\": true," +
                                "\"owner\": {" +
                                "\"id\": 1," +
                                "\"name\": \"Ivan\"," +
                                "\"email\": \"ivan@bik.com\"" +
                                "}," +
                                "\"requestId\": null," +
                                "\"comments\": null" +
                                "}")));

        ResponseEntity<Object> responseEntity = itemClient.findItemById(userId, itemId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{id=1, name=Fork, description=Thing for eat, available=true, owner={id=1, name=Ivan, email=ivan@bik.com}, requestId=null, comments=null}", responseEntity.getBody().toString());
    }

    @Test
    public void testSearchItems() {
        Long userId = 1L;
        String searchText = "searchText";

        stubFor(get(urlPathEqualTo("/items/search"))
                .withQueryParam("text", equalTo(searchText))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1," +
                                "\"name\": \"Fork\"," +
                                "\"description\": \"Thing for eat\"," +
                                "\"available\": true," +
                                "\"owner\": {" +
                                "\"id\": 1," +
                                "\"name\": \"Ivan\"," +
                                "\"email\": \"ivan@bik.com\"" +
                                "}," +
                                "\"requestId\": null," +
                                "\"comments\": null" +
                                "}]")));

        ResponseEntity<Object> responseEntity = itemClient.searchItems(userId, searchText, 0, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("[{id=1, name=Fork, description=Thing for eat, available=true, owner={id=1, name=Ivan, email=ivan@bik.com}, requestId=null, comments=null}]", responseEntity.getBody().toString());
    }
    @Test
    public void testUpdateItem() {
        Long userId = 1L;
        Long itemId = 1L;
        ItemDto updatedItem = new ItemDto(itemId, "Updated Fork", "Updated Thing for eat", true, null, null, null);

        stubFor(patch(urlEqualTo("/items/" + itemId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1," +
                                "\"name\": \"Updated Fork\"," +
                                "\"description\": \"Updated Thing for eat\"," +
                                "\"available\": true," +
                                "\"owner\": null," +
                                "\"requestId\": null," +
                                "\"comments\": null" +
                                "}")));

        ResponseEntity<Object> responseEntity = itemClient.updateItem(userId, itemId, updatedItem);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{id=1, name=Updated Fork, description=Updated Thing for eat, available=true, owner=null, requestId=null, comments=null}", responseEntity.getBody().toString());
    }
    @Test
    public void testAddComment() {
        Long userId = 1L;
        Long itemId = 1L;
        CommentDto commentDto = new CommentDto(1L, "Great item!", null, "John", LocalDateTime.now());
        LocalDateTime start = LocalDateTime.now();
        stubFor(post(urlEqualTo("/items/" + itemId + "/comment"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1," +
                                "\"text\": \"Great item!\"," +
                                "\"itemDto\": null," +
                                "\"authorName\": \"John\"," +
                                "\"created\": \"" + start.toString() + "\"" +
                                "}")));

        ResponseEntity<Object> responseEntity = itemClient.addComment(userId, itemId, commentDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("{id=1, text=Great item!, itemDto=null, authorName=John, created=" + start + "}", responseEntity.getBody().toString());
    }


}
