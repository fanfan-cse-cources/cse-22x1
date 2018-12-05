import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Monte Carlo Estimate: compute percentage of pseudo-random points in [0.0,1.0)
 * interval that fall in the left half subinterval [0.0,0.5).
 */
public final class MonteCarlo {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private MonteCarlo() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        /*
         * Open input and output streams
         */
        SimpleReader input = new SimpleReader1L();
        SimpleWriter output = new SimpleWriter1L();
        /*
         * Ask user for number of points to generate
         */
        final int factor = 2;
        final double square = 4.0;
        output.print("Number of tries: ");
        int n = input.nextInteger();
        /*
         * Declare counters and initialize them
         */
        int tries = 0, hits = 0;
        /*
         * Create pseudo-random number generator
         */
        Random rnd = new Random1L();
        double withIn = 0.00;
        /*
         * Generate points and count how many fall in [0.0,0.5) interval
         */
        while (tries < n) {
            /*
             * Generate pseudo-random number in [0.0,1.0) interval
             */
            double x = rnd.nextDouble() * factor;
            double y = rnd.nextDouble() * factor;
            /*
             * Increment total number of generated points
             */
            tries++;
            /*
             * Check if point is in [0.0,0.5) interval and increment counter if
             * it is
             */
            withIn = (x - 1) * (x - 1) + (y - 1) * (y - 1);

            if (withIn <= 1) {
                hits++;
            }

        }
        /*
         * Estimate percentage of points generated in [0.0,1.0) interval that
         * fall in the [0.0,0.5) subinterval
         */
        double areaOfCircle = (square * hits) / tries;

        output.println(areaOfCircle);
        /*
         * Close input and output streams
         */
        input.close();
        output.close();
    }

}
