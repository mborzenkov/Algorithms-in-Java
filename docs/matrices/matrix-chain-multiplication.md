# Matrix chain multiplication
```
Time: O(n^3)
Memory: O(n^2)
```

Задача умножения n матриц `A1 A2 .. An`, where `A[i]` is matrix `p[i] * r[i]` and `r[i] = p[i + 1]` for `i = 1 .. n-1`.
размеры согласуются


умножение матриц ассоциативно `A(BC) = (AB)C`
умножение требует `p * q * r` операций (с точностью до константы). Также есть более быстрые способы перемножения, но на алгоритм выбора порядка умножений это не влияет.

f(i, j) - находит наименьшее количество операций для перемножения матриц с i по j

оптимизация 2^n с помощью сохранения промежуточных результатов превращает в n^3

всего состояний O(n^2) пар T[i, j]

восстановление результата L[i][j] = k => L[i][k] * L[k+1][j]
if (res < best)
  best = res
  L[i][j] = k;

записать словами

```
MinMult(i, j)
  if i == j
    return 0
  if (T[i][j] != 0)
    return T[i][j]
  res = +inf
  for k from i to j - 1
    res = MinMult(i, k) + MinMult(k + 1, j) + p[i] * r[k] * r[j]
    best = min(res, best)
  T[i][j] = best;
  return best
```

## Complexity
Algorithm has time complexity of `O(n^3)`. There is also a faster and more complex algorithm.

Algorithm uses `O(n^2)` additional memory for building array of best results `T`. There is also a binary tree with result, that take `n` additional memory.


## Implementation
возвращает нужный порядок умножения матриц в виде бинарного дерева (записать, как производить умножение по бинарному дереву)

((((A1 * A2) * A3) * A4) * ((A5 * A6) * A7))
== [[[A1, A2], A3], [A4], [A5, A6, A7]]

```
           *
     *           *
   *   A3    A4, A5, A6
A1, A2
```


```java

```

[Implementation](../../src/matrices/MatrixChainMultiplication.java)

[Testing class](../../test/matrices/MatrixChainMultiplicationTest.java)
