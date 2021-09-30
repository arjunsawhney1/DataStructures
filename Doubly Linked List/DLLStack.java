/*
 * NAME: Arjun Sawhney
 * PID: A15499408
 */

/**
 * Stack implementation using Doubly-linked list.
 * @param <T> generic container
 *
 * @author Arjun Sawhney
 * @since 06/03/2020
 */
public class DLLStack<T> {

    // Instance variable
    private DoublyLinkedList<T> stack;

    /**
     * Initialize the instance variables of this stack
     */
    public DLLStack() {
        // Initialize the stack as a new DLL of generic type
        stack = new DoublyLinkedList<>();
    }

    /**
     * Return the number of elements currently stored in this stack
     *
     * @return int: the number of elements
     */
    public int size() {
        // Calls the size method of DLL
        return stack.size();
    }

    /**
     * Return true if this stack is empty, false otherwise.
     *
     * @return boolean: true if this stack is empty, false otherwise
     */
    public boolean isEmpty() {
        // Calls the isEmpty method of DLL
        return stack.isEmpty();
    }

    /**
     * Add the given data to this stack.
     *
     * @param data: the data to be pushed to the top of the stack
     */
    public void push(T data) {
        // Calls the add method of DLL. Effectively pushes the data to the top of stack
        stack.add(data);
    }

    /**
     * Remove and return the top element from this stack. Return null if this stack has no elements
     *
     * @return T: the data popped off from the top of the stack
     */
    public T pop() {
        if (size() > 0) {
            // Returns the popped data from top of the stack by calling the remove method of DLL
            return stack.remove(size() - 1);
        } else {
            // If there are no elements in the stack, return null
            return null;
        }
    }

    /**
     * Peek and return the top element from this stack. Return null if this stack has no elements
     *
     * @return T: the data at the top of the stack
     */
    public T peek() {
        if (size() > 0) {
            // Returns the data at the top of the stack by calling the get method of DLL
            return stack.get(size() - 1);
        } else {
            // If there are no elements in the stack, return null
            return null;
        }
    }
}
