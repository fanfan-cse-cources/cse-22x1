import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Yifan Yao, Yueyi Hua
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to statement string of body of
     *          instruction at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        tokens.dequeue();

        String s1 = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(s1),
                "Error: IDENTIFIER expected, found: \"" + s1 + "\"");
        Reporter.assertElseFatalError(
                !s1.equals("move") && !s1.equals("turnleft")
                        && !s1.equals("turnright") && !s1.equals("infect")
                        && !s1.equals("skip"),
                "Error: New instruction name must not be name of primitive instruction \""
                        + s1 + "\"");

        String s2 = tokens.dequeue();
        Reporter.assertElseFatalError(s2.equals("IS"),
                "Error: Keyword \"IS\" expected, found: \"" + s2 + "\"");

        body.parseBlock(tokens);

        s2 = tokens.dequeue();
        Reporter.assertElseFatalError(s2.equals("END"),
                "Error: Keyword \"END\" expected, found: \"" + s2 + "\"");

        s2 = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(s2),
                "Error: IDENTIFIER expected, found: \"" + s2 + "\"");
        Reporter.assertElseFatalError(s2.equals(s1),
                "Error: IDENTIFIER \"" + s2 + "\" at end of instruction \"" + s1
                        + "\" must match instruction name");

        return s1;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        String s1 = tokens.dequeue();
        Reporter.assertElseFatalError(s1.equals("PROGRAM"),
                "Error: Keyword \"PROGRAM\" expected, found: \"" + s1 + "\"");
        String s2 = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(s2),
                "Error: IDENTIFIER expected, found: \"" + s2 + "\"");

        s1 = tokens.dequeue();
        Reporter.assertElseFatalError(s1.equals("IS"),
                "Error: Keyword \"IS\" expected, found: \"" + s1 + "\"");

        Map<String, Statement> m = this.newContext();
        Reporter.assertElseFatalError(
                tokens.front().equals("BEGIN")
                        || tokens.front().equals("INSTRUCTION"),
                "Error: Keywords \"BEGIN\" or \"INSTRUCTION\" expected, found: \""
                        + tokens.front() + "\"");

        Statement stat;
        while (tokens.front().equals("INSTRUCTION")) {
            stat = this.newBody();
            String s3 = parseInstruction(tokens, stat);
            Reporter.assertElseFatalError(!m.hasKey(s3), "Error: Instruction \""
                    + s3 + "\" cannot be already defined");
            m.add(s3, stat);
        }

        s1 = tokens.dequeue();
        Reporter.assertElseFatalError(s1.equals("BEGIN"),
                "Error: Keyword \"BEGIN\" expected, found: \"" + s1 + "\"");
        stat = this.newBody();
        stat.parseBlock(tokens);

        s1 = tokens.dequeue();
        Reporter.assertElseFatalError(s1.equals("END"),
                "Error: Keyword \"END\" expected, found: \"" + s1 + "\"");

        s1 = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(s1),
                "Error: IDENTIFIER expected, found: \"" + s1 + "\"");
        Reporter.assertElseFatalError(s1.equals(s2),
                "Error: IDENTIFIER \"" + s1 + "\" at end of program \"" + s2
                        + "\" must match program name");
        Reporter.assertElseFatalError(tokens.length() == 1, "Error: found \""
                + tokens.front() + "\" beyond end of program source");

        this.setName(s2);
        this.swapContext(m);
        this.swapBody(stat);
    }

    /*
     * Main test method -------------------------------------------------------
     */

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
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
