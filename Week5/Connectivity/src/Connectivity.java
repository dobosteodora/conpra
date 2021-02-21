import java.util.*;

public class Connectivity {

    static ArrayList<ArrayList<Edge>> adj;
    static ArrayList<ArrayList<Edge>> residual;
    static int INF = Integer.MAX_VALUE;
    static int V;

    static class Edge {
        int source;
        int target;
        int capacity;
        int flow;

        Edge(int source, int target, int capacity, int flow) {
            this.source = source;
            this.target = target;
            this.capacity = capacity;
            this.flow = flow;
        }
    }

    static void addEdge(int v, Edge w, ArrayList<ArrayList<Edge>> adj) {
        adj.get(v).add(w);
    }

    static boolean bfs(int s, int t, int dist[]) {
        for (int i = 0; i < dist.length; i++) {
            dist[i] = -1;
        }
        dist[s] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int i = 0; i < residual.get(u).size(); i++) {
                Edge e = residual.get(u).get(i);
                if (dist[e.target] < 0 && e.flow < e.capacity) {
                    dist[e.target] = dist[u] + 1;
                    queue.add(e.target);
                }
            }

        }
        if (dist[t] < 0) {
            return false;
        }
        return true;
    }

    static int dinic(int[] ptr, int[] dist, int dest, int u, int f) {

        if (u == dest)
            return f;

        for (; ptr[u] < residual.get(u).size(); ++ptr[u]) {

            Iterator itr1 = residual.get(u).iterator();

            while (itr1.hasNext()) {
                Edge e = (Edge) itr1.next();
                if (e.target == ptr[u]) {
                    if (dist[e.target] == dist[u] + 1 && e.flow < e.capacity) {

                        int df = dinic(ptr, dist, dest, e.target, Math.min(f, e.capacity - e.flow));
                        if (df > 0) {
                            itr1.remove();

                        }
                    }
                    break;
                }
            }
        }
        return 0;
    }

    static int maxFlow(int src, int dest) {
        int flow = 0;
        int[] dist = new int[V];
        while (bfs(src, dest, dist)) {
            int[] ptr = new int[V];
            while (true) {
                int df = dinic(ptr, dist, dest, src, Integer.MAX_VALUE);
                if (df == 0)
                    break;
                flow += df;
            }
        }
        return flow;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, m;

        for (int test = 1; test <= t; test++) {

            n = s.nextInt();
            m = s.nextInt();

            V = 2 * n + 2 * m + 2;

            //source: 0
            //target: 2n + 2m + 1
            adj = new ArrayList<>();
            residual = new ArrayList<>();
            for (int j = 1; j <= 2 * n + 2 * m + 2; j++) {
                adj.add(new ArrayList<>());
                residual.add(new ArrayList<>());
            }

            //current fake node
            int fakeNode = n + 1; //node 1out

            for (int i = 1; i <= n; i++) {
                Edge e = new Edge(i, fakeNode, 1, 0);
                addEdge(i, e, adj);
                fakeNode++;
            }

            fakeNode = 2 * n + 1;

            for (int j = 0; j < m; j++) {
                int x = s.nextInt();
                int y = s.nextInt();

                int from = n + x;
                Edge e1 = new Edge(from, fakeNode, 1, 0);
                addEdge(from, e1, adj);

                Edge e11 = new Edge(fakeNode, y, 1, 0);
                addEdge(fakeNode, e11, adj);

                fakeNode++;

                from = n + y;
                Edge e2 = new Edge(from, fakeNode, 1, 0);
                addEdge(from, e2, adj);
                Edge e22 = new Edge(fakeNode, x, 1, 0);
                addEdge(fakeNode, e22, adj);

                fakeNode++;
            }

            Edge e1 = new Edge(0, 1, INF, 0);
            adj.get(0).add(e1);

            Edge e2 = new Edge(n, 2 * n + 2 * m + 1, INF, 0);
            adj.get(n).add(e2);

            Collections.copy(residual, adj);
            int f = maxFlow(0, 2 * n + 2 * m + 1);

            System.out.println("Case #" + test + ": " + f);
        }
        s.close();
    }
}
