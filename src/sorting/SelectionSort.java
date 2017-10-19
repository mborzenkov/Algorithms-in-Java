package sorting;

import com.sun.istack.internal.NotNull;

/** This class implementing selection sort, modifying input array such as for each i, input[i] <= input[i+1].
 */
class SelectionSort {

    // Thread safety argument:
    //      This class is thread safe, because it have no instances (everything static).
    //      Noninstantiability is enforced with private constructor.

    private SelectionSort() {
        throw new RuntimeException("SelectionSort is noninstantiable");
    }

    /** Sorts input array, using selection sort
     * Not effective algorithm.
     * Complexity: O(n^2); All cases similar.
     * @param input Array of ints
     */
    public static void sort(@NotNull int[] input) {
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
                    input[smallest] = tmp;
                }
            }
        }
    }

}
