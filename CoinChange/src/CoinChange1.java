import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Coin Change.
 *
 * @author Yifan Yao
 *
 */
public final class CoinChange1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CoinChange1() {
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
        final int centsToDollors = 100;
        final int centsToQuarters = 25;
        final int centsToDimes = 10;
        final int centsToNickels = 5;
        /*
         * Put your main program code here
         */
        out.print("Number of Cents: ");
        int amount = in.nextInteger();

        int dollors = (amount / centsToDollors);
        int quarters = (amount - dollors * centsToDollors) / centsToQuarters;
        int dimes = (amount - dollors * centsToDollors
                - quarters * centsToQuarters) / centsToDimes;
        int nickels = (amount - dollors * centsToDollors
                - quarters * centsToQuarters - dimes * centsToDimes)
                / centsToNickels;
        int pennies = amount - dollors * centsToDollors
                - quarters * centsToQuarters - dimes * centsToDimes
                - nickels * centsToNickels;

        out.println("Dollors: " + dollors);
        out.println("Quarters: " + quarters);
        out.println("Dimes: " + dimes);
        out.println("Nickels: " + nickels);
        out.println("Pennies: " + pennies);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
