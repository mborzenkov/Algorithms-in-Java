package problem;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;

/** This class is solving the task of finding the contiguous subarray within one-dimensional array of numbers
 * which has the largest add.
 */
public class MaximumSubarray {

    /* Thread safety argument:
     *      This class is thread safe,  because it have no instances (everything static).
     *      Noninstantiability is enforced with private constructor.
     */

    private MaximumSubarray() {
        throw new RuntimeException("MaximumSubarray is noninstantiable");
    }

    /** Unmodifiable datatype for result of {@link MaximumSubarray#find(int[])}.
     * indexFrom and indexTo are in [0, A.length-1]
     * indexFrom <= indexTo
     */
    public static class MaxSubarrayResult {
        /** Starting index of maximum subarray (including). */
        public final int indexFrom;
        /** Ending index of maximum subarray (including). */
        public final int indexTo;
        /** Sum of all elements from A[indexFrom] to A[indexTo] including both borders. */
        public final int sum;

        /** Constructor for results.
         * indexFrom and indexTo must be in [0, A.length-1] and indexFrom <= indexTo
         *
         * @param inputArray non-empty, non-null one-dimensional array of numbers
         * @param indexFrom starting index of result maximum subarray
         * @param indexTo ending index of result maximum subarray
         * @param sum add of elements in array from indexFrom to indexTo, including both
         */
        private MaxSubarrayResult(@NotNull int[] inputArray, int indexFrom, int indexTo, int sum) {
            assert indexFrom <= indexTo;
            assert indexFrom >= 0;
            assert indexTo <= inputArray.length;
            this.indexFrom = indexFrom;
            this.indexTo = indexTo;
            this.sum = sum;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + indexFrom;
            result = 31 * result + indexTo;
            return  31 * result + sum;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof MaxSubarrayResult)) {
                return false;
            }
            MaxSubarrayResult thatObject = (MaxSubarrayResult) obj;
            return this.indexFrom == thatObject.indexFrom
                    && this.indexTo == thatObject.indexTo
                    && this.sum == thatObject.sum;
        }

        @Override
        public String toString() {
            return String.format("Subarray [%s, %s] add is %s", indexFrom, indexTo, sum);
        }
    }

    /** Finds maximum subarray in inputArray.
     *
     * @param array non-empty, non-null one-dimensional array of numbers
     *              array is not modifying by this function
     *
     * @return {@link MaxSubarrayResult} containing indexes of maximum subarray and add of this subarray
     *      if inputArray.length == 1, result.indexFrom == result.indexTo == 0
     *
     * @throws IllegalArgumentException if array.length == 0
     * @throws NullPointerException if array == null
     */
    public static @NotNull MaxSubarrayResult find(@NotNull int[] array) {
        int[] inputArray = Arrays.copyOf(array, array.length); // safe copying
        if (inputArray.length == 0) {
            throw new IllegalArgumentException("Error @ MaximumSubarray.find() :: Array length must be > 0");
        }
        return findRecursive(array, 0, array.length - 1);
    }

    /** Recursive function for finding maximum subarray.
     *
     * @param array non-empty, non-null one-dimensional array of numbers
     *              array must not be modified until return from first call of this function
     * @param indexFrom starting index of current subarray
     * @param indexTo ending index of current subarray
     *
     * @return {@link MaxSubarrayResult} containing indexes of maximum subarray and add of this subarray
     *          if inputArray.length == 1, result.indexFrom == result.indexTo == 0
     *
     * @see MaximumSubarray#find(int[]) for public access
     */
    private static @NotNull MaxSubarrayResult findRecursive(@NotNull int[] array, int indexFrom, int indexTo) {
        if (indexFrom == indexTo) {
            return new MaxSubarrayResult(array, indexFrom, indexTo, array[indexFrom]); // base case
        } else {
            int indexMid = (indexFrom + indexTo) / 2;
            MaxSubarrayResult leftResult = findRecursive(array, indexFrom, indexMid);
            MaxSubarrayResult rightResult = findRecursive(array, indexMid + 1, indexTo);
            MaxSubarrayResult crossResult = findMaxCrossing(array, indexFrom, indexMid, indexTo);
            if ((leftResult.sum >= rightResult.sum) && (leftResult.sum >= crossResult.sum)) {
                return leftResult;
            } else if ((rightResult.sum >= leftResult.sum) && (rightResult.sum >= crossResult.sum)) {
                return rightResult;
            } else {
                return crossResult;
            }
        }
    }

    /** Function for finding maximum subarray containing middle element.
     *
     * @param array non-empty, non-null one-dimensional array of numbers
     *              array must not be modified until return from first call of this function
     * @param indexFrom starting index of array
     * @param indexMid index of middle element in array
     * @param indexTo ending index of array
     *
     * @return {@link MaxSubarrayResult} containing indexes of maximum subarray, containing middle element, and its add
     */
    private static @NotNull MaxSubarrayResult findMaxCrossing(@NotNull int[] array,
                                                              int indexFrom, int indexMid, int indexTo) {

        int leftSum = array[indexMid];
        int sum = leftSum;
        int maxLeft = indexMid;
        for (int i = indexMid - 1; i >= indexFrom; i--) {
            sum += array[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }

        int rightSum = array[indexMid + 1];
        sum = rightSum;
        int maxRight = indexMid + 1;
        for (int j = indexMid + 2; j <= indexTo; j++) {
            sum += array[j];
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = j;
            }
        }

        return new MaxSubarrayResult(array, maxLeft, maxRight, leftSum + rightSum);
    }

}
