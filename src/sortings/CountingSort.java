package sortings;

import com.sun.istack.internal.NotNull;

/** This class implementing counting sort, modifying input such as for each key input[i], input[i] <= input[i+1].
 */
class CountingSort {

    // Thread safety argument:
    //      This class is thread safe, because it have no instances (everything static).
    //      Noninstantiability is enforced with private constructor.

    private CountingSort() {
        throw new RuntimeException("CountingSort is noninstantiable");
    }

    /** Sorts input array by input[i][keyIndex] using counting sort.
     * Suitable for fast sorting integer keys in small range.
     * Complexity: O(M + n), where M = keysTo - keysFrom
     *
     * @param input array of key-value pairs to be sorted by keys
     * @param keysFrom integer <= min( keys of input )
     * @param keysTo integer >= max( keys of input )
     * @param keyIndex index of column that contain keys
     */
    public static void sort(@NotNull int[][] input, int keysFrom, int keysTo, int keyIndex) {
        if (input != null) {

            int range = keysTo - keysFrom;
            int[][] result = new int[input.length][];
            int[] counters = new int[range];

            for (int[] keys : input) {
                counters[keys[keyIndex] - keysFrom]++;
            }

            for (int i = 0, pos = 0; i < range; i++) {
                int tmp = counters[i];
                counters[i] = pos;
                pos += tmp;
            }

            for (int[] keys: input) {
                result[counters[keys[keyIndex] - keysFrom]] = keys;
                counters[keys[keyIndex] - keysFrom]++;
            }

            System.arraycopy(result, 0, input, 0, input.length);

        }
    }

}
