package sortings;

import com.sun.istack.internal.NotNull;

/** This class implementing insertion sort, modifying input array such as for each i, input[i] <= input[i+1].
 */
class InsertionSort {

    // Thread safety argument:
    //      This class is thread safe, because it have no instances (everything static).
    //      Noninstantiability is enforced with private constructor.

    private InsertionSort() {
        throw new RuntimeException("MergeSort is noninstantiable");
    }

    /** Sorts input array, using insertion sort
     * This method is effective for sorting small arrays.
     * Comparing to merge sort, that is O(N log(N)), insertion is better on small arrays, because of better constants.
     * Complexity: O(n^2); Worst case: backwards sorted array
     *
     * @param input Array of ints
     */
    public static void sort(@NotNull int[] input) {
        if (input.length > 1) {
            sort(input, 0, input.length);
        }
    }

    /** Insertion sort implementation that sorts part of input from index left to index right.
     * See sort(int[] input) for more details.
     *
     * @param input Array of ints to be sorted
     * @param left Left index (from, included), must be >= 0 && < (right - 1)
     * @param right Right index (to, excluded), must be <= input.length
     */
    static void sort(@NotNull int[] input, int left, int right) {
        if (right <= input.length && left >= 0 && left < right - 1) {
            for (int j = left + 1; j < right; j++) {
                int key = input[j];
                int i = j - 1;
                while (i >= left && input[i] > key) {
                    input[i + 1] = input[i];
                    i--;
                }
                input[i + 1] = key;
            }
        }
    }

}
