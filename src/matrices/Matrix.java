package matrices;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;

// also can be a vector;; modifiable
// TODO: Jdoc and checks
public class Matrix {

    private final int[][] content;

    public static Matrix fromArray(@NotNull int[][] array) {
        final int rows = array.length;
        final int cols = array[0].length;
        final int[][] content = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(array[i], 0, content[i], 0, cols);
        }
        return new Matrix(content);
    }

    public static Matrix fromMatrix(@NotNull Matrix matrix) {
        return Matrix.fromArray(matrix.content);
    }

    public static Matrix empty(int rows, int cols) {
        return new Matrix(new int[rows][cols]);
    }

    private Matrix(int[][] content) {
        this.content = content;
    }

    public int get(int row, int column) {
        return content[row][column];
    }

    public void set(int row, int column, int value) {
        content[row][column] = value;
    }

    public int getRowCount() {
        return content.length;
    }

    public int getColumnCount() {
        return content[0].length;
    }

    // vector
    public int[] getRow(int index) {
        return content[index];
    }

    @Override
    public int hashCode() {
        // TODO: HC, EQ, TS
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix)) {
            return false;
        }
        Matrix thatObject = (Matrix) obj;
        return Arrays.deepEquals(content, thatObject.content);
    }

    @Override
    public String toString() {
        return Arrays.deepToString(content);
    }
}
