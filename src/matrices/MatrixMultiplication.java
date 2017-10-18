package matrices;

import com.sun.istack.internal.NotNull;
import datastructures.trees.BinaryTree;
import util.AlgorithmsUtils;

/** This class contain methods for multiplying matrices.
 * Multiplication of two matrices A (size m * n) and B (size n * p) is matrix C (size m * p), each element of C
 * C.get(i, j) == SUM for k in [1, n] of A.get(i, k) * B.get(k, j)
 * <br/>
 * This class is made only in educational purpose.
 * Recursive and Strassen algorithms are using additional memory because of immutable Matrix objects (and splitting).
 * This was made intentionally due to increasing in readability of code.
 * Matrix multiplication can (should) be implemented using indexes and/or mutable objects, minimizing memory overhead.
 * I strongly recommend using specialized library instead of this class, like Apache Commons Math.
 */
@SuppressWarnings("WeakerAccess")
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
     * <b>This function accepts only square (n x n) matrices with each side is exact power of 2 (2, 4, 8, 16...).</b>
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
        final int matrARows = matrA.getRowCount();
        final int matrACols = matrA.getColumnCount();
        final int matrBRows = matrB.getRowCount();
        final int matrBCols = matrB.getColumnCount();
        if (matrARows != matrACols) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multRecursiveSquaredPow2() :: matrA is not square matrix");
        }
        if (matrBRows != matrBCols) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multRecursiveSquaredPow2() :: matrB is not square matrix");
        }
        if (matrARows != matrBRows) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multRecursiveSquaredPow2() :: matrA & matrB dimensions not equal");
        }
        if (!AlgorithmsUtils.isPowerOfTwo(matrARows)) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multRecursiveSquaredPow2() :: matrA & matrB dimens is not pow of 2");
        }

        // compute recursively
        return computeRecursiveSquaredPow2(matrA, matrB);
    }

    /** Recursive part of multRecursiveSquaredPow2 (recursive matrix multiplication).
     * For restrictions of matrA and matrB see {@link MatrixMultiplication#multRecursiveSquaredPow2(Matrix, Matrix)}
     *
     * @param matrA matrix 1 (n x n)
     * @param matrB matrix 2 (n x n)
     *
     * @return new Matrix as a result of multiplication matrA * matrB
     */
    private static @NotNull Matrix computeRecursiveSquaredPow2(@NotNull Matrix matrA, @NotNull Matrix matrB) {

        final int n = matrA.getRowCount();

        if (n == 1) {
            return Matrix.fromArray(new int[][] { new int[] {
                    matrA.get(0, 0) * matrB.get(0, 0) } });
        }

        final int mid = n / 2; // exact division because n is pow of 2
        final int start = 0;

        // Splitting both matrices on four parts each
        final Matrix a11 = splitMatrix(matrA, start, mid, start, mid);
        final Matrix a12 = splitMatrix(matrA, start, mid, mid, n);
        final Matrix a21 = splitMatrix(matrA, mid, n, start, mid);
        final Matrix a22 = splitMatrix(matrA, mid, n, mid, n);
        final Matrix b11 = splitMatrix(matrB, start, mid, start, mid);
        final Matrix b12 = splitMatrix(matrB, start, mid, mid, n);
        final Matrix b21 = splitMatrix(matrB, mid, n, start, mid);
        final Matrix b22 = splitMatrix(matrB, mid, n, mid, n);

        // c11 = (a11 * b11) + (a12 * b21)
        final Matrix c11 = MatrixAddSub.add(
                computeRecursiveSquaredPow2(a11, b11),
                computeRecursiveSquaredPow2(a12, b21));

        // c12 = (a11 * b12) + (a12 * b22)
        final Matrix c12 = MatrixAddSub.add(
                computeRecursiveSquaredPow2(a11, b12),
                computeRecursiveSquaredPow2(a12, b22));

        // c21 = (a21, b11) * (a22, b21)
        final Matrix c21 = MatrixAddSub.add(
                computeRecursiveSquaredPow2(a21, b11),
                computeRecursiveSquaredPow2(a22, b21));

        // c22 = (a21 * b12) + (a22 * b22)
        final Matrix c22 = MatrixAddSub.add(
                computeRecursiveSquaredPow2(a21, b12),
                computeRecursiveSquaredPow2(a22, b22));

        // C = [[c11, c12], [c21, c22]]
        return mergeMatrices(c11, c12, c21, c22);
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
        // Check validity of params
        final int matrARows = matrA.getRowCount();
        final int matrACols = matrA.getColumnCount();
        final int matrBRows = matrB.getRowCount();
        final int matrBCols = matrB.getColumnCount();
        if (matrARows != matrACols) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multStrassenSquaredPow2() :: matrA is not square matrix");
        }
        if (matrBRows != matrBCols) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multStrassenSquaredPow2() :: matrB is not square matrix");
        }
        if (matrARows != matrBRows) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multStrassenSquaredPow2() :: matrA & matrB dimensions not equal");
        }
        if (!AlgorithmsUtils.isPowerOfTwo(matrARows)) {
            throw new IllegalArgumentException(
                    "Error @ MatrixMultiplication.multStrassenSquaredPow2() :: matrA & matrB dimens is not pow of 2");
        }

        // compute recursively
        return computeStrassenSquaredPow2(matrA, matrB);
    }

    /** Recursive part of multStrassenSquaredPow2 (Strassen matrix multiplication).
     * For restrictions of matrA and matrB see {@link MatrixMultiplication#multStrassenSquaredPow2(Matrix, Matrix)}
     *
     * @param matrA matrix 1
     * @param matrB matrix 2
     *
     * @return new Matrix as a result of multiplication matrA * matrB
     */
    private static @NotNull Matrix computeStrassenSquaredPow2(@NotNull Matrix matrA, @NotNull Matrix matrB) {

        final int n = matrA.getRowCount();

        if (n == 1) {
            return Matrix.fromArray(new int[][] { new int[] {
                    matrA.get(0, 0) * matrB.get(0, 0) } });
        }

        final int mid = n / 2; // exact division because n is pow of 2
        final int start = 0;

        // Splitting both matrices on four parts each
        final Matrix a11 = splitMatrix(matrA, start, mid, start, mid);
        final Matrix a12 = splitMatrix(matrA, start, mid, mid, n);
        final Matrix a21 = splitMatrix(matrA, mid, n, start, mid);
        final Matrix a22 = splitMatrix(matrA, mid, n, mid, n);
        final Matrix b11 = splitMatrix(matrB, start, mid, start, mid);
        final Matrix b12 = splitMatrix(matrB, start, mid, mid, n);
        final Matrix b21 = splitMatrix(matrB, mid, n, start, mid);
        final Matrix b22 = splitMatrix(matrB, mid, n, mid, n);

        // p1 = a11 * (b12 - b22)
        final Matrix p1 = computeStrassenSquaredPow2(a11, MatrixAddSub.sub(b12, b22));
        // p2 = (a11 + a12) * b22
        final Matrix p2 = computeStrassenSquaredPow2(MatrixAddSub.add(a11, a12), b22);
        // p3 = (a21 + a22) * b11
        final Matrix p3 = computeStrassenSquaredPow2(MatrixAddSub.add(a21, a22), b11);
        // p4 = a22 * (b21 - b11)
        final Matrix p4 = computeStrassenSquaredPow2(a22, MatrixAddSub.sub(b21, b11));
        // p5 = (a11 + a22) * (b11 + b22)
        final Matrix p5 = computeStrassenSquaredPow2(MatrixAddSub.add(a11, a22), MatrixAddSub.add(b11, b22));
        // p6 = (a12 - a22) * (b21 + b22)
        final Matrix p6 = computeStrassenSquaredPow2(MatrixAddSub.sub(a12, a22), MatrixAddSub.add(b21, b22));
        // p7 = (a11 - a21) * (b11 + b12)
        final Matrix p7 = computeStrassenSquaredPow2(MatrixAddSub.sub(a11, a21), MatrixAddSub.add(b11, b12));

        // C = [[p5 + p4 - p2 + p6, p1 + p2], [p3 + p4, p5 + p1 - p3 - p7]]
        return mergeMatrices(
                MatrixAddSub.add(MatrixAddSub.sub(MatrixAddSub.add(p5, p4), p2), p6),
                MatrixAddSub.add(p1, p2),
                MatrixAddSub.add(p3, p4),
                MatrixAddSub.sub(MatrixAddSub.sub(MatrixAddSub.add(p5, p1), p3), p7));

    }

    /** Splits new matrix from existing one.
     *
     * @param matrix existing matrix
     * @param indexRowFrom starting row
     * @param indexRowTo ending row
     * @param indexColFrom starting column
     * @param indexColTo ending column
     *
     * @return new matrix with values equal to part of existing matrix, e.g.
     *      splitMatrix(matrix, 0, 1, 0, 1) where matrix is (2, 2) [[1, 2], [3, 4]] -> [[1]]
     */
    private static @NotNull Matrix splitMatrix(@NotNull Matrix matrix,
                                               int indexRowFrom, int indexRowTo, int indexColFrom, int indexColTo) {

        assert indexRowTo > indexRowFrom;
        assert indexColTo > indexColFrom;

        final int rows = indexRowTo - indexRowFrom;
        final int cols = indexColTo - indexColFrom;

        final int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix.getRow(indexRowFrom + i), indexColFrom, result[i], 0, cols);
        }

        return Matrix.fromArray(result);

    }

    /** Merging matrices c11, c12, c21 and c22 in one matrix C.
     * <b>All matrices must be squared and have equal dimensions.</b>
     *
     * @param matrC11 sub-matrix in left top corner
     * @param matrC12 sub-matrix in right top corner
     * @param matrC21 sub-matrix in left bottom corner
     * @param matrC22 sub-matrix in right bottom corner
     *
     * @return new matrix containing values from all matrices as:
     *      [ C11, C12 ],
     *      [ C21, C22 ]
     */
    private static @NotNull Matrix mergeMatrices(
            @NotNull Matrix matrC11, @NotNull Matrix matrC12, @NotNull Matrix matrC21, @NotNull Matrix matrC22) {

        assert matrC11.getRowCount() == matrC11.getColumnCount();
        assert (matrC11.getRowCount() == matrC12.getRowCount())
                && (matrC21.getRowCount() == matrC22.getRowCount())
                && (matrC11.getRowCount() == matrC22.getRowCount());
        assert (matrC11.getColumnCount() == matrC12.getColumnCount())
                && (matrC21.getColumnCount() == matrC22.getColumnCount())
                && (matrC11.getColumnCount() == matrC22.getColumnCount());

        final int halfRows = matrC11.getRowCount();
        final int halfCols = matrC11.getColumnCount();

        int[][] matrixData = new int[halfRows * 2][halfCols * 2];

        // Merging top part, C11 and C12
        for (int i = 0; i < halfRows; i++) {
            int[] row11 = matrC11.getRow(i);
            System.arraycopy(row11, 0, matrixData[i], 0, row11.length);
            int[] row12 = matrC12.getRow(i);
            System.arraycopy(row12, 0, matrixData[i], row11.length, row12.length);
        }

        // Merging bottom part, C21 and C22
        for (int i = 0; i < halfRows; i++) {
            int[] row21 = matrC21.getRow(i);
            System.arraycopy(row21, 0, matrixData[halfRows + i], 0, row21.length);
            int[] row22 = matrC22.getRow(i);
            System.arraycopy(row22, 0, matrixData[halfRows + i], row21.length, row22.length);
        }

        return Matrix.fromArray(matrixData);

    }

    /** Computes the result of matrix multiplication chain presented as binary tree.
     * For node computes leftChild * rightChild.
     * For more info on multiplication see {@link MatrixMultiplication#mult(Matrix, Matrix)}.
     *
     * @param multChain matrix multiplication chain presented as binary tree, not null
     *
     * @return matrix as a result of multiplication all matrices sequentially
     *
     * @throws IllegalArgumentException if multChain is not consistent (matrices can't be multiplied)
     * @throws NullPointerException if multChain is null
     */
    public static @NotNull Matrix computeMultChain(@NotNull BinaryTree<Matrix> multChain) {
        final Matrix value = multChain.getValue();
        if (value != null) {
            return value;
        }
        return mult(computeMultChain(multChain.getLeft()), computeMultChain(multChain.getRight()));
    }
}
