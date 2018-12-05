import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Compute Roots Using Newton Iteration.
 *
 * @author Yifan Yao
 *
 */
public final class Newton4 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton4() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            zero and positive number to compute square root of
     * @param relativError
     *            prompts the user to input the value of relative error
     * @return estimate of square root
     */
    private static double sqrt(double x, double relativError) {

        /*
         * Initial guess will be x
         */
        double guess = x;

        if (x == 0) {
            return 0;
        } else {
            do {
                /*
                 * Newton Iteration
                 */
                guess = (guess + x / guess) / 2;
            } while ((Math.abs(guess * guess - x) / x) > (relativError
                    * relativError));

            return guess;
        }

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

        double number = 0.0, relativError = 0.0;

        out.println("Enter any negitive number to exit the program");

        do {
            out.print("Please enter a number: ");
            number = in.nextDouble();

            if (number == 0) {
                /*
                 * No further action needed when x = 0
                 */
                out.println(sqrt(number, relativError));
            } else if (number > 0) {
                /*
                 * Promote user customize relative error
                 */
                out.print("Please enter your relative error: ");
                relativError = in.nextDouble();

                out.println(sqrt(number, relativError));
            } else if (number < 0) {
                /*
                 * Exit the program when x < 0
                 */
                out.println("Bye");
                System.exit(0);
            }

        } while (number >= 0);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
