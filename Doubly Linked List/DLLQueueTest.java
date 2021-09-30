/*
 * NAME: Arjun Sawhney
 * PID: A15499408
 */

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DLLQueueTest {

    DLLQueue<Integer> intQueue;
    DLLQueue<Character> charQueue;
    DLLQueue<Boolean> boolQueue;

    @Before
    public void setUp() throws Exception {
        intQueue = new DLLQueue<>();
        charQueue = new DLLQueue<>();
        boolQueue = new DLLQueue<>();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, intQueue.size());
        assertTrue(intQueue.isEmpty());
        assertNull(intQueue.peek());
        assertNull(intQueue.dequeue());
    }

    @Test
    public void enqueue() {
        assertEquals(0, intQueue.size());
        assertTrue(intQueue.isEmpty());

        intQueue.enqueue(1);
        assertEquals(new Integer(1), intQueue.peek());
        assertEquals(1, intQueue.size());

        intQueue.enqueue(2);
        assertEquals(new Integer(1), intQueue.peek());
        assertEquals(2, intQueue.size());

        intQueue.enqueue(3);
        assertEquals(new Integer(1), intQueue.peek());
        assertEquals(3, intQueue.size());

        charQueue.enqueue('A');
        assertEquals(new Character('A'), charQueue.peek());
        assertEquals(1, charQueue.size());

        boolQueue.enqueue(true);
        assertTrue(boolQueue.peek());
        assertEquals(1, boolQueue.size());

        assertFalse(intQueue.isEmpty());
        assertFalse(charQueue.isEmpty());
        assertFalse(boolQueue.isEmpty());
    }

    @Test
    public void dequeue() {
        for (int i = 1; i < 4; i++) {
            intQueue.enqueue(i);
        }

        assertEquals(3, intQueue.size());
        assertFalse(intQueue.isEmpty());

        assertEquals(new Integer(1), intQueue.dequeue());
        assertEquals(new Integer(2), intQueue.peek());
        assertEquals(2, intQueue.size());

        assertEquals(new Integer(2), intQueue.dequeue());
        assertEquals(new Integer(3), intQueue.peek());
        assertEquals(1, intQueue.size());

        assertEquals(new Integer(3), intQueue.dequeue());
        assertEquals(0, intQueue.size());
        assertTrue(intQueue.isEmpty());

        assertNull(intQueue.peek());
        assertNull(intQueue.dequeue());
    }
}
