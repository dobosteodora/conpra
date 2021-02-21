import java.util.*;

public class Networking {

    public static int[] size;
    public static int[] parent;

    public static int find(int a) {
        int root = a;
        int p;
        while (true) {
            p = parent[root];
            if (p == root)
                break;
            root = p;
        }
        int current = a;
        int next;
        while (current != root) {
            next = parent[current];
            parent[current] = root;
            current = next;
        }
        return root;
    }

    public static void union(int a, int b) {
        a = find(a);
        b = find(b);
        int tmp;

        if (a != b) {
            if (size[a] < size[b]) {
                tmp = a;
                a = b;
                b = tmp;
            }
            parent[b] = a;
            size[a] += size[b];
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();

            Edge edge[] = new Edge[n * (n - 1) / 2];

            parent = new int[n + 1];
            size = new int[n + 1];

            for (int i = 1; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;
            }

            int[][] distMatrix = new int[n + 1][n + 1];

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    distMatrix[i][j] = s.nextInt();
                }
            }

            int k = 0;
            for (int i = 1; i < n; i++) {
                for (int j = i + 1; j <= n; j++) {
                    Edge e = new Edge(i, j, distMatrix[i][j]);
                    edge[k] = e;
                    k++;
                }
            }

            Arrays.sort(edge);
            EdgeLexi result[] = new EdgeLexi[n - 1];
            k = 0;

            for (int i = 0; i < edge.length; i++) {
                if (find(edge[i].source) != find(edge[i].target)) {
                    union(edge[i].source, edge[i].target);
                    result[k] = new EdgeLexi(edge[i].source, edge[i].target, edge[i].weight);
                    k++;
                }
            }
            Arrays.sort(result);
            System.out.println("Case #" + test+ ": ");
            for(int i = 0; i < result.length; i++){
                System.out.println(result[i].source + " " + result[i].target);
            }
        }
        s.close();
    }
}
