/*
 * Name: Arjun Sawhney
 * PID: A15499408
 */

import java.io.*;

/**
 * Decompress the first given file to the second given file using Huffman coding
 *
 * @author Arjun Sawhney
 * @since 01/06/2020
 */
public class Decompress {
    private static final int EXP_ARG = 2; // number of expected arguments

    /**
     * Main method which accepts command line arguments to decompress a compressed file using
     * Huffman Encoding
     *
     * @param args: String array storing command line arguments
     */
    public static void main(String[] args) throws IOException {

        // Check if the number of arguments is correct
        if (args.length != EXP_ARG) {
            System.out.println("Invalid number of arguments.\n"
                    + "Usage: ./decompress <infile outfile>.\n");
            return;
        }

        FileInputStream inFile = new FileInputStream(args[0]);
        DataInputStream in = new DataInputStream(inFile);
        BitInputStream bitIn = new BitInputStream(in);

        FileOutputStream outFile = new FileOutputStream(args[1]);
        DataOutputStream out = new DataOutputStream(outFile);

        // read the number of bytes from the file
        int byteCount = in.readInt();

        // To handle the case when compressed file is empty
        if (byteCount > 0) {
            // decode and build the tree from the "header"
            HCTree tree = new HCTree();
            tree.decodeHCTree(bitIn);

            // decode the file and write the results
            while (byteCount > 0) {
                // Read the exact number of bytes to avoid EOFException
                out.writeByte(tree.decode(bitIn));
                byteCount--;
            }
        }

        inFile.close();
        in.close();
        outFile.close();
        out.close();
    }
}
