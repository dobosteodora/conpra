import java.util.*;

public class Supermarkets {

    static ArrayList<ArrayList<Edge>> adj;
    static HashMap<Integer, Integer> supermarkets;
    static int[] distA;
    static int[] prevA;
    static PriorityQueue<Node> pqA;
    static int[] distS;
    static int[] prevS;
    static PriorityQueue<Node> pqS;
    static int INF = 99999;

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
        boolean ok = true;

        Iterator itr = adj.get(v).iterator();

        while (itr.hasNext()) {
            Edge e = (Edge) itr.next();

            if (e.target == w.target) {
                if (e.weight > w.weight) {
                    itr.remove();
                } else {
                    ok = false;
                }
            }
        }
        if (ok) {
            adj.get(v).add(w);
        }
    }

    static void assignSupermarket(int city, int time) {

        if (supermarkets.containsKey(city)) {
            int prev = supermarkets.get(city);
            if (prev > time) {
                supermarkets.replace(city, time);
            }
        } else {
            supermarkets.put(city, time);
        }
    }

    static void dijkstraA(int n, int a) {

        distA = new int[n];
        prevA = new int[n];

        for (int j = 0; j < n; j++) {
            distA[j] = INF;
            prevA[j] = -1;
        }

        distA[a - 1] = 0;

        pqA = new PriorityQueue();
        for (int j = 0; j < n; j++) {
            pqA.add(new Node(j, distA[j]));
        }

        while (!pqA.isEmpty()) {
            Node v = pqA.poll();

            for (int j = 0; j < adj.get(v.node).size(); j++) {

                Node w = new Node(adj.get(v.node).get(j).target, distA[adj.get(v.node).get(j).target]);

                if (distA[v.node] + adj.get(v.node).get(j).weight < distA[adj.get(v.node).get(j).target]) {
                    distA[adj.get(v.node).get(j).target] = distA[v.node] + adj.get(v.node).get(j).weight;
                    pqA.remove(w);
                    pqA.add(new Node(adj.get(v.node).get(j).target, distA[adj.get(v.node).get(j).target]));
                    prevA[adj.get(v.node).get(j).target] = v.node;
                }
            }
        }
    }

    static void dijkstraB(int n, int b) {

        distS = new int[n];
        prevS = new int[n];

        for (int j = 0; j < n; j++) {
            distS[j] = INF;
            prevS[j] = -1;
        }

        distS[b - 1] = 0;

        pqS = new PriorityQueue();
        for (int j = 0; j < n; j++) {
            pqS.add(new Node(j, distS[j]));
        }

        while (!pqS.isEmpty()) {
            Node v = pqS.poll();

            for (int j = 0; j < adj.get(v.node).size(); j++) {

                Node w = new Node(adj.get(v.node).get(j).target, distS[adj.get(v.node).get(j).target]);

                if (distS[v.node] + adj.get(v.node).get(j).weight < distS[adj.get(v.node).get(j).target]) {
                    distS[adj.get(v.node).get(j).target] = distS[v.node] + adj.get(v.node).get(j).weight;
                    pqS.remove(w);
                    pqS.add(new Node(adj.get(v.node).get(j).target, distS[adj.get(v.node).get(j).target]));
                    prevS[adj.get(v.node).get(j).target] = v.node;
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, m, sa, a, b;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            m = s.nextInt();
            sa = s.nextInt();//supermarkets
            a = s.nextInt();//lea
            b = s.nextInt();//peter

            adj = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                adj.add(new ArrayList<>());
            }

            for (int j = 0; j < m; j++) {
                int x = s.nextInt();
                int y = s.nextInt();
                int z = s.nextInt();
                Edge e1 = new Edge(x - 1, y - 1, z);
                addEdge(x - 1, e1, adj);
                Edge e2 = new Edge(y - 1, x - 1, z);
                addEdge(y - 1, e2, adj);
            }

            supermarkets = new HashMap<>();

            for (int j = 0; j < sa; j++) {
                int c = s.nextInt();
                int w = s.nextInt();
                assignSupermarket(c - 1, w);
            }

            dijkstraA(n, a);//dijkstra from city a to each city
            dijkstraB(n, b);//dijkstra from city b to each city => find distances to all supermarkets

            boolean impossible = false;

            if (distA[b - 1] == INF) {
                System.out.format("Case #%d: %s\n", test, "impossible");
                impossible = true;
            }

            int min = INF;

            if (!impossible) {
                for (int j = 0; j < n; j++) {
                    if (supermarkets.containsKey(j)) {
                        //city j can be reached from cities a and b
                        if (distA[j] != INF && distS[j] != INF) {
                            if (min > distA[j] + distS[j] + supermarkets.get(j)) {
                                min = distA[j] + distS[j] + supermarkets.get(j);
                            }
                        }
                    }
                }

                if (min == INF) {
                    System.out.format("Case #%d: %s\n", test, "impossible");
                } else {
                    int hours = min / 60;
                    int minutes = min % 60;
                    System.out.format("Case #%d: %d:%02d%n", test, hours, minutes);
                }
            }
        }
        s.close();
    }
}
