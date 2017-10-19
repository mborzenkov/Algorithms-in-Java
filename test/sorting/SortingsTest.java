package sorting;

import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

/** Tests sorting algorithms in sortings package except counting and radix. */
public class SortingsTest {

    // TODO: Consider using JMH for proper benchmarking

    /* Testing strategy
     *      Best cases and worse cases for algorithms.
     *      Random array.
     *      Frequently repeating numbers.
     *      Same numbers.
     */

    /** Size of arrays, positive. */
    private static final int ARRAYS_SIZE = 10000;
    /** Size of arrays formatted. */
    private static final String ARRAYS_SIZE_FORMATTED = String.format("%.0e", (float) ARRAYS_SIZE);
    /** True == print execution time on out. */
    private static final boolean PRINT_TIME = true;

    /** Types of sortings. */
    private enum SortingTypes {
        ARRAYS_SORT("Built-in Arrays.sort()"), ARRAYS_PARALLEL_SORT("Built-in Arrays.parallelSort()"),
        MERGE("Merge Sort"), QUICK("Quick Sort"),
        INSERTION("Insertion Sort"), SELECTION("Selection Sort"), BUBBLE("Bubble Sort");

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

    /** Array of random ints from 0 to Integer.MAX_VALUE, probably not unique numbers. */
    private static final int[] RANDOM_ARRAY;
    /** Array of ints from 0 to 10, numbers are repeating frequently. */
    private static final int[] RANDOM_REPEATING_ARRAY;
    /** Array of same numbers. */
    private static final int[] SAME_NUMBERS_ARRAY;
    /** Array of ints from 0 to ARRAYS_SIZE - 1, all numbers are unique. */
    private static final int[] SORTED_ARRAY;
    /** Array of ints from ARRAYS_SIZE - 1 to 0, all numbers are unique. */
    private static final int[] BACKWARDS_SORTED_ARRAY;
    /** Empty array. */
    private static final int[] EMPTY_ARRAY = new int[0];
    /** Size 1 array. */
    private static final int[] SIZE1_ARRAY = new int[] {101};

    // All types of testing arrays, initialized with values
    static {
        RANDOM_ARRAY = new int[ARRAYS_SIZE];
        RANDOM_REPEATING_ARRAY = new int[ARRAYS_SIZE];
        SAME_NUMBERS_ARRAY = new int[ARRAYS_SIZE];
        SORTED_ARRAY = new int[ARRAYS_SIZE];
        BACKWARDS_SORTED_ARRAY = new int[ARRAYS_SIZE];
        Random randomizer = new Random();
        for (int i = 0; i < ARRAYS_SIZE; i++) {
            SAME_NUMBERS_ARRAY[i] = 1;
            SORTED_ARRAY[i] = i;
            BACKWARDS_SORTED_ARRAY[ARRAYS_SIZE - 1 - i] = i;
            RANDOM_ARRAY[i] = randomizer.nextInt();
            RANDOM_REPEATING_ARRAY[i] = randomizer.nextInt(10);
        }
    }

    /** Testing built-in Arrays.sort() algorithm. */
    @Test
    public void testBuiltInArraysSort() {
        testAllSorts(SortingTypes.ARRAYS_SORT);
    }

    /** Testing built-in Arrays.sort() algorithm. */
    @Test
    public void testBuiltInArraysParallelSort() {
        testAllSorts(SortingTypes.ARRAYS_PARALLEL_SORT);
    }

    /** Testing insertion sort algorithm. */
    @Test
    public void testInsertionSort() {
        testAllSorts(SortingTypes.INSERTION);
    }

    /** Testing merge sort algorithm. */
    @Test
    public void testMergeSort() {
        testAllSorts(SortingTypes.MERGE);
    }

    /** Testing selection sort algorithm. */
    @Test // this test is long
    @SuppressWarnings("unused")
    public void testSelectionSort() {
        testAllSorts(SortingTypes.SELECTION);
    }

    /** Testing bubble sort algorithm. */
    @Test // this test is long
    @SuppressWarnings("unused")
    public void testBubbleSort() {
        testAllSorts(SortingTypes.BUBBLE);
    }

    /** Testing quick sort algorithm. */
    @Test
    public void testQuickSort() {
        testAllSorts(SortingTypes.QUICK);
    }

    private void testAllSorts(SortingTypes sortingType) {
        testSortOnArray(SORTED_ARRAY, "SORTED_ARRAY", sortingType);
        testSortOnArray(SAME_NUMBERS_ARRAY, "SAME_NUM_ARRAY", sortingType);
        testSortOnArray(RANDOM_ARRAY, "RANDOM_ARRAY", sortingType);
        testSortOnArray(RANDOM_REPEATING_ARRAY, "RANDOM_REP_ARRAY", sortingType);
        testSortOnArray(BACKWARDS_SORTED_ARRAY, "BACKWARDS_SORTED_ARRAY", sortingType);
        testSortOnArray(EMPTY_ARRAY, "EMPTY_ARRAY", sortingType);
        testSortOnArray(SIZE1_ARRAY, "SIZE1_ARRAY", sortingType);
    }


    /** Utility method for all tests.
     * Copy inputArray, call sorting method, print time, test result.
     * @param inputArray Array to be sorted (not modified by this method)
     * @param nameOfArray Name of array to print in results (if print is enabled)
     * @param sortingType Type of sorting algorithm to execute
     */
    private void testSortOnArray(int[] inputArray, String nameOfArray, SortingTypes sortingType) {
        int[] array = Arrays.copyOf(inputArray, inputArray.length);
        long start = System.currentTimeMillis();
        switch (sortingType) {
            case ARRAYS_SORT:
                Arrays.sort(array);
                break;
            case ARRAYS_PARALLEL_SORT:
                Arrays.parallelSort(array);
                break;
            case INSERTION:
                InsertionSort.sort(array);
                break;
            case MERGE:
                MergeSort.sort(array);
                break;
            case SELECTION:
                SelectionSort.sort(array);
                break;
            case BUBBLE:
                BubbleSort.sort(array);
                break;
            case QUICK:
                QuickSort.sort(array);
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
            assert (array[i] <= array[i + 1]);
        }
    }

}
