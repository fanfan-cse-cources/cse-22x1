import components.statement.Statement;
import components.statement.StatementKernel.Condition;

/**
 * Utility class with method to count the number of calls to primitive
 * instructions (move, turnleft, turnright, infect, skip) in a given
 * {@code Statement}.
 *
 * @author Yifan Yao
 *
 */
public final class CountPrimitiveCalls {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CountPrimitiveCalls() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @return the number of calls to primitive instructions in {@code s}
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */
                for (int i = 0; i < s.lengthOfBlock(); i++) {
                    Statement x = s.newInstance();

                    x = s.removeFromBlock(i);
                    count += countOfPrimitiveCalls(x);

                    s.addToBlock(i, x);
                }

                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */
                Statement x = s.newInstance();
                Condition c = s.disassembleIf(x);

                count = countOfPrimitiveCalls(x);

                s.assembleIf(c, x);

                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */
                Statement x = s.newInstance();
                Statement xx = s.newInstance();
                Condition c = s.disassembleIfElse(x, xx);

                count = countOfPrimitiveCalls(x) + countOfPrimitiveCalls(xx);

                s.assembleIfElse(c, x, xx);

                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */
                Statement x = s.newInstance();
                Condition c = s.disassembleWhile(x);

                count = countOfPrimitiveCalls(x);

                s.assembleWhile(c, x);

                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */
                String call = s.disassembleCall();

                if (call.equals("move") || call.equals("infect")
                        || call.equals("skip") || call.equals("turnleft")
                        || call.equals("turnright")) {

                    count = 1;

                }
                s.assembleCall(call);

                break;
            }
            default: {
                // this will never happen...can you explain why?
                // No, I can't.
                break;
            }
        }
        return count;
    }

}
