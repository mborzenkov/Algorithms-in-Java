package matrices;

import com.sun.istack.internal.NotNull;

/** This class contain methods for multiplying matrices.
 * Multiplication of two matrices A (size m * n) and B (size n * p) is matrix C (size m * p), each element of C
 * C.get(i, j) == SUM for k in [1, n] of A.get(i, k) * B.get(k, j)
 */
public class MatrixMultiplication {

    /** Multiplies matrices first and second using basic algorithm.
     *
     * @param first matrix, size m * n
     * @param second matrix, size n * p
     *
     * @return matrix size m * p, result of first matrix multiplied by second
     *
     * @throws IllegalArgumentException if first.getColumnCount() != second.getRowCount()
     * @throws NullPointerException if any of params is null
     */
    public static @NotNull Matrix mult(@NotNull Matrix first, @NotNull Matrix second) {
        final Matrix matrixOne = Matrix.fromMatrix(first);
        final Matrix matrixTwo = Matrix.fromMatrix(second);
        final int compatibleValue = matrixOne.getColumnCount();
        if (compatibleValue != matrixTwo.getRowCount()) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.mult() :: incompatible matrix first.columns != second.rows");
        }

        final int rowCount = matrixOne.getRowCount();
        final int colCount = matrixTwo.getColumnCount();
        final Matrix result = Matrix.empty(rowCount, colCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                int val = 0;
                for (int k = 0; k < compatibleValue; k++) {
                    val += matrixOne.get(i, k) * matrixTwo.get(k, j);
                }
                result.set(i, j, val);
            }
        }

        return result;
    }

    /** Multiplies matrices first and second using recursive algorithm.
     *
     * @param first matrix, size m * n
     * @param second matrix, size n * p
     *
     * @return matrix size m * p, result of first matrix multiplied by second
     *
     * @throws IllegalArgumentException if first.getColumnCount() != second.getRowCount()
     * @throws NullPointerException if any of params is null
     */
    public static @NotNull Matrix multRecursive(@NotNull Matrix first, @NotNull Matrix second) {
        final Matrix matrixOne = Matrix.fromMatrix(first);
        final Matrix matrixTwo = Matrix.fromMatrix(second);
        if (matrixOne.getColumnCount() != matrixTwo.getRowCount()) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multRecursive() :: incompatible matrix first.columns != second.rows");
        }
        throw new RuntimeException("not implemented");
    }

    /** Multiplies matrices first and second using Strassen algorithm.
     *
     * @param first matrix, size m * n
     * @param second matrix, size n * p
     *
     * @return matrix size m * p, result of first matrix multiplied by second
     *
     * @throws IllegalArgumentException if first.getColumnCount() != second.getRowCount()
     * @throws NullPointerException if any of params is null
     */
    public static @NotNull Matrix multStrassen(@NotNull Matrix first, @NotNull Matrix second) {
        final Matrix matrixOne = Matrix.fromMatrix(first);
        final Matrix matrixTwo = Matrix.fromMatrix(second);
        if (matrixOne.getColumnCount() != matrixTwo.getRowCount()) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multStrassen() :: incompatible matrix first.columns != second.rows");
        }
        throw new RuntimeException("not implemented");
    }

}
