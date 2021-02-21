import java.util.ArrayList;
import java.util.Scanner;

//methods centroid() and area() inspired from from http://paulbourke.net/geometry/polygonmesh/PolygonUtilities.java

public class FragileLetters {

    static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static double CCW(Point a, Point b, Point p) {
        double res = (p.y - a.y) * (b.x - a.x) - (p.x - a.x) * (b.y - a.y);
        return res;
    }

    static double area(Point[] points) {
        int i, j, n = points.length;
        double area = 0;

        for (i = 0; i < n; i++) {
            j = (i + 1) % n;
            area += points[i].x * points[j].y;
            area -= points[j].x * points[i].y;
        }
        area /= 2.0;
        return (area);
    }

    static ArrayList<Double> centroid(Point[] points) {
        double cx = 0, cy = 0;
        double area = area(points);

        ArrayList<Double> res = new ArrayList<>();
        int i, j, n = points.length;

        double factor;
        for (i = 0; i < n; i++) {
            j = (i + 1) % n;
            factor = (points[i].x * points[j].y
                    - points[j].x * points[i].y);
            cx += (points[i].x + points[j].x) * factor;
            cy += (points[i].y + points[j].y) * factor;
        }
        area *= 6.0f;
        factor = 1 / area;
        cx *= factor;
        cy *= factor;

        res.add(cx);
        res.add(cy);

        return res;
    }

    static boolean angle(Point a, Point b, Point p) {
        double val1 = b.x - a.x;
        double val2 = b.y - a.y;

        double val3 = p.x - a.x;
        double val4 = p.y - a.y;

        double d = val1 * val3 + val2 * val4;
        if (d > 0) return true;
        return false;
    }

    static boolean above(Point a, Point b, Point p, boolean clockwise) {
        if (!clockwise && CCW(a, b, p) > 0) {
            if (angle(a, b, p) && angle(b, a, p)) {
                return true;
            }
        } else if (clockwise && CCW(a, b, p) < 0) {
            if (angle(a, b, p) && angle(b, a, p)) {
                return true;
            }
        }
        return false;
    }

    static boolean clockwise(Point[] points) {
        int i = 0;
        int j = 1;

        double sum = 0;
        sum += (points[i].x - points[j].x) * (points[i].y + points[j].y);

        for (int k = 1; k < points.length; k++) {
            i = j;
            j = k;
            sum += (points[i].x - points[j].x) * (points[i].y + points[j].y);
        }

        sum += (points[points.length - 1].x - points[0].x) * (points[points.length - 1].y + points[0].y);

        if (sum < 0) {
            return true;
        }
        return false;
    }

    static int orientation(Point p, Point q, Point r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    static ArrayList<Integer> convexHull(Point points[], int n) {
        int leftMost = 0;
        for (int i = 1; i < n; i++) {
            if (points[i].x < points[leftMost].x ||
                    (points[i].x == points[leftMost].x && points[i].y < points[leftMost].y)) {
                leftMost = i;
            }
        }

        ArrayList<Integer> hull = new ArrayList<>();

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
        int n;
        double x, y;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();

            Point[] points = new Point[n];

            for (int i = 0; i < n; i++) {
                x = s.nextDouble();
                y = s.nextDouble();

                points[i] = new Point(x, y);
            }

            ArrayList<Double> c = centroid(points);
            double cx = c.get(0);
            double cy = c.get(1);

            Point centroid = new Point(cx, cy);

            int count = 0;

            boolean clockwise = clockwise(points);

            ArrayList<Integer> convexHull = convexHull(points, n);
            convexHull.add(convexHull.get(0));
            int k = convexHull.get(0);
            int l = convexHull.get(1);

            if (k == l - 1) {
                if (above(points[k], points[l], centroid, clockwise)) {
                    count++;
                }
            } else if (l == k - 1) {
                if (above(points[l], points[k], centroid, clockwise)) {
                    count++;
                }
            } else if (k == 0 && l == n - 1) {
                if (above(points[l], points[k], centroid, clockwise)) {
                    count++;
                }
            } else if (k == n - 1 && l == 0) {
                if (above(points[k], points[l], centroid, clockwise)) {
                    count++;
                }
            }

            for (int i = 2; i < convexHull.size(); i++) {
                k = l;
                l = convexHull.get(i);

                if (k == l - 1) {
                    if (above(points[k], points[l], centroid, clockwise)) {
                        count++;
                    }
                } else if (l == k - 1) {
                    if (above(points[l], points[k], centroid, clockwise)) {
                        count++;
                    }
                } else if (k == 0 && l == n - 1) {
                    if (above(points[l], points[k], centroid, clockwise)) {
                        count++;
                    }
                } else if (k == n - 1 && l == 0) {
                    if (above(points[k], points[l], centroid, clockwise)) {
                        count++;
                    }
                }
            }

            System.out.println("Case " + "#" + test + ": " + count);
        }
        s.close();
    }
}
