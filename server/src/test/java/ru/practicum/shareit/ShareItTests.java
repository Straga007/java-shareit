
package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShareItTests {

    @Test
    public void testMainMethod() {
        try {
            ShareItServer.main(new String[]{});
        } catch (Exception e) {
            Assertions.fail("Exception thrown during execution: " + e.getMessage());
        }

    }
}
