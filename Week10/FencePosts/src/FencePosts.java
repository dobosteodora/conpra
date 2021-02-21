import java.awt.*;
import java.util.HashSet;
import java.util.Scanner;

public class FencePosts {

    static int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    static HashSet<Integer> convexHull(Point points[], int n) {
        int leftMost = 0;
        for (int i = 1; i < n; i++) {
            if (points[i].x < points[leftMost].x ||
                    (points[i].x == points[leftMost].x && points[i].y < points[leftMost].y)) {
                leftMost = i;
            }
        }

        HashSet<Integer> hull = new HashSet<>();

        int p = leftMost, q;
        do {

            hull.add(p);

            q = (p + 1) % n;

            for (int i = 0; i < n; i++) {

                if (orientation(points[p], points[i], points[q]) == 2) {
                    q = i;
                } else if (orientation(points[p], points[i], points[q]) == 0) {
                    if (distance(points[i], points[p]) > distance(points[q], points[p])) {
                        q = i;
                    }
                }
            }
            p = q;

        } while (p != leftMost);

        return hull;
    }

    static double distance(Point a, Point b) {
        double res = Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2);
        return Math.sqrt(res);
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, x, y;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();

            Point[] points = new Point[n];

            for (int i = 0; i < n; i++) {
                x = s.nextInt();
                y = s.nextInt();

                points[i] = new Point(x, y);
            }

            HashSet<Integer> hull = convexHull(points, n);

            System.out.print("Case " + "#" + test + ": ");
            for (int i = 0; i < n; i++) {
                if (hull.contains(i)) {
                    System.out.print((i + 1) + " ");
                }
            }
            System.out.println();
        }
        s.close();
    }
}
