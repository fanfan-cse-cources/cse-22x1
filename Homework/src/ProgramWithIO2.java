import components.queue.Queue;
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
public final class ProgramWithIO2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ProgramWithIO2() {
    }

    /**
     * Evaluates a Boolean expression and returns its value.
     *
     * @param tokens
     *            the {@code Queue<String>} that starts with a bool-expr string
     * @return value of the expression
     * @updates tokens
     * @requires [a bool-expr string is a prefix of tokens]
     * @ensures
     *
     *          <pre>
     * valueOfBoolExpr =
     *   [value of longest bool-expr string at start of #tokens]  and
     * #tokens = [longest bool-expr string at start of #tokens] * tokens
     *          </pre>
     */
    public static boolean valueOfBoolExpr(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";

        boolean result = false;

        while (tokens.length() > 0) {
            switch (tokens.dequeue()) {
                case "T": {
                    result = true;
                    break;
                }
                case "F": {
                    result = false;
                    break;
                }
                case "NOT": {
                    result = !valueOfBoolExpr(tokens);
                    break;
                }
                case "(": {
                    result = valueOfBoolExpr(tokens);
                    break;
                }
                case ")": {
                    break;
                }
                case "AND": {
                    result &= valueOfBoolExpr(tokens);
                    break;
                }
                case "OR": {
                    result |= valueOfBoolExpr(tokens);
                    break;
                }
                default:
                    break;
            }
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
        /*
         * Put your main program code here
         */
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
