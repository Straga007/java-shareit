
package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.fail;

@SpringBootTest
class ShareItTests {

    @Test
    public void testMainMethod() {
        try {
            ShareItServer.main(new String[]{});
        } catch (Exception e) {
            fail("Exception thrown during execution: " + e.getMessage());
        }

    }
}
