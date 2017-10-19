package sorting;

import com.sun.istack.internal.NotNull;

import java.util.Random;

/** This class implementing quick sort, modifying input array such as for each i, input[i] <= input[i+1].
 */
class QuickSort {

    /** Defines size of array when quick sort should switch to insertion sort. */
    private static final int QUICK_TO_INSERTION_THRESHOLD = 100;

    // Thread safety argument:
    //      This class is thread safe, because it have no instances (everything static).
    //      Noninstantiability is enforced with private constructor.

    private QuickSort() {
        throw new RuntimeException("QuickSort is noninstantiable");
    }

    /** Sorts input array, using quick sort
     * This algorithm using divide & conquer paradigm. And it is recursive.
     * Complexity: O(n log(n))
     *
     * @param input Array of ints
     */
    public static void sort(@NotNull int[] input) {
        if (input != null) {
            sort(input, 0, input.length);
        }
    }

    /** Quick sort implementation that sorts part of input from index left to index right.
     * See sort(int[] input) for more details.
     *
     * @param input Array of ints to be sorted
     * @param left Left index (from, included), must be >= 0 && < (right - 1)
     * @param right Right index (to, excluded), must be <= input.length
     */
    private static void sort(@NotNull int[] input, int left, int right) {

        while (right - left > QUICK_TO_INSERTION_THRESHOLD) {
            Random rand = new Random();
            swap(input, right - 1, left + rand.nextInt(right - left));
            int part = partitionHoare(input, left, right);
            if (part - left < right - part) {
                sort(input, left, part);
                left = part;
            } else {
                sort(input, part, right);
                right = part;
            }
        }
        InsertionSort.sort(input, left, right); // uses insertion sort on arrays from threshold length

    }

    /** Performs partition of input[left..right-1] such as all elements input[left..result] <= pivot and all elements
     * input[result..right-1] >= pivot where pivot picked as input[right-1] at start.
     * This method uses Hoare's partition algorithm.
     *
     * @param input Array of ints for partitioning
     * @param left Left index (from, included), must be >= 0 && < (right - 1)
     * @param right Right index (to, excluded), must be <= input.length
     *
     * @return index of element result such as all input[left..result] <= pivot && input[result..right-1] >= pivot
     */
    @SuppressWarnings("StatementWithEmptyBody") // empty for loops are made intentionally here
    private static int partitionHoare(@NotNull int[] input, int left, int right) {
        int pivot = input[right - 1];
        int i = left - 1;
        int j = right;
        while (i < j) {
            for (i++; i < j && input[i] < pivot; i++) { }
            for (j--; i < j && input[j] > pivot; j--) { }
            if (i < j) {
                swap(input, i, j);
            }
        }
        return i;
    }

    /** Performs partition of input[left..right-1] such as all elements input[left..result] <= pivot and all elements
     * input[result..right-1] >= pivot where pivot picked as input[right-1] at start.
     * This method uses Lomuto partition algorithm.
     *
     * @param input Array of ints for partitioning
     * @param left Left index (from, included), must be >= 0 && < (right - 1)
     * @param right Right index (to, excluded), must be <= input.length
     *
     * @return index of element result such as all input[left..result] <= pivot && input[result..right-1] >= pivot
     */
    @SuppressWarnings("unused")
    private static int partitionLomuto(@NotNull int[] input, int left, int right) {
        int pivot = input[right - 1];
        int i = left;
        int j = left;
        int leftEqual = 0;
        int rightEqual = 0;
        for ( ; i < right; i++) {
            if (input[i] < pivot) {
                swap(input, i, j);
                j++;
            } else if (input[i] == pivot) {
                if (leftEqual > rightEqual) {
                    rightEqual++;
                } else {
                    swap(input, i, j);
                    j++;
                    leftEqual++;
                }
            }
        }
        return j;
    }

    /** Swaps input[i] and input[j].
     *
     * @param input Source array
     * @param i 0 <= index <= input.length - 1
     * @param j 0 <= index <= input.length - 1
     */
    private static void swap(@NotNull int[] input, int i, int j) {
        if (input[i] == input[j]) {
            return;
        }
        int tmp = input[i];
        input[i] = input[j];
        input[j] = tmp;
    }

}
