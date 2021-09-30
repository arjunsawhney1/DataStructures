/*
 * Name: Arjun Sawhney
 * PID: A15499408
 */

/**
 * An implementation of a bloom filter prototype.
 */
public class BloomFilterJunior {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 50;
    private static final int HASH_THREE_LEFT_SHIFT = 8;
    private static final int RIGHT_SHIFT = 27; // Horners hashing method
    private static final int LEFT_SHIFT = 5; // Horners hashing method

    /* Instance variables */
    private boolean[] table;

    /**
     * Initialize a BloomFilterJunior with a table (boolean array) with a given capacity.
     *
     * @param capacity the capacity of the bloom filter
     * @throws IllegalArgumentException if capacity < 50 (minimum initial capacity)
     */
    public BloomFilterJunior(int capacity) {
        if (capacity < MIN_INIT_CAPACITY) {
            // throws IllegalArgumentException if capacity < 50
            throw new IllegalArgumentException();
        }

        // Initialize boolean table of specified capacity
        table = new boolean[capacity];
    }

    /**
     * Insert element ​value ​in the BloomFilterJunior.
     *
     * @param value string to insert into bloom filter
     * @throws NullPointerException if value is null
     */
    public void insert(String value) {
        if (value == null) {
            // throw ​NullPointerException​ if value is null
            throw new NullPointerException();
        }

        // Set value at all three indices given by three hash functions to true
        // Likelihood of false positive is there, but reduced since there are 3 hash functions
        table[hashOne(value)] = true;
        table[hashTwo(value)] = true;
        table[hashThree(value)] = true;
    }

    /**
     * Check if ​value ​is a member of the BloomFilterJunior.
     *
     * @param value string to search for
     * @return boolean: true if value at all three indices is true, else false
     * @throws NullPointerException if value is null
     */
    public boolean lookup(String value) {
        if (value == null) {
            // throw ​NullPointerException​ if value is null
            throw new NullPointerException();
        }

        // Returns true if value at all three indices is true, else false
        // Can be no false negative, only false positives
        return table[hashOne(value)] && table[hashTwo(value)] && table[hashThree(value)];
    }

    /**
     * Hash function 1.
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashOne(String value) {
        // Initialize value to 0
        int hashVal = 0;

        // Horner's hash function covered in lecture, depends on table capacity
        for (int j = 0; j < value.length(); j++) {
            int letter = value.charAt(j);
            hashVal = (hashVal * RIGHT_SHIFT + letter) % table.length;
        }

        // Return the index of first hash function
        return hashVal;
    }

    /**
     * Hash function 2.
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashTwo(String value) {
        // Initialize hash index to 0
        int hashVal = 0;

        // Simplified CRC hash algorithm covered in lecture involves shifting bits
        for (int i = 0; i < value.length(); i++) {
            int leftShiftedVal = hashVal << LEFT_SHIFT;
            int rightShiftedVal = hashVal >>> RIGHT_SHIFT;

            hashVal = (leftShiftedVal | rightShiftedVal) ^ value.charAt(i);
        }

        // Return absolute value of the index, depends on capacity of HashTable
        return Math.abs(hashVal % table.length);
    }

    /**
     * Hash function 3.
     *
     * @param value string to hash
     * @return hash value
     */
    private int hashThree(String value) {
        // Base-256 String
        int hash = 0;
        char[] chars = value.toCharArray();
        for (char c : chars) {
            hash = ((hash << HASH_THREE_LEFT_SHIFT) + c) % table.length;
        }
        return Math.abs(hash % table.length);
    }
}
