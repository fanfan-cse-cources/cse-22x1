import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Yifan Yao
 *
 */
public class CryptoUtilitiesTest {

    /*
     * Tests of reduceToGCD
     */

    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals("0", n.toString());
        assertEquals("0", m.toString());
    }

    @Test
    public void testReduceToGCD_30_21() {
        NaturalNumber n = new NaturalNumber2(30);
        NaturalNumber m = new NaturalNumber2(21);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals("3", n.toString());
        assertEquals("0", m.toString());
    }

    // Tests of larger numbers for reduceToGCD
    @Test
    public void testReduceToGCD_319_377() {
        NaturalNumber n = new NaturalNumber2(319);
        NaturalNumber m = new NaturalNumber2(377);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals("29", n.toString());
        assertEquals("0", m.toString());
    }

    /*
     * Tests of isEven
     */

    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals("0", n.toString());
        assertTrue(result);
    }

    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals("1", n.toString());
        assertTrue(!result);
    }

    // Tests of larger numbers for testIsEven
    @Test
    public void testIsEven_9() {
        NaturalNumber n = new NaturalNumber2(9);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals("9", n.toString());
        assertTrue(!result);
    }

    @Test
    public void testIsEven_100() {
        NaturalNumber n = new NaturalNumber2(100);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals("100", n.toString());
        assertTrue(result);
    }

    @Test
    public void testIsEven_999() {
        NaturalNumber n = new NaturalNumber2(999);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals("999", n.toString());
        assertTrue(!result);
    }

    /*
     * Tests of powerMod
     */

    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals("1", n.toString());
        assertEquals("0", p.toString());
        assertEquals("2", m.toString());
    }

    @Test
    public void testPowerMod_17_18_19() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals("1", n.toString());
        assertEquals("18", p.toString());
        assertEquals("19", m.toString());
    }

    // Tests of larger numbers for testPowerMod
    @Test
    public void testPowerMod_234_23_5() {
        NaturalNumber n = new NaturalNumber2(234);
        NaturalNumber p = new NaturalNumber2(23);
        NaturalNumber m = new NaturalNumber2(5);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals("4", n.toString());
        assertEquals("23", p.toString());
        assertEquals("5", m.toString());
    }

    @Test
    public void testPowerMod_888_66_183() {
        NaturalNumber n = new NaturalNumber2(888);
        NaturalNumber p = new NaturalNumber2(66);
        NaturalNumber m = new NaturalNumber2(183);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals("156", n.toString());
        assertEquals("66", p.toString());
        assertEquals("183", m.toString());
    }

    /*
     * Tests of generateNextLikelyPrime
     */

    @Test
    public void testgenerateNextLikelyPrime_12() {
        NaturalNumber n = new NaturalNumber2("12");
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("13", n.toString());
    }

    // When n is an prime number
    @Test
    public void testgenerateNextLikelyPrime_17() {
        NaturalNumber n = new NaturalNumber2("17");
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("19", n.toString());
    }

    @Test
    public void testgenerateNextLikelyPrime_999() {
        NaturalNumber n = new NaturalNumber2("999");
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals("1009", n.toString());
    }

    /*
     * Tests of isPrime2
     */

    @Test
    public void testIsPrime2_2() {
        NaturalNumber n = new NaturalNumber2(2);
        boolean result = CryptoUtilities.isPrime2(n);
        assertTrue(result);
    }

    @Test
    public void testIsPrime2_17() {
        NaturalNumber n = new NaturalNumber2(17);
        boolean result = CryptoUtilities.isPrime2(n);
        assertTrue(result);
    }

    @Test
    public void testIsPrime2_123() {
        NaturalNumber n = new NaturalNumber2(123);
        boolean result = CryptoUtilities.isPrime2(n);
        assertTrue(!result);
    }

    @Test
    public void testIsPrime2_123987() {
        NaturalNumber n = new NaturalNumber2(123987);
        boolean result = CryptoUtilities.isPrime2(n);
        assertTrue(!result);
    }

    /*
     * Tests of isWitnessToCompositeness
     */

    @Test
    public void testIsWitnessToCompositeness_4_24() {
        NaturalNumber w = new NaturalNumber2(4);
        NaturalNumber n = new NaturalNumber2(24);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals("4", w.toString());
        assertEquals("24", n.toString());
        assertTrue(result);
    }

    @Test
    public void testIsWitnessToCompositeness_25_24() {
        NaturalNumber w = new NaturalNumber2(25);
        NaturalNumber n = new NaturalNumber2(24);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals("25", w.toString());
        assertEquals("24", n.toString());
        assertTrue(!result);
    }
}
