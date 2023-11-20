package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.object.State;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StateTest {

    @Test
    void testEnumValues() {
        assertEquals(7, State.values().length);

        assertEquals(State.ALL, State.valueOf("ALL"));
        assertEquals(State.CURRENT, State.valueOf("CURRENT"));
        assertEquals(State.PAST, State.valueOf("PAST"));
        assertEquals(State.FUTURE, State.valueOf("FUTURE"));
        assertEquals(State.WAITING, State.valueOf("WAITING"));
        assertEquals(State.REJECTED, State.valueOf("REJECTED"));
        assertEquals(State.UNSUPPORTED_STATUS, State.valueOf("UNSUPPORTED_STATUS"));
    }
}
