/*
 * Name: Arjun Sawhney
 */

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

/**
 * Binary search tree tester.
 *
 * @author Arjun Sawhney
 * @since  05/07/2020
 */
public class BSTreeTester {
    BSTree<Integer> intTree;
    BSTree<Character> charTree;
    BSTree<String> stringTree;

    Iterator<Integer> intIter;
    Iterator<Character> charIter;
    Iterator<String> stringIter;

    @Before
    public void setUp() throws Exception {
        intTree = new BSTree<>();
        charTree = new BSTree<>();
        stringTree = new BSTree<>();
    }

    @Test
    public void testConstructor() {
        assertNull(intTree.getRoot());
        assertEquals(0, intTree.getSize());
        assertEquals(-1, intTree.findHeight());
        assertEquals(0, intTree.leafCount());

        assertNull(charTree.getRoot());
        assertEquals(0, charTree.getSize());
        assertEquals(-1, charTree.findHeight());
        assertEquals(0, charTree.leafCount());

        assertNull(stringTree.getRoot());
        assertEquals(0, stringTree.getSize());
        assertEquals(-1, stringTree.findHeight());
        assertEquals(0, stringTree.leafCount());
    }

    @Test
    public void testInsert() {
        // Testing Integers
        assertTrue(intTree.insert(2));
        assertTrue(intTree.findKey(2));
        assertEquals(0, intTree.findDataList(2).size());
        assertEquals(new Integer(2), intTree.getRoot().getKey());
        assertEquals(1, intTree.getSize());
        assertEquals(1, intTree.leafCount());
        assertEquals(0, intTree.findHeight());

        assertTrue(intTree.insert(1));
        assertTrue(intTree.findKey(1));
        assertEquals(0, intTree.findDataList(1).size());
        assertEquals(new Integer(1), intTree.getRoot().getLeft().getKey());
        assertEquals(2, intTree.getSize());
        assertEquals(1, intTree.leafCount());
        assertEquals(1, intTree.findHeight());

        assertTrue(intTree.insert(3));
        assertTrue(intTree.findKey(3));
        assertEquals(0, intTree.findDataList(3).size());
        assertEquals(new Integer(3), intTree.getRoot().getRight().getKey());
        assertEquals(3, intTree.getSize());
        assertEquals(2, intTree.leafCount());
        assertEquals(1, intTree.findHeight());

        assertTrue(intTree.insert(4));
        assertTrue(intTree.findKey(4));
        assertEquals(0, intTree.findDataList(4).size());
        assertEquals(new Integer(4), intTree.getRoot().getRight().getRight().getKey());
        assertEquals(4, intTree.getSize());
        assertEquals(2, intTree.leafCount());
        assertEquals(2, intTree.findHeight());


        // Testing Characters
        assertTrue(charTree.insert('A'));
        assertTrue(charTree.findKey('A'));
        assertEquals(0, charTree.findDataList('A').size());
        assertEquals(new Character('A'), charTree.getRoot().getKey());
        assertEquals(1, charTree.getSize());
        assertEquals(1, charTree.leafCount());
        assertEquals(0, charTree.findHeight());

        assertTrue(charTree.insert('B'));
        assertTrue(charTree.findKey('B'));
        assertEquals(0, charTree.findDataList('B').size());
        assertEquals(new Character('B'), charTree.getRoot().getRight().getKey());
        assertEquals(2, charTree.getSize());
        assertEquals(1, charTree.leafCount());
        assertEquals(1, charTree.findHeight());

        assertTrue(charTree.insert('C'));
        assertTrue(charTree.findKey('C'));
        assertEquals(0, charTree.findDataList('C').size());
        assertEquals(new Character('C'),
                charTree.getRoot().getRight().getRight().getKey());
        assertEquals(3, charTree.getSize());
        assertEquals(1, charTree.leafCount());
        assertEquals(2, charTree.findHeight());

        assertTrue(charTree.insert('D'));
        assertTrue(charTree.findKey('D'));
        assertEquals(0, charTree.findDataList('D').size());
        assertEquals(new Character('D'),
                charTree.getRoot().getRight().getRight().getRight().getKey());
        assertEquals(4, charTree.getSize());
        assertEquals(1, charTree.leafCount());
        assertEquals(3, charTree.findHeight());


        // Testing Strings
        assertTrue(stringTree.insert("Chhaya"));
        assertTrue(stringTree.findKey("Chhaya"));
        assertEquals(0, stringTree.findDataList("Chhaya").size());
        assertEquals("Chhaya", stringTree.getRoot().getKey());
        assertEquals(1, stringTree.getSize());
        assertEquals(1, stringTree.leafCount());
        assertEquals(0, stringTree.findHeight());

        assertTrue(stringTree.insert("Aaditya"));
        assertTrue(stringTree.findKey("Aaditya"));
        assertEquals(0, stringTree.findDataList("Aaditya").size());
        assertEquals("Aaditya", stringTree.getRoot().getLeft().getKey());
        assertEquals(2, stringTree.getSize());
        assertEquals(1, stringTree.leafCount());
        assertEquals(1, stringTree.findHeight());

        assertTrue(stringTree.insert("Arjun"));
        assertTrue(stringTree.findKey("Arjun"));
        assertEquals(0, stringTree.findDataList("Arjun").size());
        assertEquals("Arjun", stringTree.getRoot().getLeft().getRight().getKey());
        assertEquals(3, stringTree.getSize());
        assertEquals(1, stringTree.leafCount());
        assertEquals(2, stringTree.findHeight());

        assertTrue(stringTree.insert("Rohan"));
        assertTrue(stringTree.findKey("Rohan"));
        assertEquals(0, stringTree.findDataList("Rohan").size());
        assertEquals("Rohan", stringTree.getRoot().getRight().getKey());
        assertEquals(4, stringTree.getSize());
        assertEquals(2, stringTree.leafCount());
        assertEquals(2, stringTree.findHeight());

        assertFalse(intTree.findKey(10));
        assertFalse(charTree.findKey('Z'));
        assertFalse(stringTree.findKey("Sawhney"));
    }

    @Test (expected = NullPointerException.class)
    public void testInsertThrowsNPE() {
        intTree.insert(null);
        fail("Exception not thrown");
        charTree.insert(null);
        fail("Exception not thrown");
        stringTree.insert(null);
        fail("Exception not thrown");
    }

    @Test (expected = NullPointerException.class)
    public void testFindKeyThrowsNPE() {
        intTree.findKey(null);
        fail("Exception not thrown");
        charTree.findKey(null);
        fail("Exception not thrown");
        stringTree.findKey(null);
        fail("Exception not thrown");
    }

    @Test
    public void testInsertData() {
        testInsert();

        for (int i = 0; i < 5; i++) {
            intTree.insertData(2, i);
        }

        assertEquals(5, intTree.findDataList(2).size());
        assertEquals(new Integer(4), intTree.findDataList(2).get(4));
        assertArrayEquals(new Integer[] {0, 1, 2, 3, 4}, intTree.findDataList(2).toArray());

        for (int i = 0; i < 5; i++) {
            charTree.insertData('B', (char) (65 + i));
        }

        assertEquals(5, charTree.findDataList('B').size());
        assertEquals(new Character('E'), charTree.findDataList('B').get(4));
        assertArrayEquals(new Character[] {'A', 'B', 'C', 'D', 'E'},
                charTree.findDataList('B').toArray());

        stringTree.insertData("Arjun", "I");
        stringTree.insertData("Arjun", "hope");
        stringTree.insertData("Arjun", "this");
        stringTree.insertData("Arjun", "test");
        stringTree.insertData("Arjun", "passes");

        assertEquals(5, stringTree.findDataList("Arjun").size());
        assertEquals("I", stringTree.findDataList("Arjun").get(0));
        assertArrayEquals(new String[] {"I", "hope", "this", "test", "passes"},
                stringTree.findDataList("Arjun").toArray());
    }

    @Test (expected = NullPointerException.class)
    public void testInsertDataThrowsNPE() {
        intTree.insertData(null, 13);
        fail("Exception not thrown");
        intTree.insertData(13, null);
        fail("Exception not thrown");

        charTree.insertData(null, 'Z');
        fail("Exception not thrown");
        charTree.insertData('Z', null);
        fail("Exception not thrown");

        stringTree.insertData(null, "Arjun");
        fail("Exception not thrown");
        stringTree.insertData("Arjun", null);
        fail("Exception not thrown");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInsertDataThrowsIAE() {
        testInsert();

        intTree.insertData(5, 13);
        fail("Exception not thrown");
        charTree.insertData('E', 'Z');
        fail("Exception not thrown");
        stringTree.insertData("Sawhney", "Arjun");
        fail("Exception not thrown");
    }

    @Test (expected = NullPointerException.class)
    public void testFindDataListThrowsNPE() {
        intTree.findDataList(null);
        fail("Exception not thrown");
        charTree.findDataList(null);
        fail("Exception not thrown");
        stringTree.findDataList(null);
        fail("Exception not thrown");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFindDataListThrowsIAE() {
        testInsert();

        intTree.findDataList(5);
        fail("Exception not thrown");
        charTree.findDataList('E');
        fail("Exception not thrown");
        stringTree.findDataList("Sawhney");
        fail("Exception not thrown");
    }

    @Test
    public void testIterator() {
        testInsert();

        intIter = intTree.iterator();
        assertTrue(intIter.hasNext());
        assertEquals(new Integer(1), intIter.next());
        assertTrue(intIter.hasNext());
        assertEquals(new Integer(2), intIter.next());
        assertTrue(intIter.hasNext());
        assertEquals(new Integer(3), intIter.next());
        assertTrue(intIter.hasNext());
        assertEquals(new Integer(4), intIter.next());
        assertFalse(intIter.hasNext());

        charIter = charTree.iterator();
        assertTrue(charIter.hasNext());
        assertEquals(new Character('A'), charIter.next());
        assertTrue(charIter.hasNext());
        assertEquals(new Character('B'), charIter.next());
        assertTrue(charIter.hasNext());
        assertEquals(new Character('C'), charIter.next());
        assertTrue(charIter.hasNext());
        assertEquals(new Character('D'), charIter.next());
        assertFalse(charIter.hasNext());

        stringIter = stringTree.iterator();
        assertTrue(stringIter.hasNext());
        assertEquals("Aaditya", stringIter.next());
        assertTrue(stringIter.hasNext());
        assertEquals("Arjun", stringIter.next());
        assertTrue(stringIter.hasNext());
        assertEquals("Chhaya", stringIter.next());
        assertTrue(stringIter.hasNext());
        assertEquals("Rohan", stringIter.next());
        assertFalse(stringIter.hasNext());
    }

    @Test (expected = NoSuchElementException.class)
    public void testIteratorNextThrowsNSEE() {
        testIterator();

        intIter.next();
        fail("Exception not thrown");
        charIter.next();
        fail("Exception not thrown");
        stringIter.next();
        fail("Exception not thrown");
    }

    @Test
    public void testIntersection() {
        testInsert();

        BSTree<Integer> intTree2 = new BSTree<>();
        BSTree<Integer> intTree3 = new BSTree<>();
        BSTree<Integer> intTree4 = new BSTree<>();
        BSTree<Integer> intTree5 = new BSTree<>();
        BSTree<Integer> intTree6 = new BSTree<>();

        for (int i = 1; i < 5; i++) {
            intTree2.insert(i);
            intTree3.insert(i*5);
            intTree4.insert(i*10);
            intTree5.insert(i*15);
            intTree6.insert(i*20);
        }

        intIter = intTree.iterator();
        Iterator<Integer> intIter2 = intTree2.iterator();
        Iterator<Integer> intIter3 = intTree3.iterator();
        Iterator<Integer> intIter4 = intTree4.iterator();
        Iterator<Integer> intIter5 = intTree5.iterator();
        Iterator<Integer> intIter6 = intTree6.iterator();

        assertArrayEquals(new Integer[] {1, 2, 3, 4},
                intTree.intersection(intIter, intIter2).toArray());
        assertArrayEquals(new Integer[] {10, 20},
                intTree3.intersection(intIter3, intIter4).toArray());
        assertArrayEquals(new Integer[] {60}, intTree5.intersection(intIter5, intIter6).toArray());
    }
}
