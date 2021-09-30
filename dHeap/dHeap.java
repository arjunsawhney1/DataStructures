/*
 * Name: Arjun Sawhney
 * PID:  A15499408
 */

import java.util.*;

/**
 * Class to create a generic dHeap using a heap-structured array by implementing dHeapInterface.
 *
 * @param <T> Generic type
 *
 * @author Arjun Sawhney
 * @since  05/15/2020
 */
public class dHeap<T extends Comparable<? super T>> implements dHeapInterface<T> {
    // Instance variables
    private T[] heap; // heap array
    private int d; // branching factor
    private int nelems; // number of elements
    private boolean isMaxHeap; // boolean to indicate whether heap is max or min

    // Static constants
    private static final int DEFAULT_SIZE = 6; // Default size of heap
    private static final int BINARY = 2; // d for Binary heap
    private static final int DOUBLE = 2; // Resize factor

    /**
     * Initializes a binary max-dHeap with capacity = 6.
     */
    @SuppressWarnings("unchecked")
    public dHeap() {
        // Calls third constructor to initialize a binary max-dHeap with capacity = 6
        this(BINARY, DEFAULT_SIZE, true);
    }

    /**
     * Initializes a binary max-dHeap with a given initial capacity.
     *
     * @param heapSize The initial capacity of the heap.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int heapSize) {
        // Calls third constructor to initialize a binary max-dHeap with specified capacity
        this(BINARY, heapSize, true);
    }

    /**
     * Initializes a d-ary heap (with a given value for d), with a given initial
     * capacity.
     *
     * @param d         The number of child nodes each node in the heap should have.
     * @param heapSize  The initial capacity of the heap.
     * @param isMaxHeap indicates whether the heap should be max or min.
     * @throws IllegalArgumentException if d is less than one.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int d, int heapSize, boolean isMaxHeap) throws IllegalArgumentException {
        if (d < 1) {
            // throws IllegalArgumentException if d is less than one
            throw new IllegalArgumentException();
        }

        // Initialize instance variables according to parameters
        this.d = d;
        this.isMaxHeap = isMaxHeap;
        // Number of elements is initialized to 0
        nelems = 0;
        // Initialize heap as an array with specified capacity
        heap = (T[]) new Comparable[heapSize];
    }

    /**
     * Returns the number of elements stored in the heap.
     *
     */
    @Override
    public int size() {
        // Returns the number of elements stored in the heap
        return nelems;
    }

    /**
     * Adds the given data to the heap.
     *
     * @param data to be added to the heap.
     * @throws NullPointerException  if data is null.
     */
    @Override
    public void add(T data) throws NullPointerException {
        if (data == null) {
            // throws NullPointerException  if data is null
            throw new NullPointerException();
        }

        // Append data to the end of the heap
        heap[size()] = data;
        // Bubble up the data to the appropriate level in the heap
        bubbleUp(size());
        // Increase the number of elements in the heap by 1
        nelems++;

        // double the size of heap array when it is full by calling the resize function
        if (size() == heap.length) {
            resize();
        }
    }

    /**
     * Returns and removes the root element from the heap.
     *
     * @return T the removed root element of the heap.
     * @throws NoSuchElementException if the heap is empty.
     */
    @Override
    public T remove() throws NoSuchElementException {
        if (size() == 0) {
            // throws NoSuchElementException if the heap is empty
            throw new NoSuchElementException();
        }

        // Store the root of heap before it is removed
        T root = heap[0];
        // Replace root with last element in the heap
        heap[0] = heap[size() - 1];
        // Decrease number of elements by 1, removes the old root from heap
        nelems--;
        // Trickle down the new root to the appropriate level in the heap
        trickleDown(0);

        // Return the old root
        return root;
    }

    /**
     * Clear all elements in the heap.
     *
     */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        // Clear all elements in the heap by assigning heap to a new array of same size
        heap = (T[]) new Comparable[heap.length];
        // Set number of elements equal to 0
        nelems = 0;
    }

    /**
     * Returns the root element of the heap.
     *
     * @return T the root element of the heap.
     * @throws NoSuchElementException if the heap is empty.
     */
    public T element() throws NoSuchElementException {
        if (size() == 0) {
            // throws NoSuchElementException if the heap is empty
            throw new NoSuchElementException();
        }

        // Root of the heap exists at the 0th index
        return heap[0];
    }

    /**
     * Trickles down the element at specified index to its appropriate level in the heap
     * and position in the heap array.
     *
     * @param index at which element to be trickled down exists.
     */
    private void trickleDown(int index) {
        // Get the index of the first child of the element at the specified index
        int firstChildIndex = firstChild(index);
        // Get the value stored in the heap array at the specified index
        T value = heap[index];

        // Trickle down element until end of heap
        while (firstChildIndex < size()) {
            // Keep track of swapValue and swapIndex in heap. Initialize with value at index
            T swapValue = value;
            int swapIndex = -1;

            // Loop to find the maximum or minimum among all d children of node using
            // compareToHelper which checks if heap is max-dHeap or min-dHeap and returns value
            // accordingly
            for (int i = 0; i < d && i + firstChildIndex < size(); i++) {
                if (compareToHelper(heap[i + firstChildIndex], swapValue) > 0) {
                    // Update swapValue and swapIndex if greater or smaller value is found among
                    // children depending on whether heap is maxheap or min-dHeap
                    swapValue = heap[i + firstChildIndex];
                    swapIndex = i + firstChildIndex;
                }
            }

            // Break from the loop when none of the children are greater or smaller than value at
            // index
            if (swapValue.compareTo(value) == 0) {
                break;
            } else {
                // If any of the children are greater or smaller  than value at index, swap the
                // values and store swapIndex in index depending on whether heap is maxheap or
                // min-dHeap
                heap[swapIndex] = heap[index];
                heap[index] = swapValue;
                index = swapIndex;

                // Find the children of the maximum or minimum child node for the next iteration
                // depending on whether heap is maxheap or min-dHeap
                firstChildIndex = firstChild(index);
            }
        }
    }

    /**
     * Bubbles up the node at specified index to its correct level in the heap and position in
     * the array
     *
     * @param index at which to find node to be bubbled up.
     */
    private void bubbleUp(int index) {
        // Iterate until the root of the heap is reached
        while (index > 0) {
            // Find the parent index of the node at given index
            int parentIndex = parent(index);
            // Call the compareToHelper method to decide whether to swap values at index
            // and parent index depending on whether the heap is a min-dHeap or a maxheap
            if (compareToHelper(heap[index], heap[parentIndex]) > 0) {
                T temp = heap[index];
                heap[index] = heap[parentIndex];
                heap[parentIndex] = temp;
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    /**
     * Double the capacity of array.
     *
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        // Create a temporary array of the old capacity of the heap and store all of the heaps
        // elements in it
        int size = heap.length;
        T[] temp = (T[]) new Comparable[size];
        System.arraycopy(heap, 0, temp, 0, size);

        // Double the capacity of the heap array and copy the values of the temp array back into
        // the heap
        heap = (T[]) new Comparable[size * DOUBLE];
        System.arraycopy(temp, 0, heap, 0, size);
    }

    /**
     * Returns the index of the parent of node at given index
     *
     * @param index of the child node.
     * @return int The index of the parent node.
     */
    private int parent(int index) {
        // Formula to calculate the index of the parent node
        return Math.floorDiv(index - 1, d);
    }

    /**
     * Returns the index of the first child of node at given index.
     *
     * @param index of the parent node.
     * @return int The index of the first child.
     */
    private int firstChild(int index) {
        // Formula to calculate the index of the first child of parent node
        return d * index + 1;
    }

    /**
     * Helper method that wraps around compareTo() to reduce the amount of code needed to 
     * differentiate between a min-dHeap and a max-dHeap
     *
     * @param a the first value to compare.
     * @param b the second value to compare.
     * @return int indicating if the value is greater, smaller, or equal for min and max dHeaps
     */
    private int compareToHelper(T a, T b) {
        if (isMaxHeap) {
            // compareTo function should work as expected for a max-dHeap
            return a.compareTo(b);
        } else {
            // compareTo function should be return -1 if a > b, 1 if a < b and 0 if a == b for a
            // min-dHeap
            return -1 * a.compareTo(b);
        }
    }
}
