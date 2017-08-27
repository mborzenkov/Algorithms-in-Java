package matrices;

import com.sun.istack.internal.NotNull;
import util.AlgorithmsUtils;

import java.util.Arrays;

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
        return computeRecursiveSquaredPow2(
                matrA, 0, matrARows, 0, matrACols,
                matrB, 0, matrBRows, 0, matrBCols);
    }

    /** Recursive part of multRecursiveSquaredPow2 (recursive matrix multiplication).
     * For restrictions of matrA and matrB see {@link MatrixMultiplication#multRecursiveSquaredPow2(Matrix, Matrix)}
     *
     * @param matrA matrix 1
     * @param matrARowStart start row index of current part of matrA
     * @param matrARowEnd end row index of current part of matrA
     * @param matrAColStart start column index of current part of matrA
     * @param matrAColEnd end column index of current part of matrA
     * @param matrB matrix 2
     * @param matrBRowStart start row index of current part of matrB
     * @param matrBRowEnd end row index of current part of matrB
     * @param matrBColStart start column index of current part of matrB
     * @param matrBColEnd end column index of current part of matrB
     *
     * @return new Matrix as a result of multiplication matrA * matrB
     */
    private static @NotNull Matrix computeRecursiveSquaredPow2(
            @NotNull Matrix matrA, int matrARowStart, int matrARowEnd, int matrAColStart, int matrAColEnd,
            @NotNull Matrix matrB, int matrBRowStart, int matrBRowEnd, int matrBColStart, int matrBColEnd) {

        assert matrARowEnd > matrARowStart;
        assert matrAColEnd > matrAColStart;
        assert matrBRowEnd > matrBRowStart;
        assert matrBColEnd > matrBColStart;

        if ((matrARowEnd - matrARowStart) == 1) {
            return Matrix.fromArray(
                    new int[][] { new int[]
                            { matrA.get(matrARowStart, matrAColStart) * matrB.get(matrBRowStart,matrBColStart) }});
        }

        int matrARowMid = matrARowStart + (matrARowEnd - matrARowStart) / 2;
        int matrAColMid = matrAColStart + (matrAColEnd - matrAColStart) / 2;
        int matrBRowMid = matrBRowStart + (matrBRowEnd - matrBRowStart) / 2;
        int matrBColMid = matrBColStart + (matrBColEnd - matrBColStart) / 2;

        final Matrix c11 = MatrixAddition.sum(
                computeRecursiveSquaredPow2(
                        matrA, matrARowStart, matrARowMid, matrAColStart, matrAColMid,  // (a11 *
                        matrB, matrBRowStart, matrBRowMid, matrBColStart, matrBColMid), // b11) +
                computeRecursiveSquaredPow2(
                        matrA, matrARowStart, matrARowMid, matrAColMid, matrAColEnd,    // (a12 *
                        matrB, matrBRowMid, matrBRowEnd, matrBColStart, matrBColMid));  // b21)

        final Matrix c12 = MatrixAddition.sum(
                computeRecursiveSquaredPow2(
                        matrA, matrARowStart, matrARowMid, matrAColStart, matrAColMid,  // (a11 *
                        matrB, matrBRowStart, matrBRowMid, matrBColMid, matrBColEnd),   // b12) +
                computeRecursiveSquaredPow2(
                        matrA, matrARowStart, matrARowMid, matrAColMid, matrAColEnd,    // (a12 *
                        matrB, matrBRowMid, matrBRowEnd, matrBColMid, matrBColEnd));    // b22)

        final Matrix c21 = MatrixAddition.sum(
                computeRecursiveSquaredPow2(
                        matrA, matrARowMid, matrARowEnd, matrAColStart, matrAColMid,    // (a21 *
                        matrB, matrBRowStart, matrBRowMid, matrBColStart, matrBColMid), // b11) +
                computeRecursiveSquaredPow2(
                        matrA, matrARowMid, matrARowEnd, matrAColMid, matrAColEnd,      // (a22 *
                        matrB, matrBRowMid, matrBRowEnd, matrBColStart, matrBColMid));  // b21)

        final Matrix c22 = MatrixAddition.sum(
                computeRecursiveSquaredPow2(
                        matrA, matrARowMid, matrARowEnd, matrAColStart, matrAColMid,    // (a21 *
                        matrB, matrBRowStart, matrBRowMid, matrBColMid, matrBColEnd),   // b12) +
                computeRecursiveSquaredPow2(
                        matrA, matrARowMid, matrARowEnd, matrAColMid, matrAColEnd,      // (a22 *
                        matrB, matrBRowMid, matrBRowEnd, matrBColMid, matrBColEnd));    // b22)

        return mergeMatrices(c11, c12, c21, c22);
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
    private static @NotNull Matrix mergeMatrices(@NotNull Matrix matrC11, @NotNull Matrix matrC12, @NotNull Matrix matrC21, @NotNull Matrix matrC22) {

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
