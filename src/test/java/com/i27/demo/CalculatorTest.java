package com.i27.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    @Test
    void testAdd() {
        Calculator c = new Calculator();
        assertEquals(5, c.add(2, 3));
    }

    @Test
    void testDivideOk() {
        Calculator c = new Calculator();
        assertEquals(5, c.divide(10, 2));
    }

    @Test
    void testDivideByZero() {
        Calculator c = new Calculator();
        assertNull(c.divide(1, 0));
    }

    @Test
    void testIsEven() {
        Calculator c = new Calculator();
        assertTrue(c.isEven(4));
        assertFalse(c.isEven(5));
    }

    @Test
    void testDuplicateLogic() {
        Calculator c = new Calculator();
        assertEquals("BIG", c.duplicateLogic(12));
        assertEquals("MID", c.duplicateLogic(7));
        assertEquals("SMALL", c.duplicateLogic(2));
    }
}
