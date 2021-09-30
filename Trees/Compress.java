/*
 * Name: Arjun Sawhney
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Compress the first given file to the second given file using Huffman coding
 *
 * @author Arjun Sawhney
 * @since 01/06/2020
 */
public class Compress {
    private static final int EXP_ARG = 2; // number of expected arguments

    /**
     * Main method which accepts command line arguments to create a compressed file using
     * Huffman Encoding
     *
     * @param args: String array storing command line arguments
     */
    public static void main(String[] args) throws IOException {

        // Check if the number of arguments is correct
        if (args.length != EXP_ARG) {
            System.out.println("Invalid number of arguments.\n"
                    + "Usage: ./compress <infile outfile>.\n");
            return;
        }

        // read all the bytes from the given file and make it to a byte array
        byte[] input = Files.readAllBytes(Paths.get(args[0]));

        FileOutputStream file = new FileOutputStream(args[1]);
        DataOutputStream out = new DataOutputStream(file);
        BitOutputStream bitOut = new BitOutputStream(out);

        // construct frequency array from the byte array
        int[] freq = new int[256];
        for (byte b : input) {
            freq[b & 0xff]++;
        }

        // construct a tree from the frequency array of the input file
        HCTree tree = new HCTree();
        tree.buildTree(freq);

        // Calculate the total number of bytes in the file and store it in sum
        int sum = 0;
        for (int i = 0; i < freq.length; i++) {
            sum += freq[i];
        }

        // write number of bytes to out file
        out.writeInt(sum);

        // encode HCTree and every byte
        tree.encodeHCTree(tree.getRoot(), bitOut);
        for (byte b : input) {
            tree.encode(b, bitOut);
        }

        // There might be several padding bits in the bitOut that we haven't written, so
        // flush it first.
        bitOut.flush();
        out.close();
        file.close();
    }
}
