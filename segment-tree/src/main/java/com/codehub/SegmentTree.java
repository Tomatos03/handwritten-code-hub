package com.codehub;

/**
 * 线段树 - 维护区间和，支持区间加
 *
 * @author : Administrator
 * @date : 2026/6/12
 */
public class SegmentTree {
    private final int[] tree;
    private final int[] addTag;
    private final int n;

    public SegmentTree(int[] arr) {
        this.n = arr.length;
        if (n == 0) {
            tree = new int[0];
            addTag = new int[0];
            return;
        }
        tree = new int[4 * n];
        addTag = new int[4 * n];
        build(0, n - 1, 0, arr);
    }

    public int add(int pos, int val) {
        assertLegalRange(pos, pos);
        return add0(pos, pos, 0, n - 1, 0, val);
    }

    private int add0(int l, int r, int l0, int r0, int k, int val) {
        if (r0 < l || l0 > r) {
            return 0;
        }
        if (l <= l0 && r0 <= r) {
            tree[k] += val * (r0 - l0 + 1);
            addTag[k] += val;
            return 1;
        }

        int mid0 = calMid(l0, r0);
        pushDown(l0, r0, k);
        add0(l, r, l0, mid0, nextL(k), val);
        add0(l, r, mid0 + 1, r0, nextR(k), val);
        tree[k] = tree[nextL(k)] + tree[nextR(k)];
        return 1;
    }

    public int add(int l, int r, int newVal) {
        assertLegalRange(l, r);
        return add0(l, r, 0, n - 1, 0, newVal);
    }

    public int query(int l, int r) {
        assertLegalRange(l, r);
        return query0(l, r, 0, n - 1, 0);
    }

    private int build(int l, int r, int k, int[] arr) {
        if (l == r) {
            tree[k] = arr[l];
            return arr[l];
        }

        int mid = l + (r - l) / 2;
        tree[k] = build(l, mid, nextL(k), arr) + build(mid + 1, r, nextR(k), arr);
        return tree[k];
    }

    private int calMid(int l, int r) {
        return l + (r - l) / 2;
    }

    private int nextL(int k) {
        return (k << 1) + 1;
    }

    private int nextR(int k) {
        return (k << 1) + 2;
    }

    private void assertLegalRange(int l, int r) {
        if (l > r) {
            throw new IllegalArgumentException("l > r");
        }
        if (l >= n || r < 0) {
            String errorMsg = l >= n ? "l >= n" : "r < 0";
            throw new ArrayIndexOutOfBoundsException(errorMsg);
        }
    }

    private void pushDown(int l, int r, int k) {
        if (addTag[k] == 0) {
            return;
        }

        int left = nextL(k);
        int right = nextR(k);
        int mid = calMid(l, r);
        tree[left] += addTag[k] * (mid - l + 1);
        tree[right] += addTag[k] * (r - mid);
        addTag[left] += addTag[k];
        addTag[right] += addTag[k];
        addTag[k] = 0;
    }

    private int query0(int l, int r, int l0, int r0, int k) {
        if (r0 < l || l0 > r) {
            return 0;
        }
        if (l <= l0 && r0 <= r) {
            return tree[k];
        }

        int mid0 = calMid(l0, r0);
        pushDown(l0, r0, k);
        return query0(l, r, l0, mid0, nextL(k))
                + query0(l, r, mid0 + 1, r0, nextR(k));
    }
}