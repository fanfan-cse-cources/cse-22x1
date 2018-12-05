import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class Hailstone4 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Hailstone4() {
    }

    /**
     * Generates and outputs the Hailstone series starting with the given
     * integer.
     *
     * @param n
     *            the starting integer
     * @param out
     *            the output stream
     */
    private static void generateSeries(int n, SimpleWriter out) {
        int number = n, counter = 0, max = 0;
        while (number != 1) {
            if (number % 2 == 0) {
                number = number / 2;
                out.print(number + " ");
                counter++;
            } else {
                number = 3 * number + 1;
                out.print(number + " ");
                counter++;
            }

            if (number > max) {
                max = number;
            }
        }
        out.println("\nLength of the series: " + counter);
        out.println("Maximum value of the series: " + max);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        int inputNumber = 0;
        String continueOrNot = null;
        do {
            do {
                out.print("Please enter an positive int: ");
                inputNumber = in.nextInteger();
            } while (inputNumber <= 0);
            generateSeries(inputNumber, out);
            out.print("Continue(y/n): ");
            continueOrNot = in.nextLine();
        } while (continueOrNot.equals("y") || continueOrNot.equals("Y"));

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
