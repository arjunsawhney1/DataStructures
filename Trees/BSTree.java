/*
 * Name: Arjun Sawhney
 * PID:  A15499408
 */

import java.util.*;

/**
 * Binary search tree implementation.
 * 
 * @author Arjun Sawhney
 * @since  05/07/2020
 */
public class BSTree<T extends Comparable<? super T>> implements Iterable {

    /* * * * * BST Instance Variables * * * * */

    private int nelems; // number of elements stored
    private BSTNode root; // reference to root node

    /* * * * * BSTNode Inner Class * * * * */

    /**
     * BSTNode inner class implementation.
     *
     * @author Arjun Sawhney
     * @since  05/07/2020
     */
    protected class BSTNode {

        T key;
        LinkedList<T> dataList;
        BSTNode left;
        BSTNode right;

        /**
         * A constructor that initializes the BSTNode instance variables.
         *
         * @param left     Left child
         * @param right    Right child
         * @param dataList Linked list of related info
         * @param key      BSTNode's key
         */
        public BSTNode(BSTNode left, BSTNode right, LinkedList<T> dataList, T key) {
            // Call second constructor to initialize variables
            this(left, right, key);
            // Set the BSTNode's datalist to Linked list of related info
            setDataList(dataList);
        }

        /**
         * A constructor that initializes BSTNode variables. Note: This constructor is
         * used when you want to add a key with no related information yet. In this
         * case, you must create an empty LinkedList for the node.
         *
         * @param left  Left child
         * @param right Right child
         * @param key   BSTNode's key
         */
        public BSTNode(BSTNode left, BSTNode right, T key) {
            // Create an empty linked list
            this.dataList = new LinkedList<>();
            // Initialize instance variables, call instance methods
            this.key = key;
            setleft(left);
            setright(right);
        }

        /**
         * Return the key
         *
         * @return The key
         */
        public T getKey() {
            // Return instance variable key
            return key;
        }

        /**
         * Return the left child of the node
         *
         * @return The left child of the node
         */
        public BSTNode getLeft() {
            // Return instance variable left
            return left;
        }

        /**
         * Return the right child of the node
         *
         * @return The right child of the node
         */
        public BSTNode getRight() {
            // Return instance variable right
            return right;
        }

        /**
         * Return the linked list of the node
         *
         * @return The linked list of the node
         */
        public LinkedList<T> getDataList() {
            // Return instance variable datalist
            return dataList;
        }

        /**
         * Setter for left child of the node
         *
         * @param newleft New left child
         */
        public void setleft(BSTNode newleft) {
            // Set instance variable left to newleft
            left = newleft;
        }

        /**
         * Setter for right child of the node
         *
         * @param newright New right child
         */
        public void setright(BSTNode newright) {
            // Set instance variable right to newright
            right =  newright;
        }

        /**
         * Setter for the linked list of the node
         *
         * @param newData New linked list
         */
        public void setDataList(LinkedList<T> newData) {
            // Clear all elements in datalist if it has any elements in it
            if (dataList.size() > 0) {
                dataList.clear();
            }
            // Add all the elements in newData to empty datalist
            dataList.addAll(newData);
        }

        /**
         * Append new data to the end of the existing linked list of the node
         *
         * @param data New data to be appended
         */
        public void addNewInfo(T data) {
            // Append data to end of datalist
            dataList.add(data);
        }

        /**
         * Remove 'data' from the linked list of the node and return true. If the linked
         * list does not contain the value 'data', return false.
         *
         * @param data Info to be removed
         * @return True if data was found, false otherwise
         */
        public boolean removeInfo(T data) {
            // Remove data from datalist, returns a boolean value
            return dataList.remove(data);
        }
    }

    /* * * * * BST Methods * * * * */

    /**
     * 0-arg constructor that initializes root to null and nelems to 0
     */
    public BSTree() {
        // Initialize tree root BSTNode to null and nelems to 0
        root = null;
        nelems = 0;
    }

    /**
     * Return the root of BSTree. Returns null if the tree is empty
     *
     * @return The root of BSTree, null if the tree is empty
     */
    public BSTNode getRoot() {
        // Return root BSTNode
        return root;
    }

    /**
     * Return the BST size
     *
     * @return The BST size
     */
    public int getSize() {
        // Return number of elements in tree
        return nelems;
    }

    /**
     * Helper method to find BSTNode containing a key in the tree. Returns null if not found
     *
     * @param node BSTNode to check for key
     * @param key to look for
     * @return BSTNode containing the key, or null if not found
     */
    private BSTNode findBSTNode(BSTNode node, T key) {
        // If the node is null, return null. Otherwise check for value and keep looking if not
        // found 
        if (node != null) {
            if (key.compareTo(node.getKey()) < 0) {
                // If key is smaller than key or current node, recurse with left child node
                return findBSTNode(node.getLeft(), key);
            } else if (key.compareTo(node.getKey()) > 0) {
                // If key is larger than key or current node, recurse with right child node
                return findBSTNode(node.getRight(), key);
            } else {
                // Return node if key matches key of current node
                return node;
            }
        }

        // Return null if not found, or node is null
        return null;
    }

    /**
     * Find the BSTNode at which to insert new BSTNode with key as a child
     *
     * @param node current BSTNode to compare with key
     * @param key to be inserted
     * @return BSTNode at which to insert new BSTNode with key as child
     */
    private BSTNode findInsertNode(BSTNode node, T key) {
        // If key is smaller than node and left child of node is null, return node, otherwise
        // recurse with left child of node
        if (key.compareTo(node.getKey()) < 0) {
            if (node.getLeft() != null) {
                return findInsertNode(node.getLeft(), key);
            } else {
                return node;
            }
        } else {
            // If key is larger than node and right child of node is null, return node,
            // otherwise recurse with right child of node
            if (node.getRight() != null) {
                return findInsertNode(node.getRight(), key);
            } else {
                return node;
            }
            // Since we check for same key in insert, we do not have to check that case here
        }
    }

    /**
     * Insert a key into BST
     * 
     * @param key to be inserted
     * @return true if insertion is successful and false otherwise
     * @throws NullPointerException if key is null
     */
    public boolean insert(T key) {
        // Throws NullPointerException if key is null
        if (key == null) {
            throw new NullPointerException();
        } else if (findKey(key)) {
            // Our BST does not allow duplicates. Return false if key is found in tree
            return false;
        }

        // Insert new node with key as root if BST root is null
        if (getRoot() == null) {
            root = new BSTNode(null, null, key);
        } else {
            // Find the node to insert new node with key by calling findInsertNode
            BSTNode insertNode = findInsertNode(getRoot(), key);

            // If key is smaller than found node, insert as left child, otherwise insert as right
            // child
            if (key.compareTo(insertNode.getKey()) < 0) {
                insertNode.setleft(new BSTNode(null, null, key));
            } else {
                insertNode.setright(new BSTNode(null, null, key));
            }
        }

        // Increment number of elements in tree by 1 and return true
        nelems++;
        return true;
    }

    /**
     * Return true if the 'key' is found in the tree, false otherwise
     *
     * @param key To be searched
     * @return True if the 'key' is found, false otherwise
     * @throws NullPointerException If key is null
     */
    public boolean findKey(T key) {
        // Throws NullPointerException if key is null
        if (key == null) {
            throw new NullPointerException();
        }

        // Calls findBSTNode and returns false if returned node is null, otherwise true
        return findBSTNode(getRoot(), key) != null;
    }

    /**
     * Insert 'data' into the LinkedList of the node whose key is 'key'
     *
     * @param key  Target key
     * @param data To be added to key's LinkedList
     * @throws NullPointerException     If either key or data is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public void insertData(T key, T data) {
        // Throw NullPointerException if key or ‘data’ is null
        if (key == null || data == null) {
            throw new NullPointerException();
        } else if (!findKey(key)) {
            // Throw IllegalArgumentException if ‘key’ is not found in the BST
            throw new IllegalArgumentException();
        }

        // Calls findBSTNode to find appropriate node with specified key. Calls addNewInfo to
        // append data to BSTNode's datalist
        findBSTNode(getRoot(), key).addNewInfo(data);
    }

    /**
     * Return the LinkedList of the node with key value 'key'
     *
     * @param key Target key
     * @return LinkedList of the node whose key value is 'key'
     * @throws NullPointerException     If key is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public LinkedList<T> findDataList(T key) {
        // Throw NullPointerException if key is null
        if (key == null) {
            throw new NullPointerException();
        } else if (!findKey(key)) {
            // Throw IllegalArgumentException if ‘key’ is not found in the BST
            throw new IllegalArgumentException();
        }

        // Call findBSTNode to find BSTNode with specified key in BST and return its data list
        return findBSTNode(getRoot(), key).getDataList();
    }

    /**
     * Return the height of the tree
     *
     * @return The height of the tree, -1 if BST is empty
     */
    public int findHeight() {
        // Call the recursive helper method by sending root of BST
        return findHeightHelper(getRoot());
    }

    /**
     * Helper for the findHeight method
     *
     * @param root Root node
     * @return The height of the tree, -1 if BST is empty
     */
    private int findHeightHelper(BSTNode root) {
        // By convention, the height of an empty tree is -1. This -1 also acts as the base case
        // and subtracts one from total height which counts the root as a level
        if (root == null) {
            return -1;
        } else {
            // Recursively find the height of right and left subtrees
            int leftCount = findHeightHelper(root.getLeft());
            int rightCount = findHeightHelper(root.getRight());

            // Add 1 to the maximum height between the left and right subtrees for the current
            // level of tree
            return 1 + Math.max(leftCount, rightCount);
        }
    }

    /**
     * Return the number of leaf nodes in the tree
     *
     * @return The number of leaf nodes in the tree
     */
    public int leafCount() {
        // Call the helper method for counting leaves in tree and pass tree root as parameter
        return leafCountHelper(getRoot());
    }

    /**
     * Helper for the leafCount method
     *
     * @param root Root node
     * @return The number of leaf nodes in the tree
     */
    private int leafCountHelper(BSTNode root) {
        // If the root is null, it not counted as a leaf. Acts as base case
        if (root == null) {
            return 0;
        } else if (root.getLeft() == null && root.getRight() == null) {
            // Leaves are nodes with no children so we add 1 each time we encounter such a Node
            return 1;
        } else {
            // Recursively count number of leaves in left and right sub-trees
            int leftCount = leafCountHelper(root.getLeft());
            int rightCount = leafCountHelper(root.getRight());

            // Add together number of leaves in left and right sub-trees at each level
            return leftCount + rightCount;
        }
    }


    /* * * * * BST Iterator * * * * */

    /**
     * Iterator class for BST to traverse BStree InOrder
     *
     * @author Arjun Sawhney
     * @since  05/07/2020
     */
    public class BSTree_Iterator implements Iterator<T> {
        // Stack to hold in-order elements of BST
        Stack<BSTNode> stackBST = new Stack<>();

        /**
         * Constructor that initializes the Stack with the leftPath of the root
         *
         */
        public BSTree_Iterator() {
            // Push root to stack
            BSTNode curNode = getRoot();
            stackBST.push(curNode);

            // Push entire leftpath of tree to stack
            while (curNode.getLeft() != null) {
                stackBST.push(curNode.getLeft());
                curNode = curNode.getLeft();
            }
        }

        /**
         * Returns false if the Stack is empty i.e. no more nodes left to iterate, true otherwise
         *
         * @return false if the Stack is empty, true otherwise
         */
        public boolean hasNext() {
            // Return true if Stack is not empty, meaning that stack has a next element. Return
            // false if stack is empty, there is no next element
            return !stackBST.isEmpty();
        }

        /**
         * Returns the next item in the BST. Throws NoSuchElementException if there is no next item
         *
         * @return The element stored inside the next InOrder item in the BST
         * @throws NoSuchElementException if there is no next item
         */
        public T next() {
            if (!hasNext()) {
                // Throws NoSuchElementException if there is no next item
                throw new NoSuchElementException();
            } else {
                // If the stack is not empty, pop the BSTNode from the top and return its key
                BSTNode popped = stackBST.pop();

                // push the leftPath of the tree rooted at the popped node's right node to the
                // stack
                if (popped.getRight() != null) {
                    // Push right node of popped node to stack
                    BSTNode curNode = popped.getRight();
                    stackBST.push(curNode);

                    // Push leftPath of tree rooted at right node
                    while (curNode.getLeft() != null) {
                        stackBST.push(curNode.getLeft());
                        curNode = curNode.getLeft();
                    }
                }

                // Return key of popped key
                return popped.getKey();
            }
        }
    }

    /**
     * Return new instance of BSTree_Iterator
     *
     * @return Iterator<T> an iterator of the appropriate type
     */
    public Iterator<T> iterator() {
        // Create new instance
        return new BSTree_Iterator();
    }

    /* * * * * Extra Credit Methods * * * * */

    /**
     * Method that returns the intersection of two BSTrees (Elements that can be found in both
     * BSTs).
     *
     * @param iter1: iterator for first BST
     * @param iter2: iterator for second BST
     * @return ArrayList<T> containing common values of type T between the two BSTs
     */
    public ArrayList<T> intersection(Iterator<T> iter1, Iterator<T> iter2) {
        // Lists to hold values of BSTs
        ArrayList<T> list1 = new ArrayList<>();
        ArrayList<T> list2 = new ArrayList<>();
        // List to hold common elements between the BSTs
        ArrayList<T> intersection = new ArrayList<>();

        // Append all values in first BST by iterating through the iterator
        while (iter1.hasNext()) {
            list1.add(iter1.next());
        }

        // Append all values in second BST by iterating through the iterator
        while (iter2.hasNext()) {
            list2.add(iter2.next());
        }

        // For all values in first list, check if value is in second list and append to
        // intersection list if it is
        for (T data: list1) {
            if (list2.contains(data)) {
                intersection.add(data);
            }
        }

        // Return arraylist containing common values between two BSTs
        return intersection;
    }

    public int levelCount(int level) {
        return -1;
    }
}
