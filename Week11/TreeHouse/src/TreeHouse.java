import java.util.*;

//solution inspired from https://www.geeksforgeeks.org/find-lca-in-binary-tree-using-rmq/

public class TreeHouse {

    static int v;
    static int euler[];
    static int depth[];
    static int f_occur[];
    static int fill;
    static St_class sc;
    static ArrayList<ArrayList<Integer>> adj;
    static int[] vis;
    static int N;

    static void add_edge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    static class St_class {
        int st;
        int stt[];/////////
    }


    static int RMQUtil(int index, int ss, int se, int qs, int qe, St_class st) {

        if (qs <= ss && qe >= se)
            return st.stt[index];

        else if (se < qs || ss > qe)
            return -1;

        int mid = (ss + se) / 2;

        int q1 = RMQUtil(2 * index + 1, ss, mid, qs, qe, st);
        int q2 = RMQUtil(2 * index + 2, mid + 1, se, qs, qe, st);

        if (q1 == -1)
            return q2;
        else if (q2 == -1)
            return q1;

        return (depth[q1] < depth[q2]) ? q1 : q2;
    }

    static int RMQ(St_class st, int n, int qs, int qe) {
        if (qs < 0 || qe > n - 1 || qs > qe) {
            return -1;
        }

        return RMQUtil(0, 0, n - 1, qs, qe, st);
    }


    static void constructSTUtil(int si, int ss, int se, int arr[], St_class st) {

        if (ss == se)
            st.stt[si] = ss;
        else {
            int mid = (ss + se) / 2;
            constructSTUtil(si * 2 + 1, ss, mid, arr, st);
            constructSTUtil(si * 2 + 2, mid + 1, se, arr, st);

            if (arr[st.stt[2 * si + 1]] < arr[st.stt[2 * si + 2]])
                st.stt[si] = st.stt[2 * si + 1];
            else
                st.stt[si] = st.stt[2 * si + 2];
        }
    }


    static int constructST(int arr[], int n) {

        int tmp = (int) (Math.ceil(Math.log(n) / Math.log(2)));
        int maxSize = 2 * (int) Math.pow(2, tmp) - 1;

        sc.stt = new int[maxSize];

        constructSTUtil(0, 0, n - 1, arr, sc);

        return sc.st;
    }


    static void eulerTour(int u, int l) {
        vis[u] = 1;

        euler[fill] = u;
        depth[fill] = l;
        fill++;

        if (f_occur[u] == -1) {
            f_occur[u] = fill - 1;
        }

        for (int it : adj.get(u)) {
            if (vis[it] == 0) {
                eulerTour(it, l + 1);
                euler[fill] = u;
                depth[fill] = l;
                fill++;
            }
        }
    }

    static int findLCA(int u, int v) {

        if (f_occur[u] > f_occur[v]) {
            int tmp = u;
            u = v;
            v = tmp;
        }

        // Starting and ending indexes of query range
        int qs = f_occur[u];
        int qe = f_occur[v];

        int index = RMQ(sc, N, qs, qe);

        return euler[index];
    }

    static int[] computeDepths(ArrayList<ArrayList<Integer>> graph, int V, int x) {
        int level[] = new int[V];
        boolean marked[] = new boolean[V];

        Queue<Integer> que = new LinkedList<>();

        que.add(x);
        level[x] = 0;

        marked[x] = true;
        while (que.size() > 0) {

            x = que.peek();
            que.remove();

            for (int i = 0; i < graph.get(x).size(); i++) {

                int b = graph.get(x).get(i);

                if (!marked[b]) {
                    que.add(b);
                    level[b] = level[x] + 1;

                    marked[b] = true;
                }
            }
        }
        return level;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, c, b;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();//number of nodes
            v = n;

            euler = new int[2 * v - 1]; // for euler tour sequence
            depth = new int[2 * v - 1]; // level of nodes in tour sequence
            f_occur = new int[2 * v - 1]; // to store 1st occurrence of nodes
            sc = new St_class();

            adj = new ArrayList<>();

            //construct adjacency matrix
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }

            for (int i = 0; i < n; i++) {

                c = s.nextInt(); //number of neighbors

                for (int j = 0; j < c; j++) {
                    b = s.nextInt(); //neighbor
                    add_edge(i, b - 1);
                }
            }
            //////////////////////////////////////
            vis = new int[n];
            fill = 0;
            Arrays.fill(f_occur, -1);

            eulerTour(0, 0);

            int[] levels = computeDepths(adj, n, 0);

            sc.st = constructST(depth, 2 * v - 1);

            int visitNr = s.nextInt();

            int first = 0;
            int next;

            int result = 0;

            N = 2 * v - 1;

            for (int i = 0; i < visitNr; i++) {
                next = s.nextInt();
                next--;

                int lca = findLCA(first, next);

                result += levels[first] - levels[lca];
                result += levels[next] - levels[lca];

                first = next;

            }
            System.out.println("Case " + "#" + test + ": " + result);
        }
        s.close();
    }
}
