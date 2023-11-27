package ru.practicum.shareit.user;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.dto.UserDto;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class UserClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);

    private UserClient userClient;

    @Before
    public void setup() {
        String baseUrl = "http://localhost:9090";
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        userClient = new UserClient(baseUrl, restTemplateBuilder);
    }

    @Test
    public void testGetUsers() {
        stubFor(get(urlEqualTo("/users"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{" +
                                "\"id\": 1," +
                                "\"name\": \"John\"," +
                                "\"email\": \"john@example.com\"" +
                                "},{" +
                                "\"id\": 2," +
                                "\"name\": \"Jane\"," +
                                "\"email\": \"jane@example.com\"" +
                                "}]")));

        ResponseEntity<Object> responseEntity = userClient.getUsers();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("[{id=1, name=John, email=john@example.com}, {id=2, name=Jane, email=jane@example.com}]", responseEntity.getBody().toString());
    }

    @Test
    public void testAddUser() {
        UserDto userDto = new UserDto(null, "John", "john@example.com");

        stubFor(post(urlEqualTo("/users"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1, \"name\": \"John\", \"email\": \"john@example.com\"}")));

        ResponseEntity<Object> responseEntity = userClient.addUser(userDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("{id=1, name=John, email=john@example.com}", responseEntity.getBody().toString());
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "UpdatedJohn", "updatedjohn@example.com");

        stubFor(patch(urlEqualTo("/users/" + userId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1, \"name\": \"UpdatedJohn\", \"email\": \"updatedjohn@example.com\"}")));

        ResponseEntity<Object> responseEntity = userClient.updateUser(userId, userDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{id=1, name=UpdatedJohn, email=updatedjohn@example.com}", responseEntity.getBody().toString());
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;

        stubFor(get(urlEqualTo("/users/" + userId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1, \"name\": \"John\", \"email\": \"john@example.com\"}")));

        ResponseEntity<Object> responseEntity = userClient.getUserById(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{id=1, name=John, email=john@example.com}", responseEntity.getBody().toString());
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        stubFor(delete(urlEqualTo("/users/" + userId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        ResponseEntity<Object> responseEntity = userClient.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
