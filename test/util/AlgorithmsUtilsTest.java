package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** Tests algorithms utils. */
public class AlgorithmsUtilsTest {

    // There are no common testing strategy for this class because all methods are serving distinct specific tasks.

    @SuppressWarnings("CheckStyle")
    @Test
    public void testIsPowerOfTwo() {
        assertTrue(AlgorithmsUtils.isPowerOfTwo(1));
        assertTrue(AlgorithmsUtils.isPowerOfTwo(2));
        assertTrue(AlgorithmsUtils.isPowerOfTwo(4));
        assertTrue(AlgorithmsUtils.isPowerOfTwo(8));
        assertTrue(AlgorithmsUtils.isPowerOfTwo(16));
        assertTrue(AlgorithmsUtils.isPowerOfTwo(256));

        assertFalse(AlgorithmsUtils.isPowerOfTwo(3));
        assertFalse(AlgorithmsUtils.isPowerOfTwo(7));
        assertFalse(AlgorithmsUtils.isPowerOfTwo(12));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsPowerOfTwoExceptionZero() {
        assertEquals(false, AlgorithmsUtils.isPowerOfTwo(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsPowerOfTwoExceptionNegative() {
        assertEquals(false, AlgorithmsUtils.isPowerOfTwo(-1));
    }

}
