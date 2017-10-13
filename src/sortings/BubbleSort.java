package sortings;

import com.sun.istack.internal.NotNull;

/** This class implementing bubble sort, modifying input array such as for each i, input[i] <= input[i+1].
 */
class BubbleSort {

    // Thread safety argument:
    //      This class is thread safe, because it have no instances (everything static).
    //      Noninstantiability is enforced with private constructor.

    private BubbleSort() {
        throw new RuntimeException("BubbleSort is noninstantiable");
    }

    /** Sorts input array, using bubble sort
     * This method is purely educational and ineffective.
     * Complexity: O(n^2); Worst case: backwards sorted array
     *
     * @param input Array of ints
     */
    public static void sort(@NotNull int[] input) {
        if (input.length > 1) {
            boolean swapped;
            int length = input.length;
            do {
                swapped = false;
                for (int i = 1; i < length; i++) {
                    if (input[i - 1] > input[i]) {
                        int tmp = input[i - 1];
                        input[i - 1] = input[i];
                        input[i] = tmp;
                        swapped = true;
                    }
                }
            } while (swapped);
        }
    }

}
