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
public final class Newton2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton2() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            zero and positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {

        final double relativError = 0.0001;
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

        String decision = null;
        double number = 0.0;

        do {
            out.print("Please enter a number: ");
            number = in.nextDouble();

            out.println(sqrt(number));

            /*
             * Ask user to continue after initial execute or not
             */
            out.print("Continue(y/n): ");
            decision = in.nextLine();
        } while (decision.equals("y") || decision.equals("Y"));

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
