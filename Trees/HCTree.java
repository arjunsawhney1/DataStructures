/*
 * Name: Arjun Sawhney
 */

import java.io.*;
import java.util.Stack;
import java.util.PriorityQueue;

/**
 * The Huffman Coding Tree
 *
 * @author Arjun Sawhney
 * @since 30/05/2020
 */
public class HCTree {
    // alphabet size of extended ASCII
    private static final int NUM_CHARS = 256;
    // number of bits in a byte
    private static final int BYTE_BITS = 8;

    // the root of HCTree
    private HCNode root;
    // the leaves of HCTree that contain all the symbols
    private HCNode[] leaves = new HCNode[NUM_CHARS];

    /**
     * The Huffman Coding Node
     */
    protected class HCNode implements Comparable<HCNode> {
        byte symbol; // the symbol contained in this HCNode
        int freq; // the frequency of this symbol
        HCNode c0, c1, parent; // c0 is the '0' child, c1 is the '1' child

        /**
         * Initialize a HCNode with given parameters
         *
         * @param symbol the symbol contained in this HCNode
         * @param freq   the frequency of this symbol
         */
        HCNode(byte symbol, int freq) {
            this.symbol = symbol;
            this.freq = freq;
        }

        /**
         * Getter for symbol
         *
         * @return the symbol contained in this HCNode
         */
        byte getSymbol() {
            return this.symbol;
        }

        /**
         * Setter for symbol
         *
         * @param symbol the given symbol
         */
        void setSymbol(byte symbol) {
            this.symbol = symbol;
        }

        /**
         * Getter for freq
         *
         * @return the frequency of this symbol
         */
        int getFreq() {
            return this.freq;
        }

        /**
         * Setter for freq
         *
         * @param freq the given frequency
         */
        void setFreq(int freq) {
            this.freq = freq;
        }

        /**
         * Getter for '0' child of this HCNode
         *
         * @return '0' child of this HCNode
         */
        HCNode getC0() {
            return c0;
        }

        /**
         * Setter for '0' child of this HCNode
         *
         * @param c0 the given '0' child HCNode
         */
        void setC0(HCNode c0) {
            this.c0 = c0;
        }

        /**
         * Getter for '1' child of this HCNode
         *
         * @return '1' child of this HCNode
         */
        HCNode getC1() {
            return c1;
        }

        /**
         * Setter for '1' child of this HCNode
         *
         * @param c1 the given '1' child HCNode
         */
        void setC1(HCNode c1) {
            this.c1 = c1;
        }

        /**
         * Getter for parent of this HCNode
         *
         * @return parent of this HCNode
         */
        HCNode getParent() {
            return parent;
        }

        /**
         * Setter for parent of this HCNode
         *
         * @param parent the given parent HCNode
         */
        void setParent(HCNode parent) {
            this.parent = parent;
        }

        /**
         * Check if the HCNode is leaf (has no children)
         *
         * @return if it's leaf, return true. Otherwise, return false.
         */
        boolean isLeaf() {
            // HCNode is a leaf if it's child node pointers are null
            return getC0() == null && getC1() == null;
        }

        /**
         * String representation
         *
         * @return string representation
         */
        public String toString() {
            return "Symbol: " + this.symbol + "; Freq: " + this.freq;
        }

        /**
         * Compare two nodes
         *
         * @param o node to compare
         * @return int positive if this node is greater
         */
        public int compareTo(HCNode o) {
            // CompareTo will compare frequency of two nodes
            if (getFreq() > o.getFreq()) {
                // Positive if this node has greater frequency. Higher frequency will have lower
                // priority in the priority queue implemented as a min-heap
                return 1;
            } else if (getFreq() < o.getFreq()) {
                // Negative if this node has smaller frequency. Lower frequency will have higher
                // priority in the priority queue implemented as a min-heap
                return -1;
            } else {
                // If nodes have equal frequency, compare symbols using Byte.compare to decide
                // priority like frequency in the priority queue implemented as a min-heap
                return Byte.compare(getSymbol(), o.getSymbol());
            }
        }
    }

    /**
     * Returns the root node
     *
     * @return root node
     */
    public HCNode getRoot() {
        return root;
    }

    /**
     * Sets the root node
     *
     * @param root node to set
     */
    public void setRoot(HCNode root) {
        this.root = root;
    }

    /**
     * Build a Huffman Tree such that the most frequent symbol will correspond to the shortest
     * sequence of encoded bits.
     *
     * @param freq: array used to keep track of the frequency of each byte from the input file
     */
    public void buildTree(int[] freq) {
        // Instantiate a PriorityQueue to hold leaves of the tree in a min-heap. This will help
        // create the tree structure
        PriorityQueue<HCNode> nodes = new PriorityQueue<>();

        // Create all leaf nodes of HCTree with frequency values corresponding to each symbol
        // in the freq array.
        for (int i = 0; i < freq.length; i++) {
            leaves[i] = new HCNode((byte) i, freq[i]);

            // Only add the leaf to the priority queue if the symbol appears in the input file
            // (if it has a frequency greater than 0 in the freq array)
            if (freq[i] > 0) {
                // Priority Queue uses compareTo method defined above to insert leaves in the
                // min-heap
                nodes.add(leaves[i]);
            }
        }

        // If the file only contains one character, set it as the root and exit from the function
        // early
        if (nodes.size() == 1) {
            setRoot(nodes.remove());
            return;
        }

        // Iteratively, remove two nodes from the priority queue. Then use the first node to be
        // the ‘0’ child and the second node to be the ‘1’ child of a newly created parent
        // node. The frequency of this parent node is the sum of the frequencies of its two
        // children nodes. Parent node takes a symbol from the first child. Then, add this parent
        // node back to the priority queue, so that it later becomes a “branch” of the final
        // tree
        while (nodes.size() > 1) {
            HCNode first = nodes.remove();
            HCNode second = nodes.remove();
            HCNode p = new HCNode(first.getSymbol(), first.getFreq() + second.getFreq());

            p.setC0(first);
            p.setC1(second);
            first.setParent(p);
            second.setParent(p);

            nodes.add(p);
        }

        // Ensure that there is one node left in the queue, to avoid exception in case of empty
        // file
        if (nodes.size() == 1) {
            // The last node in the priority queue is the root of the tree
            setRoot(nodes.remove());
        }
    }

    /**
     * For a given symbol, use the HCTree built before to find its encoding bits and write those
     * bits to the given BitOutputStream
     *
     * @param symbol: the symbol of the leaf to encode the path to
     * @param out: the BitOutputStream to write the bits to
     * @throws IOException
     */
    public void encode(byte symbol, BitOutputStream out) throws IOException {
        // Stack will allow us to pop bits in reverse order to which they were pushed
        Stack<Integer> bits = new Stack<>();
        // Leaf node from which to find encoding bits for it's symbol
        HCNode node = leaves[symbol & 0xff];

        // If there is one character in the file, then the node is the root of the tree
        if (node == getRoot()) {
            // This means that the root is the sole leaf in the tree
            out.writeBit(1);
            // Exit from the method early
            return;
        }

        //  to avoid inefficiently searching the whole tree to find the encoding bits of the
        //  given symbol, start from the leaf node containing the given symbol, and traverse
        //  back to the root
        while (node != getRoot()) {
            if (node.getParent().getC0() == node) {
                // Push 0 to the stack when node is the first child of parent
                bits.push(0);
            } else if (node.getParent().getC1() == node) {
                // Push 1 to the stack when node is the second child of parent
                bits.push(1);
            }

            // Traverse up the tree
            node = node.getParent();
        }

        // Reverse collected bits at the end to get the encoding
        while (!bits.isEmpty()) {
            out.writeBit(bits.pop());
        }
    }

    /**
     * Decodes the bits from BitInputStream and returns a byte that represents the symbol that
     * is encoded by a sequence of bits from BitInputStream
     *
     * @param in: the BitInputStream from which to read encoded sequence of bits
     * @return byte: the decoded symbol
     * @throws IOException
     */
    public byte decode(BitInputStream in) throws IOException {
        // Begin traversal from the root node
        HCNode node = getRoot();

        // If the root node is a leaf, there is only one character in the file
        if (node.isLeaf()) {
            // Return the symbol of the root node and exit method
            return node.getSymbol();
        }

        // Traverse the tree by iteratively reading bits until a leaf node is encountered
        while (!node.isLeaf()) {
            if (in.readBit() == 0) {
                // If bit is 0, traverse from the first child node
                node = node.getC0();
            } else {
                // If bit is 1, traverse from the second child node
                node = node.getC1();
            }
        }

        // Return the symbol of leaf node
        return node.getSymbol();
    }

    /**
     * Use a recursive pre-order traversing to print out the structure of the HCTree in bits.
     * Writing out is achieved by writing bits and bytes to BitOutputStream of file
     *
     * @param node: The node from which to begin pre-order traversal
     * @param out: The BitOutputStream to write bits and bytes to
     * @throws IOException
     */
    public void encodeHCTree(HCNode node, BitOutputStream out) throws IOException {
        // Base case for recursion is encountering a null node
        if (node != null) {
            if (node.isLeaf()) {
                //  we use bit 1 to represent a leaf node, with the following 8 bits
                //  (1 byte) being the symbol in that leaf node
                out.writeBit(1);
                out.writeByte(node.getSymbol());
            } else {
                // We use bit 0 to represent a non-leaf node
                out.writeBit(0);
            }

            // Recursively call encodeHCTree on the first and second child of node. This is how
            // we achieve a pre-order traversal
            encodeHCTree(node.getC0(), out);
            encodeHCTree(node.getC1(), out);
        }
    }

    /**
     * In this method, we will be building the original HCTree from the header that we printed
     * in bits when encoding the HCTree
     *
     * @param in: The BitInputStream from which to read the encoded header of HCTree
     * @return HCNode: for recursively storing the left and right child of parent node
     * @throws IOException
     */
    public HCNode decodeHCTree(BitInputStream in) throws IOException {
        if (in.readBit() == 1) {
            // whenever the next bit is 1, we will take the following 8 bits (1 byte) as the
            // symbol of a new leaf node.
            byte symbol = in.readByte();
            // Update the leaves array with the newly created HCNode
            leaves[symbol & 0xff] = new HCNode(symbol, 0);
            // Return leaf node to be stored as either left or right child
            return leaves[symbol & 0xff];
        } else {
            // Store the child nodes in first and second using recursion
            HCNode first = decodeHCTree(in);
            HCNode second = decodeHCTree(in);
            // Create parent node with the symbol of the first child. Frequency does not matter
            // in this tree so it is 0
            HCNode p = new HCNode(first.getSymbol(), 0);

            // Set all the pointers between children and parents
            p.setC0(first);
            p.setC1(second);
            first.setParent(p);
            second.setParent(p);

            // Make the parent node the root of the tree on each iteration. The final iteration
            // will store the correct root node
            setRoot(p);

            // Return the parent node as it might be the left or right child of another node
            return p;
        }
    }

    /**
     * Print out the in-order traversal of the HCTree
     *
     * @param root: The root node for recursive in-order traversal
     */
    public void inorder(HCNode root) {
        // Node being null is the base case for recursion
        if (root != null) {
            // Recursively says visit left node, then current node, then right
            // node. A visit is printing the node using the implicitly called toString() method
            inorder(root.getC0());
            System.out.println(root);
            inorder(root.getC1());
        }
    }
}
