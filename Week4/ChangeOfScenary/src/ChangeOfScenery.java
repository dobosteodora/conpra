import java.util.*;

//idea from https://stackoverflow.com/questions/40049959/how-to-find-whether-the-shortest-path-from-s-any-starting-vertex-to-v-any-ver

public class ChangeOfScenery {

    static ArrayList<ArrayList<Edge>> adj;
    static int[] dist;
    static PriorityQueue<Node> pq;
    static int INF = Integer.MAX_VALUE;
    static ArrayList<Integer> passedJunctions;
    static ArrayList<Edge> usedEdges;

    static class Node implements Comparable<Node> {
        int node;
        int dist;

        Node(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }

        public int compareTo(Node node) {
            if (this.dist - node.dist < 0) {
                return -1;
            }
            return 1;
        }
    }

    static class Edge implements Comparable<Edge> {
        int source;
        int target;
        int weight;
        int count;

        Edge(int source, int target, int weight, int count) {
            this.source = source;
            this.target = target;
            this.weight = weight;
            this.count = count;
        }

        public int compareTo(Edge compareEdge) {
            if (this.weight - compareEdge.weight < 0) {
                return -1;
            }
            return 1;
        }
    }


    static void addEdge(int v, Edge w, ArrayList<ArrayList<Edge>> adj) {
        boolean ok = true;

        int incrementCount = 0;

        Iterator itr = adj.get(v).iterator();

        while (itr.hasNext()) {
            Edge e = (Edge) itr.next();

            if (e.target == w.target) {
                if (e.weight > w.weight) {
                    itr.remove();
                } else if (e.weight < w.weight) {
                    ok = false;
                } else if (e.weight == w.weight) {
                    incrementCount = e.count;
                    itr.remove();
                }
            }
        }
        if (incrementCount != 0) {
            int count = incrementCount + 1;
            Edge e1 = new Edge(w.source, w.target, w.weight, count);
            adj.get(v).add(e1);
        } else if (ok) {
            adj.get(v).add(w);
        }
    }

    static boolean dijkstra(int n) {

        dist[0] = 0;

        pq = new PriorityQueue();
        pq.add(new Node(0, 0));

        while (!pq.isEmpty()) {

            int v = pq.poll().node;

            for (int j = 0; j < adj.get(v).size(); j++) {

                int target = adj.get(v).get(j).target;
                double currDist = dist[target];

                if (dist[v] + adj.get(v).get(j).weight < currDist) {

                    dist[target] = dist[v] + adj.get(v).get(j).weight;

                    pq.remove(target);
                    pq.add(new Node(target, dist[target]));

                } else if (dist[v] + adj.get(v).get(j).weight == dist[target]) {
                    if (passedJunctions.contains(target)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, m, k;

        for (int test = 1; test <= t; test++) {

            n = s.nextInt();
            m = s.nextInt();
            k = s.nextInt();

            adj = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                adj.add(new ArrayList<>());
            }

            passedJunctions = new ArrayList<>();

            for (int j = 0; j < k; j++) {
                int junction = s.nextInt();
                passedJunctions.add((junction - 1));
            }

            for (int j = 0; j < m; j++) {
                int x = s.nextInt();
                int y = s.nextInt();
                int z = s.nextInt();

                if (x != y) {
                    Edge e1 = new Edge(x - 1, y - 1, z, 1);
                    addEdge(x - 1, e1, adj);
                    Edge e2 = new Edge(y - 1, x - 1, z, 1);
                    addEdge(y - 1, e2, adj);
                }
            }

            dist = new int[n];

            for (int j = 0; j < n; j++) {
                dist[j] = INF;
            }

            dist[0] = 0;

            usedEdges = new ArrayList<>();

            int source = 0;
            int minPath = 0;

            for (int i = 1; i <= k - 1; i++) {
                int to = passedJunctions.get(i);
                int weight = adj.get(source).get(to).weight;
                dist[to] = weight;
                usedEdges.add(adj.get(source).get(to));
                minPath += weight;
                source = to;
            }

            boolean ok = dijkstra(n);

            if (ok) {
                System.out.println("Case #" + test + ": " + "yes");
            } else {
                System.out.println("Case #" + test + ": " + "no");
            }
        }
        s.close();
    }
}