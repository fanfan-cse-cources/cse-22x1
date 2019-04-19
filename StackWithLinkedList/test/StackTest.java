import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 *
 * @author Yifan Yao
 *
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     *
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    @Test
    public final void testDefaultConstructor() {
        Stack<String> s = this.constructorTest();
        Stack<String> sExpected = this.constructorRef();

        assertEquals(sExpected, s);
    }

    @Test
    public final void testPushFromEmpty() {
        Stack<String> s = this.createFromArgsTest();
        Stack<String> sExpected = this.createFromArgsRef("Hello");

        s.push("Hello");

        assertEquals(sExpected, s);
    }

    @Test
    public final void testPushFromNonEmpty() {
        Stack<String> s = this.createFromArgsTest("World");
        Stack<String> sExpected = this.createFromArgsRef("Hello", "World");

        s.push("Hello");

        assertEquals(sExpected, s);
    }

    @Test
    public final void testPopToEmpty() {
        Stack<String> s = this.createFromArgsTest("Hello");
        Stack<String> sExpected = this.createFromArgsRef();

        assertEquals("Hello", s.pop());
        assertEquals(sExpected, s);
    }

    @Test
    public final void testPopToNonEmpty() {
        Stack<String> s = this.createFromArgsTest("Hello", "World");
        Stack<String> sExpected = this.createFromArgsRef("Hello", "World");

        assertEquals(sExpected.pop(), s.pop());
        assertEquals(sExpected, s);
    }

    @Test
    public final void testEmptyLength() {
        Stack<String> s = this.createFromArgsTest();

        assertEquals(0, s.length());
    }

    @Test
    public final void testLength() {
        Stack<String> s = this.createFromArgsTest("Hello");

        assertEquals(1, s.length());
    }

    @Test
    public final void testLargerLength() {
        Stack<String> s = this.createFromArgsTest("Hello", "World");

        assertEquals(2, s.length());
    }
}
