package ru.practicum.shareit.client;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class BaseClientTest {

    @Mock
    private RestTemplate restTemplate;

    private BaseClient baseClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        baseClient = new BaseClient(restTemplate);
    }

}
