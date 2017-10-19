package sorting;

import com.sun.istack.internal.NotNull;

/** This class implementing radix sort, modifying input such as for each key input[i], input[i] <= input[i+1].
 */
class RadixSort {

    // Thread safety argument:
    //      This class is thread safe, because it have no instances (everything static).
    //      Noninstantiability is enforced with private constructor.

    private RadixSort() {
        throw new RuntimeException("RadixSort is noninstantiable");
    }

    /** Sorts input array of strings using counting sort.
     * Suitable for fast sorting integer keys in small range.
     * Complexity: O(nd + MD), where n = input.length, d = stringsSize, M = charTo - charFrom
     *
     * @param input array of key-value pairs to be sorted by keys
     * @param charFrom first allowed character code in input
     * @param charTo last allowed character code in input
     * @param stringsSize maximum length of strings
     */
    public static void sort(@NotNull String[] input, int charFrom, int charTo, int stringsSize) {
        if (input != null) {

            // Convert strings to 2d int arrays
            int range = charTo - charFrom;
            int[][] inputAsChars = new int[input.length][stringsSize];
            for (int i = 0; i < input.length; i++) {
                for (int j = 0, length = input[i].length(); j < stringsSize && j < length; j++) {
                    // each element in 2d int array is char[i,j] - charFrom + 1 && empty == 0
                    inputAsChars[i][j] = input[i].charAt(j) - charFrom + 1;
                }
            }

            // sort input array of ints
            for (int i = stringsSize - 1; i >= 0; i--) {
                CountingSort.sort(inputAsChars, 0, range + 1, i);
            }

            // recover the result
            for (int i = 0; i < input.length; i++) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < stringsSize && inputAsChars[i][j] != 0; j++) {
                    // recover each char as res[i,j] + charFrom - 1 && if res[i,j] == 0, skip to next string
                    builder.append((char) (inputAsChars[i][j] + charFrom - 1));
                }
                input[i] = builder.toString();
            }

        }
    }

}
