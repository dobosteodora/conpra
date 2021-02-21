import java.util.*;

public class JungleNetwork {
    static int[] size;
    static int[] parent;

    static class Point {
        int x;
        int y;
        int c;

        Point(int x, int y, int c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }

    static class Edge implements Comparable<Edge> {
        int source;
        int target;
        int weight;

        Edge(int source, int target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    }

    static int find(int a) {
        int root = a;
        int p;
        while (true) {
            p = parent[root];
            if (p == root)
                break;
            root = p;
        }
        int current = a;
        int next;
        while (current != root) {
            next = parent[current];
            parent[current] = root;
            current = next;
        }
        return root;
    }

    static void union(int a, int b) {
        a = find(a);
        b = find(b);
        int tmp;

        if (a != b) {
            if (size[a] < size[b]) {
                tmp = a;
                a = b;
                b = tmp;
            }
            parent[b] = a;
            size[a] += size[b];
        }
    }

    static int squareDist(int x1, int y1, int x2, int y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();

            ArrayList<Point> points = new ArrayList<>();

            for (int i = 1; i <= n; i++) {
                points.add(new Point(s.nextInt(), s.nextInt(), s.nextInt()));
            }

            ArrayList<Edge> edges = new ArrayList<>();
            int dist;

            for (int i = 0; i < points.size(); i++) {
                dist = squareDist(0, 0, points.get(i).x, points.get(i).y);
                if (dist <= points.get(i).c) {
                    edges.add(new Edge(0, i + 1, 2 * dist));
                }
                for (int j = i + 1; j < points.size(); j++) {
                    dist = squareDist(points.get(i).x, points.get(i).y, points.get(j).x, points.get(j).y);
                    if (dist <= points.get(i).c && dist <= points.get(j).c) {
                        edges.add(new Edge(i + 1, j + 1, 2 * dist));
                    }
                }
            }

            int result = 0;
            parent = new int[n + 1];
            size = new int[n + 1];

            for (int i = 0; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;
            }

            int count = 0;
            Collections.sort(edges);

            for (int i = 0; i < edges.size(); i++) {

                if (find(edges.get(i).source) != find(edges.get(i).target)) {
                    union(edges.get(i).source, edges.get(i).target);
                    result += edges.get(i).weight;
                    count++;
                }
            }

            if (count == n) {
                System.out.println("Case " + "#" + test + ": " + result);
            } else {
                System.out.println("Case " + "#" + test + ": " + "impossible");
            }
        }
        s.close();
    }
}
