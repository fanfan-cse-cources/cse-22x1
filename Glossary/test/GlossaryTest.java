import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 *
 * @author Yifan Yao
 *
 */
public final class GlossaryTest {
    // Test of generateElements
    // Single element
    @Test
    public void generateElementsTest1() {
        final String s = ",";
        Set<Character> sSet = new Set1L<>();

        sSet.add(',');

        Set<Character> sSetTest = new Set1L<>();
        Glossary.generateElements(s, sSetTest);

        assertEquals(sSet, sSetTest);
    }

    // Test of generateElements
    // Multiple elements
    @Test
    public void generateElementsTest2() {
        final String s = " \t,";
        Set<Character> sSet = new Set1L<>();
        sSet.add(' ');
        sSet.add('\t');
        sSet.add(',');

        Set<Character> sSetTest = new Set1L<>();
        Glossary.generateElements(s, sSetTest);

        assertEquals(sSet, sSetTest);
    }

    // Test of generateElements
    // Multiple elements with duplicate
    @Test
    public void generateElementsTest3() {
        final String s = " \t, ";
        Set<Character> sSet = new Set1L<>();
        sSet.add(' ');
        sSet.add('\t');
        sSet.add(',');

        Set<Character> sSetTest = new Set1L<>();
        Glossary.generateElements(s, sSetTest);

        assertEquals(sSet, sSetTest);
    }

    // Test of generateElements
    // Multiple elements with additional character
    @Test
    public void generateElementsTest4() {
        final String s = " \t,:";
        Set<Character> sSet = new Set1L<>();
        sSet.add(' ');
        sSet.add('\t');
        sSet.add(',');
        sSet.add(':');

        Set<Character> sSetTest = new Set1L<>();
        Glossary.generateElements(s, sSetTest);

        assertEquals(sSet, sSetTest);
    }

    // Test of nextWordOrSeparator
    // Two words with one separator
    @Test
    public void nextWordOrSeparatorTest1() {
        SimpleWriter out = new SimpleWriter1L();
        final String s = "Hello world";
        int p = 0;
        Set<Character> sSet = new Set1L<>();
        sSet.add(' ');
        sSet.add('\t');
        sSet.add(',');

        out.println("nextWordOrSeparatorTest1");

        while (p < s.length()) {
            String token = Glossary.nextWordOrSeparator(s, p, sSet);

            if (sSet.contains(token.charAt(0))) {
                out.print("  Separator: <");
            } else {
                out.print("  Word: <");
            }
            out.println(token + ">");
            p += token.length();
        }

        out.println();
        out.close();
    }

    // Test of nextWordOrSeparator
    // Two words with two same separator
    @Test
    public void nextWordOrSeparatorTest2() {
        SimpleWriter out = new SimpleWriter1L();
        final String s = "Hello  world";
        int p = 0;
        Set<Character> sSet = new Set1L<>();
        sSet.add(' ');
        sSet.add('\t');
        sSet.add(',');

        out.println("nextWordOrSeparatorTest2");

        while (p < s.length()) {
            String token = Glossary.nextWordOrSeparator(s, p, sSet);

            if (sSet.contains(token.charAt(0))) {
                out.print("  Separator: <");
            } else {
                out.print("  Word: <");
            }
            out.println(token + ">");
            p += token.length();
        }

        out.println();
        out.close();
    }

    // Test of nextWordOrSeparator
    // Two words with two different separator
    @Test
    public void nextWordOrSeparatorTest3() {
        SimpleWriter out = new SimpleWriter1L();
        final String s = "Hello, world";
        int p = 0;
        Set<Character> sSet = new Set1L<>();
        sSet.add(' ');
        sSet.add('\t');
        sSet.add(',');

        out.println("nextWordOrSeparatorTest3");

        while (p < s.length()) {
            String token = Glossary.nextWordOrSeparator(s, p, sSet);

            if (sSet.contains(token.charAt(0))) {
                out.print("  Separator: <");
            } else {
                out.print("  Word: <");
            }
            out.println(token + ">");
            p += token.length();
        }

        out.println();
        out.close();
    }

    // Test of nextWordOrSeparator
    // Three words with three different separator
    @Test
    public void nextWordOrSeparatorTest4() {
        SimpleWriter out = new SimpleWriter1L();
        final String s = "Hello, this world\t";
        int p = 0;
        Set<Character> sSet = new Set1L<>();
        sSet.add(' ');
        sSet.add('\t');
        sSet.add(',');

        out.println("nextWordOrSeparatorTest4");

        while (p < s.length()) {
            String token = Glossary.nextWordOrSeparator(s, p, sSet);

            if (sSet.contains(token.charAt(0))) {
                out.print("  Separator: <");
            } else {
                out.print("  Word: <");
            }
            out.println(token + ">");
            p += token.length();
        }

        out.println();
        out.close();
    }

    // Test of putWordsIntoGlossary
    // One term, one line definition
    @Test
    public void putWordsIntoGlossaryTest1() {
        Queue<String> q = new Queue1L<String>();
        Map<String, String> g = new Map1L<String, String>();
        q.enqueue("Hello");
        g.add("Hello",
                "used to express a greeting, answer a telephone, or attract attention.");

        Queue<String> qTest = new Queue1L<String>();
        Map<String, String> gTest = new Map1L<String, String>();

        Glossary.putWordsIntoGlossary(
                "data/testFile/putWordsIntoGlossaryTest1.txt", qTest, gTest);

        assertEquals(q, qTest);
        assertEquals(g, gTest);
    }

    // Test of putWordsIntoGlossary
    // One term, multiple line definition
    @Test
    public void putWordsIntoGlossaryTest2() {
        Queue<String> q = new Queue1L<String>();
        Map<String, String> g = new Map1L<String, String>();
        q.enqueue("Hello");
        g.add("Hello",
                "used to express a greeting, answer a telephone, or attract attention.");

        Queue<String> qTest = new Queue1L<String>();
        Map<String, String> gTest = new Map1L<String, String>();

        Glossary.putWordsIntoGlossary(
                "data/testFile/putWordsIntoGlossaryTest2.txt", qTest, gTest);

        assertEquals(q, qTest);
        assertEquals(g, gTest);
    }

    // Test of putWordsIntoGlossary
    // Multiple term, one line definition
    @Test
    public void putWordsIntoGlossaryTest3() {
        Queue<String> q = new Queue1L<String>();
        Map<String, String> g = new Map1L<String, String>();
        q.enqueue("Hello");
        q.enqueue("World");
        g.add("Hello",
                "used to express a greeting, answer a telephone, or attract attention.");
        g.add("World", "the earth or globe, considered as a planet.");

        Queue<String> qTest = new Queue1L<String>();
        Map<String, String> gTest = new Map1L<String, String>();

        Glossary.putWordsIntoGlossary(
                "data/testFile/putWordsIntoGlossaryTest3.txt", qTest, gTest);

        assertEquals(q, qTest);
        assertEquals(g, gTest);
    }

    // Test of putWordsIntoGlossary
    // Multiple term, multiple line definition
    @Test
    public void putWordsIntoGlossaryTest4() {
        Queue<String> q = new Queue1L<String>();
        Map<String, String> g = new Map1L<String, String>();
        q.enqueue("Hello");
        q.enqueue("World");
        g.add("Hello",
                "used to express a greeting, answer a telephone, or attract attention.");
        g.add("World", "the earth or globe, considered as a planet.");

        Queue<String> qTest = new Queue1L<String>();
        Map<String, String> gTest = new Map1L<String, String>();

        Glossary.putWordsIntoGlossary(
                "data/testFile/putWordsIntoGlossaryTest4.txt", qTest, gTest);

        assertEquals(q, qTest);
        assertEquals(g, gTest);
    }

    // Test of sort (Used for outputIndex)
    @Test
    public void sortTest() {
        Queue<String> t = new Queue1L<String>();
        Queue<String> tTest = new Queue1L<String>();

        t.enqueue("blue");
        t.enqueue("black");
        t.enqueue("green");
        t.enqueue("blue");
        t.enqueue("white");

        // Make tTest as a copy of t
        for (String s : t) {
            tTest.enqueue(s);
        }

        // Sort tTest
        final Comparator<String> cs = String.CASE_INSENSITIVE_ORDER;
        tTest.sort(cs);

        // Restore t and make it as correct order
        t.clear();
        t.enqueue("black");
        t.enqueue("blue");
        t.enqueue("blue");
        t.enqueue("green");
        t.enqueue("white");

        assertEquals(t, tTest);
    }

}
