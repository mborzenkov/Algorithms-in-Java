package sortings;

// TODO: Class javadoc
public class Sortings {

    // TODO: Rep invariant
    // TODO: Abstract function
    // TODO: ChecnRep
    // TODO: Safety from rep exposure
    // TODO: Thread safety

    private Sortings() { /* this is static utility class, noninstatiable */ }

    /** Sorts input array, using insertion sort
     * Modifying input array such as for each i, input[i] <= input[i+1]
     * This method is effective for sorting small arrays.
     *
     * @param input Array of ints
     */
    public static void insertionSort(int[] input) {
        if (input != null && input.length > 1) {
            for (int j = 1; j < input.length; j++) {
                int key = input[j];
                int i = j-1;
                while (i >= 0 && input[i] > key) {
                    input[i+1] = input[i];
                    i--;
                }
                input[i+1] = key;
            }
        }
    }


}
