package sortings;

import com.sun.istack.internal.NotNull;

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
    private static void merge(@NotNull int[] input, final int left, final int middle, final int right) {
        int[] result = new int[right - left];
        int i = left;
        int j = middle;
        int k = 0;
        while (k < right && i < middle && j < right) {
            if (input[i] <= input[j]) {
                result[k] = input[i];
                i++;
            } else {
                result[k] = input[j];
                j++;
            }
            k++;
        }
        while (i < middle) {
            result[k] = input[i];
            i++;
            k++;
        }
        while (j < right) {
            result[k] = input[j];
            j++;
            k++;
        }
        System.arraycopy(result, 0, input, left, right - left);
    }

}
