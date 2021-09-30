/*
 * Name: Arjun Sawhney
 * PID: A15499408
 */

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * FADAF Tester Class
 *
 * @author Arjun Sawhney
 * @since 06/07/2020
 */
public class FADAFTest { 
    FADAF<String, Integer> stringTree = new FADAF<>(10);

    @Test
    public void testConstructor() {
        assertEquals(0, stringTree.size());
        assertEquals(0, stringTree.nUniqueKeys());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorThrowsIAE() {
        stringTree = new FADAF<>(8);
        fail("Exception not thrown.");
    }

    @Test
    public void testInsert() {
        assertTrue(stringTree.insert("Chhaya", 1));
        assertTrue(stringTree.lookup("Chhaya", 1));
        assertEquals(1, stringTree.size());
        assertEquals(1, stringTree.nUniqueKeys());

        assertTrue(stringTree.insert("Chhaya", 2));
        assertTrue(stringTree.lookup("Chhaya", 2));
        assertEquals(2, stringTree.size());
        assertEquals(1, stringTree.nUniqueKeys());

        assertTrue(stringTree.insert("Chhaya", 3));
        assertTrue(stringTree.lookup("Chhaya", 3));
        assertEquals(3, stringTree.size());
        assertEquals(1, stringTree.nUniqueKeys());

        assertTrue(stringTree.lookupAny("Chhaya"));
        assertArrayEquals(new Integer[] {1, 2, 3}, stringTree.getAllData("Chhaya").toArray());
        assertArrayEquals(new String[] {"Chhaya", "Chhaya", "Chhaya"},
                stringTree.getAllKeys().toArray());
        assertEquals("Chhaya", stringTree.getMinKey());
        assertEquals("Chhaya", stringTree.getMaxKey());


        assertTrue(stringTree.insert("Arjun", 4));
        assertTrue(stringTree.lookup("Arjun", 4));
        assertEquals(4, stringTree.size());
        assertEquals(2, stringTree.nUniqueKeys());

        assertFalse(stringTree.insert("Arjun", 4));
        assertTrue(stringTree.lookup("Arjun", 4));
        assertEquals(4, stringTree.size());
        assertEquals(2, stringTree.nUniqueKeys());

        assertTrue(stringTree.lookupAny("Arjun"));
        assertArrayEquals(new Integer[] {4}, stringTree.getAllData("Arjun").toArray());
        assertArrayEquals(new String[] {"Arjun", "Chhaya", "Chhaya", "Chhaya"},
                stringTree.getAllKeys().toArray());
        assertEquals("Arjun", stringTree.getMinKey());
        assertEquals("Chhaya", stringTree.getMaxKey());


        stringTree.insert("Rajesh", 5);
        assertTrue(stringTree.lookup("Rajesh", 5));
        assertEquals(5, stringTree.size());
        assertEquals(3, stringTree.nUniqueKeys());

        stringTree.insert("Rajesh", 6);
        assertTrue(stringTree.lookup("Rajesh", 6));
        assertEquals(6, stringTree.size());
        assertEquals(3, stringTree.nUniqueKeys());

        assertTrue(stringTree.lookupAny("Rajesh"));
        assertArrayEquals(new Integer[] {5, 6}, stringTree.getAllData("Rajesh").toArray());
        assertArrayEquals(new String[] {"Arjun", "Chhaya", "Chhaya", "Chhaya", "Rajesh", "Rajesh"},
                stringTree.getAllKeys().toArray());
        assertEquals("Arjun", stringTree.getMinKey());
        assertEquals("Rajesh", stringTree.getMaxKey());


        stringTree.insert("Aaditya", 7);
        assertTrue(stringTree.lookup("Aaditya", 7));
        assertEquals(7, stringTree.size());
        assertEquals(4, stringTree.nUniqueKeys());

        stringTree.insert("Aaditya", 8);
        assertTrue(stringTree.lookup("Aaditya", 8));
        assertEquals(8, stringTree.size());
        assertEquals(4, stringTree.nUniqueKeys());

        assertTrue(stringTree.lookupAny("Aaditya"));
        assertArrayEquals(new Integer[] {7, 8}, stringTree.getAllData("Aaditya").toArray());
        assertArrayEquals(new String[] {"Aaditya", "Aaditya", "Arjun", "Chhaya", "Chhaya",
                "Chhaya", "Rajesh", "Rajesh"}, stringTree.getAllKeys().toArray());
        assertEquals("Aaditya", stringTree.getMinKey());
        assertEquals("Rajesh", stringTree.getMaxKey());
    }

    @Test
    public void testRemove() {
        testInsert();

        assertTrue(stringTree.remove("Chhaya", 2));
        assertFalse(stringTree.lookup("Chhaya", 2));
        assertTrue(stringTree.lookupAny("Chhaya"));
        assertEquals(7, stringTree.size());
        assertEquals(4, stringTree.nUniqueKeys());
        assertArrayEquals(new Integer[] {1, 3}, stringTree.getAllData("Chhaya").toArray());
        assertArrayEquals(new String[] {"Aaditya", "Aaditya", "Arjun", "Chhaya", "Chhaya",
                "Rajesh", "Rajesh"}, stringTree.getAllKeys().toArray());

        assertTrue(stringTree.remove("Chhaya", 1));
        assertFalse(stringTree.lookup("Chhaya", 1));
        assertTrue(stringTree.lookupAny("Chhaya"));
        assertEquals(6, stringTree.size());
        assertEquals(4, stringTree.nUniqueKeys());
        assertArrayEquals(new Integer[] {3}, stringTree.getAllData("Chhaya").toArray());
        assertArrayEquals(new String[] {"Aaditya", "Aaditya", "Arjun", "Chhaya", "Rajesh",
                "Rajesh"}, stringTree.getAllKeys().toArray());

        assertTrue(stringTree.remove("Chhaya", 3));
        assertFalse(stringTree.lookup("Chhaya", 3));
        assertEquals(5, stringTree.size());
        assertEquals(3, stringTree.nUniqueKeys());
        assertArrayEquals(new String[] {"Aaditya", "Aaditya", "Arjun", "Rajesh", "Rajesh"},
                stringTree.getAllKeys().toArray());

        assertFalse(stringTree.lookupAny("Chhaya"));
        assertEquals("Aaditya", stringTree.getMinKey());
        assertEquals("Rajesh", stringTree.getMaxKey());


        assertTrue(stringTree.remove("Rajesh", 5));
        assertFalse(stringTree.lookup("Rajesh", 5));
        assertTrue(stringTree.lookupAny("Rajesh"));
        assertEquals(4, stringTree.size());
        assertEquals(3, stringTree.nUniqueKeys());
        assertArrayEquals(new Integer[] {6}, stringTree.getAllData("Rajesh").toArray());
        assertArrayEquals(new String[] {"Aaditya", "Aaditya", "Arjun", "Rajesh"},
                stringTree.getAllKeys().toArray());

        assertTrue(stringTree.lookup("Rajesh", 6));
        assertTrue(stringTree.remove("Rajesh", 6));
        assertFalse(stringTree.lookup("Rajesh", 6));
        assertEquals(3, stringTree.size());
        assertEquals(2, stringTree.nUniqueKeys());
        assertArrayEquals(new String[] {"Aaditya", "Aaditya", "Arjun"},
                stringTree.getAllKeys().toArray());

        assertFalse(stringTree.lookupAny("Rajesh"));
        assertEquals("Aaditya", stringTree.getMinKey());
        assertEquals("Arjun", stringTree.getMaxKey());


        assertTrue(stringTree.remove("Arjun", 4));
        assertFalse(stringTree.lookup("Arjun", 4));
        assertFalse(stringTree.lookupAny("Arjun"));
        assertEquals(2, stringTree.size());
        assertEquals(1, stringTree.nUniqueKeys());
        assertArrayEquals(new String[] {"Aaditya", "Aaditya"}, stringTree.getAllKeys().toArray());

        assertFalse(stringTree.lookupAny("Arjun"));
        assertEquals("Aaditya", stringTree.getMinKey());
        assertEquals("Aaditya", stringTree.getMaxKey());


        assertTrue(stringTree.remove("Aaditya", 7));
        assertFalse(stringTree.lookup("Aaditya", 7));
        assertTrue(stringTree.lookupAny("Aaditya"));
        assertEquals(1, stringTree.size());
        assertEquals(1, stringTree.nUniqueKeys());
        assertArrayEquals(new Integer[] {8}, stringTree.getAllData("Aaditya").toArray());
        assertArrayEquals(new String[] {"Aaditya"},
                stringTree.getAllKeys().toArray());

        assertTrue(stringTree.remove("Aaditya", 8));
        assertFalse(stringTree.lookup("Aaditya", 8));
        assertFalse(stringTree.lookupAny("Aaditya"));
        assertEquals(0, stringTree.size());
        assertEquals(0, stringTree.nUniqueKeys());
    }

    @Test
    public void testRemoveAll() {
        testInsert();

        assertTrue(stringTree.removeAll("Aaditya"));
        assertFalse(stringTree.lookupAny("Aaditya"));
        assertEquals(6, stringTree.size());
        assertEquals(3, stringTree.nUniqueKeys());
        assertArrayEquals(new String[] {"Arjun", "Chhaya", "Chhaya", "Chhaya", "Rajesh", "Rajesh"},
                stringTree.getAllKeys().toArray());
        assertEquals("Arjun", stringTree.getMinKey());
        assertEquals("Rajesh", stringTree.getMaxKey());

        assertTrue(stringTree.removeAll("Arjun"));
        assertFalse(stringTree.lookupAny("Arjun"));
        assertEquals(5, stringTree.size());
        assertEquals(2, stringTree.nUniqueKeys());
        assertArrayEquals(new String[] {"Chhaya", "Chhaya", "Chhaya", "Rajesh", "Rajesh"},
                stringTree.getAllKeys().toArray());
        assertEquals("Chhaya", stringTree.getMinKey());
        assertEquals("Rajesh", stringTree.getMaxKey());

        assertTrue(stringTree.removeAll("Rajesh"));
        assertFalse(stringTree.lookupAny("Rajesh"));
        assertEquals(3, stringTree.size());
        assertEquals(1, stringTree.nUniqueKeys());
        assertArrayEquals(new String[] {"Chhaya", "Chhaya", "Chhaya"},
                stringTree.getAllKeys().toArray());
        assertEquals("Chhaya", stringTree.getMinKey());
        assertEquals("Chhaya", stringTree.getMaxKey());


        assertTrue(stringTree.removeAll("Chhaya"));
        assertFalse(stringTree.lookupAny("Chhaya"));
        assertEquals(0, stringTree.size());
        assertEquals(0, stringTree.nUniqueKeys());
    }

    @Test (expected = NullPointerException.class)
    public void testInsertThrowsNPE() {
        stringTree.insert(null, 1);
        fail("Exception not thrown");

        stringTree.insert("Arjun", null);
        fail("Exception not thrown");
    }

    @Test (expected = NullPointerException.class)
    public void testRemoveAllThrowsNPE() {
        stringTree.removeAll(null);
        fail("Exception not thrown");
    }

    @Test (expected = NullPointerException.class)
    public void testRemoveThrowsNPE() {
        stringTree.remove(null, 1);
        fail("Exception not thrown");

        stringTree.remove("Arjun", null);
        fail("Exception not thrown");
    }

    @Test (expected = NullPointerException.class)
    public void testLookupAnyThrowsNPE() {
        stringTree.lookupAny(null);
        fail("Exception not thrown");
    }

    @Test (expected = NullPointerException.class)
    public void testLookupThrowsNPE() {
        stringTree.lookup(null, 1);
        fail("Exception not thrown");

        stringTree.lookup("Arjun", null);
        fail("Exception not thrown");
    }

    @Test (expected = NullPointerException.class)
    public void testGetAllDataThrowsNPE() {
        stringTree.getAllData(null);
        fail("Exception not thrown");
    }
}
