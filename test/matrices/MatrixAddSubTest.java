package matrices;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Tests matrix addition and subtraction. */
public class MatrixAddSubTest {

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
        assertEquals(expectedResult, MatrixAddSub.add(matrA, matrB));
    }

    // covers not squared matrices
    @Test
    public void testSubtract() {
        final Matrix matrA = Matrix.fromArray(new int[][] {{1, 2}, {3, 4}, {5, 6}});
        final Matrix matrB = Matrix.fromArray(new int[][] {{5, 6}, {4, 1}, {2, 3}});
        final Matrix expectedResult = Matrix.fromArray(new int[][] {{-4, -4}, {-1, 3}, {3, 3}});
        assertEquals(expectedResult, MatrixAddSub.sub(matrA, matrB));
    }

    // covers squared matrices
    @Test
    public void testSumSquared() {
        final Matrix matrA = Matrix.fromArray(new int[][] {{1, 2}, {3, 4}});
        final Matrix matrB = Matrix.fromArray(new int[][] {{2, 3}, {4, 1}});
        final Matrix expectedResult = Matrix.fromArray(new int[][] {{3, 5}, {7, 5}});
        assertEquals(expectedResult, MatrixAddSub.add(matrA, matrB));
    }

    // covers squared matrices
    @Test
    public void testSubtractSquared() {
        final Matrix matrA = Matrix.fromArray(new int[][] {{1, 2}, {3, 4}});
        final Matrix matrB = Matrix.fromArray(new int[][] {{2, 3}, {4, 1}});
        final Matrix expectedResult = Matrix.fromArray(new int[][] {{-1, -1}, {-1, 3}});
        assertEquals(expectedResult, MatrixAddSub.sub(matrA, matrB));
    }

    // covers single element matrices
    @Test
    public void testSumSingleElement() {
        final Matrix matrA = Matrix.fromArray(new int[][] {{1}});
        final Matrix matrB = Matrix.fromArray(new int[][] {{2}});
        final Matrix expectedResult = Matrix.fromArray(new int[][] {{3}});
        assertEquals(expectedResult, MatrixAddSub.add(matrA, matrB));
    }

    // covers single element matrices
    @Test
    public void testSubtractSingleElement() {
        final Matrix matrA = Matrix.fromArray(new int[][] {{1}});
        final Matrix matrB = Matrix.fromArray(new int[][] {{2}});
        final Matrix expectedResult = Matrix.fromArray(new int[][] {{-1}});
        assertEquals(expectedResult, MatrixAddSub.sub(matrA, matrB));
    }

    // covers empty matrices (= 0)
    @Test
    public void testSumEmpty() {
        final Matrix matrA = new Matrix.Builder(1, 1).build();
        final Matrix matrB = new Matrix.Builder(1, 1).build();
        assertEquals(matrA, MatrixAddSub.add(matrA, matrB));
    }

    // covers empty matrices (= 0)
    @Test
    public void testSubtractEmpty() {
        final Matrix matrA = new Matrix.Builder(1, 1).build();
        final Matrix matrB = new Matrix.Builder(1, 1).build();
        assertEquals(matrA, MatrixAddSub.sub(matrA, matrB));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSumNotEqualDimensions() {
        final Matrix matrA = Matrix.fromArray(new int[][] {{2, 3}, {4, 1}});
        final Matrix matrB = Matrix.fromArray(new int[][] {{1}, {4}});
        assertEquals(null, MatrixAddSub.add(matrA, matrB));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtractNotEqualDimensions() {
        final Matrix matrA = Matrix.fromArray(new int[][] {{2, 3}, {4, 1}});
        final Matrix matrB = Matrix.fromArray(new int[][] {{1}, {4}});
        assertEquals(null, MatrixAddSub.sub(matrA, matrB));
    }

}
