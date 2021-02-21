import java.util.Scanner;

public class Swordfighting {

    static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static Point intersection(Point a, Point b, Point c, Point d) {
        double[] res = new double[2];
        double val = (b.x - a.x) * (d.y - c.y) - (b.y - a.y) * (d.x - c.x);
        res[0] = ((b.x - a.x) * (c.x * d.y - d.x * c.y) - (d.x - c.x) * (a.x * b.y - b.x * a.y)) / val;
        res[1] = ((b.y - a.y) * (c.x * d.y - d.x * c.y) - (d.y - c.y) * (a.x * b.y - b.x * a.y)) / val;
        return new Point(res[0], res[1]);
    }

    static int CCW(Point a, Point b, Point p) {
        double val = (p.y - a.y) * (b.x - a.x) - (p.x - a.x) * (b.y - a.y);
        if (val == 0) {
            return 0;//on
        }
        if (val > 0) {
            return 1;//left
        }
        return -1;//right
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        double x11, y11, x12, y12, x13, y13, x21, y21, x22, y22, x23, y23;

        for (int test = 1; test <= t; test++) {
            x11 = s.nextDouble();
            y11 = s.nextDouble();
            x12 = s.nextDouble();
            y12 = s.nextDouble();
            x13 = s.nextDouble();
            y13 = s.nextDouble();
            x21 = s.nextDouble();
            y21 = s.nextDouble();
            x22 = s.nextDouble();
            y22 = s.nextDouble();
            x23 = s.nextDouble();
            y23 = s.nextDouble();

            double proj1x, proj1y, proj2x, proj2y;
 
            if (x11 == x12) {
                proj1x = x11;
                proj1y = y13;
            } else {
                double m1 = (y11 - y12) / (x11 - x12);
                double b1 = y11 - m1 * x11;
                proj1x = (x13 + m1 * y13 - m1 * b1) / (1 + m1 * m1);
                proj1y = (m1 * x13 + m1 * m1 * y13 + b1) / (1 + m1 * m1);
            }

            if (x21 == x22) {
                proj2x = x21;
                proj2y = y23;
            } else {
                double m2 = (y21 - y22) / (x21 - x22);
                double b2 = y22 - m2 * x22;
                proj2x = (x23 + m2 * y23 - m2 * b2) / (1 + m2 * m2);
                proj2y = (m2 * x23 + m2 * m2 * y23 + b2) / (1 + m2 * m2);
            }

            double val = (proj1x - x13) * (proj2y - y23) - (proj1y - y13) * (proj2x - x23);
            if (Math.abs(val) < 1E-4) {
                System.out.println("Case " + "#" + test + ": " + "strange");
            } else {
                Point pommel1 = new Point(x13, y13);
                Point pommel2 = new Point(x23, y23);
                Point proj1 = new Point(proj1x, proj1y);
                Point proj2 = new Point(proj2x, proj2y);

                Point intersection = intersection(pommel1, proj1, pommel2, proj2);

                Point s1Left = new Point(x11, y11);
                Point s1Right = new Point(x12, y12);

                Point s2Left = new Point(x21, y21);
                Point s2Right = new Point(x22, y22);

                if (CCW(s1Left, s1Right, pommel1) * CCW(s1Left, s1Right, intersection) > 0 ||
                        CCW(s2Left, s2Right, pommel2) * CCW(s2Left, s2Right, intersection) > 0) {
                    System.out.println("Case " + "#" + test + ": " + "strange");
                } else {
                    System.out.println("Case " + "#" + test + ": " + intersection.x + " " + intersection.y);
                }
            }
        }
        s.close();
    }
}
