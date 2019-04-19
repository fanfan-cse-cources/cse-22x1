import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.sequence.Sequence;

/**
 * JUnit test fixture for {@code Sequence<String>}'s constructor and kernel
 * methods.
 *
 * @author Yifan Yao
 *
 */
public abstract class SequenceTest {

    /**
     * Invokes the appropriate {@code Sequence} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new sequence
     * @ensures constructorTest = <>
     */
    protected abstract Sequence<String> constructorTest();

    /**
     * Invokes the appropriate {@code Sequence} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new sequence
     * @ensures constructorRef = <>
     */
    protected abstract Sequence<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsTest = [entries in args]
     */
    private Sequence<String> createFromArgsTest(String... args) {
        Sequence<String> sequence = this.constructorTest();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     *
     * Creates and returns a {@code Sequence<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsRef = [entries in args]
     */
    private Sequence<String> createFromArgsRef(String... args) {
        Sequence<String> sequence = this.constructorRef();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     * test for add method.
     */
    @Test
    public void testConstructor() {
        Sequence<String> sequence = this.constructorTest();
        Sequence<String> expected = this.constructorRef();
        assertEquals(sequence, expected);

    }

    /**
     * test for add.
     */
    @Test
    public void testAdd() {
        Sequence<String> s = this.createFromArgsTest("2", "3", "4");
        Sequence<String> expected = this.createFromArgsRef("1", "2", "3", "4");

        s.add(0, "1");

        assertEquals(expected, s);
    }

    /**
     * test for empty add.
     */
    @Test
    public void testEmptyAdd() {
        Sequence<String> s = this.createFromArgsTest();
        Sequence<String> expected = this.createFromArgsRef("1");

        s.add(0, "1");

        assertEquals(s, expected);
    }

    /**
     * test for Remove.
     */
    @Test
    public void testRemove() {
        Sequence<String> s = this.createFromArgsTest("1", "2", "3");
        Sequence<String> expected = this.createFromArgsRef("2", "3");

        String x = s.remove(0);

        assertEquals(expected, s);
        assertEquals(x, "1");
    }

    /**
     * test for Remove.
     */
    @Test
    public void testOneRemove() {
        Sequence<String> s = this.createFromArgsTest("1");
        Sequence<String> expected = this.createFromArgsRef();

        String x = s.remove(0);

        assertEquals(expected, s);
        assertEquals(x, "1");
    }

    /**
     * test for Length.
     */
    @Test
    public void testLength() {
        Sequence<String> s = this.createFromArgsTest("1", "2", "3");
        Sequence<String> expected = this.createFromArgsRef("1", "2", "3");

        assertEquals(3, s.length());
        assertEquals(s, expected);
    }

    /**
     * test for empty Length.
     */
    @Test
    public void testEmptyLength() {
        Sequence<String> s = this.createFromArgsTest();
        Sequence<String> expected = this.createFromArgsRef();

        assertEquals(0, s.length());
        assertEquals(s, expected);
    }

    /**
     * test for Length.
     */
    @Test
    public void testOneLength() {
        Sequence<String> s = this.createFromArgsTest("1");
        Sequence<String> expected = this.createFromArgsRef("1");

        assertEquals(1, s.length());
        assertEquals(s, expected);
    }

}
