# Counting sort
```
Time: O(n + k)
Memory: Θ(n + k)
not-in-place
stable
```
Integer sorting algorithm used for sorting keys `a_1 .. a_n`, where `a_i` is integer within range `k e.g. [0..k]` and `k` is relatively small.

There are two variations of algorithm:
- If the problem is to sort integers only (without associated data), we can count each key in input using counters array of size `k` and then generate the result, printing each element of `k` counted times.
- Often there are some data associated with keys so we can't generate result and forced to sort data with keys.
  1. Let's create `c` - array size `k`, where each element is a counter for each possible key value.
  At first, `c[i] = 0`.
  2. Then iterate through the input array and increment associated counters `for i = 1..n c[a_i]++`.
  3. Result array `res` is an array of size `n` splitted into segments `S[1]..S[k-1]`, where `|S[i]| = c[i]`. For filling the result we also need an array of iterators `b` size `k`, where each element points to current position where element `a[i]` must be placed in `res` (at first, each iterator points to the beginning of the corresponding segment). `b[i] = sum[i<j] c[j]`. We can calculate `b` above `c` in one pass.
  4. Having `b` and `res` we can iterate through input and put each `a[i]` in appropriate segment of `res`:
  `res[ b[ a[i] ] ] = a[i]; ++b[a_i]`.

This algorithm is stable and not-in-place.

## Complexity
Algorithm has time complexity of `O(n + k)`, where `k` is range of input keys.

Algorithm uses `O(n + k)` additional memory because it is not-in-place (`n`) and needs an array length `k` for counters.

## Implementation
Used for sorting array of keys `a`, where `a_i in 0..k`
```java
public static void countingSort(int[] input, int k) {
      int[] result = new int[input.length];
      int[] counters = new int[k];
      for (int key : input) {
          counters[key]++; // count all keys
      }
      // make iterators from counters ie calculate positions
      for (int i = 0, pos = 0; i < k; i++) {
        int tmp = counters[i];
        counters[i] = pos;
        pos += tmp;
      }
      for (int key : input) {
        result[counters[key]] = key; // put keys in order
        counters[key]++; // increment iterators
      }
      return result;
  }
}
```

## Improved
Used for sorting any integer keys in specified range, where keys are any column in 2d array (used in Radix Sort).

[Implementation](../src/sortings/CountingSort.java)

[Testing class](../test/sortings/CountingSortTest.java)
