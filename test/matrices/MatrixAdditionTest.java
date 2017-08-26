package matrices;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Tests matrix addition. */
public class MatrixAdditionTest {

    /* Testing strategy:
     *      squared matrices
     *      not squared matrices
     *      single element matrices
     *      empty matrices (= 0)
     */

    // covers not squared matrices
    @Test
    public void testSum() {
        final Matrix matrA = Matrix.fromArray(new int[][] {{1, 2}, {3, 4}, {5, 6}});
        final Matrix matrB = Matrix.fromArray(new int[][] {{5, 6}, {4, 1}, {2, 3}});
        final Matrix expectedResult = Matrix.fromArray(new int[][] {{6, 8}, {7, 5}, {7, 9}});
        assertEquals(expectedResult, MatrixAddition.sum(matrA, matrB));
    }

    // covers squared matrices
    @Test
    public void testSumSquared() {
        final Matrix matrA = Matrix.fromArray(new int[][] {{1, 2}, {3, 4}});
        final Matrix matrB = Matrix.fromArray(new int[][] {{2, 3}, {4, 1}});
        final Matrix expectedResult = Matrix.fromArray(new int[][] {{3, 5}, {7, 5}});
        assertEquals(expectedResult, MatrixAddition.sum(matrA, matrB));
    }

    // covers single element matrices
    @Test
    public void testSumSingleElement() {
        final Matrix matrA = Matrix.fromArray(new int[][] {{1}});
        final Matrix matrB = Matrix.fromArray(new int[][] {{2}});
        final Matrix expectedResult = Matrix.fromArray(new int[][] {{3}});
        assertEquals(expectedResult, MatrixAddition.sum(matrA, matrB));
    }

    // covers empty matrices (= 0)
    @Test
    public void testSumEmpty() {
        final Matrix matrA = Matrix.empty(1,1);
        final Matrix matrB = Matrix.empty(1,1);
        assertEquals(matrA, MatrixAddition.sum(matrA, matrB));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEqualDimensions() {
        final Matrix matrA = Matrix.fromArray(new int[][] {{2, 3}, {4, 1}});
        final Matrix matrB = Matrix.fromArray(new int[][] {{1}, {4}});
        assertEquals(null, MatrixAddition.sum(matrA, matrB));
    }

}
