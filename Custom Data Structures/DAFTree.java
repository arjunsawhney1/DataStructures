/*
 * Name: Arjun Sawhney
 */

import java.util.*;

/**
 * Modified generic Binary Search Tree that supports storing duplicates
 *
 * @param <K> Generic type of key
 * @param <D> Generic type of data
 * @author Arjun Sawhney
 * @since 06/05/2020
 */
@SuppressWarnings("rawtypes")
public class DAFTree<K extends Comparable<? super K>, D> implements Iterable {
    // instance variables
    private DAFNode<K, D> root; // root node
    private int nElems; // number of elements stored
    private int nKeys; // number of unique keys stored

    /**
     * Generic Node to hold a key-data pair
     *
     * @param <K> Generic type of key
     * @param <D> Generic type of data
     */
    protected class DAFNode<K extends Comparable<? super K>, D> {
        K key;
        D data;
        DAFNode<K, D> left, dup, right; // children
        DAFNode<K, D> par; // parent

        /**
         * Initializes a DAFNode object.
         *
         * @param key  key of the node
         * @param data data of the node
         * @throws NullPointerException if key or data is null
         */
        public DAFNode(K key, D data) {
            if (key == null || data == null) {
                // throws NullPointerException if key or data is null
                throw new NullPointerException();
            } else {
                // initializes a DAFNode with specified key and data
                this.key = key;
                this.data = data;
            }
        }

        /**
         * Return the key
         *
         * @return The key
         */
        public K getKey() {
            return key;
        }

        /**
         * Return the data
         *
         * @return The data
         */
        public D getData() {
            return data;
        }

        /**
         * Return the left child of the node
         *
         * @return The left child of the node
         */
        public DAFNode<K, D> getLeft() {
            return left;
        }

        /**
         * Return the right child of the node
         *
         * @return The right child of the node
         */
        public DAFNode<K, D> getRight() {
            return right;
        }

        /**
         * Return the duplicate child of the node
         *
         * @return The duplicate child of the node
         */
        public DAFNode<K, D> getDup() {
            return dup;
        }

        /**
         * Return the parent of the node
         *
         * @return The parent of the node
         */
        public DAFNode<K, D> getPar() {
            return par;
        }

        /**
         * Setter for left child of the node
         *
         * @param newLeft New left child
         */
        private void setLeft(DAFNode<K, D> newLeft) {
            left = newLeft;
        }

        /**
         * Setter for right child of the node
         *
         * @param newRight New right child
         */
        private void setRight(DAFNode<K, D> newRight) {
            right =  newRight;
        }

        /**
         * Setter for dup child of the node
         *
         * @param newDup New duplicate child
         */
        private void setDup(DAFNode<K, D> newDup) {
            dup =  newDup;
        }

        /**
         * Setter for parent of the node
         *
         * @param newPar new parent node
         */
        private void setPar(DAFNode<K, D> newPar) {
            par =  newPar;
        }

        /**
         * String representation of DAFNode
         *
         * @return String representation of DAFNode
         */
        public String toString() {
            // Formatting String representation
            return "Key: " + this.getKey() + ", Data: " + this.getData();
        }

        /**
         * Check if obj equals to this object.
         *
         * @param obj object to compare with
         * @return true if equal, false otherwise
         */
        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object obj) {
            // Objects are equal if both are DAFNodes of the same type with the same key and data
            if (this.getClass() == obj.getClass()) {
                return this.getKey() == ((DAFNode<K, D>) obj).getKey()
                        && this.getData() == ((DAFNode<K, D>) obj).getData();
            } else {
                return false;
            }
        }

        /**
         * Returns the hash value of current node.
         *
         * @return hash value
         */
        @Override
        public int hashCode() {
            // hash code of DAFNode is the sum of hash codes of key and data
            return key.hashCode() + data.hashCode();
        }

        /* PROVIDED HELPERS, MODIFY WITH CAUTION! */

        /**
         * Public helper to swap all DAFNode references of this and the given node.
         *
         * @param other Node to swap with this
         */
        public void swapReferencesWith(DAFNode<K, D> other) {
            DAFNode<K, D> temp = this.left;
            this.left = other.left;
            other.left = temp;
            if (this.left != null) {
                this.left.par = this;
            }
            if (other.left != null) {
                other.left.par = other;
            }

            temp = this.right;
            this.right = other.right;
            other.right = temp;
            if (this.right != null) {
                this.right.par = this;
            }
            if (other.right != null) {
                other.right.par = other;
            }

            // no swap of dup as dup is coupled with the node

            temp = this.par;
            this.changeParentTo(other, other.par);
            other.changeParentTo(this, temp);
        }

        /**
         * Public helper to change this node's par to the given parent. The given child
         * is used to determine which child (left, right, dup) of the given parent this
         * node should be. Only the connection between this and the given parent will
         * update. Does nothing if the given child is not a child of parent.
         *
         * @param child  Old child of the given parent
         * @param parent New parent of this node
         * @throws NullPointerException if child is null
         */
        public void changeParentTo(DAFNode<K, D> child, DAFNode<K, D> parent) {
            if (child == null)
                throw new NullPointerException();

            if (parent == null) {
                this.par = null;
                return;
            }

            if (parent.left == child) {
                parent.left = this;
                this.par = parent;
            } else if (parent.right == child) {
                parent.right = this;
                this.par = parent;
            } else if (parent.dup == child) {
                parent.dup = this;
                this.par = parent;
            }
        }
    }

    /**
     * Initializes an empty DAFTree.
     */
    public DAFTree() {
        // initializes an empty DAFTree with 0 elements and 0 unique keys
        nElems = 0;
        nKeys = 0;
    }

    /**
     * Return the root of DAFTree. Returns null if the tree is empty
     *
     * @return The root of DAFTree, null if the tree is empty
     */
    public DAFNode<K, D> getRoot() {
        // Return the root of the DAFTree
        return root;
    }

    /**
     * Sets the root of DAFTree to newRoot
     *
     * @param newRoot: the new root node of the DAFTree
     */
    private void setRoot(DAFNode<K, D> newRoot) {
        root = newRoot;
    }

    /**
     * Returns the total number of elements stored in the tree.
     *
     * @return total number of elements stored
     */
    public int size() {
        return nElems;
    }

    /**
     * Returns the total number of unique keys stored in the tree.
     *
     * @return total number of unique keys stored
     */
    public int nUniqueKeys() {
        return nKeys;
    }

    /**
     * Inserts a new node that has given key and data to the tree.
     *
     * @param key  key to insert
     * @param data data to insert
     * @return the inserted node object, or null if already exist
     * @throws NullPointerException if key or data is null
     */
    public DAFNode<K, D> insert(K key, D data) {
        if (key == null || data == null) {
            // Throws NullPointerException if key is null
            throw new NullPointerException();
        } else if (lookup(key, data)) {
            // Returns null if the tree already has a node with the same key and data
            return null;
        }

        // Increment the number of elements and unique keys in the tree by 1
        nElems++;
        nKeys++;

        if (getRoot() == null) {
            // Insert new node as root if root is null
            setRoot(new DAFNode<>(key, data));
            // Return the root
            return getRoot();
        } else {
            // Find the parentNode to insert new node as a child by calling insertHelper with the
            // DAFTree root
            DAFNode<K, D> parentNode = insertHelper(getRoot(), key);
            // Create a newNode using given key and data
            DAFNode<K, D> newNode = new DAFNode<>(key, data);
            // Set the parent of newNode to parentNode
            newNode.setPar(parentNode);

            if (parentNode.getKey().compareTo(key) > 0) {
                // If key is smaller than parent node, insert as left child
                parentNode.setLeft(newNode);
            } else if (parentNode.getKey().compareTo(key) < 0) {
                // If key is larger than parent node, insert as left child
                parentNode.setRight(newNode);
            } else {
                // If key is the same as parent node, insert as dup child
                parentNode.setDup(newNode);
                // Decrement the number of unique keys which was previously incremented
                nKeys--;
            }

            // Return the inserted node
            return newNode;
        }
    }

    /**
     * Find the DAFNode at which to insert new DAFNode as a child
     *
     * @param node current DAFNode to compare with key
     * @param key to be inserted
     * @return DAFNode at which to insert new DAFNode as child
     */
    private DAFNode<K, D> insertHelper(DAFNode<K, D> node, K key) {
        // If key is smaller than node key and left child of node is null, return node, otherwise
        // recurse with left child of node
        if (node.getKey().compareTo(key) > 0) {
            if (node.getLeft() != null) {
                return insertHelper(node.getLeft(), key);
            } else {
                return node;
            }
        } else if (node.getKey().compareTo(key) < 0) {
            // If key is larger than node key and right child of node is null, return node,
            // otherwise recurse with right child of node
            if (node.getRight() != null) {
                return insertHelper(node.getRight(), key);
            } else {
                return node;
            }
        } else {
            // If the key is the same as the node key and dup child is null, return node,
            // otherwise recurse with dup child of the node
            if (node.getDup() != null) {
                return insertHelper(node.getDup(), key);
            } else {
                return node;
            }
        }
    }

    /**
     * Checks if the key is stored in the tree.
     *
     * @param key key to search
     * @return true if found, false otherwise
     * @throws NullPointerException if the key is null
     */
    public boolean lookupAny(K key) {
        if (key == null) {
            // Throws NullPointerException if key is null
            throw new NullPointerException();
        } else {
            // Calls lookupAnyHelper and returns false if returned node is null, otherwise true
            return lookupAnyHelper(getRoot(), key) != null;
        }
    }

    /**
     * Helper method to find first DAFNode containing a key in the tree. Returns null if not found
     *
     * @param node DAFNode to check for key
     * @param key to look for
     * @return DAFNode containing the key, or null if not found
     */
    public DAFNode<K, D> lookupAnyHelper(DAFNode<K, D> node, K key) {
        // If the node is not null, check for value and recurse if not found
        if (node != null) {
            if (node.getKey().compareTo(key) > 0) {
                // If key is smaller than key of current node, recurse with left child node
                return lookupAnyHelper(node.getLeft(), key);
            } else if (node.getKey().compareTo(key) < 0) {
                // If key is larger than key of current node, recurse with right child node
                return lookupAnyHelper(node.getRight(), key);
            } else {
                // If key is the same as key of current node, return node
                return node;
            }
        } else {
            // Return null if not found, or node is null
            return null;
        }
    }

    /**
     * Checks if the specified key-data pair is stored in the tree.
     *
     * @param key  key to search
     * @param data data to search
     * @return true if found, false otherwise
     * @throws NullPointerException if key or data is null
     */
    public boolean lookup(K key, D data) {
        if (key == null || data == null) {
            // Throws NullPointerException if key or data are null
            throw new NullPointerException();
        } else {
            // Calls lookupHelper and returns false if returned node is null, otherwise true
            return lookupHelper(getRoot(), key, data) != null;
        }
    }

    /**
     * Helper method to find DAFNode containing a key in the tree. Returns null if not found
     *
     * @param node DAFNode to check for key
     * @param key to look for
     * @return DAFNode containing the key, or null if not found
     */
    public DAFNode<K, D> lookupHelper(DAFNode<K, D> node, K key, D data) {
        // If the node is not null, check for value and recurse if not found
        if (node != null) {
            if (node.getKey().compareTo(key) > 0) {
                // If key is smaller than key of current node, recurse with left child node
                return lookupHelper(node.getLeft(), key, data);
            } else if (node.getKey().compareTo(key) < 0) {
                // If key is larger than key of current node, recurse with right child node
                return lookupHelper(node.getRight(), key, data);
            } else {
                // If key and data are equal to key and data of current node, return node, else
                // recurse with dup child of node
                if (node.getData().equals(data)) {
                    return node;
                } else {
                    return lookupHelper(node.getDup(), key, data);
                }
            }
        } else {
            // Return null if not found, or node is null
            return null;
        }
    }

    /**
     * Returns a LinkedList of all data associated with the given key.
     *
     * @return list of data (empty if no data found)
     * @throws NullPointerException if the key is null
     */
    public LinkedList<D> getAllData(K key) {
        if (key == null) {
            // throws NullPointerException if the key is null
            throw new NullPointerException();
        } else if (!lookupAny(key)) {
            // If the key does not exist in the tree, return null
            return null;
        } else {
            // Create LinkedList to store all data associated with given key
            LinkedList<D> dataList = new LinkedList<>();
            // Find the first node with given key
            DAFNode<K, D> node = lookupAnyHelper(getRoot(), key);

            // Add the data of node and all of it's dup children to dataList
            while (node.getDup() != null) {
                dataList.add(node.getData());
                node = node.getDup();
            }

            // Add the data of the last dup child
            dataList.add(node.getData());

            // Return the LinkedList containing all data associated with the given key
            return dataList;
        }
    }

    /**
     * Removes the node with given key and data from the tree.
     *
     * @return true if removed, false if this node was not found
     * @throws NullPointerException if key or data is null
     */
    public boolean remove(K key, D data) {
        if (key == null || data == null) {
            // throws NullPointerException if key or data is null
            throw new NullPointerException();
        } else if (!lookup(key, data)) {
            // False if node not in the tree
            return false;
        } else {
            // Call given remove function by finding the required node in tree
            return remove(lookupHelper(getRoot(), key, data));
        }
    }

    /**
     * Removes all nodes with given key from the tree.
     *
     * @return true if any node is removed, false otherwise
     * @throws NullPointerException if the key is null
     */
    public boolean removeAll(K key) {
        if (key == null) {
            // throws NullPointerException if the key is null
            throw new NullPointerException();
        } else if (!lookupAny(key)) {
            // False if node not in the tree
            return false;
        } else {
            // Find first node in tree with given key
            DAFNode<K, D> node = lookupAnyHelper(getRoot(), key);

            // Keep removing node with given key from tree and find next node with given key
            while (node != null) {
                remove(node);
                node = lookupAnyHelper(getRoot(), key);
            }

            // Return true since at least one node has been removed from the tree
            return true;
        }
    }

    /**
     * Returns a tree iterator instance.
     *
     * @return iterator
     */
    public Iterator<DAFNode<K, D>> iterator() {
        // returns a new DAFTree iterator instance
        return new DAFTreeIterator();
    }

    /**
     * Iterator to traverse DAFTree using in-order traversal
     */
    public class DAFTreeIterator implements Iterator<DAFNode<K, D>> {
        // Stack to hold nodes of DAFTree
        Stack<DAFNode<K, D>> nodeStack = new Stack<>();

        /**
         * Initializes a tree iterator instance.
         */
        public DAFTreeIterator() {
            // Push root to stack
            DAFNode<K, D> curNode = getRoot();
            nodeStack.push(curNode);

            // Push entire leftpath of tree to stack
            while (curNode.getLeft() != null) {
                nodeStack.push(curNode.getLeft());
                curNode = curNode.getLeft();
            }
        }

        /**
         * Checks if the iterator has next element.
         *
         * @return true if there is a next, false otherwise
         */
        public boolean hasNext() {
            // Return true if Stack is not empty, meaning that stack has a next element. Return
            // false if stack is empty, there is no next element
            return !nodeStack.isEmpty();
        }

        /**
         * Returns the next node of the iterator.
         *
         * @return next node
         * @throws NoSuchElementException if the iterator reaches the end of traversal
         */
        public DAFNode<K, D> next() {
            if (!hasNext()) {
                // Throws NoSuchElementException if there is no next item
                throw new NoSuchElementException();
            } else {
                // If the stack is not empty, pop the DAFNode from the top
                DAFNode<K, D> popped = nodeStack.pop();

                // push the leftPath of the tree rooted at the popped node's right node to the
                // stack
                if (popped.getRight() != null) {
                    // Push right node of popped node to stack
                    DAFNode<K, D> curNode = popped.getRight();
                    nodeStack.push(curNode);

                    // Push leftPath of tree rooted at right node
                    while (curNode.getLeft() != null) {
                        nodeStack.push(curNode.getLeft());
                        curNode = curNode.getLeft();
                    }
                }

                // Push dup node of popped node to stack
                if (popped.getDup() != null) {
                    DAFNode<K, D> dupNode = popped.getDup();
                    nodeStack.push(dupNode);
                }

                // Return the popped node
                return popped;
            }
        }
    }

    /* PROVIDED HELPERS, MODIFY WITH CAUTION! */

    /**
     * Public helper to remove the given node in BST's remove style.
     *
     * @param cur Node to remove
     * @boolean true always
     */
    public boolean remove(DAFNode<K, D> cur) {
        if (cur.getDup() == null && (cur.getPar() == null || cur.getPar().getDup() != cur))
            nKeys--;

        if (cur == getRoot()) {
            setRoot(removeHelper(cur, cur.getKey(), cur.getData()));
            if (getRoot() != null) {
                getRoot().setPar(null);
            }
        } else {
            // passing in par to let helper update both par and child reference
            removeHelper(cur.getPar(), cur.getKey(), cur.getData());
        }

        nElems--;
        return true;
    }

    /**
     * Helper to remove node recursively in BST style of replacement by in-order
     * successor, with modification of handling dup and also node are swapped
     * instead of data field being replaced.
     *
     * @param root Root
     * @param key  To be removed
     * @return The node that replaces the node to be removed
     */
    private DAFNode<K, D> removeHelper(DAFNode<K, D> root, K key, D data) {
        if (root == null)
            return null;

        // update child reference and make replacement if root is the target
        DAFNode<K, D> replacedChild = null; // this is different from bst
        if (key.compareTo(root.getKey()) < 0) {
            replacedChild = removeHelper(root.getLeft(), key, data);
            root.setLeft(replacedChild);
        } else if (key.compareTo(root.getKey()) > 0) {
            replacedChild = removeHelper(root.getRight(), key, data);
            root.setRight(replacedChild);
        } else if (!data.equals(root.getData())) { // this is different from bst
            replacedChild = removeHelper(root.getDup(), key, data);
            root.setDup(replacedChild);
        } else if (root.getDup() != null) { // this is different from bst
            // swap only left & right
            root.getDup().setLeft(root.getLeft());
            root.getDup().setRight(root.getRight());
            if (root.getLeft() != null) {
                root.getLeft().setPar(root.getDup());
            }
            if (root.getRight() != null) {
                root.getRight().setPar(root.getDup());
            }

            root = root.getDup();
        } else if (root.getLeft() != null && root.getRight() != null) {
            // the following is all different from bst
            DAFNode<K, D> successor = findMin(root.getRight());
            DAFNode<K, D> nextRoot = root.getRight();
            DAFNode<K, D> temp;

            // swap content
            root.swapReferencesWith(successor);
            // swap the pointer back
            temp = root;
            root = successor;
            successor = temp;

            // special case: if root's right is successor,
            // references/connection between them will be broken
            // but will still be handled correctly by following code
            if (nextRoot == root)
                nextRoot = successor;

            replacedChild = removeHelper(nextRoot, successor.getKey(), successor.getData());
            root.setRight(replacedChild);
        } else {
            root = (root.getLeft() != null) ? root.getLeft() : root.getRight();
        }

        // update parent reference
        if (replacedChild != null) // this is different from bst
            replacedChild.setPar(root);

        return root;
    }

    /**
     * Helper to return the smallest node from a given subroot.
     *
     * @param root Smallest node will be found from this node
     * @return The smallest node from the 'root' node
     */
    private DAFNode<K, D> findMin(DAFNode<K, D> root) {
        DAFNode<K, D> cur = root;
        while (cur.getLeft() != null)
            cur = cur.getLeft();
        return cur;
    }
}
