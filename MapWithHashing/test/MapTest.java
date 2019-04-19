import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Yifan Yao, Yueyi Hua
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
    public final void testConstructor() {
        Map<String, String> map = this.constructorTest();
        Map<String, String> expectedMap = this.constructorRef();

        assertEquals(map, expectedMap);
    }

    @Test
    public final void testArguConstructor() {
        Map<String, String> map = this.createFromArgsTest("Hello", "World");
        Map<String, String> expectedMap = this.createFromArgsRef("Hello",
                "World");

        assertEquals(map, expectedMap);
    }

    @Test
    public final void testAddEmpty() {
        Map<String, String> map = this.createFromArgsTest();
        Map<String, String> expectedMap = this.createFromArgsRef("Three",
                "Four");

        map.add("Three", "Four");

        assertEquals(map, expectedMap);
    }

    @Test
    public final void testAddNotEmptyOne() {
        Map<String, String> map = this.createFromArgsTest("One", "Two");
        Map<String, String> expectedMap = this.createFromArgsRef("One", "Two",
                "Three", "Four");

        map.add("Three", "Four");

        assertEquals(map, expectedMap);
    }

    @Test
    public final void testAddNotEmptyMore() {
        Map<String, String> map = this.createFromArgsTest("One", "Two", "Three",
                "Four");
        Map<String, String> expectedMap = this.createFromArgsRef("One", "Two",
                "Three", "Four", "Five", "Six", "Seven", "Eight");

        map.add("Five", "Six");
        map.add("Seven", "Eight");

        assertEquals(map, expectedMap);
    }

    @Test
    public final void testRemoveLeavingEmpty() {
        Map<String, String> map = this.createFromArgsTest("Three", "Four");
        Map<String, String> expectedMap = this.createFromArgsRef();
        Pair<String, String> removed = map.remove("Three");

        assertEquals("Three", removed.key());
        assertEquals("Four", removed.value());
        assertEquals(map, expectedMap);
    }

    @Test
    public final void testRemoveNotLeavingEmptyOne() {
        Map<String, String> map = this.createFromArgsTest("One", "Two", "Three",
                "Four", "Five", "Six", "Seven", "Eight");
        Map<String, String> expectedMap = this.createFromArgsRef("One", "Two",
                "Three", "Four", "Seven", "Eight");
        Pair<String, String> removed = map.remove("Five");

        assertEquals("Five", removed.key());
        assertEquals("Six", removed.value());
        assertEquals(map, expectedMap);
    }

    @Test
    public final void testRemoveNotLeavingEmptyMore() {
        Map<String, String> map = this.createFromArgsTest("One", "Two", "Three",
                "Four", "Five", "Six", "Seven", "Eight");
        Map<String, String> expectedMap = this.createFromArgsRef("One", "Two",
                "Seven", "Eight");
        Pair<String, String> removed = map.remove("Five");
        Pair<String, String> removedTwo = map.remove("Three");

        assertEquals("Five", removed.key());
        assertEquals("Six", removed.value());
        assertEquals("Three", removedTwo.key());
        assertEquals("Four", removedTwo.value());
        assertEquals(map, expectedMap);
    }

    @Test
    public final void testRemoveAnyOne() {
        Map<String, String> map = this.createFromArgsTest("Three", "Four");
        Map<String, String> expectedMap = this.createFromArgsRef();
        Pair<String, String> removed = map.removeAny();

        assertEquals("Three", removed.key());
        assertEquals("Four", removed.value());
        assertEquals(map, expectedMap);
    }

    @Test
    public final void testRemoveAnyMore() {
        Map<String, String> map = this.createFromArgsTest("One", "Two", "Three",
                "Four");
        Map<String, String> mapExpected = this.createFromArgsRef("One", "Two",
                "Three", "Four");
        Pair<String, String> removed = map.removeAny();

        assertEquals(true, mapExpected.hasKey(removed.key()));

        mapExpected.remove(removed.key());
        assertEquals(map, mapExpected);
    }

    @Test
    public final void testValueOne() {
        Map<String, String> map = this.createFromArgsTest("Three", "Four");
        Map<String, String> expectedMap = this.createFromArgsRef("Three",
                "Four");
        String a = map.value("Three");
        String b = expectedMap.value("Three");
        assertEquals("Four", a);
        assertEquals("Four", b);
        assertEquals(a, b);
        assertEquals(map, expectedMap);
    }

    @Test
    public final void testValueMore() {
        Map<String, String> map = this.createFromArgsTest("One", "Two", "Three",
                "Four", "Five", "Six", "Seven", "Eight");
        Map<String, String> expectedMap = this.createFromArgsRef("One", "Two",
                "Three", "Four", "Five", "Six", "Seven", "Eight");

        String a = map.value("One");
        String b = expectedMap.value("One");
        String c = map.value("Seven");
        String d = expectedMap.value("Seven");

        assertEquals("Two", a);
        assertEquals("Two", b);
        assertEquals(b, a);
        assertEquals("Eight", c);
        assertEquals("Eight", d);
        assertEquals(d, c);
        assertEquals(map, expectedMap);
    }

    @Test
    public final void testHasKeyOne() {
        Map<String, String> map = this.createFromArgsTest("Three", "Four");
        Map<String, String> expectedMap = this.createFromArgsRef("Three",
                "Four");

        Boolean has = map.hasKey("Three");
        Boolean expectedHas = expectedMap.hasKey("Three");

        assertEquals(true, has);
        assertEquals(true, expectedHas);
        assertEquals(has, expectedHas);
        assertEquals(map, expectedMap);
    }

    @Test
    public final void testHasKeyMore() {
        Map<String, String> map = this.createFromArgsTest("One", "Two", "Three",
                "Four", "Five", "Six", "Seven", "Eight");
        Map<String, String> expectedMap = this.createFromArgsRef("One", "Two",
                "Three", "Four", "Five", "Six", "Seven", "Eight");

        Boolean has = map.hasKey("Five");
        Boolean expectedHas = expectedMap.hasKey("Five");

        assertEquals(true, has);
        assertEquals(true, expectedHas);
        assertEquals(has, expectedHas);
        assertEquals(map, expectedMap);
    }

    @Test
    public final void testHasNotKey() {
        Map<String, String> map = this.createFromArgsTest("Three", "Four");
        Map<String, String> expectedMap = this.createFromArgsRef("Three",
                "Four");

        Boolean hasNot = map.hasKey("Hello");
        Boolean expectedHasNot = expectedMap.hasKey("Hello");

        assertEquals(false, hasNot);
        assertEquals(false, expectedHasNot);
        assertEquals(hasNot, expectedHasNot);
        assertEquals(map, expectedMap);
    }

    @Test
    public final void testEmptySize() {
        Map<String, String> map = this.constructorTest();
        Map<String, String> expectedMap = this.constructorRef();

        int size = map.size();
        int expectedSize = expectedMap.size();

        assertEquals(0, size);
        assertEquals(0, expectedSize);
        assertEquals(size, expectedSize);
        assertEquals(map, expectedMap);
    }

    @Test
    public final void testSizeOne() {
        Map<String, String> map = this.createFromArgsTest("Three", "Four");
        Map<String, String> expectedMap = this.createFromArgsRef("Three",
                "Four");

        int size = map.size();
        int expectedSize = expectedMap.size();

        assertEquals(1, size);
        assertEquals(1, expectedSize);
        assertEquals(size, expectedSize);
        assertEquals(map, expectedMap);
    }

    @Test
    public final void testSizeMore() {
        Map<String, String> map = this.createFromArgsTest("One", "Two", "Three",
                "Four", "Five", "Six", "Seven", "Eight");
        Map<String, String> expectedMap = this.createFromArgsRef("One", "Two",
                "Three", "Four", "Five", "Six", "Seven", "Eight");

        int size = map.size();
        int expectedSize = expectedMap.size();

        assertEquals(4, size);
        assertEquals(4, expectedSize);
        assertEquals(size, expectedSize);
        assertEquals(map, expectedMap);
    }
}
