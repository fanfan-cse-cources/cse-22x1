import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Select any positive real-valued and select any four positive real numbers not
 * equal to 1 to calculate approximated guess via deJagerFormular.
 *
 * @author Yifan Yao
 *
 */
public final class ABCDGuesser1 {
    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser1() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        String temp = null;
        boolean meetRequirements = false;

        while (!meetRequirements) {
            out.print("Enter a positive double: ");
            temp = in.nextLine();
            if (FormatChecker.canParseDouble(temp)
                    && Double.parseDouble(temp) > 0) {
                meetRequirements = true;
            }
        }

        return Double.parseDouble(temp);
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        String temp = null;
        boolean meetRequirements = false;

        while (!meetRequirements) {
            out.print("Enter a real number not equal to 1.0: ");
            temp = in.nextLine();
            if (FormatChecker.canParseDouble(temp)
                    && Double.parseDouble(temp) > 0.0
                    && Double.parseDouble(temp) != 1.0) {
                meetRequirements = true;
            }
        }

        return Double.parseDouble(temp);
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

        /*
         * Declare Variables
         */
        final int arraySize = 4;
        final double[] deJagerNumbers = { -5, -4, -3, -2, -1, -0.5, -1.0 / 3.0,
                -0.25, 0, 0.25, 1.0 / 3.0, 0.5, 1, 2, 3, 4, 5 };
        final double[] wxyz = new double[arraySize];

        /*
         * Get numbers from the user
         */
        double userU = getPositiveDouble(in, out);

        int counter = 0;
        while (counter < wxyz.length) {
            wxyz[counter] = getPositiveDoubleNotOne(in, out);
            counter++;
        }

        /*
         * Declare Variables
         */
        double guess = 0.0, answer = 0.0, error = 2.0;
        int counterA = 0, counterB = 0, counterC = 0, counterD = 0;
        int[] expUsed = new int[arraySize];

        /*
         * Calculate every possible answers come from deJagerFormular
         */
        while (counterA < deJagerNumbers.length) {
            while (counterB < deJagerNumbers.length) {
                while (counterC < deJagerNumbers.length) {
                    while (counterD < deJagerNumbers.length) {
                        /*
                         * Declare Variables for Checkstyle
                         */
                        final int three = 3;

                        /*
                         * Calculate answer via deJagerFormular (too lazy to
                         * write a Javadoc comment)
                         */
                        answer = Math.pow(wxyz[0], deJagerNumbers[counterA])
                                * Math.pow(wxyz[1], deJagerNumbers[counterB])
                                * Math.pow(wxyz[2], deJagerNumbers[counterC])
                                * Math.pow(wxyz[three],
                                        deJagerNumbers[counterD]);

                        /*
                         * When there is a smaller error, make it as the guess
                         */
                        if (Math.abs((userU - answer) / answer) < error) {
                            guess = answer;
                            error = Math.abs((userU - answer) / answer);
                            expUsed[0] = counterA;
                            expUsed[1] = counterB;
                            expUsed[2] = counterC;
                            expUsed[three] = counterD;
                        }
                        counterD++;
                    }
                    counterD = 0;
                    counterC++;
                }
                counterC = 0;
                counterB++;
            }
            counterB = 0;
            counterA++;
        }

        /*
         * Output results
         */
        out.println("***************************************");
        out.print("Your Number: ");
        out.print(guess, 0, false);
        out.println();

        final int percentage = 100;
        out.print("Error: ");
        out.print(error * percentage, 2, false);
        out.println("%");

        final int three = 3;
        out.println("Exponents Used: ");
        out.println(deJagerNumbers[expUsed[0]] + ", "
                + deJagerNumbers[expUsed[1]] + ", " + deJagerNumbers[expUsed[2]]
                + ", " + deJagerNumbers[expUsed[three]]);
        out.println("***************************************");

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
