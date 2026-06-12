package com.codehub;

import java.util.Random;

/**
 * @author : Tomatos
 * @date : 2026/6/14
 */
public class SegmentTreeTest {
    static int naiveSum(int[] arr, int l, int r) {
        int sum = 0;
        for (int i = l; i <= r; i++) {
            sum += arr[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        Random random = new Random(42);
        int rounds = 100;

        // 测试区间更新 + 区间求和查询
        for (int round = 0; round < rounds; round++) {
            int n = random.nextInt(50) + 1;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = random.nextInt(100) + 1;
            }

            SegmentTree st = new SegmentTree(arr);

            for (int q = 0; q < 30; q++) {
                int ul = random.nextInt(n);
                int ur = random.nextInt(n);
                if (ul > ur) { int tmp = ul; ul = ur; ur = tmp; }
                int delta = random.nextInt(50) + 1;
                for (int i = ul; i <= ur; i++) {
                    arr[i] += delta;
                }
                st.add(ul, ur, delta);

                int l = random.nextInt(n);
                int r = random.nextInt(n);
                if (l > r) { int tmp = l; l = r; r = tmp; }

                int expected = naiveSum(arr, l, r);
                int actual = st.query(l, r);
                if (expected != actual) {
                    throw new AssertionError(
                        "SUM range-update failed at round " + round
                            + " update [" + ul + "," + ur + "] delta=" + delta
                            + " query [" + l + "," + r + "]: "
                            + "expected=" + expected + " actual=" + actual
                    );
                }
            }
        }

        System.out.println("All tests passed");
    }
}
