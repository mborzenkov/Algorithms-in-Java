# Matrix addition and substraction
```
Time: O(n)
Memory: O(1)
```
Two matrices must have an equal number of rows and columns to be added (subtracted). The sum (sub) of two matrices `A` and `B` will be a matrix `C` which has the same number of rows and columns as do `A` and `B`. The sum (sub) `A + B` is computed by adding (subtracting) corresponding elements of `A` and `B`:
- sum `C[i][j] = A[i][j] + B[i][j]`
- sub `C[i][j] = A[i][j] - B[i][j]`

Properties:
- Commutative: `A + B = B + A`
- Associative: `(A + B) + C = A + (B + C)`
- Adding to zero matrix: `A + 0 = A`
- Existence of opposite matrix: `A + (-A) = 0`

## Complexity
Algorithm has time complexity of `O(n)`, where `n` is number of elements in `A` (or `B`, because they have the same number of elements).
Result can be produced in-place with constant additional memory `O(1)`.

## Implementation
Not-in-place implementation of addition and subtraction.

[Implementation](/src/matrix/MatrixAddSub.java)

[Testing class](/test/matrix/MatrixAddSubTest.java)
