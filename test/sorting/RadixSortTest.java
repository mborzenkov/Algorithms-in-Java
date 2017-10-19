package sorting;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import org.junit.Test;

/** Tests radix sort algorithm in sortings package. */
@SuppressWarnings("CheckStyle")
public class RadixSortTest {

    /* Testing strategy
     *      Best cases and worse cases for algorithm.
     *      Random array.
     *      Frequently repeating strings.
     *      Same strings.
     */

    /** Types of sortings. */
    private enum SortingTypes {
        ARRAYS_SORT("Built-in Arrays.sort()"), ARRAYS_PARALLEL_SORT("Built-in Arrays.parallelSort()"),
        RADIX("Radix Sort");

        /** String representation of sorting type. */
        private final String representation;
        SortingTypes(String representation) {
            this.representation = representation;
        }

        /** Return string representation of sorting type. */
        @Override public String toString() {
            return representation;
        }
    }

    /** Size of arrays, positive. */
    private static final int ARRAYS_SIZE = 10000;
    /** Maximum length of strings. */
    private static final int STR_LENGTH = 100;
    /** First allowed character. */
    private static final int CHAR_FROM = 97;
    /** Size of alphabet, range of allowed characters. */
    private static final int ALPHABET_SIZE = 122 - CHAR_FROM; // only lowercase characters
    /** Size of arrays formatted. */
    private static final String ARRAYS_SIZE_FORMATTED = String.format("%.0e", (float) ARRAYS_SIZE);
    /** True == print execution time on out. */
    private static final boolean PRINT_TIME = true;

    /** Sorted ASC array[ARRAYS_SIZE] of strings with each string length in STR_LENGTH. */
    private static final String[] SORTED_ARRAY;
    /** Sorted DESC array[ARRAYS_SIZE] of strings with each string length in STR_LENGTH. */
    private static final String[] BACKWARDS_SORTED_ARRAY;
    /** Array array[ARRAYS_SIZE] of same strings. */
    private static final String[] SAME_STRINGS_ARRAY;

    /** Array length ARRAYS_SIZE of random strings with each string length in STR_LENGTH. */
    private static final String[] RANDOM_ARRAY;
    /** Array length ARRAYS_SIZE of random strings with each string length in STR_LENGTH and frequently repeating. */
    private static final String[] RANDOM_REPEATING_ARRAY;

    /** Empty array. */
    private static final String[] EMPTY_ARRAY = new String[0];
    /** Size 1 array. */
    private static final String[] SIZE1_ARRAY = new String[] {"wubbalubbadubdub"};

    // All types of testing arrays, initialized with values
    static {

        SORTED_ARRAY = new String[ARRAYS_SIZE];
        BACKWARDS_SORTED_ARRAY = new String[ARRAYS_SIZE];
        SAME_STRINGS_ARRAY = new String[ARRAYS_SIZE];

        RANDOM_ARRAY = new String[ARRAYS_SIZE];
        RANDOM_REPEATING_ARRAY = new String[ARRAYS_SIZE];
        Random randomizer = new Random();

        for (int i = 0; i < ARRAYS_SIZE; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0, length = randomizer.nextInt(STR_LENGTH); j < length; j++) {
                builder.append((char) (CHAR_FROM + randomizer.nextInt(ALPHABET_SIZE)));
            }
            String newWord = builder.toString();
            SORTED_ARRAY[i] = newWord;
            BACKWARDS_SORTED_ARRAY[i] = newWord;
            SAME_STRINGS_ARRAY[i] = "wubbalubbadubdub";
            RANDOM_ARRAY[i] = newWord;
        }

        for (int i = 0; i < ARRAYS_SIZE; i++) {
            RANDOM_REPEATING_ARRAY[i] = SORTED_ARRAY[randomizer.nextInt(10)];
        }

        Arrays.sort(SORTED_ARRAY);
        Arrays.sort(BACKWARDS_SORTED_ARRAY, Collections.reverseOrder());

    }

    @Test
    public void testBuiltInArraysSort() {
        testAllSorts(SortingTypes.ARRAYS_SORT);
    }

    @Test
    public void testBuiltInParallelSort() {
        testAllSorts(SortingTypes.ARRAYS_PARALLEL_SORT);
    }

    /** Testing Radix sort. */
    @Test
    public void testRadixSort() {
        testAllSorts(SortingTypes.RADIX);
    }

    private void testAllSorts(SortingTypes sortingType) {
        testSortOnArray(SORTED_ARRAY, "SORTED_ARRAY", sortingType);
        testSortOnArray(BACKWARDS_SORTED_ARRAY, "BACKWARDS_SORTED_ARRAY", sortingType);
        testSortOnArray(SAME_STRINGS_ARRAY, "SAME_NUM_ARRAY", sortingType);
        testSortOnArray(RANDOM_ARRAY, "RANDOM_ARRAY", sortingType);
        testSortOnArray(RANDOM_REPEATING_ARRAY, "RANDOM_REP_ARRAY", sortingType);
        testSortOnArray(EMPTY_ARRAY, "EMPTY_ARRAY", sortingType);
        testSortOnArray(SIZE1_ARRAY, "SIZE1_ARRAY", sortingType);
    }

    /** Utility method for all tests.
     * Copy inputArray, call sorting method, print time, test result.
     *
     * @param inputArray Array to be sorted (not modified by this method)
     * @param nameOfArray Name of array to print in results (if print is enabled)
     * @param sortingType Type of sorting algorithm to execute
     */
    private void testSortOnArray(String[] inputArray, String nameOfArray, SortingTypes sortingType) {
        String[] array = Arrays.copyOf(inputArray, inputArray.length);
        long start = System.currentTimeMillis();
        switch (sortingType) {
            case ARRAYS_SORT:
                Arrays.sort(array);
                break;
            case ARRAYS_PARALLEL_SORT:
                Arrays.parallelSort(array);
                break;
            case RADIX:
                RadixSort.sort(array, CHAR_FROM, ALPHABET_SIZE + CHAR_FROM, STR_LENGTH);
                break;
            default:
                throw new RuntimeException("unknown sorting type");
        }

        long end = System.currentTimeMillis();
        if (PRINT_TIME) {
            System.out.println("SIZE: " + ARRAYS_SIZE_FORMATTED + " ints. TIME: " + (end - start) + " ms. Sorted "
                    + nameOfArray + " with " + sortingType);
        }
        for (int i = 0; i < inputArray.length - 1; i++) {
            assert array[i].compareTo(array[i+1]) <= 0;
        }
    }

}
