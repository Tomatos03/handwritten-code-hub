import com.codehub.SparseTable;

import java.util.Random;

/**
 * @author : Tomatos
 * @date : 2026/6/11
 */
public class SparseTableTest {
    static int naiveMax(int[] arr, int l, int r) {
        int max = arr[l];
        for (int i = l + 1; i <= r; i++) {
            max = Math.max(max, arr[i]);
        }
        return max;
    }

    static int naiveMin(int[] arr, int l, int r) {
        int min = arr[l];
        for (int i = l + 1; i <= r; i++) {
            min = Math.min(min, arr[i]);
        }
        return min;
    }

    static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    static int naiveGCD(int[] arr, int l, int r) {
        int result = arr[l];
        for (int i = l + 1; i <= r; i++) {
            result = gcd(result, arr[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        Random random = new Random(42);
        int rounds = 100;

        for (int round = 0; round < rounds; round++) {
            int n = random.nextInt(100) + 1;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = random.nextInt(1000) + 1;
            }

            SparseTable st = new SparseTable(arr);

            for (int q = 0; q < 50; q++) {
                int l = random.nextInt(n);
                int r = random.nextInt(n);
                if (l > r) {
                    int tmp = l;
                    l = r;
                    r = tmp;
                }

                int expectedMax = naiveMax(arr, l, r);
                int actualMax = st.queryMax(l, r);
                if (expectedMax != actualMax) {
                    throw new AssertionError(
                        "MAX failed at round " + round + " query [" + l + "," + r + "]: "
                            + "expected=" + expectedMax + " actual=" + actualMax
                    );
                }

                int expectedMin = naiveMin(arr, l, r);
                int actualMin = st.queryMin(l, r);
                if (expectedMin != actualMin) {
                    throw new AssertionError(
                        "MIN failed at round " + round + " query [" + l + "," + r + "]: "
                            + "expected=" + expectedMin + " actual=" + actualMin
                    );
                }

                int expectedGCD = naiveGCD(arr, l, r);
                int actualGCD = st.queryGCD(l, r);
                if (expectedGCD != actualGCD) {
                    throw new AssertionError(
                        "GCD failed at round " + round + " query [" + l + "," + r + "]: "
                            + "expected=" + expectedGCD + " actual=" + actualGCD
                    );
                }
            }
        }
        System.out.println("All tests passed");
    }
}
