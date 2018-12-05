import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Test for StringReassembly.
 *
 * @author Yifan Yao
 */
public class StringReassemblyTest {
    /**
     * Makes the set for the testing.
     *
     * @param args
     *            the strings put into the set
     * @return set the set made from the string put into it
     */
    private static Set<String> createFromArgs(String... args) {
        Set<String> set = new Set1L<String>();
        for (String s : args) {
            set.add(s);
        }
        return set;
    }

    @Test
    public void testCombinationtest1() {
        String left = "ABCDECG";
        String right = "ECGRREW";

        assertEquals("ABCDECGRREW", StringReassembly.combination(left, right,
                StringReassembly.overlap(left, right)));
    }

    @Test
    public void testCombinationtest2() {
        String left = "x1GGG";
        String right = "GGG1GG23342";

        assertEquals("x1GGG1GG23342", StringReassembly.combination(left, right,
                StringReassembly.overlap(left, right)));
    }

    @Test
    public void testAddToSetAvoidingSubstrings1() {
        Set<String> strSet = createFromArgs("hello", "go", "blue");
        Set<String> result = createFromArgs("hello", "world", "go", "blue");

        StringReassembly.addToSetAvoidingSubstrings(strSet, "world");

        assertEquals(result.toString(), strSet.toString());
    }

    @Test
    public void testAddToSetAvoidingSubstrings2() {
        Set<String> strSet = createFromArgs("go", "bucks");
        Set<String> result = createFromArgs("go", "bucks");

        StringReassembly.addToSetAvoidingSubstrings(strSet, "buc");

        assertEquals(result.toString(), strSet.toString());
    }

    @Test
    public void testAddToSetAvoidingSubstrings3() {
        Set<String> strSet = createFromArgs("Hello,", "it's me");
        Set<String> result = createFromArgs("Hello,", "it's me");
        String str = "it's";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(strSet, result);
    }

    @Test
    public void testLinesFromInput1() {
        SimpleReader input = new SimpleReader1L(
                "data/test/testLinesFromInput1.txt");
        Set<String> load = StringReassembly.linesFromInput(input);
        Set<String> result = createFromArgs("E,e,e,", "Qu xiang xiang tian ge.",
                "Bai mao fu lu shui.", "Hong zhang bo qing bo.");

        assertEquals(result.toString(), load.toString());
    }

    @Test
    public void testPrintWithLineSeparators1() {
        SimpleWriter out = new SimpleWriter1L();
        String test = "random~mmmmmmmmm~words~";

        out.println("testPrintWithLineSeparators1: ");
        StringReassembly.printWithLineSeparators(test, out);
        out.close();
    }

    @Test
    public void testPrintWithLineSeparators2() {
        SimpleWriter out = new SimpleWriter1L();
        String test = "mu lan ci~~ji ji fu ji ji,~mu lan da fei ji.\n~da de shen me "
                + "ji,~bo yin 747.~";

        out.println("testPrintWithLineSeparators2: ");
        StringReassembly.printWithLineSeparators(test, out);
        out.close();
    }
}
