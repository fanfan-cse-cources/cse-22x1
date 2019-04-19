import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Yifan Yao, Yueyi Hua
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testEmptyArguConstructor() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testNonEmptyArguConstructor() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "Hello");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "Hello", "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddMore() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "Hello");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "Hello", "green", "red");
        m.add("green");
        m.add("red");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionMoadeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionMoadeOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Hi");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "Hi");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionMoadeMore() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Hi",
                "Hello");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "Hi", "Hello");
        m.changeToExtractionMode();
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
    }

    @Test
    public final void testRemoveFirstOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String x = m.removeFirst();
        assertEquals(x, "1");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstMore() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "3",
                "2", "1");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "2", "3");
        String x = m.removeFirst();
        assertEquals(x, "1");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testEmptyisInInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        boolean x = m.isInInsertionMode();
        boolean y = mExpected.isInInsertionMode();
        assertEquals(true, x);
        assertEquals(true, y);
        assertEquals(x, y);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testEmptyisNotInInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        boolean x = m.isInInsertionMode();
        boolean y = mExpected.isInInsertionMode();
        assertEquals(false, x);
        assertEquals(false, y);
        assertEquals(x, y);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOneisInInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Hi");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "Hi");
        boolean x = m.isInInsertionMode();
        boolean y = mExpected.isInInsertionMode();
        assertEquals(true, x);
        assertEquals(true, y);
        assertEquals(x, y);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOneisNotInInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "Hi");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "Hi");
        boolean x = m.isInInsertionMode();
        boolean y = mExpected.isInInsertionMode();
        assertEquals(false, x);
        assertEquals(false, y);
        assertEquals(x, y);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testMoreisInInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Hi",
                "Hello");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "Hi", "Hello");
        boolean x = m.isInInsertionMode();
        boolean y = mExpected.isInInsertionMode();
        assertEquals(true, x);
        assertEquals(true, y);
        assertEquals(x, y);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testMoreisNotInInsertionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "Hi",
                "Hello");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "Hi", "Hello");
        boolean x = m.isInInsertionMode();
        boolean y = mExpected.isInInsertionMode();
        assertEquals(false, x);
        assertEquals(false, y);
        assertEquals(x, y);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testEmptyComparator() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        Comparator<String> x = m.order();
        Comparator<String> y = mExpected.order();
        assertEquals(ORDER, x);
        assertEquals(ORDER, y);
        assertEquals(x, y);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOneComparator() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Hi");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "Hi");
        Comparator<String> x = m.order();
        Comparator<String> y = mExpected.order();
        assertEquals(ORDER, x);
        assertEquals(ORDER, y);
        assertEquals(x, y);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testMoreComparator() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Hi",
                "Hello");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "Hi", "Hello");
        Comparator<String> x = m.order();
        Comparator<String> y = mExpected.order();
        assertEquals(ORDER, x);
        assertEquals(ORDER, y);
        assertEquals(x, y);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testEmptySize() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        int mLength = m.size();
        int mExpectedLength = mExpected.size();
        assertEquals(0, mLength);
        assertEquals(0, mExpectedLength);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOneSize() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Hi");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "Hi");
        int mLength = m.size();
        int mExpectedLength = mExpected.size();
        assertEquals(1, mLength);
        assertEquals(1, mExpectedLength);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testMoreSize() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Hi",
                "red", "green", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "Hi", "red", "green", "yellow");
        int mLength = m.size();
        int mExpectedLength = mExpected.size();
        assertEquals(4, mLength);
        assertEquals(4, mExpectedLength);
        assertEquals(mExpected, m);
    }
    // test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

}
