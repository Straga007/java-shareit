package ru.practicum.shareit.request;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class RequestControllerTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);

    private RequestController requestController;

    @Before
    public void setup() {
        String baseUrl = "http://localhost:9090";
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        RequestClient requestClient = new RequestClient(baseUrl, restTemplateBuilder);
        requestController = new RequestController(requestClient);
    }

    @Test
    public void testGetAllOwnRequests() {
        Long userId = 1L;

        stubFor(get(urlEqualTo("/requests"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1," +
                                "\"description\": \"Test Request\"," +
                                "\"requester\": {\"id\": 1, \"name\": \"John\"}," +
                                "\"created\": \"2023-01-01T12:00:00\"," +
                                "\"items\": []" +
                                "}]")));

        ResponseEntity<Object> responseEntity = requestController.getAllOwnRequests(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("[{id=1, description=Test Request, requester={id=1, name=John}, created=2023-01-01T12:00:00, items=[]}]", responseEntity.getBody().toString());
    }
    @Test
    public void testGetAllOthersRequests() {
        Long userId = 1L;
        Integer from = 0;
        Integer size = 10;

        stubFor(get(urlEqualTo("/requests/all?from=0&size=10"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1," +
                                "\"description\": \"Test Request\"," +
                                "\"requester\": {\"id\": 1, \"name\": \"John\"}," +
                                "\"created\": \"2023-01-01T12:00:00\"," +
                                "\"items\": []" +
                                "}]")));

        ResponseEntity<Object> responseEntity = requestController.getAllOthersRequests(userId, from, size);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("[{id=1, description=Test Request, requester={id=1, name=John}, created=2023-01-01T12:00:00, items=[]}]", responseEntity.getBody().toString());
    }

    @Test
    public void testGetRequest() {
        Long userId = 1L;
        Long requestId = 1L;

        stubFor(get(urlEqualTo("/requests/1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1," +
                                "\"description\": \"Test Request\"," +
                                "\"requester\": {\"id\": 1, \"name\": \"John\"}," +
                                "\"created\": \"2023-01-01T12:00:00\"," +
                                "\"items\": []" +
                                "}")));

        ResponseEntity<Object> responseEntity = requestController.getRequest(userId, requestId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{id=1, description=Test Request, requester={id=1, name=John}, created=2023-01-01T12:00:00, items=[]}", responseEntity.getBody().toString());
    }

    @Test
    public void testAddRequest() {
        Long userId = 1L;
        ItemRequestDto itemRequestDto = new ItemRequestDto(1L, "Test Request", null, null, null);

        stubFor(post(urlEqualTo("/requests"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1," +
                                "\"description\": \"Test Request\"," +
                                "\"requester\": null," +
                                "\"created\": null," +
                                "\"items\": []" +
                                "}")));

        ResponseEntity<Object> responseEntity = requestController.addRequest(userId, itemRequestDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("{id=1, description=Test Request, requester=null, created=null, items=[]}", responseEntity.getBody().toString());
    }
}
