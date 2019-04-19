import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program to copy a text file into another file.
 *
 * @author Yifan Yao
 *
 */
public final class CopyFileStdJava {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CopyFileStdJava() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     */
    public static void main(String[] args) {

        String inputFile = args[0];
        String outputFile = args[1];

        SimpleReader in = new SimpleReader1L(inputFile);
        SimpleWriter out = new SimpleWriter1L(outputFile);

        while (!in.atEOS()) {
            out.println(in.nextLine());
        }

        in.close();
        out.close();

    }

}
