# Matrix multiplication
```
Time: O(n^3) .. O(n^2.81)
Memory: O(n)
```
Matrices `A` and `B` can be multiplied if they are consistent e.g. number of columns in `A` equals number of rows in `B`.
Result of multiplying matrix `A (m * n)` and matrix `B (n * p)` is matrix `C = AB (m * p)`. For each element in `C`: `C[i][j] = sum(k=1..n) a[i][j] * b[k][j]`.

## Simple multiplication
```
Square-Matrix-Multiply(A, B)
    n = A.rows
    C = new matrix n * n
    for i = 1 to n
        for j = 1 to n
            c[i][j] = 0
            for k = 1 to n
                c[i][j] = c[i][j] + a[i][k] * b[k][j]
    return C
```

## Recursive multiplication
Recursive multiplication of square matrices `n * n` where `n` is power of 2 (this algorithm can be improved for multiplication of any matrices, not only squared and pow2).
```
Square-Matrix-Multiply-Recursive(A, B)
    n = A.rows
    C = new matrix n * n
    if n == 1
        c[1][1] = a[1][1] * b[1][1]
    else // split A, B, C
        C11 = Square-Matrix-Multiply-Recursive(A11, B11)
            + Square-Matrix-Multiply-Recursive(A12, B21)
        C12 = Square-Matrix-Multiply-Recursive(A11, B12)
            + Square-Matrix-Multiply-Recursive(A12, B22)
        C21 = Square-Matrix-Multiply-Recursive(A21, B11)
            + Square-Matrix-Multiply-Recursive(A22, B21)
        C22 = Square-Matrix-Multiply-Recursive(A21, B12)
            + Square-Matrix-Multiply-Recursive(A22, B22)
    return C
```

## Strassen's algorithm
More effective algorithm for matrix multiplication that save 1 multiplication and thus have complexity of `O(n^2.81)` instead of `O(n^3)`.
Algorithm has 4 steps:
1. Divide matrices `A`, `B` and output matrix `C` on submatrices size `n/2 * n/2` (no actual dividing needed, use indexes).
2. Create 10 matrices `S1, S2, ... , S10` size `n/2 * n/2` each.
`S1=B12-B22 ; S2=A11-A12 ; S3=A21+A22 ; S4=B21-B11 ; S5=A11+A22`
`S6=B11+B22 ; S7=A12-A22 ; S8=A21+B22 ; S9=A11-A21 ; S10=B11-B12`
3. Use submatrices from step 1 and 10 matrices from step 2 for recursively computing 7 matrix multiplications `P1, P2, ... , P7` size `n/2 * n/2` each.
`P1=A11S1 ; P2=S2B22 ; P3=S3B11 ; P4=S4A22 P5=S5S6 ; P6=S7S8 ; P7=S9S10`
4. Compute supmatrices `C11, C12, C21, C22` forming matrix `C`.
`C11=P5+P4-P2+P6 ; C12=P1+P2 ; C21=P3+P4 ; C22=P5+P1-P3-P7`

## Chain multiplication
Matrix chain multiplication is optimization problem of finding the most efficient way to multiply matrices `A1 A2 .. An`. Algorithm for finding optimal chain given in the `MatrixChainMultiplication` package. Optimal chain is presented as binary tree.
Chain multiplication algorithm is multiplying matrices `A1 A2 .. An` in given order: for node computes `leftChild * rightChild`.

## Complexity
- Basic multiplication algorithm have time complexity `O(mnp)` for multiplication of matrices `A (m * n) * B (n * p)`.
- Strassen's algorithm have time complexity `O(n^2.81)` for multiplication of matrices `A (n * n) * B (n * n)`.
- Both algorithms are not-in-place and requires `O(n)` additional memory for storing the result.

## Implementation
Implementation consist of 3 algorithms: basic, recursive (only for squared matrices which dimensions are power of 2) and strassen (same constraints as for recursive).

[Implementation](/src/matrices/MatrixMultiplication.java)

[Testing class](/test/matrices/MatrixMultiplicationTest.java)
