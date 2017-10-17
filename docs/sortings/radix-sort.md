# Radix sort
```
Time: O(nd + kd)
Memory: Θ(nd + k)
not-in-place
stable
```
Integer sorting algorithm used for sorting keys `a_1 .. a_n`, where `a_i` is sequence length `d` of integers within range `k e.g. [0..k]` and (`k`, `d`) are relatively small.
Input length is `n * d`.

Radix sort is often used for sorting strings (because string is sequence of chars, where char is integer within small range ie char code). Therefore, we consider a variation of the algorithm sorting strings.

Algorithm is simple:
* Call CountingSort for each i from d-1 to 0 (from last chars to first).

It is important to sort from the low-order to high-order and radix sort is correct if counting sort is stable.

This algorithm is stable and not-in-place.

## Complexity
Algorithm has time complexity of `O(nd + kd)`, where `d` is maximum `a[i]` length and `k` is range of `a[i]`. Because we perform `d` counting sorts, each has time complexity of `O(n + k)`.

Algorithm uses `O(nd + k)` additional memory because it is not-in-place (`nd`) and needs an array length `k` for counters in counting sort.

## Implementation
Used for sorting array of small char strings, represented as 2d array of ints `n * d`, where `input_i length in 1..d` and `input_i,j in 0..k`.
```java
public static void radixSort(int[][] input, int k, int d) {
  for (int i = stringsSize - 1; i >= 0; i--) {
    CountingSort.sort(input, 0, k + 1, i); // each char == char+1 and 0 == empty
  }
}
```

[Implementation](src/sortings/RadixSort.java)

[Testing class](test/sortings/RadixSortTest.java)
