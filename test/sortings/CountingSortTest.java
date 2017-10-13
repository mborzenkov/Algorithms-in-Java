package sortings;

import java.util.Arrays;
import java.util.Random;
import org.junit.Test;
import util.KeyValue;

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

    /** Array of ints from 0 to KEYS_RANGE, contain duplicates. */
    private static final KeyValue[] SORTED_ARRAY;
    /** Array of ints from KEYS_RANGE to 0, contain duplicates. */
    private static final KeyValue[] BACKWARDS_SORTED_ARRAY;
    /** Array of same numbers. */
    private static final KeyValue[] SAME_NUMBERS_ARRAY;

    /** Array of random ints from 0 to Integer.MAX_VALUE, probably not unique numbers. */
    private static final KeyValue[] RANDOM_ARRAY;
    /** Array of ints from 0 to 10, numbers are repeating frequently. */
    private static final KeyValue[] RANDOM_REPEATING_ARRAY;

    /** Empty array. */
    private static final KeyValue[] EMPTY_ARRAY = new KeyValue[0];
    /** Size 1 array. */
    private static final KeyValue[] SIZE1_ARRAY = new KeyValue[] {new KeyValue(101, 0)};

    // All types of testing arrays, initialized with values
    static {

        SORTED_ARRAY = new KeyValue[ARRAYS_SIZE];
        BACKWARDS_SORTED_ARRAY = new KeyValue[ARRAYS_SIZE];
        SAME_NUMBERS_ARRAY = new KeyValue[ARRAYS_SIZE];

        RANDOM_ARRAY = new KeyValue[ARRAYS_SIZE];
        RANDOM_REPEATING_ARRAY = new KeyValue[ARRAYS_SIZE];
        Random randomizer = new Random();

        for (int i = 0, j = 0; i < ARRAYS_SIZE; i++) {
            if (i % KEYS_RANGE == 0) {
                j++;
            }
            SORTED_ARRAY[i] = new KeyValue(j, i);
            BACKWARDS_SORTED_ARRAY[ARRAYS_SIZE - 1 - i] = new KeyValue(j, ARRAYS_SIZE - 1 - i);
            SAME_NUMBERS_ARRAY[i] = new KeyValue(1, i);
            RANDOM_ARRAY[i] = new KeyValue(randomizer.nextInt() % (KEYS_RANGE / 2), i);
            RANDOM_REPEATING_ARRAY[i] = new KeyValue(randomizer.nextInt(10), i);
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
     */
    private void testSortOnArray(KeyValue[] inputArray, String nameOfArray, int keysFrom, int keysTo) {
        KeyValue[] array = Arrays.copyOf(inputArray, inputArray.length);
        long start = System.currentTimeMillis();
        CountingSort.sort(array, keysFrom, keysTo);

        long end = System.currentTimeMillis();
        if (PRINT_TIME) {
            System.out.println("SIZE: " + ARRAYS_SIZE_FORMATTED + " ints. TIME: " + (end - start) + " ms. Sorted "
                    + nameOfArray);
        }
        for (int i = 0; i < inputArray.length - 1; i++) {
            assert array[i].getKey() <= array[i + 1].getKey();
            assert array[i] == inputArray[((Integer) array[i].getValue())];
        }
    }

}
