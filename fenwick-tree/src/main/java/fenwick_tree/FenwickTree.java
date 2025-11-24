package fenwick_tree;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/11/24
 */
public class FenwickTree {
    private final long[] tree;
    private final int n;

    /**
     * 构造函数，初始化树状数组
     * @param n 数组长度（下标从1到n）
     */
    public FenwickTree(int n) {
        this.tree = new long[n + 1];
        this.n = n;
    }

    /**
     * 构造函数，初始化树状数组, 时间复杂度O(n)
     * @param n 数组长度（下标从1到n）
     */
    public FenwickTree(int n, int[] nums) {
        this(n);
        for (int i = 1; i <= n; ++i) {
            tree[i] = nums[i - 1];
        }
        for (int i = 1; i <= n; ++i) {
            int j = i + lowbit(i);
            if (j <= n) {
                tree[j] += tree[i];
            }
        }
    }

    /**
     * 获取最低位的1（lowbit操作）
     * @param x 输入的整数
     * @return x的最低位1所代表的数值
     */
    private int lowbit(int x) {
        return x & (-x);
    }

    /**
     * 在pos位置加上v（单点更新）
     * @param pos 位置（从1开始）
     * @param v 增加的值
     */
    public void add(int pos, long v) {
        while (pos <= n) {
            tree[pos] += v;
            pos += lowbit(pos);
        }
    }

    /**
     * 查询区间[l, r]的元素和
     * @param l 区间左端点（从1开始）
     * @param r 区间右端点（从1开始）
     * @return 区间和
     */
    public long sum(int l, int r) {
        return query(r) - query(l - 1);
    }

    /**
     * 查询前x项的元素和（前缀和）
     * @param x 查询的前缀位置（从1开始）
     * @return 前x项的和
     */
    public long query(int x) {
        long cnt = 0;
        while (x > 0) {
            cnt += tree[x];
            x -= lowbit(x);
        }
        return cnt;
    }
}
