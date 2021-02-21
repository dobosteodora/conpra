import java.util.*;

public class PartyPlanning {

    static int[] order;
    static Queue<Integer> queue;
    static ArrayList<ArrayList<Edge>> adj;
    static int pre[];
    static int i;
    static HashMap<Integer, Integer> topoOrder;

    static class Edge {
        int source;
        int target;
        int cost;

        Edge(int source, int target, int cost) {
            this.source = source;
            this.target = target;
            this.cost = cost;
        }
    }

    static void topoSort(int vertices) {
        for (int v = 0; v < vertices; v++) {
            order[v] = Integer.MAX_VALUE;
        }

        queue = new LinkedList<>();
        i = 1;

        for (int v = 0; v < vertices; v++) {
            if (pre[v] == 0) {
                explore(v);
            }
        }
    }

    static void explore(int v) {
        if (order[v] == Integer.MAX_VALUE) {
            queue.add(v);
        }

        while (!queue.isEmpty()) {
            v = queue.poll();

            order[v] = i;
            topoOrder.put(i, v);
            i++;

            for (int u = 0; u < adj.get(v).size(); u++) {
                pre[adj.get(v).get(u).target]--;
                if (pre[adj.get(v).get(u).target] == 0) {
                    queue.add(adj.get(v).get(u).target);
                }
            }
        }
    }

    static void addEdge(int v, Edge w, ArrayList<ArrayList<Edge>> adj) {
        adj.get(v).add(w);
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n;

        for (int test = 1; test <= t; test++) {

            n = s.nextInt();

            adj = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                adj.add(new ArrayList<>());
            }

            order = new int[n];
            pre = new int[n];

            int[] time = new int[n];

            for (int j = 0; j < n; j++) {
                int p = s.nextInt();
                time[j] = p;
                int succ = s.nextInt();
                for (int k = 0; k < succ; k++) {
                    int x = s.nextInt();
                    Edge e = new Edge(j, x - 1, p);
                    addEdge(j, e, adj);
                    pre[x - 1]++;
                }
            }

            topoOrder = new HashMap<>();

            topoSort(n);

            int dist[] = new int[n];
            for (int j = 0; j < n; j++) {
                dist[j] = Integer.MIN_VALUE;
            }

            dist[0] = 0;

            for (int j = 1; j <= n; j++) {
                int node = topoOrder.get(j);
                for (int k = 0; k < adj.get(node).size(); k++) {
                    if (dist[adj.get(node).get(k).target] < dist[node] + adj.get(node).get(k).cost) {
                        dist[adj.get(node).get(k).target] = dist[node] + adj.get(node).get(k).cost;
                    }
                }
            }
            System.out.println("Case #" + test + ": " + (dist[n - 1] + time[n - 1]));
        }
        s.close();
    }
}
