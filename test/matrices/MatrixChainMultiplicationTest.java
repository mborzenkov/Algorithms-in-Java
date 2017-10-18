package matrices;

import static org.junit.Assert.assertEquals;

import datastructures.trees.BinaryTree;
import org.junit.Test;

/** Tests MatrixChainMultiplication class. */
public class MatrixChainMultiplicationTest {

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
    public void testEmptyMartix() {
        final BinaryTree<Matrix> result = MatrixChainMultiplication.pickOptimalMultiplicationChain(new Matrix[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotConsistentMartices() {
        final Matrix matrA = new Matrix.Builder(5, 5).build();
        final Matrix matrB = new Matrix.Builder(3, 5).build();
        final Matrix matrC = new Matrix.Builder(5, 5).build();
        final BinaryTree<Matrix> result = MatrixChainMultiplication.pickOptimalMultiplicationChain(
                new Matrix[] {matrA, matrB, matrC});
        assertEquals(null, result);
    }


}
