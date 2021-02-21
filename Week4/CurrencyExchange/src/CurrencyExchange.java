import java.util.*;

public class CurrencyExchange {

    static ArrayList<ArrayList<Edge>> adj;
    static double[] dist;
    static int[] prev;
    static Queue<Integer> q;
    static Queue<Integer> qPrime;
    static boolean impossible;
    static boolean jackpot;

    static class Edge implements Comparable<Edge> {
        int source;
        int target;
        double weight;

        Edge(int source, int target, double weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        public int compareTo(Edge compareEdge) {
            return (int) this.weight - (int) compareEdge.weight;
        }
    }


    static void bellmanFord(int n) {
        dist = new double[n];
        prev = new int[n];
        impossible = false;
        jackpot = false;

        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }
        dist[0] = 0;
        q = new LinkedList();
        qPrime = new LinkedList();
        q.add(0);

        for (int phase = 1; phase <= n; phase++) {
            while (!q.isEmpty()) {
                int v = q.poll();
                for (int i = 0; i < adj.get(v).size(); i++) {
                    if (dist[v] + adj.get(v).get(i).weight < dist[adj.get(v).get(i).target]) {
                        dist[adj.get(v).get(i).target] = dist[v] + adj.get(v).get(i).weight;
                        prev[adj.get(v).get(i).target] = v;
                        if (!qPrime.contains(adj.get(v).get(i).target)) {
                            qPrime.add(adj.get(v).get(i).target);
                        }
                    }
                }
            }
            //swap queues
            Queue<Integer> tmp = new LinkedList<>(q);
            q = new LinkedList<>(qPrime);
            qPrime = new LinkedList<>(tmp);
        }
        if (!q.isEmpty()) {
            jackpot = true;
        }
        if (dist[n - 1] == Integer.MAX_VALUE) {
            impossible = true;
        }
    }

    static void addEdge(int v, Edge w, ArrayList<ArrayList<Edge>> adj) {
        adj.get(v).add(w);
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
                int a = s.nextInt();
                int b = s.nextInt();
                double c = s.nextDouble();
                double logCost = Math.log10(c);
                Edge e = new Edge(a - 1, b - 1, logCost);
                addEdge(a - 1, e, adj);
            }

            bellmanFord(n);

            if (jackpot) {
                System.out.println("Case " + "#" + test + ": " + "Jackpot");
            } else if (impossible) {
                System.out.println("Case " + "#" + test + ": " + "impossible");
            } else {
                System.out.println("Case " + "#" + test + ": " + Math.pow(10, dist[n - 1]));
            }
        }
        s.close();
    }
}
