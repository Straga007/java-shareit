package ru.practicum.shareit.booking;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;


public class BookingClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9090);

    private BookingClient bookingClient;

    @Before
    public void setup() {
        String baseUrl = "http://localhost:9090";
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        bookingClient = new BookingClient(baseUrl, restTemplateBuilder);
    }

    @Test
    public void testAddBooking() {
        Long userId = 1L;
        Long bookingId = 1L;
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        BookingRequestDto bookingRequestDto = new BookingRequestDto(bookingId, start, end, userId, null, "WAITING");

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

        ResponseEntity<Object> responseEntity = bookingClient.addBooking(userId, bookingRequestDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("{id=1, start=" + start + ", end=" + end + ", bookerId=1, itemId=null, status=WAITING}", responseEntity.getBody().toString());
    }

    @Test
    public void testGetAllBookingsWithState() {
        Long userId = 1L;
        BookingState state = BookingState.WAITING;
        Integer from = 0;
        Integer size = 10;

        stubFor(get(urlEqualTo("/bookings?state=WAITING&from=0&size=10"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1," +
                                "\"start\": \"2023-01-01T12:00\"," +
                                "\"end\": \"2023-01-01T14:00\"," +
                                "\"bookerId\": 1," +
                                "\"itemId\": null," +
                                "\"status\": \"WAITING\"" +
                                "}]")));

        ResponseEntity<Object> responseEntity = bookingClient.getAllBookingsWithState(userId, state, from, size);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("[{id=1, start=2023-01-01T12:00, end=2023-01-01T14:00, bookerId=1, itemId=null, status=WAITING}]", responseEntity.getBody().toString());
    }

    @Test
    public void testGetAllBookingByOwner() {
        Long userId = 1L;
        BookingState state = BookingState.WAITING;
        Integer from = 0;
        Integer size = 10;

        stubFor(get(urlEqualTo("/bookings/owner?state=WAITING&from=0&size=10"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\": 1," +
                                "\"start\": \"2023-01-01T12:00\"," +
                                "\"end\": \"2023-01-01T14:00\"," +
                                "\"bookerId\": 1," +
                                "\"itemId\": null," +
                                "\"status\": \"WAITING\"" +
                                "}]")));

        ResponseEntity<Object> responseEntity = bookingClient.getAllBookingByOwner(userId, state, from, size);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("[{id=1, start=2023-01-01T12:00, end=2023-01-01T14:00, bookerId=1, itemId=null, status=WAITING}]", responseEntity.getBody().toString());
    }

    @Test
    public void testFindBookingById() {
        Long userId = 1L;
        Long bookingId = 1L;

        stubFor(get(urlEqualTo("/bookings/1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\": 1," +
                                "\"start\": \"2023-01-01T12:00\"," +
                                "\"end\": \"2023-01-01T14:00\"," +
                                "\"bookerId\": 1," +
                                "\"itemId\": null," +
                                "\"status\": \"WAITING\"" +
                                "}")));

        ResponseEntity<Object> responseEntity = bookingClient.findBookingById(userId, bookingId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{id=1, start=2023-01-01T12:00, end=2023-01-01T14:00, bookerId=1, itemId=null, status=WAITING}", responseEntity.getBody().toString());
    }

    @Test
    public void testBookingApprove() {
        Long userId = 1L;
        Long bookingId = 1L;
        boolean approved = true;

        stubFor(patch(urlEqualTo("/bookings/1?approved=true"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())));

        ResponseEntity<Object> responseEntity = bookingClient.bookingApprove(userId, bookingId, approved);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
