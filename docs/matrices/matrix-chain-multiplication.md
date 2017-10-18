# Matrix chain multiplication
```
Time: O(n^3)
Memory: O(n^2)
```
Matrix chain multiplication is optimization problem of finding the most efficient way to multiply matrices `A1 A2 .. An`, where `A[i]` is matrix `p[i] * r[i]` and `r[i] = p[i + 1]` for `i = 1 .. n-1`. All matrices in input are consistent (can be multiplied sequentially).

Matrix multiplication is associative `A(BC) = (AB)C`. However, the order of multiplications affects the number of simple arithmetic operations needed to compute the product, or the efficiency.

[Read more @ Wikipedia](https://en.wikipedia.org/wiki/Matrix_chain_multiplication)

Multiplication of 2 matrices size `p * q` and `q * r` requires approximately `p * q * r` operations (can be changed to any other formula).

Algorithm is relatively simple:
- `MinMult(i, j)` - finds minimum number of operations for multiplying matrices from `A[i]` to `A[j]`.
- Start from `f(0, |A|)` and compute all minimums in `0..|A|` recursively:
for every `k` from `i to j - 1` compute `res = f(i, k) + f(k + 1, j) + p[i] * r[k] * q[j]`.
- Remember the best result and return it at the end.

This algorithm have time complexity `O(2^n)` due to repeatedly executing same operations. We can optimize it to `O(n^3)` using cache for minimums.
- Add 2d array `M (n * n)`, where `M[i][j]` is minimum number of operations for multiplying matrices from `A[i]` to `A[j]`.
- Compute `MinMult(i, j)` only if `M[i][j]` is not present.
- Save `M[i][j] = best` at the end of `MinMult(i, j)`.

```
MinMult(i, j)
  if i == j
    return 0
  if (M[i][j] != 0)
    return M[i][j]
  best = +inf
  for k from i to j - 1
    res = MinMult(i, k) + MinMult(k + 1, j) + p[i] * r[k] * r[j]
    best = min(res, best)
  M[i][j] = best;
  return best
```

If we need to get the actual optimal multiplication sequence and not only minimum number of operations:
- Add 2d array `S (n * n)`, where `S[i][j]` is index of last multiplication corresponding to best result stored in `M[i][j]` e.g. if `S[i][j] == k` then last multiplication was `S[i][k]` * `S[k + 1][j]`, if `i == j` then it is `A[i]`.
- Save last multiplication and minimum number of operations for best result together.

```
// Replace best = min(res, best) with
if (res < best)
  best = res
  S[i][j] = k;
```

## Complexity
Algorithm has time complexity of `O(n^3)`. There is also a faster and more complex algorithm.

Algorithm uses `O(n^2)` additional memory for building array of best results `M`.

## Implementation
Implemented algorithm returns binary tree corresponding to optimal multiplication sequence. Multiplying can be done with special recursive function in `MatrixMultiplication` package.

Implementation is very similar to the pseudocode above, except that instead of returning numbers it returns pairs of minimum number of multiplication from `i` to `j` and binary tree. Pairs are stored in 2d array similar to `M`.

[Implementation](/src/matrices/MatrixChainMultiplication.java)

[Testing class](/test/matrices/MatrixChainMultiplicationTest.java)
