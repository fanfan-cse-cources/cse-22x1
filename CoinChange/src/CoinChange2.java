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
public final class CoinChange2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CoinChange2() {
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

        final int[] centsTo = { 100, 25, 10, 5, 1 };
        final String[] centsToName = { "Dollors", "Quarters", "Dimes",
                "Nickels", "Pennies" };
        int[] result = new int[centsTo.length];
        /*
         * Put your main program code here
         */
        out.print("Number of Cents: ");
        int amount = in.nextInteger();
        int remain = amount;

        for (int i = 0; i < centsTo.length; i++) {
            result[i] = remain / centsTo[i];
            remain -= result[i] * centsTo[i];
            out.println(centsToName[i] + ": " + result[i]);
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
