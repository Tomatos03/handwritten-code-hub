package fenwick_tree;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FenwickTreeTest {

    @Test
    public void testAddAndQuery() {
        FenwickTree tree = new FenwickTree(10);

        // 添加一些值
        tree.add(1, 5);
        tree.add(2, 3);
        tree.add(3, 7);
        tree.add(4, 6);

        // 测试单点查询
        Assertions.assertEquals(5, tree.query(1));
        Assertions.assertEquals(8, tree.query(2));
        Assertions.assertEquals(15, tree.query(3));
        Assertions.assertEquals(21, tree.query(4));

        // 测试区间和
        Assertions.assertEquals(5, tree.sum(1, 1));
        Assertions.assertEquals(8, tree.sum(1, 2));
        Assertions.assertEquals(15, tree.sum(1, 3));
        Assertions.assertEquals(21, tree.sum(1, 4));
        Assertions.assertEquals(16, tree.sum(2, 4));
    }

    @Test
    public void testONConstructor() {
        int[] nums = {1, 2, 3, 4, 5};
        FenwickTree tree = new FenwickTree(nums.length, nums);

        // 验证前缀和
        Assertions.assertEquals(1, tree.query(1));
        Assertions.assertEquals(3, tree.query(2));
        Assertions.assertEquals(6, tree.query(3));
        Assertions.assertEquals(10, tree.query(4));
        Assertions.assertEquals(15, tree.query(5));

        // 验证区间和
        Assertions.assertEquals(1, tree.sum(1, 1));
        Assertions.assertEquals(5, tree.sum(2, 3));
        Assertions.assertEquals(12, tree.sum(3, 5));
        Assertions.assertEquals(14, tree.sum(2, 5));
        Assertions.assertEquals(15, tree.sum(1, 5));
    }

    @Test
    public void testUpdateAndSum() {
        FenwickTree tree = new FenwickTree(5);

        tree.add(1, 1);
        tree.add(2, 2);
        tree.add(3, 3);
        tree.add(4, 4);
        tree.add(5, 5);

        // 更新部分值
        tree.add(3, 2); // 3号位置加2

        // 测试区间和
        Assertions.assertEquals(1, tree.sum(1, 1));
        Assertions.assertEquals(3, tree.sum(1, 2));
        Assertions.assertEquals(8, tree.sum(1, 3));
        Assertions.assertEquals(12, tree.sum(1, 4));
        Assertions.assertEquals(17, tree.sum(1, 5));
        Assertions.assertEquals(9, tree.sum(4, 5));
    }
}