package matrices;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;

/** Immutable object representing a Matrix - rectangular array of integers.
 * Object creation allowed only with static factory methods or Builder.
 * Using Builder matrix can be filled with values manually.
 * Always contain at least 1 element (even if this element is equals 0).
 * Can be a vector (n x 1) or row-vector (1 x n).
 * Content or dimensions of matrix can not be changed after creation.
 */
public class Matrix {

    // Rep invariant:
    //      content is rectangular array containing values of all elements in matrix
    //          each element in content is array representing row (row-vector 1 x n)
    //
    // Abstraction function:
    //      represents a Matrix - rectangular array of integers
    //
    // Safety from rep exposure:
    //      content is rectangular array and mutable,
    //          but it there are no mutators in Matrix,
    //          there are defensive copying when content returned in getRow
    //          and when receiving array from user in fromArray
    //
    // Thread safety argument:
    //      Matrix class is thread safe because it is immutable
    //      Builder is mutable and not thread safe

    /** Helper class for creation and manually filling a matrix. */
    public static class Builder {

        private final int[][] content;

        /** Creates new Builder object, containing zero matrix with specified dimensions.
         * Zero matrix is a rectangular array of 0's.
         *
         * @param rows number of rows in matrix, >0
         * @param cols number of columns in matrix, >0
         *
         * @throws IllegalArgumentException if rows or cols <= 0
         */
        public Builder(int rows, int cols) {
            if ((rows <= 0) || (cols <= 0)) {
                throw new IllegalArgumentException(
                        String.format("Error @ Matrix.empty() :: wrong dimensions [%s, %s]", rows, cols));
            }
            content = new int[rows][cols];
        }

        /** Changes value of specific element.
         *
         * @param rowIndex index of row containing element
         * @param columnIndex index of column containing element
         * @param value new value
         *
         * @throws IndexOutOfBoundsException if rowIndex < 0 or >= getRowCount
         * @throws IndexOutOfBoundsException if columnIndex < 0 or >= getColumnCount
         */
        public Builder set(int rowIndex, int columnIndex, int value) {
            content[rowIndex][columnIndex] = value;
            return this;
        }

        /** Creates new matrix from builder. */
        public @NotNull Matrix build() {
            return new Matrix(content);
        }

    }

    /** Creates new Matrix from provided array.
     *
     * @param array non-empty rectangular array n * m
     *
     * @return new matrix n * m
     *
     * @throws IllegalArgumentException if array is empty or not rectangular
     * @throws NullPointerException if array is null
     */
    public static Matrix fromArray(@NotNull int[][] array) {
        final int rows = array.length;
        if (rows == 0) {
            throw new IllegalArgumentException("Error @ Matrix.fromArray() :: array is empty");
        }
        final int cols = array[0].length;
        final int[][] content = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            if (array[i].length != cols) {
                throw new IllegalArgumentException("Error @ Matrix.fromArray() :: array is not rectangular");
            }
            System.arraycopy(array[i], 0, content[i], 0, cols);
        }
        return new Matrix(content);
    }

    /** Rectangular array of integers representing matrix. */
    private final int[][] content;

    /** Creates new matrix based on integer rectangular array.
     *
     * @param content rectangular array size m * n
     */
    private Matrix(int[][] content) {
        this.content = content;
    }

    /** Reads value of specific element in matrix.
     *
     * @param rowIndex index of row containing element
     * @param columnIndex index of column containing element
     *
     * @return value of element
     *
     * @throws IndexOutOfBoundsException if rowIndex < 0 or >= getRowCount
     * @throws IndexOutOfBoundsException if columnIndex < 0 or >= getColumnCount
     */
    public int get(int rowIndex, int columnIndex) {
        return content[rowIndex][columnIndex];
    }

    /** Counts rows in matrix.
     *
     * @return number of rows
     */
    public int getRowCount() {
        return content.length;
    }

    /** Counts columns in matrix.
     *
     * @return number of columns
     */
    public int getColumnCount() {
        return content[0].length;
    }

    /** Return row of matrix.
     * Row is a row-vector (1 x n).
     *
     * @param index index of row to return
     *
     * @return row-vector (1 x getColumnCount()) representing row in matrix
     *
     * @throws IndexOutOfBoundsException if index < 0 or >= getRowCount
     */
    public int[] getRow(int index) {
        return Arrays.copyOf(content[index], content[index].length);
    }

    /** Return hashcode of Matrix, based on its content. */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(content);
    }

    /** Check equality of two matrices.
     * Two matrices are equal if they have equal dimensions and all elements are equal.
     *
     * @param obj checked object
     *
     * @return true if this equals obj
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix)) {
            return false;
        }
        Matrix thatObject = (Matrix) obj;
        return Arrays.deepEquals(content, thatObject.content);
    }

    /** Returns a human-readable string representation of Matrix. */
    @Override
    public String toString() {
        return Arrays.deepToString(content);
    }

}
