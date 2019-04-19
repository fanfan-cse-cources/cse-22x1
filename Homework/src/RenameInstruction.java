import components.map.Map;
import components.program.Program;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class RenameInstruction {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RenameInstruction() {
    }

    /**
     * Refactors the given {@code Program} by renaming instruction
     * {@code oldName}, and every call to it, to {@code newName}. Everything
     * else is left unmodified.
     *
     * @param p
     *            the {@code Program}
     * @param oldName
     *            the name of the instruction to be renamed
     * @param newName
     *            the new name of the renamed instruction
     * @updates p
     * @requires <pre>
     * oldName is in DOMAIN(p.context)  and
     * [newName is a valid IDENTIFIER]  and
     * newName is not in DOMAIN(p.context)
     * </pre>
     * @ensures <pre>
     * p = [#p refactored so that instruction oldName and every call
     *   to it are replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Program p, String oldName,
            String newName) {

        Map<String, Statement> c = p.newContext();
        p.swapContext(c);

        for (Map.Pair<String, Statement> instr : c) {
            String key = instr.key();

            if (instr.key().equals(oldName)) {
                key = newName;
            }

            c.add(key, instr.value());
        }

        p.swapContext(c);

        Statement b = p.newBody();
        p.swapBody(b);

        for (int i = 0; i < b.lengthOfBlock(); i++) {
            Statement s = b.removeFromBlock(i);
            String inst = s.disassembleCall();
            if (inst.equals(oldName)) {
                inst = newName;
            }
            s.assembleCall(inst);
            b.addToBlock(i, s);
        }

        p.swapBody(b);

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
