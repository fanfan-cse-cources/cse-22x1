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
public final class CopyFileStdJava2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CopyFileStdJava2() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     * @throws IOException
     *             throw all IOExceptions
     */
    public static void main(String[] args) throws IOException {

        String inputFile = args[0];
        String outputFile = args[1];

        BufferedReader in = new BufferedReader(new FileReader(inputFile));
        PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(outputFile)));

        String s = in.readLine();
        while (s != null) {
            out.println(s);
            s = in.readLine();
        }

        in.close();
        out.close();

    }

}
