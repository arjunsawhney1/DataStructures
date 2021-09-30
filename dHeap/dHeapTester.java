/*
 * Name: Arjun Sawhney
 * PID:  A15499408
 */

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class dHeapTester {
    dHeap<Integer> maxIntHeap;
    dHeap<Character> maxCharHeap;
    dHeap<String> maxStringHeap;

    dHeap<Integer> minIntHeap;
    dHeap<Character> minCharHeap;
    dHeap<String> minStringHeap;

    @Test @Before
    public void testConstructors() {
        maxIntHeap = new dHeap<>();
        assertEquals(0, maxIntHeap.size());
        maxCharHeap = new dHeap<>(5);
        assertEquals(0, maxCharHeap.size());
        maxStringHeap = new dHeap<>(10);
        assertEquals(0, maxStringHeap.size());

        minIntHeap = new dHeap<>(4, 15, false);
        assertEquals(0, minIntHeap.size());
        minCharHeap = new dHeap<>(5, 20, false);
        assertEquals(0, minCharHeap.size());
        minStringHeap = new dHeap<>(6, 25, false);
        assertEquals(0, minStringHeap.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorThrowsIAE() {
        dHeap<Integer> invalid = new dHeap<>(0, 6, true);
        fail("Exception not thrown");
    }

    @Test
    public void testAdd() {
        for (int i = 1; i < 8; i++) {
            maxIntHeap.add(i);
            assertEquals(i, maxIntHeap.size());
            assertEquals(new Integer(i), maxIntHeap.element());

            minIntHeap.add(i);
            assertEquals(i, minIntHeap.size());
            assertEquals(new Integer(1), minIntHeap.element());
        }

        for (int i = 7; i > 0; i--) {
            maxCharHeap.add((char) (64 + i));
            assertEquals(8 - i, maxCharHeap.size());
            assertEquals(new Character('G'), maxCharHeap.element());

            minCharHeap.add((char) (64 + i));
            assertEquals(8 - i, minCharHeap.size());
            assertEquals(new Character((char) (64 + i)), minCharHeap.element());
        }

        maxStringHeap.add("Arjun");
        assertEquals(1, maxStringHeap.size());
        assertEquals("Arjun", maxStringHeap.element());
        maxStringHeap.add("Chhaya");
        assertEquals(2, maxStringHeap.size());
        assertEquals("Chhaya", maxStringHeap.element());
        maxStringHeap.add("Sawhney");
        assertEquals(3, maxStringHeap.size());
        assertEquals("Sawhney", maxStringHeap.element());
        maxStringHeap.add("Rajesh");
        assertEquals(4, maxStringHeap.size());
        assertEquals("Sawhney", maxStringHeap.element());
        maxStringHeap.add("Rohan");
        assertEquals(5, maxStringHeap.size());
        assertEquals("Sawhney", maxStringHeap.element());

        minStringHeap.add("Sawhney");
        assertEquals(1, minStringHeap.size());
        assertEquals("Sawhney", minStringHeap.element());
        minStringHeap.add("Rajesh");
        assertEquals(2, minStringHeap.size());
        assertEquals("Rajesh", minStringHeap.element());
        minStringHeap.add("Chhaya");
        assertEquals(3, minStringHeap.size());
        assertEquals("Chhaya", minStringHeap.element());
        minStringHeap.add("Rohan");
        assertEquals(4, minStringHeap.size());
        assertEquals("Chhaya", minStringHeap.element());
        minStringHeap.add("Arjun");
        assertEquals(5, minStringHeap.size());
        assertEquals("Arjun", minStringHeap.element());
    }

    @Test (expected = NullPointerException.class)
    public void testAddThrowsNPE() {
        maxIntHeap.add(null);
        fail("Exception not thrown");
        maxCharHeap.add(null);
        fail("Exception not thrown");
        maxStringHeap.add(null);
        fail("Exception not thrown");

        minIntHeap.add(null);
        fail("Exception not thrown");
        minCharHeap.add(null);
        fail("Exception not thrown");
        minStringHeap.add(null);
        fail("Exception not thrown");
    }

    @Test
    public void testRemove() {
        testAdd();

        for (int i = 7; i > 0; i--) {
            assertEquals(new Integer(i), maxIntHeap.remove());
            assertEquals(new Character((char) (64 + i)), maxCharHeap.remove());

            assertEquals(new Integer(8 - i), minIntHeap.remove());
            assertEquals(new Character((char) (72 - i)), minCharHeap.remove());
        }

        assertEquals("Sawhney", maxStringHeap.remove());
        assertEquals(4, maxStringHeap.size());
        assertEquals("Rohan", maxStringHeap.remove());
        assertEquals(3, maxStringHeap.size());
        assertEquals("Rajesh", maxStringHeap.remove());
        assertEquals(2, maxStringHeap.size());
        assertEquals("Chhaya", maxStringHeap.remove());
        assertEquals(1, maxStringHeap.size());
        assertEquals("Arjun", maxStringHeap.remove());
        assertEquals(0, maxStringHeap.size());

        assertEquals("Arjun", minStringHeap.remove());
        assertEquals(4, minStringHeap.size());
        assertEquals("Chhaya", minStringHeap.remove());
        assertEquals(3, minStringHeap.size());
        assertEquals("Rajesh", minStringHeap.remove());
        assertEquals(2, minStringHeap.size());
        assertEquals("Rohan", minStringHeap.remove());
        assertEquals(1, minStringHeap.size());
        assertEquals("Sawhney", minStringHeap.remove());
        assertEquals(0, minStringHeap.size());
    }

    @Test (expected = NoSuchElementException.class)
    public void testRemoveThrowsNSEE() {
        maxIntHeap.remove();
        fail("Exception not thrown");
        maxCharHeap.remove();
        fail("Exception not thrown");
        maxStringHeap.remove();
        fail("Exception not thrown");

        minIntHeap.remove();
        fail("Exception not thrown");
        minIntHeap.remove();
        fail("Exception not thrown");
        minIntHeap.remove();
        fail("Exception not thrown");
    }

    @Test
    public void testClear() {
        testAdd();

        maxIntHeap.clear();
        maxCharHeap.clear();
        maxStringHeap.clear();
        assertEquals(0, maxIntHeap.size());
        assertEquals(0, maxCharHeap.size());
        assertEquals(0, maxStringHeap.size());

        minIntHeap.clear();
        minCharHeap.clear();
        minStringHeap.clear();
        assertEquals(0, minIntHeap.size());
        assertEquals(0, minCharHeap.size());
        assertEquals(0, minStringHeap.size());
    }

    @Test (expected = NoSuchElementException.class)
    public void testElementThrowsNSEE() {
        maxIntHeap.element();
        fail("Exception not thrown");
        maxCharHeap.element();
        fail("Exception not thrown");
        maxStringHeap.element();
        fail("Exception not thrown");

        minIntHeap.element();
        fail("Exception not thrown");
        minCharHeap.element();
        fail("Exception not thrown");
        minStringHeap.element();
        fail("Exception not thrown");
    }
}
