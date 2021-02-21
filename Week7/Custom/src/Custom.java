import java.util.*;

public class Custom {

    static HashMap<Integer, HashSet> adj;
    static int n;
    static HashSet<Integer> A;
    static HashSet<Integer> B;

    static int countEdges() {
        int result = 0;

        Iterator<Integer> it = A.iterator();
        while (it.hasNext()) {
            int node = it.next();

            HashSet<Integer> neighbors = new HashSet<>();
            neighbors.addAll(adj.get(node));

            Iterator<Integer> it2 = neighbors.iterator();
            while (it2.hasNext()) {
                int neighbor = it2.next();
                if (B.contains(neighbor) && !A.contains(neighbor)) {
                    result++;
                }
            }
        }
        return result;
    }

    static void partition() {
        for (int i = 2; i <= n; i++) {
            int node = i;
            A.add(node);
            int val1 = countEdges();

            A.remove(node);
            B.add(node);

            int val2 = countEdges();
            B.remove(node);

            if (val1 > val2) {
                A.add(node);
            } else {
                B.add(node);
            }
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            adj = new HashMap<>();
            for (int i = 1; i <= n; i++) {
                int k = s.nextInt();
                adj.put(i, new HashSet());
                for (int j = 1; j <= k; j++) {
                    int c = s.nextInt();
                    adj.get(i).add(c);
                }
            }

            A = new HashSet<>();
            A.add(1);
            B = new HashSet<>();
            partition();

            List<Integer> sortedList = new ArrayList<>(A);
            Collections.sort(sortedList);

            System.out.println("Case " + "#" + test + ":");
            for (int i = 0; i < sortedList.size(); i++) {
                System.out.print(sortedList.get(i) + " ");
            }
            System.out.println();
        }
        s.close();
    }
}
