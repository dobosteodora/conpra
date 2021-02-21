import java.util.*;

//idea from https://towardsdatascience.com/graph-theory-center-of-a-tree-a64b63f9415d

public class CandyStore {

    public static int[] order;
    public static ArrayList<ArrayList<Integer>> adj;
    public static int i;


    public static void addEdge(int v, int w, ArrayList<ArrayList<Integer>> adj) {
        adj.get(v).add(w);
    }

    public static int BFSexplore(int v) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(v);

        int last = v;

        while (!queue.isEmpty()) {
            int w = queue.poll();

            if (w == v) {
                order[v] = i;
                i++;
                for (int u = 0; u < adj.get(w).size(); u++) {
                    queue.add(adj.get(w).get(u));
                }
                //level[w] = 0;
            }
            if (order[w] == Integer.MAX_VALUE) {
                order[w] = i;
                i++;
                for (int u = 0; u < adj.get(w).size(); u++) {
                    queue.add(adj.get(w).get(u));
                    //level[adj.get(w).get(u)] = level[w] + 1;
//                    if(level[adj.get(w).get(u)] > max){
//                        max = level[adj.get(w).get(u)];
//                    }
                }

                last = w;
                System.out.println(last);
            }
        }
        return last;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n;

        for (int test = 1; test <= t; test++) {

            n = s.nextInt();

            adj = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }

            int[] deg = new int[n];

            for (int j = 1; j <= n - 1; j++) {
                int x = s.nextInt();
                int y = s.nextInt();
                addEdge(x - 1, y - 1, adj);
                addEdge(y - 1, x - 1, adj);
                deg[x - 1]++;
                deg[y - 1]++;
            }

            ArrayList<Integer> leaves = new ArrayList();
            for (int j = 0; j < n; j++) {
                if (deg[j] == 1) {
                    leaves.add(j);
                    deg[j] = 0;
                }
            }
            int count = leaves.size();

            while (count < n) {
                ArrayList newLeaves = new ArrayList();
                for (int j = 0; j < leaves.size(); j++) {
                    for (int k = 0; k < adj.get(leaves.get(j)).size(); k++) {
                        deg[adj.get(leaves.get(j)).get(k)]--;
                        if (deg[adj.get(leaves.get(j)).get(k)] == 1) {
                            newLeaves.add(adj.get(leaves.get(j)).get(k));
                        }
                    }
                }
                count += newLeaves.size();
                leaves = newLeaves;
            }
            System.out.println("Case #" + test + ": " + (leaves.get(0) + 1));
        }
        s.close();
    }
}
