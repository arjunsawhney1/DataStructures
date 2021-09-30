/*
 * NAME: Arjun Sawhney
 * PID: A15499408
 */

import java.util.AbstractList;

/**
 * Doubly-Linked List Implementation
 *
 * @author Arjun Sawhney
 * @since 06/03/2020
 */
public class DoublyLinkedList<T> extends AbstractList<T> {
    private int nelems;
    // Dummy head and tail nodes
    private Node head;
    private Node tail;

    /**
     * Node for chaining together to create a linked list
     */
    protected class Node {
        T data;
        Node next;
        Node prev;

        /**
         * Constructor to create singleton Node
         */
        private Node(T element) {
            // Call second constructor to create a singleton Node
            this(element, null, null);
        }

        /**
         * Constructor to create singleton link it between previous and next
         *
         * @param element  Element to add, can be null
         * @param nextNode successor Node, can be null
         * @param prevNode predecessor Node, can be null
         */
        private Node(T element, Node nextNode, Node prevNode) {
            // Store element in data
            data = element;

            // Ensure that nextNode and prevNode are not null
            if (nextNode != null) {
                // Set next pointer of this node to nextNode
                setNext(nextNode);
                // Set previous pointer of nextNode to this Node
                nextNode.setPrev(this);
            }

            if (prevNode != null) {
                // Set prev pointer of this node to prevNode
                setPrev(prevNode);
                // set next pointer of prevNode to this node
                prevNode.setNext(this);
            }
        }

        /**
         * Set the element
         *
         * @param element new element
         */
        public void setElement(T element) {
            // Store element in data
            data = element;
        }

        /**
         * Accessor to get the Nodes Element
         */
        public T getElement() {
            return data;
        }

        /**
         * Set the next node in the list
         *
         * @param n new next node
         */
        public void setNext(Node n) {
            // Store next node in next
            next = n;
        }

        /**
         * Get the next node in the list
         *
         * @return the successor node
         */
        public Node getNext() {
            return next;
        }

        /**
         * Set the previous node in the list
         *
         * @param p new previous node
         */
        public void setPrev(Node p) {
            // Store previous node in prev
            prev = p;
        }

        /**
         * Accessor to get the prev Node in the list
         *
         * @return predecessor node
         */
        public Node getPrev() {
            return prev;
        }

        /**
         * Remove this node from the list.
         * Update previous and next nodes
         */
        public void remove() {
            // Removes this node's references to other nodes in DLL
            next = null;
            prev = null;
        }
    }

    /**
     * Creates a new, empty doubly-linked list.
     */
    public DoublyLinkedList() {
        // Use first constructor of node to create dummy head
        head = new Node(null);
        // Use second constructor to create dummy tail node and set pointers
        tail = new Node(null, null, head);
        // Initialize DLL with 0 elements despite dummy head and tail nodes
        nelems = 0;
    }

    /**
     * Add an element to the end of the list
     *
     * @param element data to be added
     * @return whether or not the element was added
     * @throws NullPointerException if data received is null
     */
    @Override
    public boolean add(T element) throws NullPointerException {
        if (element == null) {
            // We throw a NullPointerException if element received is null
            throw new NullPointerException();
        }

        // Create a new node using second constructor and set connections.
        Node newNode = new Node(element, tail, tail.getPrev());
        // Increment number of elements by 1 each time element is added to DLL
        nelems++;

        // Adding element to end is always successful unless element is null
        return true;
    }

    /**
     * Inserts a node containing the specified data at the desired location
     *
     * @param index location at which to add the node containing specified data
     * @param element data to be added
     * @throws NullPointerException if data received is null
     * @throws IndexOutOfBoundsException if index is outside the range [0, size]
     */
    @Override
    public void add(int index, T element)
            throws IndexOutOfBoundsException, NullPointerException {
        if (index < 0 || index > size()) {
            // throws IndexOutOfBoundsException - index is outside the range [0, size]
            throw new IndexOutOfBoundsException();
        } else if (element == null) {
            // throws NullPointerException - if data received is null
            throw new NullPointerException();
        }

        // If DLL is empty or inserting at the end, we call add() to handle insertion
        if (isEmpty() || index == size()) {
            add(element);
        } else {
            // If inserting anywhere in middle, we must first get the Node at the specified index
            Node curNode = getNth(index);
            // Create newNode with element and all set pointers
            Node newNode = new Node(element, curNode, curNode.getPrev());
            // Increment number of elements by 1 each time element is added to DLL
            nelems++;
        }
    }

    /**
     * Clear the linked list
     */
    @Override
    public void clear() {
        // Keep removing elements from the end of the list till size of list is 0
        while (size() > 0) {
            remove(size() - 1);
        }
    }

    /**
     * Determine if the list contains the data element anywhere in the list.
     *
     * @param element: generic object which must be cast to the required type. Must check if
     *               contained anywhere in the list
     * @return boolean: true if the list contains the element, false otherwise
     */
    @Override
    public boolean contains(Object element) {
        // Since we cannot change the method signature when overriding a method we must pass in
        // the parameter as an Object and then cast it to type T
        T data = (T) element;

        // Loop through elements of list and check if it matches the element. Return true if found
        for (T t : this) {
            if (t == data) {
                return true;
            }
        }

        // Return false if the search was unsuccessful
        return false;
    }

    /**
     * Retrieves the element stored with a given index on the list.
     *
     * @param index: the index at which to access the data contained in the node
     * @return T: the element of type T inside the accessed node
     * @throws IndexOutOfBoundsException if index is outside the range [0, size - 1]
     */
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        // We throw an IndexOutOfBoundsException if index is outside the range [0, size - 1]
        if (index < 0 || index > size() - 1) {
            throw new IndexOutOfBoundsException();
        }

        // Get the node at the specified index and return the data contained in that node
        return getNth(index).getElement();
    }

    /**
     * Helper method to get the Nth node in our list
     *
     * @param index: The index specifies the Nth Node to retrieve
     * @return Node: The node at the specified index to be returned
     */
    private Node getNth(int index) {
        // Begin traversal from the first Node in the list
        Node curNode = head.getNext();
        int count = 0;

        // Traverse the list by storing the next Node in curNode until the index is reached
        while (count < index) {
            curNode = curNode.getNext();
            count++;
        }

        // Return the node at the specified index
        return curNode;
    }

    /**
     * Determine if the list empty
     *
     * @return boolean: true if size is 0, false otherwise
     */
    @Override
    public boolean isEmpty() {
        // Returns boolean value. Checks if size is 0 or not
        return size() == 0;
    }

    /**
     * Remove the element from position index in the list
     *
     * @param index: the index at which to remove the element from the DLL
     * @return T: the removed element of type T
     * @throws IndexOutOfBoundsException if index is outside the range [0, size - 1]
     */
    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size() - 1) {
            // throws IndexOutOfBoundsException - index is outside the range [0, size - 1]
            throw new IndexOutOfBoundsException();
        }

        // Get the node at the specified index
        Node curNode = getNth(index);
        // We set the prev pointer of the Node after curNode to point to the Node before curNode
        curNode.getNext().setPrev(curNode.getPrev());
        // We set the next pointer of the Node before curNode to point to the Node after curNode
        curNode.getPrev().setNext(curNode.getNext());
        // Although curNode will now be garbage collected, we remove it's references to other
        // nodes in the list for good measure
        curNode.remove();
        // Decrement the number of elements in the list by 1
        nelems--;

        //Returns the value stored in the removed node
        return curNode.getElement();
    }

    /**
     * Set the value of an element at a certain index in the list.
     *
     * @param index: the index at which to set the value of element in list
     * @param element: the value to store in the element at specified index
     * @return T: the old value of data at the specified position
     * @throws IndexOutOfBoundsException if index is outside the range [0, size - 1]
     * @throws NullPointerException if data received is null
     */
    @Override
    public T set(int index, T element)
            throws IndexOutOfBoundsException, NullPointerException {
        // We throw an IndexOutOfBoundsException if index is outside the range [0, size - 1] and
        // we throw a NullPointerException if element is null
        if (index < 0 || index > size() - 1) {
            throw new IndexOutOfBoundsException();
        } else if (element == null) {
            throw new NullPointerException();
        }

        // Obtain the node at the specified index. Store it's old value and then change it to the
        // new value specified
        Node curNode = getNth(index);
        T data = curNode.getElement();
        curNode.setElement(element);

        //Return the old value at index
        return data;
    }

    /**
     * Retrieves the amount of elements that are currently on the list.
     *
     * @return int: The number of elements in the list
     */
    @Override
    public int size() {
        return nelems;
    }

    /**
     * String representation of this list in the form of:
     * "[(head) -> elem1 -> elem2 -> ... -> elemN -> (tail)]"
     *
     * @return String: the string containing the representation of the list in the specified form
     */
    @Override
    public String toString() {
        // StringBuilder to which we append elements of list
        StringBuilder s = new StringBuilder();
        // Representation always begins with the head
        s.append("[(head) -> ");

        // Get the element at each index of the list and add an arrow to point to the next element
        for (int i = 0; i < size(); i++) {
            s.append(get(i));
            s.append(" -> ");
        }

        // Every DLL representation will end with the tail
        s.append("(tail)]");

        // Convert StringBuilder to a String containing the representation of the list
        return s.toString();
    }

    // ==================== EXTRA CREDIT ==================== */

    /**
     * Inserts another linked list of the same type into this one
     *
     * @param index: the index at which to insert the second linked list
     * @param otherList: the linked list to be inserted
     * @throws IndexOutOfBoundsException: if index is outside the range [0, size]
     */
    public void splice(int index, DoublyLinkedList<T> otherList) throws IndexOutOfBoundsException {
        if (index < 0 || index > size()) {
            // We throw an IndexOutOfBoundsException if index is outside the range [0, size]
            throw new IndexOutOfBoundsException();
        }

        // We must handle the case when index == size of list differently
        if (index == size()) {
            // Get the last Node in list
            Node curNode = getNth(index - 1);

            // Set the prev pointer of the first Node in otherList to curNode
            otherList.getNth(0).setPrev(curNode);
            // Set the next pointer of the last node in otherList to the tail node
            otherList.getNth(otherList.size() - 1).setNext(curNode.getNext());

            // Set the next pointer of curNode to the first Node in otherList
            curNode.setNext(otherList.getNth(0));
        } else {
            // Get the node at the specified index
            Node curNode = getNth(index);

            // Set the prev pointer of the first Node in otherList to the node before curNode
            otherList.getNth(0).setPrev(curNode.getPrev());
            // Set the next pointer of the last Node in otherList to curNode
            otherList.getNth(otherList.size() - 1).setNext(curNode);

            // Set the next pointer of the Node before curNode to first Node in otherList
            curNode.getPrev().setNext(otherList.getNth(0));
            // Set the prev pointer of curNode to the last Node in otherList
            curNode.setPrev(otherList.getNth(otherList.size() - 1));
        }

        // Increase the number of elements by number of elements in otherList
        nelems += otherList.size();
    }

    /**
     * Determine the starting indices that match the subSequence
     *
     * @param subsequence: the subsequence to check for in the DLL
     * @return int[]: the list of starting indices that match the subsequence in the DLL. If
     * none, return an empty list
     */
    public int[] match(DoublyLinkedList<T> subsequence) {

        //A list to hold all the starting indices found
        DoublyLinkedList<Integer> indices = new DoublyLinkedList<>();
        // To count the number of matches with the subsequence at each index
        int count;

        // Loop through each index of the list
        for (int i = 0; i < size(); i++) {
            // If the value at that index equals the first element of the subsequence, begin
            // iterating through elements of subsequence to look for matches
            if (get(i) == subsequence.get(0)) {
                // Reset count to 0 for each index we begin with
                count = 0;
                for (int j = 0; j < subsequence.size(); j++) {
                    // If the subsequence can fit in the remaining elements of DLL
                    if (i + j < size()) {
                        if (get(i + j) == subsequence.get(j)) {
                            // Increment the number of matches with subsequence if equal
                            count++;
                        }
                    }
                }
                // If the number of matches is equal to the size of the subsequence, add that
                // index to the list
                if (count == subsequence.size()) {
                    indices.add(i);
                }
            }
        }

        // Array Conversion
        int[] startingIndices = new int[indices.size()];

        for (int i = 0; i < indices.size(); i++) {
            startingIndices[i] = indices.get(i);
        }

        // Return list containing matching indices
        return startingIndices;
    }
}
