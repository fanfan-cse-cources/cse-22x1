import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Yifan Yao
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        int answer = 0;

        if (exp.isTag()) {
            // Return number if <number> tag present
            if (exp.label().equals("number")) {
                String valueS = exp.attributeValue("value");
                if (FormatChecker.canParseInt(valueS)) {
                    answer = Integer.parseInt(valueS);
                } else {
                    // Report when <number> tag violate preconditions of int
                    Reporter.fatalErrorToConsole("Violation of: canParseInt");
                }
            }
        }

        // Calculations
        if (exp.label().equals("plus")) {
            answer = evaluate(exp.child(0)) + evaluate(exp.child(1));
        }
        if (exp.label().equals("minus")) {
            answer = evaluate(exp.child(0)) - evaluate(exp.child(1));
        }
        if (exp.label().equals("times")) {
            answer = evaluate(exp.child(0)) * evaluate(exp.child(1));
        }
        if (exp.label().equals("divide")) {
            if (evaluate(exp.child(1)) != 0) {
                answer = evaluate(exp.child(0)) / evaluate(exp.child(1));
            } else {
                // Report when denominator is 0
                Reporter.fatalErrorToConsole(
                        "Violation of: number on denominator != 0");
            }
        }

        return answer;

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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
