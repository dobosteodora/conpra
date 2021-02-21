import java.util.*;

//idea and method inverse() from https://www.geeksforgeeks.org/chinese-remainder-theorem-set-1-introduction/

public class NAthlon {
    static long k;
    static int length;
    static long[] num;
    static long[] rem;
    static HashMap<Long, Long> pairs;

    static long inverse(long a, long m) {
        long m0 = m, t, q;
        long x0 = 0, x1 = 1;

        if (m == 1) {
            return 0;
        }
        while (a > 1) {
            q = a / m;
            t = m;
            m = a % m;
            a = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }
        if (x1 < 0) {
            x1 += m0;
        }
        return x1;
    }

    static long solve() {
        long prod = 1;
        for (int i = 0; i < length; i++)
            prod *= num[i];

        long result = 0;

        for (int i = 0; i < length; i++) {
            long pp = prod / num[i];
            result += rem[i] * inverse(pp, num[i]) * pp;
        }

        if (result % prod > k) {
            return -1;
        }

        long smallest = result % prod;

        while (smallest < k) {
            smallest += prod;
        }
        if (smallest > k) {
            smallest -= prod;
        }
        result = smallest;

        return result;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            k = s.nextLong();

            long size, rest;

            pairs = new HashMap<>();//the sizes and rests -> pair: (size_i, rest_i)

            boolean impossible = false;

            for (int i = 1; i <= n; i++) {
                size = s.nextLong();
                rest = s.nextLong();

                if (pairs.containsKey(size)) {
                    if (pairs.get(size) != rest) {
                        impossible = true;
                    }
                } else {
                    pairs.put(size, rest);
                }
            }

            long res = -2;

            if (!impossible) {
                length = pairs.size();
                num = new long[length];
                rem = new long[length];
                int index = 0;

                Iterator it = pairs.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    size = (long) pair.getKey();
                    rest = (long) pair.getValue();
                    num[index] = size;
                    rem[index] = rest;
                    index++;
                }

                if (n != 0) {
                    res = solve();

                    if (res < 0) {
                        impossible = true;
                    }
                } else {
                    res = 0;//output 0 if there are no games
                }
            }
            if (impossible) {
                System.out.println("Case " + "#" + test + ": " + "impossible");
            } else {
                System.out.println("Case " + "#" + test + ": " + res);
            }
        }
        s.close();
    }
}
