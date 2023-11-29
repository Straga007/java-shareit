package ru.practicum.shareit;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class ShareItGatewayTest {

    @Test
    public void testMainMethod() {
        try {
            ShareItGateway.main(new String[]{});
        } catch (Exception e) {
            fail("Exception thrown during execution: " + e.getMessage());
        }

    }
}
