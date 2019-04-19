import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Program to copy a text file into another file.
 *
 * @author Yifan Yao
 *
 */
public final class CopyFileStdJava3 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CopyFileStdJava3() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     *
     */
    public static void main(String[] args) {

        String inputFile = args[0];
        String outputFile = args[1];

        BufferedReader in;
        PrintWriter out;
        try {
            in = new BufferedReader(new FileReader(inputFile));
            out = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFile)));
        } catch (IOException e) {
            System.err.println("Error: " + e.getCause());
            return;
        }

        try {
            String s = in.readLine();

            while (s != null) {
                out.println(s);
                s = in.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getCause());
        }

        try {
            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getCause());
        }

    }
}
