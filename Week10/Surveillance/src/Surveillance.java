import java.util.Scanner;

public class Surveillance {

    static double minDistFirstCamera;
    static double minDistRest;

    static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static double distance(Point a, Point b) {
        double res = Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2);
        return Math.sqrt(res);
    }

    static void computeMinDist(Point[] points) {

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double d = distance(points[i], points[j]);

                if (i != 0) {
                    if (d < minDistRest) {
                        minDistRest = d;
                    }
                } else {
                    if (d < minDistFirstCamera) {
                        minDistFirstCamera = d;
                    }
                }
            }
        }
    }

    static double computeArea(double radius1, int n, double radius2) {
        double res = 0;
        res += Math.PI * radius1 * radius1 * n;
        res += Math.PI * radius2 * radius2;
        return res;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n;
        double x, y;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();

            Point[] cameras = new Point[n];

            for (int i = 0; i < n; i++) {
                x = s.nextDouble();
                y = s.nextDouble();
                cameras[i] = new Point(x, y);
            }

            minDistFirstCamera = 1000000;
            minDistRest = 1000000;

            computeMinDist(cameras);

            double area;
            double r = 0.0000000000000000000000000001;

            if (minDistFirstCamera <= minDistRest / 2) {//first camera is involved in the minimum distance

                double area1 = computeArea(minDistFirstCamera - r, n - 1, r);
                double area2 = computeArea(minDistFirstCamera / 2, n - 1, minDistFirstCamera / 2);

                if (area1 > area2) {
                    area = area1;
                } else {
                    area = area2;
                }

            } else {//minDist from first camera is larger

                double area1 = computeArea(minDistRest / 2, n - 1, minDistFirstCamera - minDistRest / 2);
                double area2 = computeArea(r, n - 1, minDistFirstCamera - r);

                if (area1 > area2) {
                    area = area1;
                } else {
                    area = area2;
                }
            }
            System.out.println("Case " + "#" + test + ": " + area);
        }
        s.close();
    }
}
