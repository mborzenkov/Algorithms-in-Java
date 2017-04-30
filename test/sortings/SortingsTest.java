package sortings;

import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

/** Tests all sorting algorithms in Sortings.java. */
public class SortingsTest {

    // TODO: Consider using JMH for proper benchmarking

    /* Testing strategy
     *      All best cases and worse cases for algorithms.
     *      Random array.
     */

    /** Size of arrays, positive. */
    private static final int ARRAYS_SIZE = 100000;
    /** Size of arrays formatted. */
    private static final String ARRAYS_SIZE_FORMATTED = String.format("%.0e", (float) ARRAYS_SIZE);
    /** True == print execution time on out. */
    private static final boolean PRINT_TIME = true;

    /** Types of sortings. */
    private enum SortingTypes {
        ARRAYS_SORT("Built-in Arrays.sort()"), INSERTION("Insertion Sort"), MERGE("Merge Sort"), BUBBLE("Bubble Sort"),
                SELECTION("Selection Sort");

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
        SORTED_ARRAY = new int[ARRAYS_SIZE];
        BACKWARDS_SORTED_ARRAY = new int[ARRAYS_SIZE];
        Random randomizer = new Random();
        for (int i = 0; i < ARRAYS_SIZE; i++) {
            SORTED_ARRAY[i] = i;
            BACKWARDS_SORTED_ARRAY[ARRAYS_SIZE - 1 - i] = i;
            RANDOM_ARRAY[i] = randomizer.nextInt();
        }
    }

    /** Testing built-in Arrays.sort() algorithm. */
    @Test
    public void testBuiltInArraysSort() {
        testSortOnArray(SORTED_ARRAY, "SORTED_ARRAY", SortingTypes.ARRAYS_SORT);
        testSortOnArray(RANDOM_ARRAY, "RANDOM_ARRAY", SortingTypes.ARRAYS_SORT);
        testSortOnArray(BACKWARDS_SORTED_ARRAY, "BACKWARDS_SORTED_ARRAY", SortingTypes.ARRAYS_SORT);
        testSortOnArray(EMPTY_ARRAY, "EMPTY_ARRAY", SortingTypes.ARRAYS_SORT);
        testSortOnArray(SIZE1_ARRAY, "SIZE1_ARRAY", SortingTypes.ARRAYS_SORT);
    }

    /** Testing insertion sort algorithm. */
    @Test
    public void testInsertionSort() {
        testSortOnArray(SORTED_ARRAY, "SORTED_ARRAY", SortingTypes.INSERTION);
        testSortOnArray(RANDOM_ARRAY, "RANDOM_ARRAY", SortingTypes.INSERTION);
        testSortOnArray(BACKWARDS_SORTED_ARRAY, "BACKWARDS_SORTED_ARRAY", SortingTypes.INSERTION);
        testSortOnArray(EMPTY_ARRAY, "EMPTY_ARRAY", SortingTypes.INSERTION);
        testSortOnArray(SIZE1_ARRAY, "SIZE1_ARRAY", SortingTypes.INSERTION);
    }

    /** Testing merge sort algorithm. */
    @Test
    public void testMergeSort() {
        testSortOnArray(SORTED_ARRAY, "SORTED_ARRAY", SortingTypes.MERGE);
        testSortOnArray(RANDOM_ARRAY, "RANDOM_ARRAY", SortingTypes.MERGE);
        testSortOnArray(BACKWARDS_SORTED_ARRAY, "BACKWARDS_SORTED_ARRAY", SortingTypes.MERGE);
        testSortOnArray(EMPTY_ARRAY, "EMPTY_ARRAY", SortingTypes.MERGE);
        testSortOnArray(SIZE1_ARRAY, "SIZE1_ARRAY", SortingTypes.MERGE);
    }

    // @Test - this test is long, so it is turned off
    /** Testing selection sort algorithm. */
    @SuppressWarnings("unused")
    public void testSelectionSort() {
        testSortOnArray(SORTED_ARRAY, "SORTED_ARRAY", SortingTypes.SELECTION);
        testSortOnArray(RANDOM_ARRAY, "RANDOM_ARRAY", SortingTypes.SELECTION);
        testSortOnArray(BACKWARDS_SORTED_ARRAY, "BACKWARDS_SORTED_ARRAY", SortingTypes.SELECTION);
        testSortOnArray(EMPTY_ARRAY, "EMPTY_ARRAY", SortingTypes.SELECTION);
        testSortOnArray(SIZE1_ARRAY, "SIZE1_ARRAY", SortingTypes.SELECTION);
    }

    // @Test - this test is long, so it is turned off
    /** Testing bubble sort algorithm. */
    @SuppressWarnings("unused")
    public void testBubbleSort() {
        testSortOnArray(SORTED_ARRAY, "SORTED_ARRAY", SortingTypes.BUBBLE);
        testSortOnArray(RANDOM_ARRAY, "RANDOM_ARRAY", SortingTypes.BUBBLE);
        testSortOnArray(BACKWARDS_SORTED_ARRAY, "BACKWARDS_SORTED_ARRAY", SortingTypes.BUBBLE);
        testSortOnArray(EMPTY_ARRAY, "EMPTY_ARRAY", SortingTypes.BUBBLE);
        testSortOnArray(SIZE1_ARRAY, "SIZE1_ARRAY", SortingTypes.BUBBLE);
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
            case INSERTION:
                Sortings.insertionSort(array);
                break;
            case MERGE:
                Sortings.mergeSort(array);
                break;
            case SELECTION:
                Sortings.selectionSort(array);
                break;
            case BUBBLE:
                Sortings.bubbleSort(array);
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
