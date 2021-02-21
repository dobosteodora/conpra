import java.io.*;
import java.util.*;

public class LibraryHell {

    private static StringBuilder sb = new StringBuilder();

    public static int[] order;
    public static ArrayList<ArrayList<Integer>> adj;

    public static void addEdge(int v, int w, ArrayList<ArrayList<Integer>> adj) {
        adj.get(v).add(w);
    }

    public static void solve(BufferedReader in, int test, int t) throws NumberFormatException, IOException {

        int n, k, rem, d;

        String[] parts = in.readLine().split(" ");

        n = Integer.parseInt(parts[0]);
        k = Integer.parseInt(parts[1]);
        rem = Integer.parseInt(parts[2]);
        d = Integer.parseInt(parts[3]);

        HashSet<Integer> keep = new HashSet();
        HashSet<Integer> remove = new HashSet<>();
        HashSet<Integer> depRemove = new HashSet<>();

        String[] stringKeep = in.readLine().split(" ");
        for (int j = 0; j < k; j++) {
            int x = Integer.parseInt(stringKeep[j]);
            keep.add(x - 1);
        }

        String[] stringRemove = in.readLine().split(" ");
        for (int j = 0; j < rem; j++) {
            int x = Integer.parseInt(stringRemove[j]);
            remove.add(x - 1);
        }

        adj = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            adj.add(new ArrayList<>());
        }

        order = new int[n];

        for (int j = 0; j < d; j++) {
            String[] string = in.readLine().split(" ");
            int x = Integer.parseInt(string[0]);
            int y = Integer.parseInt(string[1]);
            addEdge(y - 1, x - 1, adj);
        }

        //compute dependencies for the packages that must be removed

        for (int v = 0; v < n; v++) {
            order[v] = Integer.MAX_VALUE;
        }

        depRemove.addAll(remove);

        int index = 1;
        Stack<Integer> stack = new Stack();

        Iterator<Integer> iterator = remove.iterator();
        while (iterator.hasNext()) {
            int a = iterator.next();

            if (order[a] == Integer.MAX_VALUE) {
                stack.push(a);

                while (!stack.empty()) {
                    int b = stack.pop();

                    if (order[b] == Integer.MAX_VALUE) {
                        order[b] = index;
                        index++;
                        depRemove.add(b);
                        for (int u = 0; u < adj.get(b).size(); u++) {
                            stack.add(adj.get(b).get(u));
                        }
                    }
                }
            }
        }

        boolean ok = true;

        Iterator<Integer> keepIt = keep.iterator();
        while (keepIt.hasNext()) {
            int a = keepIt.next();
            if (depRemove.contains(a)) {
                sb.append("conflict");
                ok = false;
                break;
            }
        }
        
        if (ok) {
            sb.append("ok");
        }
        if (test != t) {
            in.readLine();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in;
        in = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(in.readLine());

        for (int test = 1; test <= t; test++) {
            sb.append("Case #" + test + ": ");
            solve(in, test, t);
            sb.append("\r\n");
        }
        System.out.println(sb.toString());
    }
}
