import java.util.*;

public class FineDining {
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

    static boolean dfs(int s, int t, int[] parent, int m, int b) {
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

                if (v == t) {
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

    static int fordFulkerson(int s, int t, int m, int b) {

        Collections.copy(residual, adj);

        int[] parent = new int[V];
        f = 0;

        int u, v;

        while (dfs(s, t, parent, m, b)) {

            //find minimum residual capacity in the path discovered by DFS
            int pathFlow = INF;
            for (v = t; v != s; v = parent[v]) {
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
            for (v = t; v != s; v = parent[v]) {
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
        int n, m, b;

        for (int test = 1; test <= t; test++) {

            n = s.nextInt();
            m = s.nextInt();
            b = s.nextInt();

            //source: 0
            //target: m + b + 1

            V = 1 + m + b + 1;

            adj = new ArrayList<>();
            residual = new ArrayList<>();
            for (int j = 1; j <= V; j++) {
                adj.add(new ArrayList<>());
                residual.add(new ArrayList<>());
            }

            for (int j = 1; j <= n; j++) {
                int menu = s.nextInt();
                int bev = s.nextInt();
                int node = m + bev;
                Edge e = new Edge(menu, node, 1);
                addEdge(menu, e, adj);
            }

            for (int j = 1; j <= m; j++) {
                Edge e = new Edge(0, j, 1);
                addEdge(0, e, adj);
            }

            for (int j = m + 1; j <= m + b; j++) {
                Edge e = new Edge(j, m + b + 1, 1);
                addEdge(j, e, adj);
            }

            fordFulkerson(0, m + b + 1, m, b);

            System.out.println("Case #" + test + ": " + f);
        }
        s.close();
    }
}
