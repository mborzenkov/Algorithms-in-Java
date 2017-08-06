package sortings;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;

/** This class implementing merge sort, modifying input array such as for each i, input[i] <= input[i+1].
 */
class MergeSort {

    /** Defines size of array when merge sort should switch to insertion sort. */
    private static final int MERGE_TO_INSERTION_THRESHOLD = 10;

    // Thread safety argument:
    //      This class is thread safe, because it have no instances (everything static).
    //      Noninstantiability is enforced with private constructor.

    private MergeSort() {
        throw new RuntimeException("MergeSort is noninstantiable");
    }

    /** Sorts input array, using merge sort
     * This algorithm using divide & conquer paradigm. And it is recursive.
     * Complexity: O(n log(n)); All cases similar.
     *
     * @param input Array of ints
     */
    public static void sort(@NotNull int[] input) {
        if (input != null) {
            sort(input, 0, input.length);
        }
    }

    /** Merge sort implementation that sorts part of input from index left to index right.
     * See sort(int[] input) for more details.
     *
     * @param input Array of ints to be sorted
     * @param left Left index (from, included), must be >= 0 && < (right - 1)
     * @param right Right index (to, excluded), must be <= input.length
     */
    private static void sort(@NotNull int[] input, int left, int right) {
        if (left < (right - 1)) {

            if (right - left <= MERGE_TO_INSERTION_THRESHOLD) {
                InsertionSort.sort(input, left, right); // uses insertion sort on arrays from threshold length
            } else {
                int middle = (left + right) / 2;
                sort(input, left, middle);
                sort(input, middle, right);
                merge(input, left, middle, right);
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
