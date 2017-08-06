package matrices;

import com.sun.istack.internal.NotNull;

// also can be a vector;; modifiable
public class Matrix {

    private final int[][] content;

    public static Matrix fromArray(@NotNull int[][] bidemArray) {
        throw new RuntimeException("not implemented");
    }

    public static Matrix fromMatrix(@NotNull Matrix matrix) {
        throw new RuntimeException("not implemented");
    }

    public static Matrix empty(int rows, int columns) {
        throw new RuntimeException("not implemented");
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
        throw new RuntimeException("not implemented");
    }

    public int getColumnCount() {
        throw new RuntimeException("not implemented");
    }

    // vector
    public int[] getColumn(int index) {
        throw new RuntimeException("not implemented");
    }

    // vector
    public int[] getRow(int index) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
