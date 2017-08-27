package matrices;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Tests Matrix class. */
public class MatrixTest {

    /* Testing strategy:
     *      empty from Builder: (1 x 1), (n x m)
     *      filled manually from Builder: (1 x 1), (n x m), negative values
     *      from rectangular array: (1 x 1), (n x m), negative values
     *      equality & hashcode: empty matrices, filled manually, from arrays
     *      toString: contain values at least in correct order
     */

    // covers empty from Builder: (1 x 1), (n x m)
    @Test
    public void testEmpty() {
        Matrix matrix = new Matrix.Builder(1, 1).build();
        assertEquals(1, matrix.getRowCount());
        assertEquals(1, matrix.getColumnCount());
        assertEquals(0, matrix.get(0, 0));
        int[] row = matrix.getRow(0);
        assertEquals(1, row.length);
        assertEquals(0, row[0]);

        matrix = new Matrix.Builder(2, 2).build();
        assertEquals(2, matrix.getRowCount());
        assertEquals(2, matrix.getColumnCount());
        assertEquals(0, matrix.get(0, 0));
        assertEquals(0, matrix.get(0, 1));
        assertEquals(0, matrix.get(1, 0));
        assertEquals(0, matrix.get(1, 1));
        row = matrix.getRow(0);
        assertEquals(2, row.length);
        assertEquals(0, row[0]);
        assertEquals(0, row[1]);
        row = matrix.getRow(1);
        assertEquals(2, row.length);
        assertEquals(0, row[0]);
        assertEquals(0, row[1]);
    }

    // covers filled manually from Builder: (1 x 1), (n x m), negative values
    @Test
    public void testFilledManually() {
        Matrix matrix = new Matrix.Builder(1, 1).set(0,0, 2).build();
        assertEquals(1, matrix.getRowCount());
        assertEquals(1, matrix.getColumnCount());
        assertEquals(2, matrix.get(0, 0));
        int[] row = matrix.getRow(0);
        assertEquals(1, row.length);
        assertEquals(2, row[0]);

        matrix = new Matrix.Builder(2, 1)
                .set(0, 0, 2).set(1,0, -1).build();
        assertEquals(2, matrix.getRowCount());
        assertEquals(1, matrix.getColumnCount());
        assertEquals(2, matrix.get(0, 0));
        assertEquals(-1, matrix.get(1, 0));
        row = matrix.getRow(0);
        assertEquals(1, row.length);
        assertEquals(2, row[0]);
        row = matrix.getRow(1);
        assertEquals(1, row.length);
        assertEquals(-1, row[0]);
    }

    // covers from rectangular array: (1 x 1), (n x m), negative values
    @Test
    public void testFromRectangularArray() {
        int[][] content = new int[1][1];
        content[0][0] = 2;
        Matrix matrix = Matrix.fromArray(content);
        assertEquals(1, matrix.getRowCount());
        assertEquals(1, matrix.getColumnCount());
        assertEquals(2, matrix.get(0, 0));
        int[] row = matrix.getRow(0);
        assertEquals(1, row.length);
        assertEquals(2, row[0]);

        content = new int[2][1];
        content[0][0] = 2;
        content[1][0] = -1;
        matrix = Matrix.fromArray(content);
        assertEquals(2, matrix.getRowCount());
        assertEquals(1, matrix.getColumnCount());
        assertEquals(2, matrix.get(0, 0));
        assertEquals(-1, matrix.get(1, 0));
        row = matrix.getRow(0);
        assertEquals(1, row.length);
        assertEquals(2, row[0]);
        row = matrix.getRow(1);
        assertEquals(1, row.length);
        assertEquals(-1, row[0]);
    }

    // covers equality & hashcode: empty matrices, filled manually, from arrays
    @Test
    public void testEquality() {

        Matrix matrA = new Matrix.Builder(1, 2).build();
        Matrix matrB = new Matrix.Builder(1, 2).build();
        assertEquals(matrA, matrB);
        assertEquals(matrA.hashCode(), matrB.hashCode());
        matrA = new Matrix.Builder(1, 1).build();
        assertFalse(matrA.equals(matrB));

        matrA = new Matrix.Builder(1, 2)
                .set(0, 0, 1).set(0, 1, 2).build();
        matrB = new Matrix.Builder(1, 2)
                .set(0, 0, 1).set(0, 1, 2).build();
        assertEquals(matrA, matrB);
        assertEquals(matrA.hashCode(), matrB.hashCode());
        matrA = new Matrix.Builder(1, 2)
                .set(0, 0, 1).set(0, 1, 1).build();
        assertFalse(matrA.equals(matrB));

        int[][] contentA = new int[2][1];
        contentA[0][0] = 1;
        contentA[1][0] = -1;
        int[][] contentB = new int[2][1];
        contentB[0][0] = 1;
        contentB[1][0] = -1;

        matrA = Matrix.fromArray(contentA);
        matrB = Matrix.fromArray(contentB);
        assertEquals(matrA, matrB);
        assertEquals(matrA.hashCode(), matrB.hashCode());

        contentA[0][0] = -1;
        assertEquals(matrA, matrB);
        assertEquals(matrA.hashCode(), matrB.hashCode());

        matrA = Matrix.fromArray(contentA);
        assertFalse(matrA.equals(matrB));

    }

    @Test
    public void testToString() {
        String matrString = new Matrix.Builder(1, 2)
                .set(0, 0, 1).set(0, 1, 2).build().toString();
        System.out.println(matrString);
        assertTrue(matrString.indexOf('1') < matrString.indexOf('2'));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotRectangularFromArray() {
        int[][] content = new int[2][];
        content[0] = new int[1];
        content[1] = new int[2];
        Matrix matrA = Matrix.fromArray(content);
        assertEquals(null, matrA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroSizedBuilder() {
        Matrix matrA = new Matrix.Builder(1, 0).build();
        assertEquals(null, matrA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeBuilder() {
        Matrix matrA = new Matrix.Builder(-1, 1).build();
        assertEquals(null, matrA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFromArray() {
        Matrix matrA = Matrix.fromArray(new int[0][0]);
        assertEquals(null, matrA);
    }

}
