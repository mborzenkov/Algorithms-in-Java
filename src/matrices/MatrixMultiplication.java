package matrices;

import com.sun.istack.internal.NotNull;
import util.AlgorithmsUtils;

/** This class contain methods for multiplying matrices.
 * Multiplication of two matrices A (size m * n) and B (size n * p) is matrix C (size m * p), each element of C
 * C.get(i, j) == SUM for k in [1, n] of A.get(i, k) * B.get(k, j)
 */
public class MatrixMultiplication {

    private MatrixMultiplication() {
        throw new UnsupportedOperationException(
                "Error @ new MatrixMultiplication() :: MatrixMultiplication is noninstantiable static class");
    }


    /** Multiplies matrices matrA and matrB using basic algorithm.
     *
     * @param matrA matrix, size m * n
     * @param matrB matrix, size n * p
     *
     * @return matrix size m * p, result of matrA matrix multiplied by matrB
     *
     * @throws IllegalArgumentException if matrA.getColumnCount() != matrB.getRowCount()
     * @throws NullPointerException if any of params is null
     */
    public static @NotNull Matrix mult(@NotNull Matrix matrA, @NotNull Matrix matrB) {
        final int compatibleValue = matrA.getColumnCount();
        if (compatibleValue != matrB.getRowCount()) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.mult() :: incompatible matrix matrA.columns != matrB.rows");
        }

        final int rowCount = matrA.getRowCount();
        final int colCount = matrB.getColumnCount();
        final Matrix.Builder result = new Matrix.Builder(rowCount, colCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                int val = 0;
                for (int k = 0; k < compatibleValue; k++) {
                    val += matrA.get(i, k) * matrB.get(k, j);
                }
                result.set(i, j, val);
            }
        }

        return result.build();
    }

    /** Multiplies matrices matrA and matrB using recursive algorithm.
     * This function accepts only square (n x n) matrices with each side is exact power of 2 (2, 4, 8, 16...).
     * So when dividing n/2 recursively we ensure that result is an integer.
     *
     * @param matrA matrix, size n * n
     * @param matrB matrix, size n * n
     *
     * @return matrix size n * n, result of matrA matrix multiplied by matrB
     *
     * @throws IllegalArgumentException if matrA or matrB is not square
     * @throws IllegalArgumentException if dimensions of matrA is not equal to dimensions of matrB
     * @throws IllegalArgumentException if dimensions of matrA or matrB is not exact power of 2
     * @throws NullPointerException if any of params is null
     */
    public static @NotNull Matrix multRecursiveSquaredPow2(@NotNull Matrix matrA, @NotNull Matrix matrB) {
        // Check validity of params
        final int matrixOneRows = matrA.getRowCount();
        final int matrixOneCols = matrA.getColumnCount();
        final int matrixTwoRows = matrB.getRowCount();
        final int matrixTwoCols = matrB.getColumnCount();
        if (matrixOneRows != matrixOneCols) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multRecursiveSquaredPow2() :: matrA is not square matrix");
        }
        if (matrixTwoRows != matrixTwoCols) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multRecursiveSquaredPow2() :: matrB is not square matrix");
        }
        if (matrixOneRows != matrixTwoRows) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multRecursiveSquaredPow2() :: matrA & matrB dimensions not equal");
        }
        if (!AlgorithmsUtils.isPowerOfTwo(matrixOneRows)) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multRecursiveSquaredPow2() :: matrA & matrB dimens is not pow of 2");
        }

        // compute recursively
        return compute(matrA, matrB);
    }

    // TODO: написать recursive, протестировать его + матрикс + утилс, оптимизировать, далее штрассен

    private static @NotNull Matrix compute(@NotNull Matrix matrA, @NotNull Matrix matrB) {
        final int n = matrA.getRowCount();

        if (n == 1) {
            return Matrix.fromArray(
                    new int[][] {new int[] { matrA.get(0, 0) * matrB.get(0,0 ) }});
        }

        final int start = 0;
        final int mid = n / 2;

        final Matrix c11 = MatrixAddition.sum(
                compute(splitMatrix(matrA, start, mid, start, mid),  // (a11 *
                        splitMatrix(matrB, start, mid, start, mid)), // b11) +
                compute(splitMatrix(matrA, start, mid, mid, n),      // (a12 *
                        splitMatrix(matrB, mid, n, start, mid)));    // b21)

        final Matrix c12 = MatrixAddition.sum(
                compute(splitMatrix(matrA, start, mid, start, mid), // (a11 *
                        splitMatrix(matrB, start, mid, mid, n)),    // b12) +
                compute(splitMatrix(matrA, start, mid, mid, n),     // (a12 *
                        splitMatrix(matrB, mid, n, mid, n)));       // b22)

        final Matrix c21 = MatrixAddition.sum(
                compute(splitMatrix(matrA, mid, n, start, mid),      // (a21 *
                        splitMatrix(matrB, start, mid, start, mid)), // b11) +
                compute(splitMatrix(matrA, mid, n, mid, n),          // (a22 *
                        splitMatrix(matrB, mid, n, start, mid)));    // b21)

        final Matrix c22 = MatrixAddition.sum(
                compute(splitMatrix(matrA, mid, n, start, mid),      // (a21 *
                        splitMatrix(matrB, start, mid, mid, n)),     // b12) +
                compute(splitMatrix(matrA, mid, n, mid, n),          // (a22 *
                        splitMatrix(matrB, mid, n, mid, n)));        // b22)

        return mergeMatrices(c11, c12, c21, c22);
    }

    private static @NotNull Matrix splitMatrix(@NotNull Matrix matrix,
                                               int rowStart, int rowEnd, int colStart, int colEnd) {
        int[][] matrixData = new int[rowEnd - rowStart][colEnd - colStart];
        for (int i = rowStart; i < rowEnd; i++) {
            int[] oldRow = matrix.getRow(i);
            System.arraycopy(oldRow, colStart, matrixData[i], 0, colEnd - colStart);
        }
        return Matrix.fromArray(matrixData);
    }

    private static @NotNull Matrix mergeMatrices(@NotNull Matrix matrC11, @NotNull Matrix matrC12, @NotNull Matrix matrC21, @NotNull Matrix matrC22) {
        // TODO: првоерки параметров
        final int rows = matrC11.getRowCount() + matrC21.getRowCount();
        final int cols = matrC11.getColumnCount() + matrC12.getColumnCount();
        int[][] matrixData = new int[rows][cols];
        for (int i = 0; i < matrC11.getRowCount(); i++) {
            int[] row11 = matrC11.getRow(i);
            System.arraycopy(row11, 0, matrixData[i], 0, row11.length);
            int[] row12 = matrC12.getRow(i);
            System.arraycopy(row12, 0, matrixData[i], row11.length, row12.length);
        }
        for (int i = 0; i < matrC21.getRowCount(); i++) {
            int[] row21 = matrC21.getRow(i);
            System.arraycopy(row21, 0, matrixData[i], 0, row21.length);
            int[] row22 = matrC22.getRow(i);
            System.arraycopy(row22, 0, matrixData[i], row21.length, row22.length);
        }
        return Matrix.fromArray(matrixData);
    }

    /** Multiplies matrices matrA and matrB using Strassen algorithm.
     * This function accepts only square (n x n) matrices with each side is exact power of 2 (2, 4, 8, 16...).
     * So when dividing n/2 recursively we ensure that result is an integer.
     *
     * @param matrA matrix, size n * n
     * @param matrB matrix, size n * n
     *
     * @return matrix size n * n, result of matrA matrix multiplied by matrB
     *
     * @throws IllegalArgumentException if matrA or matrB is not square
     * @throws IllegalArgumentException if dimensions of matrA is not equal to dimensions of matrB
     * @throws IllegalArgumentException if dimensions of matrA or matrB is not exact power of 2
     * @throws NullPointerException if any of params is null
     */
    public static @NotNull Matrix multStrassenSquaredPow2(@NotNull Matrix matrA, @NotNull Matrix matrB) {
        if (matrA.getColumnCount() != matrB.getRowCount()) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multStrassen() :: incompatible matrix matrA.columns != matrB.rows");
        }
        throw new RuntimeException("not implemented");
    }

}
