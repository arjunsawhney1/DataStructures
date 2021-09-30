/*
 * Name: Arjun Sawhney
 */

import java.util.*;

/**
 * Generic HashTable that applies chaining to resolve collisions
 *
 * @param <T> Generic type of value
 * @author Arjun Sawhney
 * @since 06/05/2020
 */
public class HashTable<T> {
    // constants
    public static final int RESIZE_FACTOR = 2; // resize factor
    public static final int MIN_CAPACITY = 10; // minimum initial capacity
    public static final double MAX_LOAD_FACTOR = (double) 2 / 3; // maximum load factor

    // instance variables
    private LinkedList<T>[] table; // data storage
    private int nElems; // number of elements stored

    /**
     * Constructor for hash table.
     *
     * @throws IllegalArgumentException if capacity is less than the minimum
     *                                  threshold
     */
    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        if (capacity < MIN_CAPACITY) {
            // throws IllegalArgumentException if capacity is less than the minimum threshold
            throw new IllegalArgumentException();
        } else {
            // initializes a hash table as an array of LinkedLists with specified capacity.
            table = new LinkedList[capacity];
            // hash table is initially empty
            nElems = 0;
        }
    }

    /**
     * Insert the value into the hash table.
     *
     * @param value value to insert
     * @return true if the value was inserted, false if the value was already
     *         present
     * @throws NullPointerException if the value is null
     */
    public boolean insert(T value) {
        if (value == null) {
            // throws NullPointerException if the value is null
            throw new NullPointerException();
        } else if (lookup(value)) {
            // False if the value already exists in the hash table
            return false;
        } else {
            if (getLoadFactor() > MAX_LOAD_FACTOR) {
                // Before insertion, this method checks if the load factor is greater than ⅔,
                // and invokes rehash() if the condition satisfies
                rehash();
            }

            // Increment number of elements in the hash table
            nElems++;

            // Initialize LinkedList at calculated index only if it is null
            if (table[hashValue(value)] == null) {
                table[hashValue(value)] = new LinkedList<>();
            }

            // Add value to the LinkedList at calculated index
            return table[hashValue(value)].add(value);
        }
    }

    /**
     * Delete the given value from the hash table.
     *
     * @param value value to delete
     * @return true if the value was deleted, false if the value was not found
     * @throws NullPointerException if the value is null
     */
    public boolean delete(T value) {
        if (value == null) {
            // throws NullPointerException if the value is null
            throw new NullPointerException();
        } else if (lookup(value)) {
            // If the value is in the hash table, decrement number of elements and remove the
            // value from the LinkedList at the calculated index
            nElems--;
            return table[hashValue(value)].remove(value);
        } else {
            // False if the value does not exist in the hash table
            return false;
        }
    }

    /**
     * Check if the given value is present in the hash table.
     *
     * @param value value to look up
     * @return true if the value was found, false if the value was not found
     * @throws NullPointerException if the value is null
     */
    public boolean lookup(T value) {
        if (value == null) {
            // throws NullPointerException if the value is null
            throw new NullPointerException();
        } else if (table[hashValue(value)] != null) {
            // If the LinkedList is not null, check if it contains the specified value
            return table[hashValue(value)].contains(value);
        } else {
            // False if the LinkedList is null as no values exist at that index
            return false;
        }
    }

    /**
     * Get the total number of elements stored in the hash table.
     *
     * @return total number of elements
     */
    public int size() {
        return nElems;
    }

    /**
     * Get the capacity of the hash table.
     *
     * @return capacity
     */
    public int capacity() {
        // The capacity of the hash table is the length of the table array
        return table.length;
    }

    /**
     * Hash function calculated by the hash code of value.
     *
     * @param value input
     * @return hash value (index)
     */
    private int hashValue(T value) {
        // Calls the hashCode() of the value and mod by the capacity of the hash table
        // Value must be 0 or positive to be a valid index
        return Math.abs(value.hashCode() % capacity());
    }

    /**
     * Get the load factor of the hash table.
     *
     * @return load factor
     */
    private double getLoadFactor() {
        // load factor is calculated as the fraction of size / capacity of hash table
        return (double) size() / capacity();
    }

    /**
     * Double the capacity of the array and rehash all values.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        // Temporary LinkedList to store all non-null values in the hash table
        LinkedList<T> temp = new LinkedList<>();

        // Add all the non-null values from hash table to the temp list
        for (LinkedList<T> lst : table) {
            if (lst != null) {
                temp.addAll(lst);
            }
        }

        // Double the capacity of the LinkedList
        table = new LinkedList[capacity() * RESIZE_FACTOR];
        // No values in the hash table after resizing
        nElems = 0;

        // Re-insert all values in the temp array into the hash table
        for (T val: temp) {
            insert(val);
        }
    }
}
