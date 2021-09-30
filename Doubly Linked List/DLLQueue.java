/*
 * NAME: Arjun Sawhney
 * PID: A15499408
 */

/**
 * Queue implementation using Doubly-linked list.
 * @param <T> generic container
 *
 * @author Arjun Sawhney
 * @since 06/03/2020
 */
public class DLLQueue<T> {

    private DoublyLinkedList<T> queue;

    /**
     * Initialize the instance variables of this queue
     */
    public DLLQueue() {
        // Initialize the queue as a new DLL of generic type
        queue = new DoublyLinkedList<>();
    }

    /**
     * Return the number of elements currently stored in this queue
     *
     * @return int: the number of elements
     */
    public int size() {
        // Calls the size method of DLL
        return queue.size();
    }

    /**
     * Return true if this queue is empty, false otherwise.
     *
     * @return boolean: true if this queue is empty, false otherwise
     */
    public boolean isEmpty() {
        // Calls the isEmpty method of DLL
        return queue.isEmpty();
    }

    /**
     * Add the given data to this queue.
     *
     * @param data: the data to be enqueued to the back of the queue
     */
    public void enqueue(T data) {
        // Calls the add method of DLL. Effectively pushes the data to the back of queue
        queue.add(data);
    }

    /**
     * Remove and return the front element from this queue. Return null if this queue has no
     * elements
     *
     * @return T: the data removed off from the front of the queue
     */
    public T dequeue() {
        if (size() > 0) {
            // Returns the data removed from front of the queue by calling the remove method of DLL
            return queue.remove(0);
        } else {
            // If there are no elements in the queue, return null
            return null;
        }
    }

    /**
     * Peek and return the front element from this queue. Return null if this queue has no elements
     *
     * @return T: the data at the front of the queue
     */
    public T peek() {
        if (size() > 0) {
            // Returns the data removed from front of the queue by calling the get method of DLL
            return queue.get(0);
        } else {
            // If there are no elements in the queue, return null
            return null;
        }
    }
}
