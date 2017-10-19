package datastructure.tree;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.Objects;

/** Immutable object representing binary tree - data structure that contain value (leaf)
 * or binary trees on left and right (node).
 * Immutability does not apply to value if value is mutable data type. This ADT ensure that stored link to value
 * is never changed, but value content can be changed outside.
 *
 * @param <T> type of values in BinaryTree
 */
public class BinaryTree<T> {

    // Rep invariant:
    //      BinaryTree can be node or leaf. Each node contain exactly 2 nodes otherwise it is leaf and contain value.
    //      left is binary tree on the left if this tree is node or null if this tree is leaf
    //      right is binary tree on the right if this tree is node or null if this tree is leaf
    //      value is any not null data if this tree is leaf or null if this tree is node
    //
    // Abstraction function:
    //      represents binary tree - tree data structure of nodes and leaves
    //
    // Safety from rep exposure:
    //      this ADT is immutable and has no mutators
    //      left and right are immutable binary trees
    //      value could be mutable by design and it does not affect this ADT
    //
    // Thread safety argument:
    //      this ADT is thread safe as long as T is thread safe (because only value could be mutable)

    /** Left part of tree or null if this is leaf. */
    private final @Nullable BinaryTree<T> left;
    /** Right part of tree or null if this is leaf. */
    private final @Nullable BinaryTree<T> right;
    /** Value or null if this is node. */
    private final @Nullable T value;

    /** Creates new leaf.
     *
     * @param value value of the leaf, not null
     *
     * @throws IllegalArgumentException if value == null
     */
    public BinaryTree(@NotNull T value) {
        if (value == null) {
            throw new IllegalArgumentException("Error @ new BinaryTree(T value) :: value is null");
        }
        this.left = null;
        this.right = null;
        this.value = value;
    }

    /** Creates new node with 2 leaves.
     *
     * @param value1 value of left leaf, not null
     * @param value2 value of right leaf, not null
     *
     @throws IllegalArgumentException if value1 or value2 == null
     */
    public BinaryTree(@NotNull T value1, @NotNull T value2) {
        if (value1 == null || value2 == null) {
            throw new IllegalArgumentException("Error @ new BinaryTree(T arg1, T arg2) :: some arguments == null");
        }
        this.left = new BinaryTree<>(value1);
        this.right = new BinaryTree<>(value2);
        this.value = null;
    }

    /** Creates new node with trees on left and right.
     *
     * @param leftTree left tree, not null
     * @param rightTree right tree, not null
     */
    public BinaryTree(@NotNull BinaryTree<T> leftTree, @NotNull BinaryTree<T> rightTree) {
        if (leftTree == null || rightTree == null) {
            throw new IllegalArgumentException(
                    "Error @ new BinaryTree(BinaryTree<T> arg1, BinaryTree<T> arg2) :: some arguments == null");
        }
        this.left = leftTree;
        this.right = rightTree;
        this.value = null;
    }

    /** Return left tree.
     * If getLeft() == null then getRight() == null && getValue() != null.
     * If getLeft() != null then getRight() != null && getValue() == null.
     *
     * @return left tree if this tree is node or null if this tree is leaf
     */
    public @Nullable BinaryTree<T> getLeft() {
        return left;
    }

    /** Return right tree.
     * If getRight() == null then getLeft() == null && getValue() != null.
     * If getRight() != null then getLeft() != null && getValue() == null.
     *
     * @return right tree if this tree is node or null if this tree is leaf
     */
    public @Nullable BinaryTree<T> getRight() {
        return right;
    }

    /** Return value.
     * If getValue() == null then getLeft() != null && getRight() != null.
     * If getValue() != null then getLeft() == null && getRight() == null.
     *
     * @return value if this tree is leaf or null if this tree is node
     */
    public @Nullable T getValue() {
        return value;
    }

    /** Check if two objects are equal.
     * This implementation is recursive and have time complexity of O(n * m), where n is number of nodes and leaves
     * in tree and m is time complexity of value equals.
     *
     * @param otherObject object to compare with
     *
     * @return true if this equal thatObject: if they are both leaves than values are equal,
     *      else lefts and rights are equal
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }
        BinaryTree<?> that = (BinaryTree<?>) otherObject;
        if (value != null) {
            return value.equals(that.value);
        } else {
            return left != null && right != null && left.equals(that.left) && right.equals(that.right);
        }
    }

    /** Return hashcode of binary tree, based on its content. */
    @Override
    public int hashCode() {
        return Objects.hash(left, right, value);
    }

    /** Returns human readable representation of binary tree.
     *  This implementation is recursive and have time complexity of O(n * m), where n is number of nodes and leaves
     * in tree and m is time complexity of value toString.
     *
     * @return value.toString() for each leaf, "[left.toString, right.toString]" for each node
     */
    @Override
    public String toString() {
        if (value != null) {
            return value.toString();
        }
        if (left != null && right != null) {
            return String.format("[%s, %s]", left.toString(), right.toString());
        } else {
            return "";
        }
    }
}
