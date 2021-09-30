/*
 * Name: Arjun Sawhney
 */

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * DAFTree Tester Class
 *
 * @author Arjun Sawhney
 * @since 06/05/2020
 */
public class DAFTreeTest {

    DAFTree<String, Integer> tree = new DAFTree<>();
    DAFTree<Integer, Integer> intTree = new DAFTree<>();

    Iterator<DAFTree<Integer, Integer>.DAFNode<Integer, Integer>> iter;

    @Test
    public void testConstructors() {
        assertEquals(0, tree.size());
        assertEquals(0, tree.nUniqueKeys());
    }

    @Test
    public void testInsert() {
        tree.insert("Chhaya", 1);
        assertTrue(tree.lookup("Chhaya", 1));
        assertEquals("Chhaya", tree.getRoot().getKey());
        assertEquals(new Integer(1), tree.getRoot().getData());
        assertEquals(1, tree.size());
        assertEquals(1, tree.nUniqueKeys());

        tree.insert("Chhaya", 2);
        assertTrue(tree.lookup("Chhaya", 2));
        assertEquals("Chhaya", tree.getRoot().getDup().getKey());
        assertEquals(new Integer(2), tree.getRoot().getDup().getData());
        assertEquals("Chhaya", tree.getRoot().getDup().getPar().getKey());
        assertEquals(new Integer(1), tree.getRoot().getDup().getPar().getData());
        assertEquals(2, tree.size());
        assertEquals(1, tree.nUniqueKeys());

        tree.insert("Chhaya", 3);
        assertTrue(tree.lookup("Chhaya", 3));
        assertEquals("Chhaya", tree.getRoot().getDup().getDup().getKey());
        assertEquals(new Integer(3), tree.getRoot().getDup().getDup().getData());
        assertEquals("Chhaya", tree.getRoot().getDup().getDup().getPar().getKey());
        assertEquals(new Integer(2), tree.getRoot().getDup().getDup().getPar().getData());
        assertEquals(3, tree.size());
        assertEquals(1, tree.nUniqueKeys());
        assertArrayEquals(new Integer[] {1, 2, 3}, tree.getAllData("Chhaya").toArray());

        tree.insert("Arjun", 4);
        assertTrue(tree.lookup("Arjun", 4));
        assertEquals("Arjun", tree.getRoot().getLeft().getKey());
        assertEquals(new Integer(4), tree.getRoot().getLeft().getData());
        assertEquals(4, tree.size());
        assertEquals(2, tree.nUniqueKeys());
        assertArrayEquals(new Integer[] {4}, tree.getAllData("Arjun").toArray());

        tree.insert("Rajesh", 5);
        assertTrue(tree.lookup("Rajesh", 5));
        assertEquals("Rajesh", tree.getRoot().getRight().getKey());
        assertEquals(new Integer(5), tree.getRoot().getRight().getData());
        assertEquals(5, tree.size());
        assertEquals(3, tree.nUniqueKeys());

        tree.insert("Rajesh", 6);
        assertTrue(tree.lookup("Rajesh", 6));
        assertEquals("Rajesh", tree.getRoot().getRight().getDup().getKey());
        assertEquals(new Integer(6), tree.getRoot().getRight().getDup().getData());
        assertEquals("Rajesh", tree.getRoot().getRight().getDup().getPar().getKey());
        assertEquals(new Integer(5), tree.getRoot().getRight().getDup().getPar().getData());
        assertEquals(6, tree.size());
        assertEquals(3, tree.nUniqueKeys());
        assertArrayEquals(new Integer[] {5, 6}, tree.getAllData("Rajesh").toArray());
    }

    @Test
    public void testRemove() {
        testInsert();

        assertTrue(tree.remove("Chhaya", 2));
        assertFalse(tree.lookup("Chhaya", 2));
        assertTrue(tree.lookupAny("Chhaya"));
        assertEquals("Chhaya", tree.getRoot().getKey());
        assertEquals(new Integer(1), tree.getRoot().getData());
        assertEquals("Chhaya", tree.getRoot().getDup().getKey());
        assertEquals(new Integer(3), tree.getRoot().getDup().getData());
        assertEquals("Chhaya", tree.getRoot().getDup().getPar().getKey());
        assertEquals(new Integer(1), tree.getRoot().getDup().getPar().getData());
        assertEquals(5, tree.size());
        assertEquals(3, tree.nUniqueKeys());
        assertArrayEquals(new Integer[] {1, 3}, tree.getAllData("Chhaya").toArray());

        assertTrue(tree.remove("Chhaya", 1));
        assertFalse(tree.lookup("Chhaya", 1));
        assertTrue(tree.lookupAny("Chhaya"));
        assertEquals("Chhaya", tree.getRoot().getKey());
        assertEquals(new Integer(3), tree.getRoot().getData());
        assertEquals(4, tree.size());
        assertEquals(3, tree.nUniqueKeys());
        assertArrayEquals(new Integer[] {3}, tree.getAllData("Chhaya").toArray());

        assertTrue(tree.remove("Chhaya", 3));
        assertFalse(tree.lookup("Chhaya", 3));
        assertFalse(tree.lookupAny("Chhaya"));
        assertEquals("Rajesh", tree.getRoot().getKey());
        assertEquals(new Integer(5), tree.getRoot().getData());
        assertEquals(3, tree.size());
        assertEquals(2, tree.nUniqueKeys());

        assertTrue(tree.remove("Rajesh", 6));
        assertFalse(tree.lookup("Rajesh", 6));
        assertTrue(tree.lookupAny("Rajesh"));
        assertEquals("Rajesh", tree.getRoot().getKey());
        assertEquals(new Integer(5), tree.getRoot().getData());
        assertEquals(2, tree.size());
        assertEquals(2, tree.nUniqueKeys());
        assertArrayEquals(new Integer[] {5}, tree.getAllData("Rajesh").toArray());

        assertTrue(tree.remove("Rajesh", 5));
        assertFalse(tree.lookup("Rajesh", 5));
        assertFalse(tree.lookupAny("Rajesh"));
        assertEquals("Arjun", tree.getRoot().getKey());
        assertEquals(new Integer(4), tree.getRoot().getData());
        assertEquals(1, tree.size());
        assertEquals(1, tree.nUniqueKeys());

        assertTrue(tree.remove("Arjun", 4));
        assertFalse(tree.lookup("Arjun", 4));
        assertFalse(tree.lookupAny("Arjun"));
        assertEquals(0, tree.size());
        assertEquals(0, tree.nUniqueKeys());
    }

    @Test
    public void testRemoveAll() {
        testInsert();

        assertTrue(tree.removeAll("Arjun"));
        assertFalse(tree.lookupAny("Arjun"));
        assertEquals(5, tree.size());
        assertEquals(2, tree.nUniqueKeys());

        assertTrue(tree.removeAll("Chhaya"));
        assertFalse(tree.lookupAny("Chhaya"));
        assertEquals(2, tree.size());
        assertEquals(1, tree.nUniqueKeys());
        assertEquals("Rajesh", tree.getRoot().getKey());
        assertEquals(new Integer(5), tree.getRoot().getData());

        assertTrue(tree.removeAll("Rajesh"));
        assertFalse(tree.lookupAny("Rajesh"));
        assertEquals(0, tree.size());
        assertEquals(0, tree.nUniqueKeys());
    }

    @Test
    public void testIterator() {
        intTree.insert(6, 987);
        intTree.insert(6, 2738);
        intTree.insert(6, 673);
        intTree.insert(1, 123);
        intTree.insert(3, 234);
        intTree.insert(5, 676);
        intTree.insert(5, 4565);
        intTree.insert(9, 193);
        intTree.insert(5, 1234);
        intTree.insert(12, 846);
        intTree.insert(12, 543);

        iter = intTree.iterator();
        while (iter.hasNext()) {
            DAFTree<Integer, Integer>.DAFNode<Integer, Integer> node = iter.next();
            System.out.println(node);
        }
    }

    @Test (expected = NullPointerException.class)
    public void testInsertThrowsNPE() {
        tree.insert(null, 1);
        fail("Exception not thrown");

        tree.insert("Arjun", null);
        fail("Exception not thrown");
    }

    @Test (expected = NullPointerException.class)
    public void testLookupAnyThrowsNPE() {
        tree.lookupAny(null);
        fail("Exception not thrown");
    }

    @Test (expected = NullPointerException.class)
    public void testLookupThrowsNPE() {
        tree.lookup(null, 1);
        fail("Exception not thrown");

        tree.lookup("Arjun", null);
        fail("Exception not thrown");
    }

    @Test (expected = NullPointerException.class)
    public void testGetAllDataThrowsNPE() {
        tree.getAllData(null);
        fail("Exception not thrown");
    }

    @Test (expected = NullPointerException.class)
    public void testRemoveThrowsNPE() {
        tree.remove(null, 1);
        fail("Exception not thrown");

        tree.remove("Arjun", null);
        fail("Exception not thrown");
    }

    @Test (expected = NullPointerException.class)
    public void testRemoveAllThrowsNPE() {
        tree.removeAll(null);
        fail("Exception not thrown");
    }

    @Test (expected = NoSuchElementException.class)
    public void testIteratorThrowsNSEE() {
        intTree.insert(6, 987);
        intTree.insert(6, 2738);
        intTree.insert(6, 673);
        intTree.insert(1, 123);
        intTree.insert(3, 234);
        intTree.insert(5, 676);
        intTree.insert(5, 4565);
        intTree.insert(9, 193);
        intTree.insert(5, 1234);
        intTree.insert(12, 846);
        intTree.insert(12, 543);

        iter = intTree.iterator();

        while (iter.hasNext()) {
            DAFTree<Integer, Integer>.DAFNode<Integer, Integer> node = iter.next();
        }

        iter.next();
        fail("Exception not thrown");
    }
}
