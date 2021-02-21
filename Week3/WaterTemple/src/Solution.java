import java.util.*;

public class Solution {
    public static int[] order;
    public static int i;
    public static ArrayList<ArrayList<Edge>> adj;
    public static HashMap<Integer, Integer> controlRooms;
    public static HashSet<Integer> discovered;
    public static int currentLevel;
    public static HashSet<Integer> discoveredControlRooms;
    public static PriorityQueue<Edge> pqHallways;
    public static Queue<Integer> queue;
    public static int possibleLevel;
    public static int lastDiscovered;

    public static void addEdge(int v, Edge w, ArrayList<ArrayList<Edge>> adj) {
        adj.get(v).add(w);
    }

    public static void blocked(int minLevel, int n) {

        boolean ok = false;

        if (!pqHallways.isEmpty()) {

            Edge e = pqHallways.poll();

            if (e.weight >= possibleLevel) {

                ok = true;
                currentLevel = e.weight;
                discovered.add(e.target);
                lastDiscovered = e.target;

                if (controlRooms.containsKey(e.target)) {
                    if (controlRooms.get(e.target) < possibleLevel) {
                        possibleLevel = controlRooms.get(e.target);
                    }
                }

                queue.add(e.target);

                boolean added = true;

                while (!pqHallways.isEmpty() && added) {

                    Edge edge = pqHallways.peek();

                    if (currentLevel == edge.weight) {

                        pqHallways.poll();
                        discovered.add(edge.target);
                        lastDiscovered = edge.target;

                        if (controlRooms.containsKey(edge.target)) {
                            if (controlRooms.get(edge.target) < possibleLevel) {
                                possibleLevel = controlRooms.get(edge.target);
                            }
                        }
                        queue.add(edge.target);
                    } else {
                        added = false;
                    }
                }
            } else {
                ok = false;
            }
        }
        if (ok) {
            BFSexplore(lastDiscovered, minLevel, n);
        }
    }

    public static void BFSexplore(int v, int minLevel, int n) {

        boolean continueSearch = true;

        queue.add(v);
        discovered.add(v);
        lastDiscovered = v;

        if (controlRooms.containsKey(v)) {
            if (controlRooms.get(v) < possibleLevel) {
                possibleLevel = controlRooms.get(v);
            }
        }

        while (!queue.isEmpty() && discovered.size() != n && continueSearch) {

            int w = queue.poll();

            order[w] = i;
            i++;

            for (int j = 0; j < adj.get(w).size(); j++) {

                if (order[adj.get(w).get(j).target] == Integer.MAX_VALUE) {

                    if (adj.get(w).get(j).weight >= currentLevel) {

                        discovered.add(adj.get(w).get(j).target);
                        lastDiscovered = adj.get(w).get(j).target;
                        order[adj.get(w).get(j).target] = 0;

                        if (controlRooms.containsKey(adj.get(w).get(j).target)) {
                            if (controlRooms.get(adj.get(w).get(j).target) < possibleLevel) {
                                possibleLevel = controlRooms.get(adj.get(w).get(j).target);
                            }
                        }
                        queue.add(adj.get(w).get(j).target);
                    } else {
                        pqHallways.add(adj.get(w).get(j));
                    }
                }
            }
        }
        if (discovered.size() != n) {
            blocked(minLevel, n);
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, m, k, l;

        for (int test = 1; test <= t; test++) {

            n = s.nextInt();
            m = s.nextInt();
            k = s.nextInt();
            l = s.nextInt();

            adj = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                adj.add(new ArrayList<Edge>());
            }

            controlRooms = new HashMap();

            for (int j = 0; j < m; j++) {
                int x = s.nextInt();//source
                int y = s.nextInt();//target
                int z = s.nextInt();//weight

                Edge e1 = new Edge(x - 1, y - 1, z);
                addEdge(x - 1, e1, adj);

                Edge e2 = new Edge(y - 1, x - 1, z);
                addEdge(y - 1, e2, adj);
            }

            for (int j = 0; j < k; j++) {
                int room = s.nextInt() - 1;
                int level = s.nextInt();
                controlRooms.put(room, level);
            }

            int minLevel = l;
            for (Map.Entry<Integer, Integer> entry : controlRooms.entrySet()) {
                if (entry.getValue() < minLevel) {
                    minLevel = entry.getValue();
                }
            }

            currentLevel = l;
            discovered = new HashSet<>();

            Comparator<Edge> edgeComparator = new Comparator<Edge>() {
                @Override
                public int compare(Edge e1, Edge e2) {
                    return e2.weight - e1.weight;
                }
            };

            discoveredControlRooms = new HashSet<>();
            pqHallways = new PriorityQueue<>(edgeComparator);
            order = new int[n];

            for (int j = 0; j < n; j++) {
                order[j] = Integer.MAX_VALUE;
            }

            i = 1;
            queue = new LinkedList<>();
            possibleLevel = Integer.MAX_VALUE;

            BFSexplore(0, minLevel, n);

            if (discovered.size() != n) {
                System.out.println("Case #" + test + ": " + "impossible");
            } else {
                System.out.println("Case #" + test + ": " + currentLevel);
            }
        }
        s.close();
    }
}
