import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Hiking {

    static ArrayList<ArrayList<Edge>> adj;
    static int[] dist;
    static int[] prev;
    static PriorityQueue<Node> pq;

    static class Node implements Comparable<Node> {
        int node;
        int dist;

        Node(int node, int cost) {
            this.node = node;
            this.dist = cost;
        }

        public int compareTo(Node node) {
            return this.dist - node.dist;
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


    static void addEdge(int v, Edge w, ArrayList<ArrayList<Edge>> adj) {
        adj.get(v).add(w);
    }

    static void dijkstra(int n) {

        dist = new int[n];
        prev = new int[n];
        for (int j = 0; j < n; j++) {
            dist[j] = Integer.MAX_VALUE;
            prev[j] = -1;
        }

        dist[0] = 0;

        pq = new PriorityQueue();
        for (int j = 0; j < n; j++) {
            pq.add(new Node(j, dist[j]));
        }

        while (!pq.isEmpty()) {
            Node v = pq.poll();
            for (int j = 0; j < adj.get(v.node).size(); j++) {

                Node w = new Node(adj.get(v.node).get(j).target, dist[adj.get(v.node).get(j).target]);

                if (dist[v.node] + adj.get(v.node).get(j).weight < dist[adj.get(v.node).get(j).target]) {
                    dist[adj.get(v.node).get(j).target] = dist[v.node] + adj.get(v.node).get(j).weight;
                    pq.remove(w);
                    pq.add(new Node(adj.get(v.node).get(j).target, dist[adj.get(v.node).get(j).target]));
                    prev[adj.get(v.node).get(j).target] = v.node;
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, m;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            m = s.nextInt();

            adj = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                adj.add(new ArrayList<>());
            }

            for (int j = 0; j < m; j++) {
                int v = s.nextInt();
                int w = s.nextInt();
                int c = s.nextInt();
                Edge e1 = new Edge(v - 1, w - 1, c);
                addEdge(v - 1, e1, adj);
                Edge e2 = new Edge(w - 1, v - 1, c);
                addEdge(w - 1, e2, adj);
            }
            dijkstra(n);
            System.out.println("Case " + "#" + test + ": " + dist[n - 1]);
        }
        s.close();
    }
}
