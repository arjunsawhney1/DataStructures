/*
 * Name: Arjun Sawhney
 */

import java.util.*;

/**
 * Hybrid data structure called FADAF (Fast Access DAF) that allows fast add, remove, lookup
 * using a DAFTree and HashTable
 *
 * @param <K> Generic type of key
 * @param <D> Generic type of data
 * @author Arjun Sawhney
 * @since 06/06/2020
 */
public class FADAF<K extends Comparable<? super K>, D> {
    // instance variables
    DAFTree<K, D> tree; // DAFTree instance
    Iterator<DAFTree<K, D>.DAFNode<K, D>> iter; // Iterator for DAFTree which allows in-order
    // traversal
    HashTable<DAFTree<K, D>.DAFNode<K, D>> table; // HashTable instance to store DAFNodes

    /**
     * Constructor for FADAF.
     *
     * @param capacity initial capacity
     * @throws IllegalArgumentException if capacity is less than the minimum
     *                                  threshold
     */
    public FADAF(int capacity) {
        // Instantiate DAFTree and table with given capacity
        tree = new DAFTree<>();
        // IllegalArgumentException thrown by HashTable constructor if capacity < 10
        table = new HashTable<>(capacity);
    }

    /**
     * Returns the total number of key-data pairs stored.
     *
     * @return count of key-data pairs
     */
    public int size() {
        // Size of FADAF is number of nodes in HashTable
        return table.size();
    }

    /**
     * Returns the total number of unique keys stored.
     *
     * @return count of unique keys
     */
    public int nUniqueKeys() {
        // Number of unique keys in FADAF is the number of unique keys in DAFTree
        return tree.nUniqueKeys();
    }

    /**
     * Insert the given key-data pair.
     *
     * @param key  key to insert
     * @param data data to insert
     * @return true if the pair is inserted, false if the pair was already present
     * @throws NullPointerException if key or data is null
     */
    public boolean insert(K key, D data) {
        // NullPointerException thrown by DAFTree insert if key or data is null
        if (tree.insert(key, data) != null) {
            // If inserting key and data into the DAFTree was successful, insert new instance
            // of DAFNode with key and data into hash table
            return table.insert(tree.new DAFNode<K, D>(key, data));
        } else {
            // If insertion failed in tree, return false
            return false;
        }
    }

    /**
     * Remove all key-data pairs that share the given key from the FADAF.
     *
     * @param key key to remove
     * @return true if at least 1 pair is removed, false otherwise
     * @throws NullPointerException if the key is null
     */
    public boolean removeAll(K key) {
        // NullPointerException is thrown by lookupAny
        if (lookupAny(key)) {
            // find first node containing key in DAFTree
            DAFTree<K, D>.DAFNode<K, D> node = tree.lookupAnyHelper(tree.getRoot(), key);

            // Remove each node containing key from FADAF by checking if node with key exists
            // in the DAFTree after removal
            while (node != null) {
                // remove method of FADAF to remove from both hash table and DAFTree
                remove(node.getKey(), node.getData());
                node = tree.lookupAnyHelper(tree.getRoot(), key);
            }

            // Return true as at least one node has been deleted from FADAF
            return true;
        } else {
            // If no nodes with key exist in the DAFTree, return false
            return false;
        }
    }

    /**
     * Remove the specified pair from the FADAF.
     *
     * @param key  key of the pair to remove
     * @param data data of the pair to remove
     * @return true if this pair is removed, false if this pair is not present
     * @throws NullPointerException if key or data is null
     */
    public boolean remove(K key, D data) {
        // NullPointerException is thrown by DAFTree remove if key or data is null
        if (tree.remove(key, data)) {
            // If removal from the tree was successful, remove from the hash table by creating a
            // new instance of DAFNode and checking if an equal node exists in hash table
            return table.delete(tree.new DAFNode<K, D>(key, data));
        } else {
            // If removal from DAFtree was unsuccessful, return false
            return false;
        }
    }

    /**
     * Check if any pair with the given key is stored.
     *
     * @param key key to lookup
     * @return true if any pair is found, false otherwise
     * @throws NullPointerException if the key is null
     */
    public boolean lookupAny(K key) {
        // Call lookupAny function of DAFTree
        // DAFTree lookupAny throws NullPointerException if the key is null
        return tree.lookupAny(key);
    }

    /**
     * Check if a pair with the given key and data is stored.
     *
     * @param key  key of the pair to lookup
     * @param data data of the pair to lookup
     * @return true if the pair is found, false otherwise
     * @throws NullPointerException if key or data is null
     */
    public boolean lookup(K key, D data) {
        // Call lookup function of hash table
        // Hash table lookup throws NullPointerException if key or data is null
        return table.lookup(tree.new DAFNode<K, D>(key, data));
    }

    /**
     * Return a LinkedList of data paired with the given key.
     *
     * @param key target key
     * @return a list of data
     * @throws NullPointerException if the key is null
     */
    public LinkedList<D> getAllData(K key) {
        // Call getAllData function of DAFTree
        // DAFTree getAllData throws NullPointerException if the key is null
        return tree.getAllData(key);
    }

    /**
     * Return a LinkedList of all keys (including duplicates) in ascending order.
     *
     * @return a list of all keys, empty list if no keys stored
     */
    public LinkedList<K> getAllKeys() {
        // Create a LinkedList instance to hold all keys in the DAFTree
        LinkedList<K> keys = new LinkedList<>();
        // Create new instance of DAFTree Iterator to traverse DAFTree in-order
        iter = tree.iterator();

        // By traversing the DAFTree in-order, we add keys of each node in ascending order to keys
        while (iter.hasNext()) {
            keys.add(iter.next().getKey());
        }

        // Return the LinkedList containing the keys (including duplicates) in ascending order
        return keys;
    }

    /**
     * Return the minimum key stored.
     *
     * @return minimum key, or null if no keys stored
     */
    public K getMinKey() {
        // Call getAllKeys and return the first element in the list as it is the smallest key in
        // the DAFTree
        return getAllKeys().getFirst();
    }

    /**
     * Return the maximum key stored.
     *
     * @return maximum key, or null if no keys stored
     */
    public K getMaxKey() {
        // Call getAllKeys and return the last element in the list as it is the smallest key in
        // the DAFTree
        return getAllKeys().getLast();
    }
}
