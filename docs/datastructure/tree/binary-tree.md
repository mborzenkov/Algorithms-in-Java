# Binary Tree
Binary tree is a tree data structure in which each node has at most two children (left and right).

## Operations
Various types of insertions and deletions e.g.
- `insertLeft(p, n)` - add node `n` as left to node `p` if `p` has no left child
- `remove(n)` - remove node `n` that has 0 or 1 child == change parent left (or right, depends where `n` is) to null or child of `n`
- ...
Observers:
- `getLeft(p) -> n, v` - return left child of node p, that could be node, value (leaf) or null
- `getRight(p) -> n, v` - same as left for right child

Complexity of insertions and deletions depends on implementation. For observers usually O(1).

## Implementation
Simple immutable binary tree that can be created and observed only recursively. Each node has exactly 2 children. Each leaf contain value.

[Implementation](/src/datastructure/tree/BinaryTree.java)

[Testing class](/test/datastructure/tree/BinaryTreeTest.java)
