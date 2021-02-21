import java.util.*;

//inspired from https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/

public class RailroadNetwork {
    static ArrayList<ArrayList<Edge>> adj;
    static ArrayList<ArrayList<Edge>> residual;
    static int INF = Integer.MAX_VALUE;
    static int f;
    static int V;

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

    static void addEdge(int v, Edge w, ArrayList<ArrayList<Edge>> adj) {
        adj.get(v).add(w);
    }

    static boolean dfs(int s, int t, int[] parent, int n) {
        Stack<Integer> stack = new Stack<>();
        stack.push(s);

        for (int i = 0; i < V; i++) {
            parent[s] = -1;
        }

        int[] order = new int[V];
        for (int i = 0; i < V; i++) {
            order[i] = INF;
        }
        int index = 1;

        while (!stack.empty()) {
            int v = stack.pop();

            if (order[v] == INF) {
                order[v] = index;

                if (v == n) {
                    return true;
                }
                index++;

                for (int u = 0; u < residual.get(v).size(); u++) {
                    if (order[residual.get(v).get(u).target] == INF && residual.get(v).get(u).weight > 0) {
                        stack.add(residual.get(v).get(u).target);
                        parent[residual.get(v).get(u).target] = v;
                    }
                }
            }
        }
        return false;
    }

    static int fordFulkerson(int s, int t, int n) {

        Collections.copy(residual, adj);

        int[] parent = new int[V];
        f = 0;

        int u, v;

        while (dfs(s, t, parent, n)) {

            //find minimum residual capacity in the path discovered by DFS
            int pathFlow = INF;
            for (v = n; v != 1; v = parent[v]) {
                u = parent[v];

                int tmp = 0;
                for (int i = 0; i < residual.get(u).size(); i++) {
                    Edge e = residual.get(u).get(i);
                    if (e.target == v) {
                        tmp = e.weight;
                        break;
                    }
                }
                pathFlow = Math.min(pathFlow, tmp);
            }

            //update residual graph
            for (v = n; v != 1; v = parent[v]) {
                u = parent[v];

                int tmp = -1;

                Iterator itr1 = residual.get(u).iterator();

                while (itr1.hasNext()) {
                    Edge e = (Edge) itr1.next();
                    if (e.target == v) {
                        tmp = e.weight;
                        tmp -= pathFlow;
                        itr1.remove();
                        break;
                    }
                }

                Edge edge1 = new Edge(u, v, tmp);
                residual.get(u).add(edge1);

                tmp = -1;
                Iterator itr2 = residual.get(v).iterator();

                while (itr2.hasNext()) {
                    Edge e = (Edge) itr2.next();
                    if (e.target == u) {
                        tmp = e.weight;
                        tmp += pathFlow;
                        itr2.remove();
                        break;
                    }
                }
                if (tmp == -1) {
                    Edge edge2 = new Edge(v, u, pathFlow);
                    residual.get(v).add(edge2);
                } else {
                    Edge edge2 = new Edge(v, u, tmp);
                    residual.get(v).add(edge2);
                }
            }
            f += pathFlow;
        }
        return f;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, m;

        for (int test = 1; test <= t; test++) {

            n = s.nextInt();
            m = s.nextInt();

            //source: 0
            //target: n+1
            //n + 1 'original' vertices + 2*m 'fake' vertices
            adj = new ArrayList<>();
            for (int j = 0; j <= n + 1; j++) {
                adj.add(new ArrayList<>());
            }
            for (int j = 1; j <= 2 * m; j++) {
                adj.add(new ArrayList<>());
            }

            residual = new ArrayList<>();
            for (int j = 0; j <= n + 1; j++) {
                residual.add(new ArrayList<>());
            }
            for (int j = 1; j <= 2 * m; j++) {
                residual.add(new ArrayList<>());
            }

            //current fake node
            int fakeNode = n + 1 + 1;

            for (int j = 0; j < m; j++) {
                int x = s.nextInt();
                int y = s.nextInt();
                int z = s.nextInt();

                if (x != y) {
                    Edge e1 = new Edge(x, fakeNode, z);
                    addEdge(x, e1, adj);
                    Edge e11 = new Edge(fakeNode, y, z);
                    addEdge(fakeNode, e11, adj);

                    fakeNode++;

                    Edge e2 = new Edge(y, fakeNode, z);
                    addEdge(y, e2, adj);
                    Edge e22 = new Edge(fakeNode, x, z);
                    addEdge(fakeNode, e22, adj);

                    fakeNode++;
                }
            }

            Edge e1 = new Edge(0, 1, INF);
            adj.get(0).add(e1);

            Edge e2 = new Edge(n, n + 1, INF);
            adj.get(n).add(e2);

            V = n + 2 + 2 * m;

            fordFulkerson(0, n + 1, n);

            if (f == 0) {
                System.out.println("Case #" + test + ": " + "impossible");
            } else {
                System.out.println("Case #" + test + ": " + f);
            }
        }
        s.close();
    }
}
