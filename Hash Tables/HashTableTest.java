/*
 * Name: Arjun Sawhney
 * PID: A15499408
 */

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * HashTable Tester Class
 *
 * @author Arjun Sawhney
 * @since 06/05/2020
 */
public class HashTableTest {
    HashTable<Integer> hashTable = new HashTable<>(12);

    @Test
    public void testConstructor() {
        assertEquals(0, hashTable.size());
        assertEquals(12, hashTable.capacity());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorThrowsIAE() {
        hashTable = new HashTable<>(8);
        fail("Exception not thrown.");
    }

    @Test
    public void testInsert() {
        for (int i = 1; i < 20; i++) {
            assertFalse(hashTable.lookup(i));
            assertTrue(hashTable.insert(i));
            assertTrue(hashTable.lookup(i));
            assertFalse(hashTable.insert(i));
            assertEquals(i, hashTable.size());
        }

        assertTrue(hashTable.insert(20));
        assertEquals(20, hashTable.size());
        assertEquals(48, hashTable.capacity());
    }

    @Test
    public void testDelete() {
        testInsert();

        for (int i = 20; i > 0; i--) {
            assertEquals(i, hashTable.size());
            assertTrue(hashTable.lookup(i));
            assertTrue(hashTable.delete(i));
            assertFalse(hashTable.delete(i));
            assertFalse(hashTable.lookup(i));
        }
    }

    @Test (expected = NullPointerException.class)
    public void testInsertThrowsNPE() {
        hashTable.insert(null);
        fail("Exception not thrown.");
    }

    @Test (expected = NullPointerException.class)
    public void testLookUpThrowsNPE() {
        hashTable.lookup(null);
        fail("Exception not thrown.");
    }

    @Test (expected = NullPointerException.class)
    public void testDeleteThrowsNPE() {
        hashTable.delete(null);
        fail("Exception not thrown.");
    }
}
