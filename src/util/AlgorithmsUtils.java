package util;

/** This class contain additional support methods for algorithms.
 * Each method is relatively small and serve a specific task.
 */
public class AlgorithmsUtils {

    /* Thread safety argument:
     *      This class is thread safe because it have no instances (everything static).
     *      Noninstantiability is enforced with private constructor.
     */

    private AlgorithmsUtils() {
        throw new UnsupportedOperationException("Error @ new Utils() :: Utils is noninstantiable static class");
    }

    /** Checks if number is exact power of 2.
     *
     * @param number positive integer
     *
     * @throws IllegalArgumentException if number <= 0
     *
     * @return true if number is power of 2, false otherwise
     */
    public static boolean isPowerOfTwo(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Error @ Utils.isPowerOfTwo() :: number is not positive == " + number);
        }
        return (number & (number - 1)) == 0;
    }

}
