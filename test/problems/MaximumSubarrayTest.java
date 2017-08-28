package problems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import problems.MaximumSubarray.MaxSubarrayResult;

/** Tests solution for maximum subarray problem. */
@SuppressWarnings("CheckStyle") // suppress magic numbers
public class MaximumSubarrayTest {

    /* Testing strategy:
     *      Increasing array
     *      Decreasing array
     *      Same elements array
     *      Array with max in left border
     *      Array with max in right border
     *      Array with max exactly in middle
     *      Array with several maxes (all the same)
     *      Array with max between left border + 1 and middle
     *      Array with max between middle + 1 and right border - 1
     *      Array with max in the middle
     *      Array.length == 1
     *      Array.length == 0
     *      Array == null
     */

    // covers Increasing array
    @Test
    public void testIncreasingArray() {
        final int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        MaxSubarrayResult result = MaximumSubarray.find(array);
        assertEquals(0, result.indexFrom);
        assertEquals(9, result.indexTo);
        assertEquals(55, result.sum);
    }

    // covers Decreasing array
    @Test
    public void testDecreasingArray() {
        final int[] array = {0, -1, -2, -3, -4, -5, -6, -7, -8, -9};
        MaxSubarrayResult result = MaximumSubarray.find(array);
        assertEquals(0, result.indexFrom);
        assertEquals(0, result.indexTo);
        assertEquals(0, result.sum);
    }

    // covers Same elements array
    @Test
    public void testSameElementsArray() {
        final int[] array = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        MaxSubarrayResult result = MaximumSubarray.find(array);
        assertEquals(0, result.indexFrom);
        assertEquals(9, result.indexTo);
        assertEquals(10, result.sum);
    }

    // covers Array with max in left border
    @Test
    public void testArrayLeftBorder() {
        final int[] array = {1, 1, -1, 0, 0, 0, 0, 0, 0, 0};
        MaxSubarrayResult result = MaximumSubarray.find(array);
        assertEquals(0, result.indexFrom);
        assertEquals(1, result.indexTo);
        assertEquals(2, result.sum);
    }

    // covers Array with max in right border
    @Test
    public void testArrayRightBorder() {
        final int[] array = {0, 0, 0, 0, 0, 0, 0, -1, 1, 1};
        MaxSubarrayResult result = MaximumSubarray.find(array);
        assertEquals(8, result.indexFrom);
        assertEquals(9, result.indexTo);
        assertEquals(2, result.sum);
    }

    // covers Array with max exactly in middle
    @Test
    public void testArrayExactMiddle() {
        final int[] array = {0, 0, 0, 0, 1, 1, 0, 0, 0, 0};
        MaxSubarrayResult result = MaximumSubarray.find(array);
        assertEquals(4, result.indexFrom);
        assertEquals(5, result.indexTo);
        assertEquals(2, result.sum);
    }

    // covers Array with several maxes (all the same)
    @Test
    public void testArraySeveralMax() {
        final int[] array = {-6, 2, 2, -6, 2, 2, -6, 2, 2, -6};
        MaxSubarrayResult result = MaximumSubarray.find(array);
        assertEquals(4, result.sum);
    }

    // covers Array with max between left border + 1 and middle
    @Test
    public void testArrayNormalLeftMax() {
        final int[] array = {0, 2, 2, 0, 0, 0, 0, 0, 0, 0};
        MaxSubarrayResult result = MaximumSubarray.find(array);
        assertEquals(1, result.indexFrom);
        assertEquals(2, result.indexTo);
        assertEquals(4, result.sum);
    }

    // covers Array with max between middle + 1 and right border - 1
    @Test
    public void testArrayNormalRightMax() {
        final int[] array = {0, 0, 0, 0, 0, 0, 0, 2, 2, 0};
        MaxSubarrayResult result = MaximumSubarray.find(array);
        assertEquals(7, result.indexFrom);
        assertEquals(8, result.indexTo);
        assertEquals(4, result.sum);
    }

    // covers Array with max in the middle
    @Test
    public void testArrayNormalMiddleMax() {
        final int[] array = {0, 0, 1, 1, 1, 1, 0, 0, 0, 0};
        MaxSubarrayResult result = MaximumSubarray.find(array);
        assertEquals(2, result.indexFrom);
        assertEquals(5, result.indexTo);
        assertEquals(4, result.sum);
    }

    // covers Array.length == 1
    @Test
    public void testArraySingleElement() {
        final int[] array = {1};
        MaxSubarrayResult result = MaximumSubarray.find(array);
        assertEquals(0, result.indexFrom);
        assertEquals(0, result.indexTo);
        assertEquals(1, result.sum);
    }

    // covers Array.length == 0
    @Test(expected = IllegalArgumentException.class)
    public void testArrayEmpty() {
        MaxSubarrayResult result = MaximumSubarray.find(new int[0]);
        assertEquals(null, result);
    }

    // covers Array == null
    @Test(expected = NullPointerException.class)
    public void testArrayNull() {
        MaxSubarrayResult result = MaximumSubarray.find(null);
        assertEquals(null, result);
    }

    // covers toString of result
    @Test
    public void testToStringResult() {
        final int[] array = {0, 0, 1, 1, 1, 1, 0, 0, 0, 0};
        MaxSubarrayResult result = MaximumSubarray.find(array);
        assertEquals("Subarray [2, 5] add is 4", result.toString());
    }

    // covers equals and hashCode of result
    @Test
    public void testEqualsResult() {
        final int[] array1 = {0, 0, 1, 1, 1, 1, 0, 0, 0, 0};
        final int[] array2 = {0, 0, 2, 1, 1, 1, 0, 0, 0, 0};
        final int[] array3 = {0, 0, 0, 1, 1, 1, 1, 0, 0, 0};
        MaxSubarrayResult result1 = MaximumSubarray.find(array1);
        MaxSubarrayResult result2 = MaximumSubarray.find(array1);
        assertEquals(result1, result2);
        assertEquals(result1.hashCode(), result2.hashCode());

        result2 = MaximumSubarray.find(array2);
        assertFalse(result1.equals(result2));

        result2 = MaximumSubarray.find(array3);
        assertFalse(result1.equals(result2));
    }

}
