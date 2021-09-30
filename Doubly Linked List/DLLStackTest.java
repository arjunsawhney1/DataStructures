/*
 * NAME: Arjun Sawhney
 * PID: A15499408
 */

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DLLStackTest {

    DLLStack<Integer> intStack;
    DLLStack<Character> charStack;
    DLLStack<Boolean> boolStack;

    @Before
    public void setUp() throws Exception {
        intStack = new DLLStack<>();
        charStack = new DLLStack<>();
        boolStack = new DLLStack<>();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, intStack.size());
        assertTrue(intStack.isEmpty());
        assertNull(intStack.peek());
        assertNull(intStack.pop());
    }

    @Test
    public void push() {
        assertEquals(0, intStack.size());
        assertTrue(intStack.isEmpty());

        intStack.push(1);
        assertEquals(new Integer(1), intStack.peek());
        assertEquals(1, intStack.size());

        intStack.push(2);
        assertEquals(new Integer(2), intStack.peek());
        assertEquals(2, intStack.size());

        intStack.push(3);
        assertEquals(new Integer(3), intStack.peek());
        assertEquals(3, intStack.size());

        charStack.push('A');
        assertEquals(new Character('A'), charStack.peek());
        assertEquals(1, charStack.size());

        boolStack.push(true);
        assertTrue(boolStack.peek());
        assertEquals(1, boolStack.size());

        assertFalse(intStack.isEmpty());
        assertFalse(charStack.isEmpty());
        assertFalse(boolStack.isEmpty());
    }

    @Test
    public void pop() {
        for (int i = 1; i < 4; i++) {
            intStack.push(i);
        }

        assertEquals(3, intStack.size());
        assertFalse(intStack.isEmpty());

        assertEquals(new Integer(3), intStack.pop());
        assertEquals(new Integer(2), intStack.peek());
        assertEquals(2, intStack.size());

        assertEquals(new Integer(2), intStack.pop());
        assertEquals(new Integer(1), intStack.peek());
        assertEquals(1, intStack.size());

        assertEquals(new Integer(1), intStack.pop());
        assertEquals(0, intStack.size());
        assertTrue(intStack.isEmpty());

        assertNull(intStack.peek());
        assertNull(intStack.pop());
    }
}
