package sorting;

import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

/** Tests counting_sort-like sorting algorithms in sortings package. */
@SuppressWarnings("CheckStyle")
public class CountingSortTest {

    /* Testing strategy
     *      Best cases and worse cases for algorithms.
     *      Random array.
     *      Frequently repeating numbers.
     *      Same numbers.
     */

    /** Size of arrays, positive. */
    private static final int ARRAYS_SIZE = 100000;
    /** Range of keys. */
    private static final int KEYS_RANGE = 10000;
    /** Size of arrays formatted. */
    private static final String ARRAYS_SIZE_FORMATTED = String.format("%.0e", (float) ARRAYS_SIZE);
    /** True == print execution time on out. */
    private static final boolean PRINT_TIME = true;

    // All arrays are [ARRAY_SIZE][2], where [i][0] is key and [i][1] is value
    // All arrays contain ints [-KEYS_RANGE/2..KEYS_RANGE/2] or [0..KEYS_RANGE] as keys
    // All arrays contain i as values, representing position in original array.
    // This values are used after sorting for comparing values in sorted and original array.

    /** Array of key-values, where keys from 0 to KEYS_RANGE, contain duplicates. */
    private static final int[][] SORTED_ARRAY;
    /** Array of key-values, where keys from KEYS_RANGE to 0, contain duplicates. */
    private static final int[][] BACKWARDS_SORTED_ARRAY;
    /** Array of same numbers. */
    private static final int[][] SAME_NUMBERS_ARRAY;

    /** Array of random key-values, where keys from 0 to Integer.MAX_VALUE, probably not unique numbers. */
    private static final int[][] RANDOM_ARRAY;
    /** Array of key-values, where keys from 0 to 10, numbers are repeating frequently. */
    private static final int[][] RANDOM_REPEATING_ARRAY;

    /** Empty array. */
    private static final int[][] EMPTY_ARRAY = new int[0][0];
    /** Size 1 array. */
    private static final int[][] SIZE1_ARRAY = new int[][] { new int[] { 101, 0 } };

    // All types of testing arrays, initialized with values
    static {

        SORTED_ARRAY = new int[ARRAYS_SIZE][2];
        BACKWARDS_SORTED_ARRAY = new int[ARRAYS_SIZE][2];
        SAME_NUMBERS_ARRAY = new int[ARRAYS_SIZE][2];

        RANDOM_ARRAY = new int[ARRAYS_SIZE][2];
        RANDOM_REPEATING_ARRAY = new int[ARRAYS_SIZE][2];
        Random randomizer = new Random();

        for (int i = 0, j = 0; i < ARRAYS_SIZE; i++) {
            SORTED_ARRAY[i][0] = j;
            SORTED_ARRAY[i][1] = i;
            BACKWARDS_SORTED_ARRAY[ARRAYS_SIZE - 1 - i][0] = j;
            BACKWARDS_SORTED_ARRAY[ARRAYS_SIZE - 1 - i][1] = ARRAYS_SIZE - 1 - i;
            SAME_NUMBERS_ARRAY[i][0] = 1;
            SAME_NUMBERS_ARRAY[i][1] = i;
            RANDOM_ARRAY[i][0] = randomizer.nextInt() % (KEYS_RANGE / 2);
            RANDOM_ARRAY[i][1] = i;
            RANDOM_REPEATING_ARRAY[i][0] = randomizer.nextInt(10);
            RANDOM_REPEATING_ARRAY[i][1] = i;
            if (i % KEYS_RANGE == 0) {
                j++;
            }
        }

    }

    /** Testing Counting sort. */
    @Test
    public void testCountingSort() {
        testSortOnArray(SORTED_ARRAY, "SORTED_ARRAY", 0, KEYS_RANGE);
        testSortOnArray(BACKWARDS_SORTED_ARRAY, "BACKWARDS_SORTED_ARRAY", 0, KEYS_RANGE);
        testSortOnArray(SAME_NUMBERS_ARRAY, "SAME_NUM_ARRAY", 0, 5);
        testSortOnArray(RANDOM_ARRAY, "RANDOM_ARRAY", -KEYS_RANGE / 2, KEYS_RANGE / 2);
        testSortOnArray(RANDOM_REPEATING_ARRAY, "RANDOM_REP_ARRAY", 0, 12);
        testSortOnArray(EMPTY_ARRAY, "EMPTY_ARRAY", 0, 0);
        testSortOnArray(SIZE1_ARRAY, "SIZE1_ARRAY", 100, 102);
    }

    /** Utility method for all tests.
     * Copy inputArray, call sorting method, print time, test result.
     *
     * @param inputArray Array to be sorted (not modified by this method)
     * @param nameOfArray Name of array to print in results (if print is enabled)
     * @param keysFrom integer <= min( keys of input )
     * @param keysTo integer >= max( keys of input )
     */
    private void testSortOnArray(int[][] inputArray, String nameOfArray, int keysFrom, int keysTo) {
        int[][] array = Arrays.copyOf(inputArray, inputArray.length);
        long start = System.currentTimeMillis();
        CountingSort.sort(array, keysFrom, keysTo, 0);

        long end = System.currentTimeMillis();
        if (PRINT_TIME) {
            System.out.println("SIZE: " + ARRAYS_SIZE_FORMATTED + " objects. TIME: " + (end - start) + " ms. Sorted "
                    + nameOfArray);
        }
        for (int i = 0; i < inputArray.length - 1; i++) {
            assert array[i][0] <= array[i + 1][0];
            assert array[i][1] == inputArray[array[i][1]][1];
        }
    }

}
