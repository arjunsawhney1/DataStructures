/*
 * Name: Arjun Sawhney
 * PID:  A15499408
 */

/**
 * MyPriorityQueue class that uses the dHeap class to implement the priority queue operations
 *
 * @param <T> Generic type
 *
 * @author Arjun Sawhney
 * @since  05/15/2020
 */
public class MyPriorityQueue<T extends Comparable<? super T>> {
    // Instance variable defining the pQueue as a dHeap object
    private dHeap<T> pQueue;
    // Static constant indicating d in the dHeap
    private static final int D = 5;

    /**
     * Constructor that creates a new priority queue
     * 
     * @param initialSize the given size
     */
    public MyPriorityQueue(int initialSize) {
        // Initializing pQueue as a 5-ary max-heap of given size
        pQueue = new dHeap<>(D, initialSize, true);
    }

    /**
     * Inserts an element into the Priority Queue. The element received cannot be
     * null.
     *
     * @param element Element to be inserted.
     * @throws NullPointerException if the element received is null.
     * @return returns true
     */
    public boolean offer(T element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException();
        }

        // Calls the add() method of dHeap class to insert element into its appropriate position
        // in the priority Queue
        pQueue.add(element);
        // Returns true after element is inserted. Adding should not fail as heap resizes
        // whenever it is full
        return true;
    }

    /**
     * Retrieves the head of this Priority Queue (largest element), or null if the
     * queue is empty.
     *
     * @return The head of the queue (largest element), or null if queue is empty.
     */
    public T poll() {
        // Calls the remove method of dHeap class as the root value of dHeap is the largest value
        // when dHeap is a max-heap
        return pQueue.remove();
    }

    /**
     * Clears the contents of the queue
     */
    public void clear() {
        // Calls the clear() method of dHeap class
        pQueue.clear();
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if
     * this queue is empty.
     * 
     * @return the next item to be removed, null if the queue is empty
     */
    public T peek() {
        // Calls the element() method of the dHeap class as front of pQueue should be the root
        // value of dHeap
        return pQueue.element();
    }

    /**
     * Return true is the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        // Checks if size of dHeap object is 0 or not
        return pQueue.size() == 0;
    }

}
