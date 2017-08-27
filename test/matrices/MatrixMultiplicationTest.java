package matrices;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Tests MatrixMultiplication class by multiplying matrices and checking results. */
public class MatrixMultiplicationTest {

    /* Testing strategy:
     *      Multiplying matrix (m x n) by zero matrix
     *      Multiplying square matrices
     *      Multiplying non-square matrices
     *
     *      A(B+C) = AB+AC
     *      A(BC) = (AB)C
     *      A (m x n) * 0
     *
     */

    private static final Matrix ZERO_MATRIX = new Matrix.Builder(2, 2).build();

    private static final Matrix SQUARE_MATRIX = new Matrix.Builder(2, 2)
            .set(0,0, 1).set(0,1,2)
            .set(1,0,1).set(1,1,-1).build();
    // SQUARE (2, 2) * SQUARE (2, 2) => ANSWER (2, 2)
    private static final Matrix SQUARE_MATRIX_ANSWER = new Matrix.Builder(2, 2)
            .set(0,0, 3).set(0,1,0)
            .set(1,0,0).set(1,1,3).build();

    private static final Matrix SQUARE2_MATRIX_2 = Matrix.fromArray(new int[][] {
            new int[] {  1,  0,  0,  1,  2,  2,  2,  1},
            new int[] {  2,  1,  2,  2,  0,  1, -1, -1},
            new int[] {  1,  1,  1,  1,  0,  2,  1,  0},
            new int[] {  0,  2, -1,  1,  2, -1,  0,  1},
            new int[] { -1, -1,  2,  2,  1, -1,  1, -1},
            new int[] {  0,  0, -1, -1,  0,  0,  0,  0},
            new int[] { -1, -1,  2,  2,  0,  0, -1,  0},
            new int[] {  0,  1,  2,  2,  1,  2,  0, -1}
    });

    // SQUARE_2 (8, 8) * SQUARE_2 (8, 8) => ANSWER (8, 8)
    private static final Matrix SQUARE_MATRIX_ANSWER_2 = Matrix.fromArray(new int[][] {
            new int[] { -3, -1,  7, 10,  7,  1,  2, -1},
            new int[] {  7,  7, -3,  3,  7,  5,  6,  4},
            new int[] {  3,  3,  2,  5,  4,  4,  1,  1},
            new int[] {  1,  2,  9, 11,  5, -1, -1, -4},
            new int[] { -3,  2,  1,  4,  2, -4,  1,  2},
            new int[] { -1, -3,  0, -2, -2, -1, -1, -1},
            new int[] {  0,  6, -4, -1,  2, -1,  2,  2},
            new int[] {  3,  5,  0,  4,  4,  0,  2,  1}
    });

    private static final Matrix NON_SQUARE_MATRIX = new Matrix.Builder(2, 1)
            .set(0,0, 2)
            .set(1,0,2).build();
    // SQUARE (2, 2) * NON_SQUARE (2, 1) => ANSWER (2, 1)
    private static final Matrix NON_SQUARE_MATRIX_ANSWER = new Matrix.Builder(2, 1)
            .set(0,0, 6)
            .set(1,0,0).build();

    @Test
    public void testBasicMultiplication() {
        assertEquals(ZERO_MATRIX, MatrixMultiplication.mult(ZERO_MATRIX, SQUARE_MATRIX));
        assertEquals(ZERO_MATRIX, MatrixMultiplication.mult(SQUARE_MATRIX, ZERO_MATRIX));
        assertEquals(SQUARE_MATRIX_ANSWER, MatrixMultiplication.mult(SQUARE_MATRIX, SQUARE_MATRIX));
        assertEquals(NON_SQUARE_MATRIX_ANSWER, MatrixMultiplication.mult(SQUARE_MATRIX, NON_SQUARE_MATRIX));
        assertEquals(SQUARE_MATRIX_ANSWER_2, MatrixMultiplication.mult(SQUARE2_MATRIX_2, SQUARE2_MATRIX_2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncompatibleBasicMultiplication() {
        assertEquals(null,
                MatrixMultiplication.mult(new Matrix.Builder(1, 1).build(), SQUARE_MATRIX));
    }

    @Test
    public void testRecursiveMultiplication() {
        assertEquals(ZERO_MATRIX, MatrixMultiplication.multRecursiveSquaredPow2(ZERO_MATRIX, SQUARE_MATRIX));
        assertEquals(ZERO_MATRIX, MatrixMultiplication.multRecursiveSquaredPow2(SQUARE_MATRIX, ZERO_MATRIX));
        assertEquals(SQUARE_MATRIX_ANSWER, MatrixMultiplication.multRecursiveSquaredPow2(SQUARE_MATRIX, SQUARE_MATRIX));
        assertEquals(SQUARE_MATRIX_ANSWER_2,
                MatrixMultiplication.multRecursiveSquaredPow2(SQUARE2_MATRIX_2, SQUARE2_MATRIX_2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncompatibleRecursiveMultiplication() {
        assertEquals(null,
                MatrixMultiplication.multRecursiveSquaredPow2(
                        new Matrix.Builder(1, 1).build(), SQUARE_MATRIX));
    }

    @Test
    public void testStrassenMultiplication() {
        assertEquals(ZERO_MATRIX, MatrixMultiplication.multStrassenSquaredPow2(ZERO_MATRIX, SQUARE_MATRIX));
        assertEquals(ZERO_MATRIX, MatrixMultiplication.multStrassenSquaredPow2(SQUARE_MATRIX, ZERO_MATRIX));
        assertEquals(SQUARE_MATRIX_ANSWER, MatrixMultiplication.multStrassenSquaredPow2(SQUARE_MATRIX, SQUARE_MATRIX));
        assertEquals(SQUARE_MATRIX_ANSWER_2,
                MatrixMultiplication.multStrassenSquaredPow2(SQUARE2_MATRIX_2, SQUARE2_MATRIX_2));
    }

}
