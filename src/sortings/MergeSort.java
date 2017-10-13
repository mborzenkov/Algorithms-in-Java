package sortings;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;

/** This class implementing merge sort, modifying input array such as for each i, input[i] <= input[i+1].
 */
class MergeSort {

    /** Defines size of array when merge sort should switch to insertion sort. */
    private static final int MERGE_TO_INSERTION_THRESHOLD = 16;

    // Thread safety argument:
    //      This class is thread safe, because it have no instances (everything static).
    //      Noninstantiability is enforced with private constructor.

    private MergeSort() {
        throw new RuntimeException("MergeSort is noninstantiable");
    }

    /** Sorts input array, using merge sort
     * This algorithm using divide & conquer paradigm. Not recursive.
     * Complexity: O(n log(n));
     *
     * @param input Array of ints
     */
    public static void sort(@NotNull int[] input) {
        if (input != null) {

            for (int k = 1, segment = (int) Math.pow(2, k);
                 segment < input.length;
                 k++, segment = (int) Math.pow(2, k)) {

                for (int left = segment, right = Math.min(input.length, left + segment);
                     left < input.length;
                     left += 2 * segment, right = Math.min(input.length, left + segment)) {

                    if (2 * segment <= MERGE_TO_INSERTION_THRESHOLD) {
                        InsertionSort.sort(input, left - segment, right);
                    } else {
                        merge(input, left - segment, left, right);
                    }

                }

            }

        }
    }

    /** Utility method for merge sort.
     * Merge two parts of input in one sorted: left to middle and middle to right.
     *
     * @param input Array of ints to be sorted
     * @param left Left index (from, included), must be >= 0 && < (right - 1)
     * @param middle Right index for left array (to, excluded) and left index for right array (from, included),
     *               must be >= left && <= (right - 1)
     * @param right Right index (to, excluded), must be <= input.length
     */
    private static void merge(int[] input, int left, int middle, int right) {
        int[] leftArray = Arrays.copyOfRange(input, left, middle);
        int[] rightArray = Arrays.copyOfRange(input, middle, right);
        int i = 0;
        int j = 0;
        int k = left;
        while (k < right && i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                input[k] = leftArray[i];
                i++;
            } else {
                input[k] = rightArray[j];
                j++;
            }
            k++;
        }
        while (i < leftArray.length) {
            input[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < rightArray.length) {
            input[k] = rightArray[j];
            j++;
            k++;
        }
    }

}
