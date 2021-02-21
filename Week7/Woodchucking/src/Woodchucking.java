import java.util.*;

public class Woodchucking {
    static int m, n;
    static ArrayList<Integer> d;
    static long[] p;
    static long time;
    static int index;
    static boolean next;

    static void solve() {
        time = 0;
        for (int i = 0; i < n; i++) {
            p[index] += d.get(i);
            if (time < p[index]) {
                time = p[index];
            }
            if (next) {
                index++;
                if (index == m) {
                    index = m - 1;
                    next = false;
                }
            } else {
                index--;
                if (index < 0) {
                    index = 0;
                    next = true;
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            m = s.nextInt();

            d = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                d.add(s.nextInt());
            }

            Comparator<Integer> comparator = Comparator.comparing(e -> -e);
            d.sort(comparator);

            p = new long[m];
            index = 0;
            next = true;

            solve();
            System.out.println("Case " + "#" + test + ": " + time);
        }
        s.close();
    }
}