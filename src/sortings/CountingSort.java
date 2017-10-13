package sortings;

import com.sun.istack.internal.NotNull;
import util.KeyValue;

/** This class implementing counting sort, modifying input such as for each key input[i], input[i] <= input[i+1].
 */
class CountingSort {

    // Thread safety argument:
    //      This class is thread safe, because it have no instances (everything static).
    //      Noninstantiability is enforced with private constructor.

    private CountingSort() {
        throw new RuntimeException("CountingSort is noninstantiable");
    }

    /** Sorts input map, using counting sort.
     * Suitable for fast sorting integer keys in small range.
     * Complexity: O(M + n), where M = keysTo - keysFrom
     *
     * @param input array of key-value pairs to be sorted by keys
     * @param keysFrom integer <= min( keys of input )
     * @param keysTo integer >= max( keys of input )
     */
    public static void sort(@NotNull KeyValue[] input, int keysFrom, int keysTo) {
        if (input != null) {

            int range = keysTo - keysFrom;
            KeyValue[] result = new KeyValue[input.length];
            int[] counters = new int[range];

            for (KeyValue keyValue : input) {
                counters[keyValue.getKey() - keysFrom]++;
            }

            for (int i = 0, pos = 0; i < range; i++) {
                int tmp = counters[i];
                counters[i] = pos;
                pos += tmp;
            }

            for (KeyValue keyValue : input) {
                result[counters[keyValue.getKey() - keysFrom]] = keyValue;
                counters[keyValue.getKey() - keysFrom]++;
            }

            System.arraycopy(result, 0, input, 0, input.length);

        }
    }

}
