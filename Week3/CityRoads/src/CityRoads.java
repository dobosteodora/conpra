import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CityRoads {

    public static int[] order;
    public static Queue<Integer> queue;
    public static ArrayList<ArrayList<Integer>> adj;
    public static int pre[];
    public static int i;

    public static void topoSort(int vertices) {
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

    public static void explore(int v) {
        if (order[v] == Integer.MAX_VALUE) {
            queue.add(v);
        }

        while (!queue.isEmpty()) {
            v = queue.poll();

            order[v] = i;
            i++;

            for (int u = 0; u < adj.get(v).size(); u++) {
                pre[adj.get(v).get(u)]--;
                if (pre[adj.get(v).get(u)] == 0) {
                    queue.add(adj.get(v).get(u));
                }
            }
        }
    }

    public static void addEdge(int v, int w, ArrayList<ArrayList<Integer>> adj) {
        adj.get(v).add(w);
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, m, l;

        for (int test = 1; test <= t; test++) {

            n = s.nextInt();
            m = s.nextInt();
            l = s.nextInt();

            adj = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                adj.add(new ArrayList<>());
            }

            order = new int[n];
            pre = new int[n];

            for (int j = 0; j < m; j++) {
                int x = s.nextInt();
                int y = s.nextInt();
                addEdge(x - 1, y - 1, adj);
                pre[y - 1]++;
            }

            ArrayList<Edge> undirected = new ArrayList<>();

            for (int j = 0; j < l; j++) {
                int x = s.nextInt();
                int y = s.nextInt();
                undirected.add(new Edge(x, y));
            }

            topoSort(n);

            boolean no = false;
            for (int j = 0; j < order.length; j++) {
                if (order[j] == Integer.MAX_VALUE) {
                    System.out.println("Case #" + test + ": " + "no");
                    no = true;
                    break;
                }
            }

            if(!no){
                System.out.println("Case #" + test + ": " + "yes");

                for (Edge e : undirected) {
                    int x = e.source;
                    int y = e.target;
                    if (order[x - 1] > order[y - 1]) {
                        System.out.println(y + " " + x);
                    } else {
                        System.out.println(x + " " + y);
                    }
                }
            }
        }
        s.close();
    }
}
