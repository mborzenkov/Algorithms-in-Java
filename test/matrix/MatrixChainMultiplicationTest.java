package matrix;

import static org.junit.Assert.assertEquals;

import datastructure.tree.BinaryTree;
import org.junit.Test;

/** Tests MatrixChainMultiplication class. */
@SuppressWarnings("CheckStyle")
public class MatrixChainMultiplicationTest {

    // TODO: Improve testing strategy, add more tests

    // [n * m] * [m * p] => [n * p]

    /* Testing strategy:
     *      direct order (AB)C
     *      indirect order A(BC)
     *      single matrix
     */

    // covers: direct order (AB)C
    @Test
    public void testDirectOrder() {
        // A [3 * 5], B [5 * 3], C [3 * 5]
        // (AB)C ~= 90
        // A(BC) ~= 150
        final Matrix matrA = new Matrix.Builder(3, 5).build();
        final Matrix matrB = new Matrix.Builder(5, 3).build();
        final Matrix matrC = new Matrix.Builder(3, 5).build();
        final BinaryTree<Matrix> result = MatrixChainMultiplication.pickOptimalMultiplicationChain(
                new Matrix[] {matrA, matrB, matrC});
        assertEquals(matrA, result.getLeft().getLeft().getValue());
        assertEquals(matrB, result.getLeft().getRight().getValue());
        assertEquals(matrC, result.getRight().getValue());
    }

    // covers: indirect order A(BC)
    @Test
    public void testIndirectOrderChain() {
        // A [5 * 3], B [3 * 5], C [5 * 5]
        // (AB)C ~= 200
        // A(BC) ~= 150
        final Matrix matrA = new Matrix.Builder(5, 3).build();
        final Matrix matrB = new Matrix.Builder(3, 5).build();
        final Matrix matrC = new Matrix.Builder(5, 5).build();
        final BinaryTree<Matrix> result = MatrixChainMultiplication.pickOptimalMultiplicationChain(
                new Matrix[] {matrA, matrB, matrC});
        assertEquals(matrA, result.getLeft().getValue());
        assertEquals(matrB, result.getRight().getLeft().getValue());
        assertEquals(matrC, result.getRight().getRight().getValue());
    }

    // covers: single matrix
    @Test
    public void testSingleMatrix() {
        final Matrix matrA = new Matrix.Builder(5, 3).build();
        final BinaryTree<Matrix> result = MatrixChainMultiplication.pickOptimalMultiplicationChain(
                new Matrix[] {matrA});
        assertEquals(matrA, result.getValue());
    }


    // covers: exceptions..

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMatrix() {
        final BinaryTree<Matrix> result = MatrixChainMultiplication.pickOptimalMultiplicationChain(new Matrix[0]);
        assertEquals(null, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotConsistentMatrices() {
        final Matrix matrA = new Matrix.Builder(5, 5).build();
        final Matrix matrB = new Matrix.Builder(3, 5).build();
        final Matrix matrC = new Matrix.Builder(5, 5).build();
        final BinaryTree<Matrix> result = MatrixChainMultiplication.pickOptimalMultiplicationChain(
                new Matrix[] {matrA, matrB, matrC});
        assertEquals(null, result);
    }


}
