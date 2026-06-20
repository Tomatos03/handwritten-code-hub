import com.codehub.BalancedBinaryTree;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BalancedBinaryTree 单元测试
 * 每个测试方法对应一个二叉搜索树的公共方法
 */
public class BalancedBinaryTreeTest {

    // ==================== 构造函数测试 ====================

    @Test
    void testConstructor() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{3, 1, 5, 2, 4});
        assertEquals(5, tree.size());
        assertEquals(1, tree.min());
        assertEquals(5, tree.max());
    }

    @Test
    void testConstructorEmpty() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{});
        assertEquals(0, tree.size());
    }

    @Test
    void testConstructorSingleElement() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{42});
        assertEquals(1, tree.size());
        assertEquals(42, tree.min());
        assertEquals(42, tree.max());
    }

    @Test
    void testConstructorSorted() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), tree.inOrder());
    }

    // ==================== search 测试 ====================

    @Test
    void testSearchFound() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertTrue(tree.search(3));
        assertTrue(tree.search(1));
        assertTrue(tree.search(5));
    }

    @Test
    void testSearchNotFound() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertFalse(tree.search(0));
        assertFalse(tree.search(6));
        assertFalse(tree.search(100));
    }

    @Test
    void testSearchAfterInsert() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 3, 5});
        assertFalse(tree.search(2));
        tree.insert(2);
        assertTrue(tree.search(2));
    }

    @Test
    void testSearchAfterDelete() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertTrue(tree.search(3));
        tree.delete(3);
        assertFalse(tree.search(3));
    }

    // ==================== insert 测试 ====================

    @Test
    void testInsertNewValue() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{});
        tree.insert(5);
        assertEquals(1, tree.size());
        assertTrue(tree.search(5));
    }

    @Test
    void testInsertMultipleValues() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{});
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        assertEquals(3, tree.size());
        assertTrue(tree.search(3));
        assertTrue(tree.search(5));
        assertTrue(tree.search(7));
    }

    @Test
    void testInsertDuplicateValue() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3});
        int sizeBefore = tree.size();
        tree.insert(2); // 重复插入
        assertEquals(sizeBefore, tree.size()); // size 不变，因为 count++ 而不是新增节点
        assertTrue(tree.search(2));
    }

    @Test
    void testInsertMaintainsBSTProperty() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{});
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.insert(1);
        tree.insert(4);
        tree.insert(6);
        tree.insert(8);
        // 中序遍历应该是有序的
        assertEquals(List.of(1, 3, 4, 5, 6, 7, 8), tree.inOrder());
    }

    // ==================== delete 测试 ====================

    @Test
    void testDeleteLeafNode() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        tree.delete(1); // 叶子节点
        assertFalse(tree.search(1));
        assertEquals(4, tree.size());
    }

    @Test
    void testDeleteNodeWithOneChild() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        tree.delete(2); // 只有右子节点
        assertFalse(tree.search(2));
        assertEquals(4, tree.size());
        assertTrue(tree.search(1));
        assertTrue(tree.search(3));
    }

    @Test
    void testDeleteNodeWithTwoChildren() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        tree.delete(3); // 有两个子节点
        assertFalse(tree.search(3));
        assertEquals(4, tree.size());
        // 验证 BST 性质仍然保持
        assertEquals(List.of(1, 2, 4, 5), tree.inOrder());
    }

    @Test
    void testDeleteRootNode() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        tree.delete(3); // 根节点（中位数）
        assertFalse(tree.search(3));
        assertEquals(4, tree.size());
    }

    @Test
    void testDeleteNonExistentValue() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        int sizeBefore = tree.size();
        tree.delete(100); // 不存在的值
        assertEquals(sizeBefore, tree.size());
    }

    @Test
    void testDeleteDuplicateValueDecrementsCount() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3});
        tree.insert(2); // count++ for value 2
        int sizeBefore = tree.size();
        tree.delete(2); // 应该只减少 count，不删除节点
        assertEquals(sizeBefore, tree.size()); // size 不变
        assertTrue(tree.search(2)); // 仍然存在
    }

    @Test
    void testDeleteAllNodes() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3});
        tree.delete(1);
        tree.delete(2);
        tree.delete(3);
        assertEquals(0, tree.size());
    }

    // ==================== 遍历测试 ====================

    @Test
    void testInOrder() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        // 中序遍历应该是有序的
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), tree.inOrder());
    }

    @Test
    void testInOrderSingleElement() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{42});
        assertEquals(List.of(42), tree.inOrder());
    }

    @Test
    void testPreOrder() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        List<Integer> result = tree.preOrder();
        // 前序遍历第一个元素应该是根节点（中位数）
        assertEquals(4, result.get(0));
        // 验证长度
        assertEquals(7, result.size());
    }

    @Test
    void testPostOrder() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        List<Integer> result = tree.postOrder();
        // 后序遍历最后一个元素应该是根节点
        assertEquals(4, result.get(result.size() - 1));
        // 验证长度
        assertEquals(7, result.size());
    }

    @Test
    void testLevelOrder() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        List<List<Integer>> result = tree.levelOrder();
        // 第一层应该是根节点
        assertEquals(List.of(4), result.get(0));
        // 第二层应该是左右子节点
        assertEquals(List.of(2, 6), result.get(1));
        // 第三层应该是叶子节点
        assertEquals(List.of(1, 3, 5, 7), result.get(2));
    }

    @Test
    void testLevelOrderSingleElement() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{42});
        List<List<Integer>> result = tree.levelOrder();
        assertEquals(1, result.size());
        assertEquals(List.of(42), result.get(0));
    }

    // ==================== height 测试 ====================

    @Test
    void testHeight() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        assertEquals(3, tree.height());
    }

    @Test
    void testHeightSingleElement() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{42});
        assertEquals(1, tree.height());
    }

    @Test
    void testHeightUnbalanced() {
        // 插入有序数组创建不平衡树
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{});
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        assertEquals(5, tree.height()); // 链状结构
    }

    @Test
    void testHeightEmptyTree() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{});
        assertEquals(0, tree.height());
    }

    // ==================== size 测试 ====================

    @Test
    void testSize() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertEquals(5, tree.size());
    }

    @Test
    void testSizeAfterInsert() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3});
        assertEquals(3, tree.size());
        tree.insert(4);
        assertEquals(4, tree.size());
    }

    @Test
    void testSizeAfterDelete() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertEquals(5, tree.size());
        tree.delete(3);
        assertEquals(4, tree.size());
    }

    @Test
    void testSizeEmptyTree() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{});
        assertEquals(0, tree.size());
    }

    @Test
    void testSizeDuplicateInsert() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3});
        tree.insert(2); // 重复值，count++，size 不变
        assertEquals(3, tree.size());
    }

    // ==================== min 测试 ====================

    @Test
    void testMin() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertEquals(1, tree.min());
    }

    @Test
    void testMinSingleElement() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{42});
        assertEquals(42, tree.min());
    }

    @Test
    void testMinAfterInsert() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{2, 3, 4, 5});
        assertEquals(2, tree.min());
        tree.insert(1);
        assertEquals(1, tree.min());
    }

    @Test
    void testMinAfterDelete() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertEquals(1, tree.min());
        tree.delete(1);
        assertEquals(2, tree.min());
    }

    @Test
    void testMinEmptyTree() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{});
        assertThrows(IllegalStateException.class, tree::min);
    }

    // ==================== max 测试 ====================

    @Test
    void testMax() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertEquals(5, tree.max());
    }

    @Test
    void testMaxSingleElement() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{42});
        assertEquals(42, tree.max());
    }

    @Test
    void testMaxAfterInsert() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4});
        assertEquals(4, tree.max());
        tree.insert(5);
        assertEquals(5, tree.max());
    }

    @Test
    void testMaxAfterDelete() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertEquals(5, tree.max());
        tree.delete(5);
        assertEquals(4, tree.max());
    }

    @Test
    void testMaxEmptyTree() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{});
        assertThrows(IllegalStateException.class, tree::max);
    }

    // ==================== successor 测试 ====================

    @Test
    void testSuccessor() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertEquals(4, tree.successor(3));
        assertEquals(2, tree.successor(1));
        assertEquals(5, tree.successor(4));
    }

    @Test
    void testSuccessorNoSuccessor() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertNull(tree.successor(5)); // 最大值没有后继
        assertNull(tree.successor(100)); // 不存在的值也没有后继
    }

    @Test
    void testSuccessorAfterInsert() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 3, 5});
        assertEquals(5, tree.successor(3));
        tree.insert(4);
        assertEquals(4, tree.successor(3)); // 新插入的值成为新的后继
    }

    // ==================== predecessor 测试 ====================

    @Test
    void testPredecessor() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertEquals(2, tree.predecessor(3));
        assertEquals(4, tree.predecessor(5));
        assertEquals(1, tree.predecessor(2));
    }

    @Test
    void testPredecessorNoPredecessor() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 2, 3, 4, 5});
        assertNull(tree.predecessor(1)); // 最小值没有前驱
        assertNull(tree.predecessor(0)); // 不存在的值也没有前驱
    }

    @Test
    void testPredecessorAfterInsert() {
        BalancedBinaryTree tree = new BalancedBinaryTree(new int[]{1, 3, 5});
        assertEquals(1, tree.predecessor(3));
        tree.insert(2);
        assertEquals(2, tree.predecessor(3)); // 新插入的值成为新的前驱
    }
}
