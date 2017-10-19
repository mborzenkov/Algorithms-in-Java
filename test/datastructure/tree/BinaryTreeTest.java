package datastructure.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import datastructure.tree.BinaryTree;
import org.junit.Test;

/** Tests BinaryTree implementation in util. */
public class BinaryTreeTest {

    /* Testing strategy:
     *      binary tree height: 1 (leaf), 2 (node with 2 leafs), >2
     *      equality, hashcode
     *      string representation
     *      exceptions
     */

    // covers: binary tree height 1 (leaf)
    @Test
    public void testLeaf() {
        final Object value = new Object();
        final BinaryTree<Object> leaf = new BinaryTree<>(value);
        assertEquals(null, leaf.getLeft());
        assertEquals(null, leaf.getRight());
        assertTrue(value == leaf.getValue());
    }

    // covers: binary tree height 2 (node with 2 leafs)
    @Test
    public void testNodeTwoLeafs() {
        final Object value1 = new Object();
        final Object value2 = new Object();
        final BinaryTree<Object> node = new BinaryTree<>(value1, value2);
        assertEquals(null, node.getValue());
        final BinaryTree<Object> leftLeaf = node.getLeft();
        assertEquals(null, leftLeaf.getLeft());
        assertEquals(null, leftLeaf.getRight());
        assertTrue(value1 == leftLeaf.getValue());
        final BinaryTree<Object> rightLeaf = node.getRight();
        assertEquals(null, rightLeaf.getLeft());
        assertEquals(null, rightLeaf.getRight());
        assertTrue(value2 == rightLeaf.getValue());
    }

    // covers: binary tree height >2
    @Test
    public void testTree() {
        final Object value1 = new Object();
        final Object value2 = new Object();
        final Object value3 = new Object();
        final Object value4 = new Object();

        final BinaryTree<Object> node1 = new BinaryTree<>(value1, value2);
        final BinaryTree<Object> node2 = new BinaryTree<>(value3, value4);
        final BinaryTree<Object> node3 = new BinaryTree<>(node1, node2);

        assertEquals(null, node3.getValue());
        // node 1
        final BinaryTree<Object> leftLeaf = node3.getLeft();
        assertEquals(node1, leftLeaf);
        assertTrue(value1 == leftLeaf.getLeft().getValue());
        assertTrue(value2 == leftLeaf.getRight().getValue());
        // node 2
        final BinaryTree<Object> rightLeaf = node3.getRight();
        assertEquals(node2, rightLeaf);
        assertTrue(value3 == rightLeaf.getLeft().getValue());
        assertTrue(value4 == rightLeaf.getRight().getValue());
    }

    // covers: equality, hashcode
    @Test
    public void testEquality() {
        final Object value1 = new Object();
        final Object value2 = new Object();
        final Object value3 = new Object();

        // 2 leafs
        final BinaryTree<Object> leaf1 = new BinaryTree<>(value1);
        final BinaryTree<Object> leaf2 = new BinaryTree<>(value1);
        final BinaryTree<Object> leaf3 = new BinaryTree<>(value3);
        assertEquals(leaf1, leaf1);
        assertEquals(leaf1, leaf2);
        assertEquals(leaf1.hashCode(), leaf2.hashCode());
        assertFalse(leaf1.equals(leaf3));

        // 2 nodes both with 2 leafs
        final BinaryTree<Object> nodeLeaf1 = new BinaryTree<>(value1, value2);
        final BinaryTree<Object> nodeLeaf2 = new BinaryTree<>(value1, value2);
        final BinaryTree<Object> nodeLeaf3 = new BinaryTree<>(value2, value1);
        final BinaryTree<Object> nodeLeaf4 = new BinaryTree<>(value1, value3);
        final BinaryTree<Object> nodeLeaf5 = new BinaryTree<>(value3, value2);
        assertEquals(nodeLeaf1, nodeLeaf1);
        assertEquals(nodeLeaf1, nodeLeaf2);
        assertEquals(nodeLeaf1.hashCode(), nodeLeaf2.hashCode());
        assertFalse(nodeLeaf1.equals(nodeLeaf3));
        assertFalse(nodeLeaf1.equals(nodeLeaf4));
        assertFalse(nodeLeaf1.equals(nodeLeaf5));

        // 2 trees
        final BinaryTree<Object> tree1 = new BinaryTree<>(nodeLeaf1, nodeLeaf3);
        final BinaryTree<Object> tree2 = new BinaryTree<>(nodeLeaf1, nodeLeaf3);
        final BinaryTree<Object> tree3 = new BinaryTree<>(nodeLeaf3, nodeLeaf1);
        final BinaryTree<Object> tree4 = new BinaryTree<>(nodeLeaf1, nodeLeaf4);
        final BinaryTree<Object> tree5 = new BinaryTree<>(nodeLeaf4, nodeLeaf3);
        assertEquals(tree1, tree1);
        assertEquals(tree1, tree2);
        assertEquals(tree1.hashCode(), tree2.hashCode());
        assertFalse(tree1.equals(tree3));
        assertFalse(tree1.equals(tree4));
        assertFalse(tree1.equals(tree5));

        final BinaryTree<Object> tree6 = new BinaryTree<>(tree1, tree3);
        final BinaryTree<Object> tree7 = new BinaryTree<>(tree1, tree3);
        final BinaryTree<Object> tree8 = new BinaryTree<>(tree3, tree1);
        final BinaryTree<Object> tree9 = new BinaryTree<>(tree1, tree4);
        final BinaryTree<Object> tree0 = new BinaryTree<>(tree4, tree3);
        assertEquals(tree6, tree6);
        assertEquals(tree6, tree7);
        assertEquals(tree6.hashCode(), tree7.hashCode());
        assertFalse(tree6.equals(tree8));
        assertFalse(tree6.equals(tree9));
        assertFalse(tree6.equals(tree0));
    }

    // covers: string representation
    @Test
    public void testToString() {
        final String value1 = "one";
        final String value2 = "two";
        final String value3 = "thr";

        // leaf
        final BinaryTree<String> leaf1 = new BinaryTree<>(value1);
        assertEquals(value1, leaf1.toString());

        // node with 2 leafs
        final BinaryTree<String> nodeLeaf1 = new BinaryTree<>(value1, value2);
        assertEquals(String.format("[%s, %s]", value1, value2), nodeLeaf1.toString());

        // tree
        final BinaryTree<String> nodeLeaf2 = new BinaryTree<>(value2, value3);
        final BinaryTree<String> tree1 = new BinaryTree<>(nodeLeaf1, nodeLeaf2);
        final String expected = String.format("[[%s, %s], [%s, %s]]", value1, value2, value2, value3);
        assertEquals(expected, tree1.toString());

        // slightly more complicated tree
        final BinaryTree<String> tree2 = new BinaryTree<>(tree1, tree1);
        assertEquals(String.format("[%s, %s]", expected, expected), tree2.toString());

        // disbalanced tree
        final BinaryTree<String> tree3 = new BinaryTree<>(tree1, leaf1);
        assertEquals(String.format("[%s, %s]", expected, value1), tree3.toString());
    }

    // covers: exceptions...

    @Test(expected =  IllegalArgumentException.class)
    public void testExceptionNullLeaf() {
        final Object value = null;
        final BinaryTree<Object> leaf = new BinaryTree<>(value);
        assertEquals(null, leaf);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void testExceptionNullNodeTwoLeafs() {
        final Object value = null;
        final BinaryTree<Object> leaf = new BinaryTree<>(value, value);
        assertEquals(null, leaf);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void testExceptionNullTree() {
        final BinaryTree<Object> leaf = null;
        final BinaryTree<Object> node = new BinaryTree<>(leaf, leaf);
        assertEquals(null, node);
    }


}
