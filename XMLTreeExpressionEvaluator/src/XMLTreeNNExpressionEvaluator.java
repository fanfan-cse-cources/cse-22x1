import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Yifan Yao
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
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
    private static NaturalNumber evaluate(XMLTree exp) {
        final NaturalNumber zero = new NaturalNumber2();
        NaturalNumber answer = new NaturalNumber2();

        if (exp.isTag()) {
            // Return number if <number> tag present
            if (exp.label().equals("number")) {
                String valueS = exp.attributeValue("value");

                // When the <number> tag can be convert to NaturalNumber
                if (answer.canSetFromString(valueS)) {
                    NaturalNumber value = new NaturalNumber2(valueS);
                    answer.copyFrom(value);
                } else {
                    // Report when <number> tag violate preconditions of NaturalNumber
                    Reporter.fatalErrorToConsole(
                            "Violation of: NaturalNumber >= 0");
                }
            }

            // NaturalNumber Calculations
            if (exp.label().equals("plus")) {
                answer.copyFrom(evaluate(exp.child(0)));
                answer.add(evaluate(exp.child(1)));
            }
            if (exp.label().equals("minus")) {
                if (evaluate(exp.child(0))
                        .compareTo(evaluate(exp.child(1))) >= 0) {
                    answer.copyFrom(evaluate(exp.child(0)));
                    answer.subtract(evaluate(exp.child(1)));
                } else {
                    // Report when result is negative
                    Reporter.fatalErrorToConsole(
                            "Violation of: NaturalNumber >= 0");
                }
            }
            if (exp.label().equals("times")) {
                answer.copyFrom(evaluate(exp.child(0)));
                answer.multiply(evaluate(exp.child(1)));
            }
            if (exp.label().equals("divide")) {
                if (evaluate(exp.child(1)).compareTo(zero) != 0) {
                    answer.copyFrom(evaluate(exp.child(0)));
                    answer.divide(evaluate(exp.child(1)));
                } else {
                    // Report when denominator is 0
                    Reporter.fatalErrorToConsole(
                            "Violation of: NaturalNumber on denominator > 0");
                }
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
