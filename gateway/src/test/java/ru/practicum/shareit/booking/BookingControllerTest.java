package ru.practicum.shareit.booking;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.booking.dto.BookingRequestDto;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class BookingControllerTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);

    private BookingController bookingController;

    @Before
    public void setup() {
        String baseUrl = "http://localhost:9090";
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        BookingClient bookingClient = new BookingClient(baseUrl, restTemplateBuilder);
        bookingController = new BookingController(bookingClient);
    }

    @Test
    public void testGetAllBookingsWithState() {
        long userId = 1L;
        String stateParam = "WAITING";
        int from = 0;
        int size = 10;

        stubFor(get(urlEqualTo("/bookings?state=WAITING&from=0&size=10"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1," +
                                "\"start\": \"2023-01-01T12:00:00\"," +
                                "\"end\": \"2023-01-01T14:00:00\"," +
                                "\"bookerId\": 1," +
                                "\"itemId\": null," +
                                "\"status\": \"WAITING\"" +
                                "}]")));

        ResponseEntity<Object> responseEntity = bookingController.getAllBookingsWithState(userId, stateParam, from, size);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("[{id=1, start=2023-01-01T12:00:00, end=2023-01-01T14:00:00, bookerId=1, itemId=null, status=WAITING}]", responseEntity.getBody().toString());
    }
    @Test
    public void testGetAllBookingByOwner() {
        long userId = 1L;
        String stateParam = "WAITING";
        int from = 0;
        int size = 10;

        stubFor(get(urlEqualTo("/bookings/owner?state=WAITING&from=0&size=10"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1," +
                                "\"start\": \"2023-01-01T12:00:00\"," +
                                "\"end\": \"2023-01-01T14:00:00\"," +
                                "\"bookerId\": 1," +
                                "\"itemId\": null," +
                                "\"status\": \"WAITING\"" +
                                "}]")));

        ResponseEntity<Object> responseEntity = bookingController.getAllBookingByOwner(userId, stateParam, from, size);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("[{id=1, start=2023-01-01T12:00:00, end=2023-01-01T14:00:00, bookerId=1, itemId=null, status=WAITING}]", responseEntity.getBody().toString());
    }

    @Test
    public void testFindBookingById() {
        long userId = 1L;
        long bookingId = 1L;

        stubFor(get(urlEqualTo("/bookings/1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1," +
                                "\"start\": \"2023-01-01T12:00:00\"," +
                                "\"end\": \"2023-01-01T14:00:00\"," +
                                "\"bookerId\": 1," +
                                "\"itemId\": null," +
                                "\"status\": \"WAITING\"" +
                                "}")));

        ResponseEntity<Object> responseEntity = bookingController.findBookingById(userId, bookingId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{id=1, start=2023-01-01T12:00:00, end=2023-01-01T14:00:00, bookerId=1, itemId=null, status=WAITING}", responseEntity.getBody().toString());
    }

    @Test
    public void testAddBooking() {
        long userId = 1L;
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        BookingRequestDto bookingRequestDto = new BookingRequestDto(null, start, end, userId, null, "WAITING");

        stubFor(post(urlEqualTo("/bookings"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1," +
                                "\"start\": \"" + start + "\"," +
                                "\"end\": \"" + end + "\"," +
                                "\"bookerId\": 1," +
                                "\"itemId\": null," +
                                "\"status\": \"WAITING\"" +
                                "}")));

        ResponseEntity<Object> responseEntity = bookingController.addBooking(userId, bookingRequestDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("{id=1, start=" + start + ", end=" + end + ", bookerId=1, itemId=null, status=WAITING}", responseEntity.getBody().toString());
    }
    @Test
    public void testBookingApprove() {
        Long ownerId = 1L;
        Long bookingId = 1L;
        boolean approved = true;

        stubFor(patch(urlEqualTo("/bookings/1?approved=true"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1," +
                                "\"start\": \"2023-01-01T12:00:00\"," +
                                "\"end\": \"2023-01-01T14:00:00\"," +
                                "\"bookerId\": 1," +
                                "\"itemId\": null," +
                                "\"status\": \"APPROVED\"" +
                                "}")));

        ResponseEntity<Object> responseEntity = bookingController.bookingApprove(ownerId, bookingId, approved);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{id=1, start=2023-01-01T12:00:00, end=2023-01-01T14:00:00, bookerId=1, itemId=null, status=APPROVED}", responseEntity.getBody().toString());
    }
}
