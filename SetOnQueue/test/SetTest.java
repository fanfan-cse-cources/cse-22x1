import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Yifan Yao
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
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

    /**
     * Test add.
     */
    @Test
    public final void testAdd() {
        Set<String> s = this.createFromArgsTest("1", "2");
        Set<String> expected = this.createFromArgsRef("1", "2", "3");

        s.add("3");

        assertEquals(s, expected);
    }

    /**
     * Test remove.
     */
    @Test
    public final void testRemove() {
        Set<String> s = this.createFromArgsTest("1", "2", "3");
        Set<String> expected = this.createFromArgsRef("1", "2");

        s.remove("3");

        assertEquals(s, expected);
    }

    /**
     * Test removeAny.
     */
    @Test
    public final void testRemoveAny() {
        Set<String> s = this.createFromArgsTest("1", "2", "3");
        Set<String> expected = this.createFromArgsRef("1", "2", "3");

        String x = s.removeAny();
        assertTrue(expected.contains(x) && s.size() == expected.size() - 1);
    }

    /**
     * Test contains.
     */
    @Test
    public final void testContains() {
        Set<String> s = this.createFromArgsTest("1", "2", "3", "4");
        Set<String> expected = this.createFromArgsRef("1", "2", "3", "4");

        String x = "1";

        assertTrue(expected.contains(x) && s.contains(x));
    }

    /**
     * Test non contains.
     */
    @Test
    public final void testNonContains() {
        Set<String> s = this.createFromArgsTest("1", "2", "3", "4");
        Set<String> expected = this.createFromArgsRef("1", "2", "3", "4");

        String x = "0";

        assertTrue(!expected.contains(x) && !s.contains(x));
    }

    /**
     * Test size.
     */
    @Test
    public final void testSize() {
        Set<String> s = this.createFromArgsTest("1", "2", "3", "4");
        Set<String> expected = this.createFromArgsRef("1", "2", "3", "4");

        int s1 = s.size();
        int s2 = expected.size();

        assertTrue(s1 == s2);
    }

}
