package ru.practicum.shareit;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.item.ItemClient;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

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
}
