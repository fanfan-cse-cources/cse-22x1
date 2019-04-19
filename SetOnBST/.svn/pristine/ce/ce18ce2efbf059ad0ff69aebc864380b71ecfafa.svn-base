import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Yifan Yao, Yueyi Hua
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    @Test
    public final void testConstructor() {
        Set<String> set = this.constructorTest();
        Set<String> expectedSet = this.constructorRef();

        assertEquals(set, expectedSet);
    }

    @Test
    public final void testArguConstructor() {
        Set<String> set = this.createFromArgsTest("Hello");
        Set<String> expectedSet = this.createFromArgsRef("Hello");

        assertEquals(set, expectedSet);
    }

    @Test
    public final void testAddEmpty() {
        Set<String> set = this.createFromArgsTest();
        Set<String> expectedSet = this.createFromArgsRef("Hello");

        set.add("Hello");

        assertEquals(set, expectedSet);
    }

    @Test
    public final void testAddNotEmptyOne() {
        Set<String> set = this.createFromArgsTest("Hello");
        Set<String> expectedSet = this.createFromArgsRef("Hello", "World");

        set.add("World");

        assertEquals(set, expectedSet);
    }

    @Test
    public final void testAddNotEmptyMore() {
        Set<String> set = this.createFromArgsTest("One", "Two", "Three",
                "Four");
        Set<String> expectedSet = this.createFromArgsRef("One", "Two", "Three",
                "Four", "Five", "Six");

        set.add("Five");
        set.add("Six");

        assertEquals(set, expectedSet);
    }

    @Test
    public final void testRemoveLeavingEmpty() {
        Set<String> set = this.createFromArgsTest("One");
        Set<String> expectedSet = this.createFromArgsRef();

        String removed = set.remove("One");

        assertEquals(removed, "One");
        assertEquals(set, expectedSet);
    }

    @Test
    public final void testRemoveNotLeavingEmptyOne() {
        Set<String> set = this.createFromArgsTest("One", "Two");
        Set<String> expectedSet = this.createFromArgsRef("One");

        String removed = set.remove("Two");

        assertEquals(removed, "Two");
        assertEquals(set, expectedSet);
    }

    @Test
    public final void testRemoveNotLeavingEmptyMore() {
        Set<String> set = this.createFromArgsTest("One", "Two", "Three",
                "Four");
        Set<String> expectedSet = this.createFromArgsRef("One", "Two");

        String removed = set.remove("Three");
        String removedTwo = set.remove("Four");

        assertEquals(removed, "Three");
        assertEquals(removedTwo, "Four");
        assertEquals(set, expectedSet);
    }

    @Test
    public final void testRemoveAnyOne() {
        Set<String> set = this.createFromArgsTest("One");
        Set<String> expectedSet = this.createFromArgsRef();

        String removed = set.removeAny();

        assertEquals(removed, "One");
        assertEquals(set, expectedSet);
    }

    @Test
    public final void testRemoveAnyMore() {
        Set<String> set = this.createFromArgsTest("One", "Two");
        Set<String> expectedSet = this.createFromArgsRef("One", "Two");

        String removed = set.removeAny();
        assertEquals(expectedSet.contains(removed), true);
        expectedSet.remove(removed);

        assertEquals(set, expectedSet);
    }

    @Test
    public final void testContainsNone() {
        Set<String> set = this.createFromArgsTest();
        Set<String> expectedSet = this.createFromArgsRef();

        boolean notContains = set.contains("One");
        boolean expectedNotContains = expectedSet.contains("One");

        assertEquals(false, notContains);
        assertEquals(false, expectedNotContains);
        assertEquals(notContains, expectedNotContains);
        assertEquals(set, expectedSet);
    }

    @Test
    public final void testContainsOne() {
        Set<String> set = this.createFromArgsTest("One");
        Set<String> expectedSet = this.createFromArgsRef("One");

        boolean hasContains = set.contains("One");
        boolean expectedHasContains = expectedSet.contains("One");

        assertEquals(true, hasContains);
        assertEquals(true, expectedHasContains);
        assertEquals(hasContains, expectedHasContains);
        assertEquals(set, expectedSet);
    }

    @Test
    public final void testContainsMore() {
        Set<String> set = this.createFromArgsTest("One", "Two", "Three",
                "Four");
        Set<String> expectedSet = this.createFromArgsRef("One", "Two", "Three",
                "Four");

        boolean hasContains = set.contains("One");
        boolean expectedHasContains = expectedSet.contains("One");

        assertEquals(true, hasContains);
        assertEquals(true, expectedHasContains);
        assertEquals(hasContains, expectedHasContains);
        assertEquals(set, expectedSet);
    }

    @Test
    public final void testNotContains() {
        Set<String> set = this.createFromArgsTest("One", "Two", "Three",
                "Four");
        Set<String> expectedSet = this.createFromArgsRef("One", "Two", "Three",
                "Four");

        boolean notContains = set.contains("Five");
        boolean expectedNotContains = expectedSet.contains("Five");

        assertEquals(false, notContains);
        assertEquals(false, expectedNotContains);
        assertEquals(notContains, expectedNotContains);
        assertEquals(set, expectedSet);
    }

    @Test
    public final void testEmptySize() {
        Set<String> set = this.createFromArgsTest();
        Set<String> expectedSet = this.createFromArgsRef();

        int size = set.size();
        int expectedSize = expectedSet.size();

        assertEquals(0, size);
        assertEquals(0, expectedSize);
        assertEquals(size, expectedSize);
        assertEquals(set, expectedSet);
    }

    @Test
    public final void testSizeOne() {
        Set<String> set = this.createFromArgsTest("One");
        Set<String> expectedSet = this.createFromArgsRef("One");

        int size = set.size();
        int expectedSize = expectedSet.size();

        assertEquals(1, size);
        assertEquals(1, expectedSize);
        assertEquals(size, expectedSize);
        assertEquals(set, expectedSet);
    }

    @Test
    public final void testSizeMore() {
        Set<String> set = this.createFromArgsTest("One", "Two", "Three",
                "Four");
        Set<String> expectedSet = this.createFromArgsRef("One", "Two", "Three",
                "Four");

        int size = set.size();
        int expectedSize = expectedSet.size();

        assertEquals(4, size);
        assertEquals(4, expectedSize);
        assertEquals(size, expectedSize);
        assertEquals(set, expectedSet);
    }
}
