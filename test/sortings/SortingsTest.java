package sortings;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class SortingsTest {

    /* Testing strategy
     * TODO: Write testing strategy
     */

    /** Size of arrrays */
    private static final int ARRAYS_SIZE = 100000;
    /** Size of arrays formatted */
    private static final String ARRAYS_SIZE_FORMATTED = String.format("%.0e", (float) ARRAYS_SIZE);
    /** True if print execution time on out */
    private static final boolean PRINT_TIME = true;

    // All types of testing arrays
    private final int[] randomArray = new int[ARRAYS_SIZE];
    private final int[] sortedArray = new int[ARRAYS_SIZE];
    private final int[] backwardsSortedArray = new int[ARRAYS_SIZE];

    @Before
    public void initializeArrays() {
        Random randomizer = new Random();
        for(int i = 0; i < ARRAYS_SIZE; i++) {
            sortedArray[i] = i;
            backwardsSortedArray[ARRAYS_SIZE-1-i] = i;
            randomArray[i] = randomizer.nextInt();
        }
    }

    @Test
    public void testInsertionSort() {

        testInsertionSortOnArray(sortedArray, "sortedArray");
        testInsertionSortOnArray(randomArray, "randomArray");
        testInsertionSortOnArray(backwardsSortedArray, "backwardsSortedArray");

    }

    private void testInsertionSortOnArray(int[] array, String name) {
        long start = System.currentTimeMillis();
        Sortings.insertionSort(array);
        long end = System.currentTimeMillis();
        if (PRINT_TIME) {
            System.out.println("SIZE: " + ARRAYS_SIZE_FORMATTED + " ints. TIME: " + (end - start) + " ms. Sorted " + name);
        }

        for (int i = 0; i < ARRAYS_SIZE - 1; i++) {
            assert(array[i] <= array[i+1]);
        }
    }

}
