package sorting;

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
            for (int i = 0; i + 1 < input.length; ++i) {
                for (int j = 0; j + i + 1 < input.length; ++j) {
                    if (input[j] > input[j + 1]) {
                        int tmp = input[j + 1];
                        input[j + 1] = input[j];
                        input[j] = tmp;
                    }
                }
            }
        }
    }

}
