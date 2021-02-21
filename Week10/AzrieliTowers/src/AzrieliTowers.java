import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

//some methods which I also used for problem A are from https://www.geeksforgeeks.org/how-to-check-if-a-given-point-lies-inside-a-polygon/

public class AzrieliTowers {

    static boolean checkOrtho(Point a, Point b, Point c, Point d) {
        double val1 = (b.y - a.y) * (d.y - c.y);
        double val2 = (a.x - b.x) * (d.x - c.x);
        return val1 == val2;
    }

    //should be ok
    static boolean checkParallel(Point a, Point b, Point c, Point d) {
        int val1 = (b.y - a.y) * (d.x - c.x);
        int val2 = (b.x - a.x) * (d.y - c.y);

        if (val1 == val2) {
            return true;
        }
        return false;
    }

    //return true if point p lies on segment (a,b)
    static boolean onSegment(Point a, Point p, Point b) {
        if (((a.x <= p.x && p.x <= b.x) || (b.x <= p.x && p.x <= a.x)) &&
                ((a.y <= p.y && p.y <= b.y) || (b.y <= p.y && p.y <= a.y))) {
            return true;
        }
        return false;
    }

    //returns true if the line segments (p1,q1) and (p2,q2) intersect
    static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4) {
            return true;
        }

        if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }

        if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true;
        }

        if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true;
        }

        if (o4 == 0 && onSegment(p2, q1, q2)) {
            return true;
        }

        return false;
    }

    static int orientation(Point p, Point q, Point r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    static boolean isInside(Point polygon[], int n, Point meteorite) {
        Point extreme = new Point(1500, meteorite.y + 100);

        int count = 0, i = 0;
        do {
            int next = (i + 1) % n;

            if (doIntersect(polygon[i], polygon[next], meteorite, extreme)) {

                if (orientation(polygon[i], meteorite, polygon[next]) == 0) {
                    return onSegment(polygon[i], meteorite,
                            polygon[next]);
                }
                count++;
            }

            i = next;
        } while (i != 0);
        return (count % 2 == 1);
    }

    static void print(int test, Point[] rectangle, Point[] triangle) {
        System.out.println("Case " + "#" + test + ": " + "possible");
        System.out.println(rectangle[0].x + " " + rectangle[0].y);
        System.out.println(rectangle[1].x + " " + rectangle[1].y);
        System.out.println(rectangle[2].x + " " + rectangle[2].y);
        System.out.println(rectangle[3].x + " " + rectangle[3].y);

        System.out.println(triangle[0].x + " " + triangle[0].y);
        System.out.println(triangle[1].x + " " + triangle[1].y);
        System.out.println(triangle[2].x + " " + triangle[2].y);
    }

    static boolean intersect(Point[] rectangle, Point[] triangle) {
        //any point of the triangle inside the rectangle
        for (int i = 0; i < 3; i++) {
            if (isInside(rectangle, 4, triangle[i])) {
                return true;
            }
        }

        //any point of the rectangle inside the triangle
        for (int i = 0; i < 4; i++) {
            if (isInside(triangle, 3, rectangle[i])) {
                return true;
            }
        }

        if (doIntersect(triangle[0], triangle[1], rectangle[0], rectangle[1])) return true;
        if (doIntersect(triangle[0], triangle[1], rectangle[1], rectangle[2])) return true;
        if (doIntersect(triangle[0], triangle[1], rectangle[2], rectangle[3])) return true;
        if (doIntersect(triangle[0], triangle[1], rectangle[3], rectangle[0])) return true;

        if (doIntersect(triangle[1], triangle[2], rectangle[0], rectangle[1])) return true;
        if (doIntersect(triangle[1], triangle[2], rectangle[1], rectangle[2])) return true;
        if (doIntersect(triangle[1], triangle[2], rectangle[2], rectangle[3])) return true;
        if (doIntersect(triangle[1], triangle[2], rectangle[3], rectangle[0])) return true;

        if (doIntersect(triangle[2], triangle[0], rectangle[0], rectangle[1])) return true;
        if (doIntersect(triangle[2], triangle[0], rectangle[1], rectangle[2])) return true;
        if (doIntersect(triangle[2], triangle[0], rectangle[2], rectangle[3])) return true;
        if (doIntersect(triangle[2], triangle[0], rectangle[3], rectangle[0])) return true;

        return false;
    }

    static boolean isRectangle(Point a, Point b, Point c, Point d) {

        if (!checkParallel(a, b, d, c)) return false;
        if (!checkParallel(b, c, a, d)) return false;

        if (!checkOrtho(a, b, b, c)) return false;
        if (!checkOrtho(b, c, c, d)) return false;
        if (!checkOrtho(c, d, d, a)) return false;

        return true;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, x, y;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();

            ArrayList<Point> points = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                x = s.nextInt();
                y = s.nextInt();
                Point p = new Point(x, y);

                boolean appears = false;

                for (int j = 0; j < points.size(); j++) {
                    Point point = points.get(j);
                    if (point.x == x && point.y == y) {
                        appears = true;
                        break;
                    }
                }

                if (!appears) {
                    points.add(p);
                }
            }

            boolean rectangleFound = false;

            for (int i = 0; i < points.size() && !rectangleFound; i++) {
                for (int j = 0; j < points.size() && !rectangleFound; j++) {
                    if (j != i)
                        for (int k = 0; k < points.size() && !rectangleFound; k++) {
                            if (k != j && k != i)
                                for (int l = 0; l < points.size() && !rectangleFound; l++) {
                                    if (l != k && l != j && l != i)

                                        if (isRectangle(points.get(i), points.get(j), points.get(k), points.get(l))) {

                                            Point[] rectangle = new Point[4];

                                            rectangle[0] = points.get(i);
                                            rectangle[1] = points.get(j);
                                            rectangle[2] = points.get(k);
                                            rectangle[3] = points.get(l);

                                            for (int t1 = 0; t1 < points.size() && !rectangleFound; t1++) {
                                                if (t1 != i && t1 != j && t1 != k && t1 != l) {
                                                    for (int t2 = 0; t2 < points.size() && !rectangleFound; t2++) {
                                                        if (t2 != t1 && t2 != i && t2 != j && t2 != k && t2 != l) {
                                                            for (int t3 = 0; t3 < points.size() && !rectangleFound; t3++) {
                                                                if (t3 != t2 && t3 != t1 && t3 != i && t3 != j && t3 != k && t3 != l) {

                                                                    Point[] triangle = new Point[3];

                                                                    triangle[0] = points.get(t1);
                                                                    triangle[1] = points.get(t2);
                                                                    triangle[2] = points.get(t3);

                                                                    if (!intersect(rectangle, triangle)) {
                                                                        rectangleFound = true;
                                                                        print(test, rectangle, triangle);
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                }
                        }
                }
            }

            if (!rectangleFound) {
                System.out.println("Case " + "#" + test + ": " + "impossible");
            }
        }
        s.close();
    }
}
