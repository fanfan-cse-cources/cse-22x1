import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Yifan Yao
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    @Test
    public final void testMapConstructor() {

        Map<String, String> m = this.constructorTest();
        Map<String, String> expected = this.constructorRef();

        assertEquals(expected, m);
    }

    @Test
    public final void testAdd() {

        Map<String, String> m = this.constructorTest();
        Map<String, String> expected = this.createFromArgsRef("1", "2", "3",
                "4");

        m.add("1", "2");
        m.add("3", "4");

        assertEquals(expected, m);
    }

    @Test
    public final void testRemove() {

        Map<String, String> m = this.createFromArgsTest("1", "2", "3", "4");
        Map<String, String> expected = this.createFromArgsRef("3", "4");

        m.remove("1");

        assertEquals(expected, m);
    }

    @Test
    public final void testRemoveAny() {

        Map<String, String> m = this.createFromArgsTest("1", "2", "3", "4");
        Map<String, String> expected = this.createFromArgsRef("1", "2", "3",
                "4");

        Pair<String, String> x = m.removeAny();
        expected.remove(x.key());

        assertEquals(expected, m);
    }

    @Test
    public final void testSize() {

        Map<String, String> m = this.createFromArgsTest("1", "2", "3", "4");
        Map<String, String> expected = this.createFromArgsRef("1", "2", "3",
                "4");

        assertEquals(m.size(), expected.size());
    }

    @Test
    public final void testSizeEmpty() {

        Map<String, String> m = this.constructorTest();
        Map<String, String> expected = this.createFromArgsRef();

        assertEquals(m.size(), expected.size());
    }

    @Test
    public final void testValue() {

        Map<String, String> m = this.createFromArgsTest("1", "2", "3", "4");
        Map<String, String> expected = this.createFromArgsRef("1", "2", "3",
                "4");
        String value = m.value("1");

        assertEquals(value, expected.value("1"));
    }

    @Test
    public final void testHasKey() {

        Map<String, String> m = this.createFromArgsTest("1", "2", "3", "4");

        assertTrue(m.hasKey("1"));
        assertTrue(m.hasKey("3"));
    }
}
