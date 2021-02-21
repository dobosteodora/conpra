import java.util.*;

public class FallingWater {

    static class Point implements Comparable<Point> {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            if (o.y > this.y) return 1;
            if (o.y < this.y) return -1;
            return 0;
        }
    }

    static ArrayList<ArrayList<Point>> ledges;

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

    static Point computeIntersection(Point a, Point b, Point c, Point d) {
        double px = ((b.x - a.x) * (c.x * d.y - d.x * c.y) - (d.x - c.x) * (a.x * b.y - b.x * a.y)) /
                ((b.x - a.x) * (d.y - c.y) - (b.y - a.y) * (d.x - c.x));
        double py = ((b.y - a.y) * (c.x * d.y - d.x * c.y) - (d.y - c.y) * (a.x * b.y - b.x * a.y)) /
                ((b.x - a.x) * (d.y - c.y) - (b.y - a.y) * (d.x - c.x));
        return new Point(px, py);
    }

    static int orientation(Point p, Point q, Point r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, x, y;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            x = s.nextInt();
            y = s.nextInt();

            Point source = new Point(x, y);
            Point end = new Point(x, 0);

            ledges = new ArrayList<>();

            int xi1, yi1, xi2, yi2;

            HashMap<Point, Integer> pointToLedge = new HashMap<>();

            for (int i = 0; i < n; i++) {
                xi1 = s.nextInt();
                yi1 = s.nextInt();
                xi2 = s.nextInt();
                yi2 = s.nextInt();

                ArrayList l = new ArrayList();
                Point p1 = new Point(xi1, yi1);
                Point p2 = new Point(xi2, yi2);

                //first point should have smaller or equal y-coord
                pointToLedge.put(p1, i);
                pointToLedge.put(p2, i);

                if (yi1 <= yi2) {
                    l.add(p1);
                    l.add(p2);
                } else {
                    l.add(p2);
                    l.add(p1);
                }
                ledges.add(l);
            }

            HashSet<Double> result = new HashSet<>();

            Queue<Point> queue = new LinkedList<>();

            queue.add(source);

            Point current1;
            Point current2;
            int currentIndex;
            boolean hitsGround;
            double ymax;

            boolean twoHits;
            int firstLedge;

            pointToLedge.put(source, -1);

            while (!queue.isEmpty()) {

                current1 = queue.poll();
                current2 = new Point(current1.x, 0);
                currentIndex = pointToLedge.get(current1);

                ymax = -1;
                firstLedge = -1;
                twoHits = false;

                hitsGround = true;

                //check if the perpendicular intersects any ledge
                for (int i = 0; i < ledges.size(); i++) {

                    if (i != currentIndex && ledges.get(i).get(0).y < current1.y &&
                            doIntersect(current1, current2, ledges.get(i).get(0), ledges.get(i).get(1))) {

                        Point intersection = computeIntersection(current1, current2, ledges.get(i).get(0), ledges.get(i).get(1));

                        hitsGround = false;
                        if (intersection.y > ymax && intersection.y < current1.y) {
                            ymax = intersection.y;
                            firstLedge = i;
                            twoHits = false;

                            //hit the higher endpoint -> potentially add both endpoints in queue
                            if (intersection.x == ledges.get(i).get(1).x && intersection.y == ledges.get(i).get(1).y) {
                                twoHits = true;
                            }
                        }
                    }
                }

                if (hitsGround) {
                    result.add(current1.x);
                } else {

                    if (twoHits) {
                        if (!queue.contains(ledges.get(firstLedge).get(1))) {
                            queue.add(ledges.get(firstLedge).get(1));
                        }
                        if (!queue.contains(ledges.get(firstLedge).get(0))) {
                            queue.add(ledges.get(firstLedge).get(0));
                        }
                    } else {
                        if (!queue.contains(ledges.get(firstLedge).get(0))) {
                            queue.add(ledges.get(firstLedge).get(0));
                        }
                        if (ledges.get(firstLedge).get(0).y == ledges.get(firstLedge).get(1).y) {
                            if (!queue.contains(ledges.get(firstLedge).get(1))) {
                                queue.add(ledges.get(firstLedge).get(1));
                            }
                        }
                    }
                }
            }
            
            System.out.print("Case " + "#" + test + ": ");

            ArrayList<Double> finalRes = new ArrayList<>();
            finalRes.addAll(result);

            Collections.sort(finalRes);

            double tmp;
            for (int i = 0; i < finalRes.size(); i++) {
                tmp = finalRes.get(i);
                System.out.print(Math.round(tmp) + " ");
            }
            System.out.println();
        }
        s.close();
    }
}
