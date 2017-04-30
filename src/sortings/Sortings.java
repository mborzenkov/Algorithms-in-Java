package sortings;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;

/** This is static utility class with different sorting algorithms.
 *  Each method modifying input array such as for each i, input[i] <= input[i+1]
 */
public class Sortings {

    /** Defines size of array when merge sort should switch to insertion sort */
    private static final int MERGE_TO_INSERTION_THRESHOLD = 10;

    // Thread safety argument:
    //      This class is thread safe, because it have no instances (everithing static).
    //      Noninstantability is enforced with private constructor.

    private Sortings() { throw new RuntimeException("Sortings is noninstantiable"); }

    /** Sorts input array, using insertion sort
     * This method is effective for sorting small arrays.
     * Comparing to merge sort, that is O(N log(N)), insertion is better on small arrays, because of better constants.
     * Complexity: O(n^2); Worst case: backwards sorted array
     * @param input Array of ints
     */
    public static void insertionSort(@NotNull int[] input) {
        if (input.length > 1) {
            insertionSort(input, 0, input.length);
        }
    }

    /** Insertion sort implementation that sorts part of input from index left to index right.
     * See insertionSort(int[] input) for more details.
     * @param input Array of ints to be sorted
     * @param left Left index (from, included), must be >= 0 && < (right - 1)
     * @param right Right index (to, excluded), must be <= input.length
     */
    private static void insertionSort(@NotNull int[] input, int left, int right) {
        if (right <= input.length && left >= 0 && left < right - 1) {
            for (int j = left + 1; j < right; j++) {
                int key = input[j];
                int i = j-1;
                while (i >= left && input[i] > key) {
                    input[i+1] = input[i];
                    i--;
                }
                input[i+1] = key;
            }
        }
    }

    /** Sorts input array, using bubble sort
     * This method is purely educational and ineffective.
     * Complexity: O(n^2); Worst case: backwards sorted array
     * @param input Array of ints
     */
    public static void bubbleSort(@NotNull int[] input) {
        if (input.length > 1) {
            boolean swapped;
            int length = input.length;
            do {
                swapped = false;
                for (int i = 1; i < length; i++) {
                    if (input[i - 1] > input[i]) {
                        int tmp = input[i-1];
                        input[i-1] = input[i];
                        input[i] = tmp;
                        swapped = true;
                    }
                }
            } while (swapped);
        }
    }

    /** Sorts input array, using selection sort
     * Not effective algorithm.
     * Complexity: O(n^2); All cases similar.
     * @param input Array of ints
     */
    public static void selectionSort(@NotNull int[] input) {
        if (input.length > 1) {
            int n = input.length;
            for (int j = 0; j < n - 1; j++) {
                int smallest = j;
                for (int i = j + 1; i < n; i++) {
                    if (input[i] < input[smallest]) {
                        smallest = i;
                    }
                }
                if (j != smallest) {
                    int tmp = input[j];
                    input[j] = input[smallest];
                    input[smallest] = input[j];
                }
            }
        }
    }

    /** Sorts input array, using merge sort
     * This algorithm using divide & conquer paradigm. And it is recursive.
     * Complexity: O(n log(n)); All cases similar.
     * @param input Array of ints
     */
    public static void mergeSort(@NotNull int[] input) {
        if (input != null) {
            mergeSort(input, 0, input.length);
        }
    }

    /** Merge sort implementation that sorts part of input from index left to index right.
     * See mergeSort(int[] input) for more details.
     * @param input Array of ints to be sorted
     * @param left Left index (from, included), must be >= 0 && < (right - 1)
     * @param right Right index (to, excluded), must be <= input.length
     */
    private static void mergeSort(@NotNull int[] input, int left, int right) {
        if (left < (right - 1)) {

            if (right - left <= MERGE_TO_INSERTION_THRESHOLD) {
                insertionSort(input, left, right);
            } else {
                int middle = (left + right) / 2;
                mergeSort(input, left, middle);
                mergeSort(input, middle, right);
                merge(input, left, middle, right);
            }
        }
    }

    /** Utility method for merge sort.
     * Merge two parts of input in one sorted: left to middle and middle to right.
     * @param input Array of ints to be sorted
     * @param left Left index (from, included), must be >= 0 && < (right - 1)
     * @param middle Right index for left array (to, excluded) and left index for right array (from, included),
     *               must be >= left && <= (right - 1)
     * @param right Right index (to, excluded), must be <= input.length
     */
    private static void merge(int[] input, int left, int middle, int right) {
        int[] leftArray = Arrays.copyOfRange(input, left, middle);
        int[] rightArray = Arrays.copyOfRange(input, middle, right);
        int i = 0, j = 0, k = left;
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
        while (i < leftArray.length)
        {
            input[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < rightArray.length)
        {
            input[k] = rightArray[j];
            j++;
            k++;
        }
    }

}
