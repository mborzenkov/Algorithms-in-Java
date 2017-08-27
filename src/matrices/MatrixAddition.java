package matrices;

import com.sun.istack.internal.NotNull;

/** This class contain methods for matrix addition.
 * Addition of two matrices A (size m * n) and B (size m * n) is matrix C (size m * n),
 * each element of C: C.get(i, j) == A.get(i, j) + B.get(i, j)
 */
public class MatrixAddition {

    /* Thread safety argument:
     *      This class is thread safe because it have no instances (everything static).
     *      Noninstantiability is enforced with private constructor.
     */

    private MatrixAddition() {
        throw new UnsupportedOperationException(
                "Error @ new MatrixAddition() :: MatrixAddition is noninstantiable static class");
    }

    /** Adds two matrices.
     *
     * @param matrA matrix n x m
     * @param matrB matrix n x m
     *
     * @throws IllegalArgumentException if dimensions of matrA and matrB is not equal
     * @throws NullPointerException if any of params is null
     *
     * @return new matrix n x m that is addition of matrA and matrB
     */
    public static @NotNull Matrix sum(@NotNull Matrix matrA, @NotNull Matrix matrB) {
        if ((matrA.getRowCount() != matrB.getRowCount()) || (matrA.getColumnCount() != matrB.getColumnCount())) {
            throw new IllegalArgumentException(
                    "Error @ MatrixAddition.sum() :: matrA & matrB dimensions are not equal");
        }
        final int rows = matrA.getRowCount();
        final int cols = matrA.getColumnCount();
        final Matrix.Builder result = new Matrix.Builder(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, matrA.get(i, j) + matrB.get(i, j));
            }
        }
        return result.build();
    }

}
