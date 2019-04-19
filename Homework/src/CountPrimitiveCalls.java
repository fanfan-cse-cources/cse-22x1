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

                count += countOfPrimitiveCalls(x);

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

                count += countOfPrimitiveCalls(x);
                count += countOfPrimitiveCalls(xx);

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

                count += countOfPrimitiveCalls(x);

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

    /**
     * Refactors the given {@code Statement} so that every IF_ELSE statement
     * with a negated condition (NEXT_IS_NOT_EMPTY, NEXT_IS_NOT_ENEMY,
     * NEXT_IS_NOT_FRIEND, NEXT_IS_NOT_WALL) is replaced by an equivalent
     * IF_ELSE with the opposite condition and the "then" and "else" BLOCKs
     * switched. Every other statement is left unmodified.
     *
     * @param s
     *            the {@code Statement}
     * @updates s
     * @ensures <pre>
     * s = [#s refactored so that IF_ELSE statements with "not"
     *   conditions are simplified so the "not" is removed]
     * </pre>
     */
    public static void simplifyIfElse(Statement s) {
        switch (s.kind()) {
            case BLOCK: {

                for (int i = 0; i < s.lengthOfBlock(); i++) {
                    Statement s1 = s.removeFromBlock(i);
                    simplifyIfElse(s1);
                    s.addToBlock(i, s1);
                }

                break;
            }
            case IF: {

                Statement s1 = s.newInstance();
                Condition c = s.disassembleIf(s1);
                simplifyIfElse(s1);
                s.assembleIf(c, s1);

                break;
            }
            case IF_ELSE: {

                Statement s1 = s.newInstance();
                Statement s2 = s.newInstance();
                Condition c = s.disassembleIfElse(s1, s2);

                switch (c.name()) {
                    case "NEXT_IS_NOT_EMPTY": {

                        c = Condition.NEXT_IS_EMPTY;
                        simplifyIfElse(s1);
                        simplifyIfElse(s2);
                        s.assembleIfElse(c, s2, s1);

                        break;
                    }
                    case "NEXT_IS_NOT_ENEMY": {

                        c = Condition.NEXT_IS_ENEMY;
                        simplifyIfElse(s1);
                        simplifyIfElse(s2);
                        s.assembleIfElse(c, s2, s1);

                        break;
                    }
                    case "NEXT_IS_NOT_FRIEND": {

                        c = Condition.NEXT_IS_FRIEND;
                        simplifyIfElse(s1);
                        simplifyIfElse(s2);
                        s.assembleIfElse(c, s2, s1);

                        break;
                    }
                    case "NEXT_IS_NOT_WALL": {

                        c = Condition.NEXT_IS_WALL;
                        simplifyIfElse(s1);
                        simplifyIfElse(s2);
                        s.assembleIfElse(c, s2, s1);

                        break;
                    }
                    default:
                        break;
                }

                s.assembleIfElse(c, s1, s2);

                break;
            }
            case WHILE: {

                Statement s1 = s.newInstance();
                Statement.Condition c = s.disassembleWhile(s1);
                simplifyIfElse(s1);
                s.assembleWhile(c, s1);

                break;
            }
            case CALL: {
                // nothing to do here...can you explain why?
                // No, I can't
                break;
            }
            default: {
                // this will never happen...can you explain why?
                // No, I can't
                break;
            }
        }
    }

}
