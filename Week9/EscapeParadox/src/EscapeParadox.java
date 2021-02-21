import java.util.*;

public class EscapeParadox {
    static int n, m, g;
    static ArrayList<ArrayList<Edge>> adjClone;
    static ArrayList<ArrayList<Edge>> adjLea;
    static int[] objects;
    static int INF = Integer.MAX_VALUE;
    static int[] distLea;
    static int[] distClone;
    static int allowedLength;
    static int[][] dp;
    static int[] max;
    static PriorityQueue<Map.Entry<Integer, Integer>> queue;
    static int[][] matrix;

    static class Edge {
        int source;
        int target;
        int weight;

        Edge(int source, int target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }
    }

    static void addEdge(Edge w, boolean lea) {
        if (lea) {
            adjLea.get(w.source).add(w);
        } else {
            adjClone.get(w.source).add(w);
        }
    }

    static int[] dijkstra(int start, ArrayList<ArrayList<Edge>> adj) {

        int[] dist = new int[n + 1];

        for (int j = 0; j <= n; j++) {
            dist[j] = INF;
        }
        dist[start] = 0;

        queue = new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
        queue.add(new AbstractMap.SimpleEntry<>(start, 0));

        while (!queue.isEmpty()) {

            int v = queue.poll().getKey();

            for (int j = 0; j < adj.get(v).size(); j++) {

                int target = adj.get(v).get(j).target;
                int currDist = dist[target];

                if (dist[v] + adj.get(v).get(j).weight < currDist) {

                    AbstractMap.SimpleEntry tmp2 = new AbstractMap.SimpleEntry<>(target, dist[target]);

                    dist[target] = dist[v] + adj.get(v).get(j).weight;

                    if (queue.contains(tmp2)) {
                        queue.remove(tmp2);
                    }
                    queue.add(new AbstractMap.SimpleEntry<>(target, dist[target]));
                }
            }
        }
        return dist;
    }

    static void solve() {

        max = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            max[i] = -1;
            for (int j = 0; j <= allowedLength; j++) {
                dp[i][j] = -1;
            }
        }

        dp[n][0] = objects[n];
        max[n] = objects[n];

        boolean[] reached = new boolean[n + 1];
        reached[n] = true;

        for (int i = n; i > 0; i--) {
            if (reached[i]) {
                for (int j = 0; j < i; j++) {

                    if (matrix[i][j] != 0) {

                        for (int k = 0; k <= allowedLength; k++) {
                            if (dp[i][k] != -1) {//we can reach i in time=k
                                if (k + matrix[i][j] <= allowedLength) {

                                    reached[j] = true;

                                    if (objects[j] + dp[i][k] > dp[j][k + matrix[i][j]]) {
                                        dp[j][k + matrix[i][j]] = objects[j] + dp[i][k];

                                        if (max[j] < dp[j][k + matrix[i][j]]) {
                                            max[j] = dp[j][k + matrix[i][j]];
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

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int x, y, l;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            m = s.nextInt();
            g = s.nextInt();

            objects = new int[n + 1];
            adjLea = new ArrayList<>();
            adjClone = new ArrayList<>();

            matrix = new int[n + 1][n + 1];

            for (int i = 1; i <= n; i++) {
                objects[i] = s.nextInt();
                adjLea.add(new ArrayList<>());
                adjClone.add(new ArrayList<>());
            }

            adjLea.add(new ArrayList<>());
            adjClone.add(new ArrayList<>());

            for (int i = 1; i <= m; i++) {
                x = s.nextInt();
                y = s.nextInt();
                l = s.nextInt();

                if (x > y) { //big -> small
                    Edge e1 = new Edge(x, y, l);
                    addEdge(e1, false);//for clone
                    addEdge(e1, true);//for Lea

                    int tmp = matrix[x][y];
                    if (tmp > l || tmp == 0) {
                        matrix[x][y] = l;
                    }

                    Edge e2 = new Edge(y, x, l);
                    addEdge(e2, false);//for clone
                } else {
                    Edge e1 = new Edge(y, x, l);
                    addEdge(e1, false);//for clone
                    addEdge(e1, true);//for Lea

                    int tmp = matrix[y][x];
                    if (tmp > l || tmp == 0) {
                        matrix[y][x] = l;
                    }

                    Edge e2 = new Edge(x, y, l);
                    addEdge(e2, false);//for clone
                }
            }

            distLea = new int[n + 1];
            distClone = new int[n + 1];

            distLea = dijkstra(n, adjLea);
            distClone = dijkstra(g, adjClone);

            if (distLea[0] >= distClone[0]) {
                System.out.println("Case " + "#" + test + ": " + "impossible");
            } else {
                allowedLength = distClone[0] - 1;
                dp = new int[n + 1][allowedLength + 1];
                solve();
                System.out.println("Case " + "#" + test + ": " + max[0]);
            }
        }
        s.close();
    }
}
