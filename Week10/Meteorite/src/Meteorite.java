import java.awt.*;
import java.util.Scanner;

//idea from https://www.geeksforgeeks.org/how-to-check-if-a-given-point-lies-inside-a-polygon/

public class Meteorite {

    static int INF = 10000;

    //point p lies on segment (a,b)
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
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

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

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int x, y, n;
        int x1, y1, x2, y2;

        for (int test = 1; test <= t; test++) {
            x = s.nextInt();
            y = s.nextInt();
            n = s.nextInt();

            Point meteorite = new Point(x, y);
            Point polygon[] = new Point[n];
            int index = 0;


            int[] xp = new int[n * 2];
            int[] yp = new int[n * 2];

            for (int i = 0; i < n; i++) {
                x1 = s.nextInt();
                y1 = s.nextInt();

                xp[index] = x1;
                yp[index] = y1;

                index++;

                x2 = s.nextInt();
                y2 = s.nextInt();

                xp[index] = x2;
                yp[index] = y2;

                index++;
            }

            int xPoint = xp[0];
            int yPoint = yp[0];

            index = 0;

            int[] xPoints = new int[n];
            int[] yPoints = new int[n];
            xPoints[index] = xPoint;
            yPoints[index] = yPoint;

            polygon[index] = new Point(xPoint, yPoint);
            index++;

            while (index < n) {
                for (int i = 1; i < xp.length; i++) {
                    if (xPoint == xp[i - 1] && yPoint == yp[i - 1]) {
                        polygon[index] = new Point(xp[i], yp[i]);

                        xPoints[index] = xp[i];
                        yPoints[index] = yp[i];
                        index++;

                        xPoint = xp[i];
                        yPoint = yp[i];

                        xp[i] = INF;
                        yp[i] = INF;
                        break;
                    }
                }
            }

            if (isInside(polygon, n, meteorite)) {
                System.out.println("Case " + "#" + test + ": " + "jackpot");
            } else {
                System.out.println("Case " + "#" + test + ": " + "too bad");
            }
        }
        s.close();
    }
}
