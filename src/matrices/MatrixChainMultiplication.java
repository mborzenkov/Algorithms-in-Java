package matrices;

import com.sun.istack.internal.NotNull;
import datastructures.trees.BinaryTree;

/** Static utility class for solving Matrix chain multiplication problem.
 * Matrix chain multiplication is optimization problem of finding the most efficient way to multiply matrices
 * `A1 A2 .. An`, where `A[i]` is matrix `p[i] * r[i]` and `r[i] = p[i + 1]` for `i = 1 .. n-1`.
 * All matrices in input are consistent (can be multiplied sequentially).
 */
public class MatrixChainMultiplication {

    /** Error for illegal argument meaning broken consistency of input. */
    private static final String ERROR_CONSISTENCY =
            "Error @ MatrixChainMultiplication.pickOptimalMultiplicationChain(A) :: "
            + "consistency is broken - A[%s] is [%s, %s] and A[%s] is [%s, %s]";

    /** Service data type for storing pairs of minimum number of operations and corresponding multiplication chain. */
    private static class Result {

        /** Minimum number of operations. */
        private final int minOperations;
        /** Multiplication chain. */
        private final BinaryTree<Matrix> multChain;

        /** Creates new instance of result. */
        private Result(int minOperations, BinaryTree<Matrix> multChain) {
            this.minOperations = minOperations;
            this.multChain = multChain;
        }

    }

    private MatrixChainMultiplication() {
        throw new UnsupportedOperationException(
                "Error @ new MatrixChainMultiplication() :: MatrixChainMultiplication is noninstantiable static class");
    }

    /** Computes the optimal chain for multiplying matrices in input.
     * Multiplying of result can be done with special function in `MatrixMultiplication` package.
     *
     * @param input nonsequence of matrices for multiplication, not null, not empty, all martices must be consistent
     *              (can be multiplied sequentially).
     *
     * @return binary tree corresponding to optimal multiplication sequence, for each node multiply left * right
     *
     * @throws IllegalArgumentException if input is not consistent or if input.length == 0
     * @throws NullPointerException if input is null
     */
    public static @NotNull BinaryTree<Matrix> pickOptimalMultiplicationChain(@NotNull Matrix[] input) {
        if (input.length == 0) {
            throw new IllegalArgumentException(
                    "Error @ MatrixChainMultiplication.pickOptimalMultiplicationChain(A) :: input is empty");
        }
        // Check consistency
        for (int i = 0; i + 1 < input.length; i++) {
            if (input[i].getColumnCount() != input[i + 1].getRowCount()) {
                throw new IllegalArgumentException(String.format(ERROR_CONSISTENCY,
                        i, input[i].getRowCount(), input[i].getColumnCount(),
                        i + 1, input[i + 1].getRowCount(), input[i + 1].getColumnCount()));
            }
        }
        // compute result
        return multChainRecursive(input, 0, input.length - 1, new Result[input.length][input.length + 1]).multChain;
    }

    /** Recursively computes the optimal chain for multiplying matrices [from..to] in input.
     * Returns cached result if exists.
     * Number of operations for multiplying 2 matrices p * q and q * r is computed as `p * q * r`.
     *
     * @param input sequence of matrices for multiplication, not null, all martices must be consistent
     *              (can be multiplied sequentially). consistency is not checked.
     * @param from starting index, 0 <= from <= to
     * @param to ending index, from <= to < input.length
     * @param cache 2d array of results where cache[i][j] is optimal result for multiplying matrices [i..j]
     *
     * @return optimal result containing minimum number of operations for multiplying matrices [from..to]
     *          and optimal multiplication chain
     */
    private static @NotNull Result multChainRecursive(@NotNull Matrix[] input, int from, int to,
                                                      @NotNull Result[][] cache) {
        if (cache[from][to] != null) {
            return cache[from][to]; // return from cache if exists
        } else if (from == to) {
            // single matrix, save to cache and return
            cache[from][to] = new Result(0, new BinaryTree<>(input[from]));
            return cache[from][to];
        }
        // computing result
        int result;
        Result bestResult = null;
        // result of multiplication p and r from matrices A[i] (p * q) and A[j] (q * r)
        int pirj = input[from].getRowCount() * input[to].getColumnCount();
        for (int k = from; k < to; k++) {
            final Result firstMultChain = multChainRecursive(input, from, k, cache);
            final Result secondMultChain = multChainRecursive(input, k + 1, to, cache);
            // result = f(i, k) + f(k + 1, j) + p[i] * r[k] * q[j]
            result = firstMultChain.minOperations + secondMultChain.minOperations + pirj * input[k].getColumnCount();
            if (bestResult == null || bestResult.minOperations > result) {
                // pick best result
                bestResult = new Result(result, new BinaryTree<>(firstMultChain.multChain, secondMultChain.multChain));
            }
        }
        // save cache
        cache[from][to] = bestResult;
        return bestResult;
    }

}
