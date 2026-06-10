package com.codehub;

import java.util.EnumMap;
import java.util.Map;

import static java.lang.Math.log;

/**
 *
 *
 * @author : Tomatos
 * @date : 2026/6/10
 */
public class SparseTable {
    private final Map<Aggregator, int[][]> stMap = new EnumMap<>(Aggregator.class);

    public SparseTable(int[] arr) {
        for (Aggregator type : Aggregator.values()) {
            stMap.put(type, init(type, arr));
        }
    }

    private int[][] init(Aggregator type, int[] arr) {
        if (arr == null) {
            return new int[0][0];
        }

        int n = arr.length;
        int k = (int) (log(n) / log(2)) + 1;
        int[][] st = new int[n][k];
        for (int i = 0; i < n; ++i) {
            st[i][0] = arr[i];
        }

        for (int j = 1; (1 << j) <= n; ++j) {
            for (int i = 0; i + (1 << j) - 1 < n; ++i) {
                st[i][j] = type.cal(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
            }
        }
        return st;
    }

    public int queryMax(int l, int r) {
        return query(Aggregator.MAX, l, r);
    }

    public int queryMin(int l, int r) {
        return query(Aggregator.MIN, l, r);
    }

    public int queryGCD(int l, int r) {
        return query(Aggregator.GCD, l, r);
    }

    public int query(Aggregator type, int l, int r) {
        if (l > r) {
            throw new IllegalArgumentException("查询范围不合法：l > r");
        }
        int len = r - l + 1;
        int k = (int) (log(len) / log(2));
        int[][] st = stMap.get(type);
        return type.cal(st[l][k], st[r - (1 << k) + 1][k]);
    }

    enum Aggregator {
        MIN {
            @Override
            public int cal(int x, int y) {
                return Math.min(x, y);
            }
        },
        MAX {
            @Override
            public int cal(int x, int y) {
                return Math.max(x, y);
            }
        },
        GCD {
            @Override
            public int cal(int x, int y) {
                while (y != 0) {
                    int r = x % y;
                    x = y;
                    y = r;
                }
                return x;
            }
        };

        public abstract int cal(int x, int y);
    }
}