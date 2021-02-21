import java.util.Scanner;

public class JJ {
    static Node[] st;

    static class Node {
        int v, l, r, lazy;

        public Node(int v, int l, int r, int lazy) {
            this.v = v;
            this.l = l;
            this.r = r;
            this.lazy = lazy;
        }
    }

    static int build(int[] a, int p, int l, int r) {
        st[p].l = l;
        st[p].r = r;
        if (l == r) {
            st[p].v = a[l];
            return st[p].v;
        }
        int m = (l + r) / 2;
        st[p].v = build(a, 2 * p + 1, l, m) + build(a, 2 * p + 2, m + 1, r);
        return st[p].v;
    }

    static long sum(int p, int l, int r) {
        if (l > st[p].r || r < st[p].l) {
            return 0;
        }
        propagate(p);
        if (l <= st[p].l && st[p].r <= r) {
            return st[p].v;
        }
        return sum(2 * p + 1, l, r) + sum(2 * p + 2, l, r);
    }

    static void propagate(int p) {
        st[p].v = st[p].v + (st[p].r - st[p].l + 1) * st[p].lazy;
        if (st[p].l != st[p].r) {
            st[2 * p + 1].lazy = st[2 * p + 1].lazy + st[p].lazy;
            st[2 * p + 2].lazy = st[2 * p + 2].lazy + st[p].lazy;
        }
        st[p].lazy = 0;
    }

    static void rangeAdd(int p, int l, int r, int v) {
        propagate(p);
        if (l > st[p].r || r < st[p].l) {
            return;
        }
        if (l <= st[p].l && st[p].r <= r) {
            st[p].lazy = st[p].lazy + v;
            propagate(p);
        } else if (st[p].l != st[p].r) {
            rangeAdd(2 * p + 1, l, r, v);
            rangeAdd(2 * p + 2, l, r, v);
            st[p].v = st[2 * p + 1].v + st[2 * p + 2].v;
        }
    }


    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, k, a, l, r, v;
        String x;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            k = s.nextInt();


            int tmp = (int) (Math.ceil(Math.log(n) / Math.log(2)));
            int maxSize = 2 * (int) Math.pow(2, tmp) - 1;

            st = new Node[maxSize];
            for (int i = 0; i < st.length; i++) {
                st[i] = new Node(0, 0, 0, 0);
            }

            int[] array = new int[n];

            build(array, 0, 0, n - 1);

            long sumTotal = 0;


            for (int j = 0; j < k; j++) {
                x = s.next();
                if (x.equals("q")) {
                    a = s.nextInt();
                    sumTotal += sum(0, a - 1, a - 1);
                } else {
                    l = s.nextInt();
                    r = s.nextInt();
                    v = s.nextInt();

                    rangeAdd(0, l - 1, r - 1, v);
                }
            }

            sumTotal = sumTotal % 1000000007;

            System.out.println("Case " + "#" + test + ": " + sumTotal);
        }
        s.close();
    }
}
