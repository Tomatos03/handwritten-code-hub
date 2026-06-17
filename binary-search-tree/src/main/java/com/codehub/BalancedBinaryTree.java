package com.codehub;

import java.util.*;

/**
 *
 *
 * @author : Tomatos
 * @date : 2026/6/17
 */
public class BalancedBinaryTree {
    private TreeNode root;
    private int size;

    public BalancedBinaryTree(int[] arr) {
        Arrays.sort(arr);
        this.size = arr.length;
        root = buildTree(0, size - 1, arr);
    }

    /**
     * 查找指定值是否存在
     *
     * @param val 要查找的值
     * @return 存在返回true，否则返回false
     */
    public boolean search(int val) {
        return doSearch(root, val) != null;
    }

    /**
     * 插入新值，重复值通过count字段计数
     *
     * @param val 要插入的值
     */
    public void insert(int val) {
        if (root == null) {
            root = new TreeNode(val);
            ++size;
            return;
        }
        doInsert(root, val);
    }

    /**
     * 删除指定值，count大于1时只减少计数
     *
     * @param val 要删除的值
     */
    public void delete(int val) {
        boolean[] isDeleted = new boolean[1];
        root = doDelete(root, val, isDeleted);
        if (isDeleted[0]) {
            --size;
        }
    }

    /**
     * 中序遍历（左->根->右）
     *
     * @return 遍历结果列表
     */
    public List<Integer> inOrder() {
        List<Integer> list = new ArrayList<>(size);
        doInOrder(root, list);
        return list;
    }

    /**
     * 前序遍历（根->左->右）
     *
     * @return 遍历结果列表
     */
    public List<Integer> preOrder() {
        List<Integer> list = new ArrayList<>(size);
        doPreOrder(root, list);
        return list;
    }

    /**
     * 后序遍历（左->右->根）
     *
     * @return 遍历结果列表
     */
    public List<Integer> postOrder() {
        List<Integer> list = new ArrayList<>(size);
        doPostOrder(root, list);
        return list;
    }

    /**
     * 获取树的高度
     *
     * @return 树的高度，空树返回0
     */
    public int height() {
        return doHeight(root);
    }

    /**
     * 获取唯一节点数量
     *
     * @return 唯一节点数量
     */
    public int size() {
        return this.size;
    }

    /**
     * 获取最小值
     *
     * @return 最小值
     * @throws IllegalStateException 树为空时抛出
     */
    public int min() {
        assertTreeRootNotEmpty();
        return doMin(root);
    }

    /**
     * 获取最大值
     *
     * @return 最大值
     * @throws IllegalStateException 树为空时抛出
     */
    public int max() {
        assertTreeRootNotEmpty();
        return doMax(root);
    }

    /**
     * 查找后继（比val大的最小值）
     *
     * @param val 参考值
     * @return 后继值，不存在返回null
     */
    public Integer successor(int val) {
        TreeNode node = root;
        Integer res = null;
        while (node != null) {
            if (node.val > val) {
                res = node.val;
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return res;
    }

    /**
     * 查找前驱（比val小的最大值）
     *
     * @param val 参考值
     * @return 前驱值，不存在返回null
     */
    public Integer predecessor(int val) {
        TreeNode node = root;
        Integer res = null;
        while (node != null) {
            if (node.val >= val) {
                node = node.left;
            } else {
                res = node.val;
                node = node.right;
            }
        }
        return res;
    }

    /**
     * 层序遍历（BFS）
     *
     * @return 每层遍历结果的列表
     */
    public List<List<Integer>> levelOrder() {
        assertTreeRootNotEmpty();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        List<List<Integer>> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> vals = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                vals.add(node.val);
            }
            ans.add(vals);
        }
        return ans;
    }

    private void buildTop(StringBuilder sb, int h, int topVal) {
        int n = 1 << h;
        sb.append(" ".repeat(n))
          .append(topVal)
          .append(" ".repeat(n))
          .append('\n');
    }

    private void assertTreeRootNotEmpty() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
    }

    private TreeNode buildTree(int l, int r, int[] arr) {
        if (l > r) {
            return null;
        }

        int mid = l + (r - l) / 2;
        TreeNode node = new TreeNode(arr[mid]);
        node.left = buildTree(l, mid - 1, arr);
        node.right = buildTree(mid + 1, r, arr);
        return node;
    }

    /**
     * 递归删除指定值
     * 删除策略：
     * 1. count > 1 时只减少计数，不删除节点
     * 2. 叶子节点直接置空
     * 3. 只有一个子节点时用子节点替代
     * 4. 有两个子节点时用后继节点替代，再删除后继节点
     */
    private TreeNode doDelete(TreeNode node, int val, boolean[] isDeleted) {
        if (node == null) {
            return null;
        }
        if (node.val == val) {
            --node.count;
            if (node.count > 0) {
                return node;
            }
        }

        if (node.val > val) {
            node.left = doDelete(node.left, val, isDeleted);
        } else if (node.val < val) {
            node.right = doDelete(node.right, val, isDeleted);
        } else if (node.left != null && node.right != null) { // node.val == val and node.count == 0
            isDeleted[0] = true;
            TreeNode successorNode = findSuccessorNode(node.val);
            node.val = successorNode.val;
            node.count = successorNode.count;
            successorNode.count = 1;
            node.right = doDelete(node.right, successorNode.val, isDeleted);
        } else if (node.left == null) {
            isDeleted[0] = true;
            node = node.right;
        } else { // node.right == null
            isDeleted[0] = true;
            node = node.left;
        }
        return node;
    }

    private int doHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(doHeight(node.right), doHeight(node.left));
    }

    private void doInOrder(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }

        doInOrder(node.left, list);
        list.add(node.val);
        doInOrder(node.right, list);
    }

    private void doInsert(TreeNode node, int val) {
        if (node.val == val) {
            node.count++;
        } else if (node.val > val) {
            if (node.left == null) {
                node.left = new TreeNode(val);
                ++size;
            } else {
                doInsert(node.left, val);
            }
        } else {
            if (node.right == null) {
                node.right = new TreeNode(val);
                ++size;
            } else {
                doInsert(node.right, val);
            }
        }
    }

    private int doMax(TreeNode node) {
        if (node.right != null) {
            return doMax(node.right);
        }
        return node.val;
    }

    private int doMin(TreeNode node) {
        if (node.left != null) {
            return doMin(node.left);
        }
        return node.val;
    }

    private void doPostOrder(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        doPostOrder(node.left, list);
        doPostOrder(node.right, list);
        list.add(node.val);
    }

    private void doPreOrder(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        list.add(node.val);
        doPreOrder(node.left, list);
        doPreOrder(node.right, list);
    }

    private TreeNode doSearch(TreeNode node, int targetVal) {
        if (node == null) {
            return null;
        }
        if (node.val == targetVal) {
            return node;
        } else if (node.val > targetVal) {
            return doSearch(node.left, targetVal);
        } else {
            return doSearch(node.right, targetVal);
        }
    }

    /**
     * 查找比val大的最小节点（从根节点开始搜索）
     *
     * @param val 参考值
     * @return 后继节点，不存在返回null
     */
    private TreeNode findSuccessorNode(int val) {
        TreeNode node = root;
        TreeNode res = null;
        while (node != null) {
            if (node.val > val) {
                res = node;
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return res;
    }

    private static class TreeNode {
        int val;
        int count;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
            this.count = 1;
        }
    }
}
