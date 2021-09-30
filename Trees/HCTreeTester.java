/*
 * Name: Arjun Sawhney
 * PID: A15499408
 */

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * The Huffman Coding Tree Tester
 *
 * @author Arjun Sawhney
 * @since 30/05/2020
 */
public class HCTreeTester {
    HCTree c1Tree = new HCTree();

    @Test
    public void buildTree() {
        try {
            File check1 = new File("./src/io/check1.txt");
            byte[] c1 = Files.readAllBytes(check1.toPath());
            int[] c1freq = new int[256];

            for (byte b : c1) {
                int ascii = b & 0xff;
                if (ascii != 13) {
                    c1freq[b & 0xff]++;
                }
            }

            c1Tree.buildTree(c1freq);
            c1Tree.inorder(c1Tree.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
