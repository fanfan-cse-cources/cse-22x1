import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    @Test
    public final void testNoArgumentConstructor() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber expected = this.constructorRef();

        assertEquals(expected, n);
    }

    @Test
    public final void testIntConstructor() {
        NaturalNumber n = this.constructorTest(1);
        NaturalNumber expected = this.constructorRef(1);

        assertEquals(expected, n);
    }

    @Test
    public final void testStringConstructor() {
        NaturalNumber n = this.constructorTest("1");
        NaturalNumber expected = this.constructorRef("1");

        assertEquals(expected, n);
    }

    @Test
    public final void testNaturalNumberConstructor() {
        NaturalNumber num = new NaturalNumber2(1);

        NaturalNumber n = this.constructorTest(num);
        NaturalNumber expected = this.constructorRef(num);

        assertEquals(expected, n);
    }

    @Test
    public final void testEmptyMultiplyBy10() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber expected = this.constructorRef(1);

        n.multiplyBy10(1);

        assertEquals(expected, n);
    }

    @Test
    public final void testNumberMultiplyBy10() {
        NaturalNumber n = this.constructorTest(10);
        NaturalNumber expected = this.constructorRef(100);

        n.multiplyBy10(0);

        assertEquals(expected, n);
    }

    @Test
    public final void testLargeNumberMultiplyBy10() {
        NaturalNumber n = this.constructorTest(15);
        NaturalNumber expected = this.constructorRef(150);

        n.multiplyBy10(0);

        assertEquals(expected, n);
    }

    @Test
    public final void testLargestIntNumberMultiplyBy10() {
        NaturalNumber n = this.constructorTest(2147483647);
        NaturalNumber expected = this.constructorRef("21474836470");

        n.multiplyBy10(0);

        assertEquals(expected, n);
    }

    @Test
    public final void testLargestIntNumberMultiplyBy10Plus() {
        NaturalNumber n = this.constructorTest(2147483647);
        NaturalNumber expected = this.constructorRef("21474836475");

        n.multiplyBy10(5);

        assertEquals(expected, n);
    }

    @Test
    public final void testNumberDivideBy10() {
        NaturalNumber n = this.constructorTest(10);
        NaturalNumber expected = this.constructorRef(1);

        int x = n.divideBy10();
        int expectedX = 0;

        assertEquals(expected, n);
        assertEquals(expectedX, x);
    }

    @Test
    public final void testNumberDivideBy10WithNonZero() {
        NaturalNumber n = this.constructorTest(101);
        NaturalNumber expected = this.constructorRef(10);

        int x = n.divideBy10();
        int expectedX = 1;

        assertEquals(expected, n);
        assertEquals(expectedX, x);
    }

    @Test
    public final void testNumberDivideBy10ToEmpty() {
        NaturalNumber n = this.constructorTest(5);
        NaturalNumber expected = this.constructorRef();

        int x = n.divideBy10();
        int expectedX = 5;

        assertEquals(expected, n);
        assertEquals(expectedX, x);
    }

    @Test
    public final void testLargestIntNumberDivideBy10() {
        NaturalNumber n = this.constructorTest(2147483647);
        NaturalNumber expected = this.constructorRef("214748364");

        int x = n.divideBy10();
        int expectedX = 7;

        assertEquals(expected, n);
        assertEquals(expectedX, x);
    }

    @Test
    public final void testMoreThanLargestIntNumberDivideBy10() {
        NaturalNumber n = this.constructorTest("21474836475");
        NaturalNumber expected = this.constructorRef("2147483647");

        int x = n.divideBy10();
        int expectedX = 5;

        assertEquals(expected, n);
        assertEquals(expectedX, x);
    }

    @Test
    public final void testIsZeroTrue() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber expected = this.constructorRef();

        assertEquals(expected.isZero(), n.isZero());
    }

    @Test
    public final void testIsZeroFalse() {
        NaturalNumber n = this.constructorTest(5);
        NaturalNumber expected = this.constructorRef(5);

        assertEquals(expected.isZero(), n.isZero());
    }

}
