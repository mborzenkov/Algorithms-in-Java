package matrix;

import com.sun.istack.internal.NotNull;

/** This class contain methods for matrix addition and subtraction.
 * Addition of two matrices A (size m * n) and B (size m * n) is matrix C (size m * n),
 * each element of C: C.get(i, j) == A.get(i, j) + B.get(i, j)
 */
@SuppressWarnings("WeakerAccess")
public class MatrixAddSub {

    /* Thread safety argument:
     *      This class is thread safe because it have no instances (everything static).
     *      Noninstantiability is enforced with private constructor.
     */

    private MatrixAddSub() {
        throw new UnsupportedOperationException(
                "Error @ new MatrixAddSub() :: MatrixAddSub is noninstantiable static class");
    }

    /** Adds two matrices.
     *
     * @param matrA matrix n x m
     * @param matrB matrix n x m
     *
     * @return new matrix n x m equals (matrA + matrB)
     *
     * @throws IllegalArgumentException if dimensions of matrA and matrB is not equal
     * @throws NullPointerException if any of params is null
     */
    public static @NotNull Matrix add(@NotNull Matrix matrA, @NotNull Matrix matrB) {
        if ((matrA.getRowCount() != matrB.getRowCount()) || (matrA.getColumnCount() != matrB.getColumnCount())) {
            throw new IllegalArgumentException(
                    "Error @ MatrixAddSub.add() :: matrA & matrB dimensions are not equal");
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

    /** Subtracts two matrices.
     *
     * @param matrA matrix n x m
     * @param matrB matrix n x m
     *
     * @return new matrix n x m equals (matrA - matrB)
     *
     * @throws IllegalArgumentException if dimensions of matrA and matrB is not equal
     * @throws NullPointerException if any of params is null
     */
    public static @NotNull Matrix sub(@NotNull Matrix matrA, @NotNull Matrix matrB) {
        if ((matrA.getRowCount() != matrB.getRowCount()) || (matrA.getColumnCount() != matrB.getColumnCount())) {
            throw new IllegalArgumentException(
                    "Error @ MatrixAddSub.add() :: matrA & matrB dimensions are not equal");
        }
        final int rows = matrA.getRowCount();
        final int cols = matrA.getColumnCount();
        final Matrix.Builder result = new Matrix.Builder(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, matrA.get(i, j) - matrB.get(i, j));
            }
        }
        return result.build();
    }

}
