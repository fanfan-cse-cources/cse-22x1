import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * A program that checks whether a string entered by the user meets several
 * criteria to qualify as a valid password.
 *
 * @author Yifan Yao
 *
 */
public final class CheckPassword {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CheckPassword() {
    }

    /**
     * Put a short phrase describing the static method myMethod here.
     */
    /**
     * Checks whether the given String satisfies the CSE department criteria for
     * a valid password. Prints an appropriate message to the given output
     * stream.
     *
     * @param s
     *            the String to check
     * @param out
     *            the output stream
     */
    private static void checkPassword(String s, SimpleWriter out) {
        final int minLength = 6;
        final int minRequir = 3;
        int secureCredit = 0;

        if (containsUpperCaseLetter(s)) {
            secureCredit++;
            out.println("Contains upper case letter");
        } else {
            out.println("*DOES NOT* contains upper case letter");
        }
        if (containsLowerCaseLetter(s)) {
            secureCredit++;
            out.println("Contains lower case letter");
        } else {
            out.println("*DOES NOT* contains lower case letter");
        }
        if (containsDigit(s)) {
            secureCredit++;
            out.println("Contains digit");
        } else {
            out.println("*DOES NOT* contains digit");
        }
        if (containsSpecial(s)) {
            secureCredit++;
            out.println("Contains special characters");
        } else {
            out.println("*DOES NOT* contains special characters");
        }
        if (s.length() > minLength && secureCredit >= minRequir) {
            out.println("\nCheck Pass");
        } else if (s.length() <= minLength) {
            out.println("\nCheck Failed");
            out.println("Length *DOES NOT* meet minimum requirment");
        } else if (secureCredit < minRequir) {
            out.println("\nCheck Failed");
            out.println("*DOES NOT* meet minimum requirment");
        }

    }

    /**
     * Checks if the given String contains an upper case letter.
     *
     * @param s
     *            the String to check
     * @return true if s contains an upper case letter, false otherwise
     */
    private static boolean containsUpperCaseLetter(String s) {
        int i = 0;
        boolean result = false;
        while (i < s.length()) {
            if (Character.isUpperCase(s.charAt(i))) {
                result = true;
            }
            i++;
        }
        return result;
    }

    /**
     * Checks if the given String contains an lower case letter.
     *
     * @param s
     *            the String to check
     * @return true if s contains an lower case letter, false otherwise
     */
    private static boolean containsLowerCaseLetter(String s) {
        int i = 0;
        boolean result = false;
        while (i < s.length()) {
            if (Character.isLowerCase(s.charAt(i))) {
                result = true;
            }
            i++;
        }
        return result;
    }

    /**
     * Checks if the given String contains a digit.
     *
     * @param s
     *            the String to check
     * @return true if s contains a digit, false otherwise
     */
    private static boolean containsDigit(String s) {
        int i = 0;
        boolean result = false;
        while (i < s.length()) {
            if (Character.isDigit(s.charAt(i))) {
                result = true;
            }
            i++;
        }
        return result;
    }

    /**
     * Checks if the given String contains an special characters.
     *
     * @param s
     *            the String to check
     * @return true if s contains a special characters, false otherwise
     */
    private static boolean containsSpecial(String s) {
        int i = 0;
        boolean result = false;
        while (i < s.length()) {
            if (!Character.isUpperCase(s.charAt(i))
                    && !Character.isLowerCase(s.charAt(i))
                    && !Character.isDigit(s.charAt(i))) {
                result = true;
            }
            i++;
        }
        return result;
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

        out.print("Password: ");
        String password = in.nextLine();
        checkPassword(password, out);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
