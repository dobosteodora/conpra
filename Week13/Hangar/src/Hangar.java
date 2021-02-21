import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Hangar {

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Line implements Comparable<Line> {
        Point p1;//has larger y-coord
        Point p2;
        double dist;

        Line(Point p1, Point p2, double dist) {
            this.p1 = p1;
            this.p2 = p2;
            this.dist = dist;
        }

        @Override
        public int compareTo(Line l) {
            if (l.dist - this.dist < 0) return -1;
            if (l.dist - this.dist == 0) return 0;
            return 1;
        }
    }

    static double dist(Point a, Point b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    static boolean checkOrtho(Point a, Point b, Point c, Point d) {
        double val1 = (b.y - a.y) * (d.y - c.y);
        double val2 = (a.x - b.x) * (d.x - c.x);
        return val1 == val2;
    }

    static boolean checkParallel(Point a, Point b, Point c, Point d) {
        int val1 = (b.y - a.y) * (d.x - c.x);
        int val2 = (b.x - a.x) * (d.y - c.y);

        if (val1 == val2) {
            return true;
        }
        return false;
    }

    static double area(Line length, Line width) {
        return length.dist * width.dist;
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
        int n;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();

            ArrayList<Point> points = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                points.add(new Point(s.nextInt(), s.nextInt()));
            }

            ArrayList<Line> lines = new ArrayList<>();

            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    double d = dist(points.get(i), points.get(j));
                    if (points.get(i).y >= points.get(j).y) {
                        lines.add(new Line(points.get(i), points.get(j), d));
                    } else {
                        lines.add(new Line(points.get(j), points.get(i), d));
                    }
                }
            }

            Collections.sort(lines);
            double max = 0;

            for (int i = 0; i < lines.size(); i++) {
                for (int j = i + 1; j < lines.size(); j++) {
                    if (lines.get(i).dist == lines.get(j).dist) {
                        if (isRectangle(lines.get(i).p1, lines.get(i).p2, lines.get(j).p2, lines.get(j).p1)) {
                            Line l = new Line(lines.get(i).p1, lines.get(j).p1, dist(lines.get(i).p1, lines.get(j).p1));
                            double area = area(lines.get(i), l);
                            if (area > max) {
                                max = area;
                            }
                        }
                    } else {
                        break;
                    }
                }
            }
            System.out.println("Case " + "#" + test + ": " + Math.round(max));
        }
        s.close();
    }
}
