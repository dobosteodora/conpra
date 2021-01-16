import java.util.ArrayList;
import java.util.Scanner;

public class Diplomacy {

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

    public static void addRelation(ArrayList<ArrayList<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
    }

    public static void setFriends(int a, int b) {
        union(a, b);
        union(a + 20000, b + 20000);
    }

    public static void setEnemies(int a, int b) {
        union(a, b + 20000);
        union(a + 20000, b);
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int n, m;
        String rel;
        int c1, c2;

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {

            n = s.nextInt();
            m = s.nextInt();

            //complementary node for i is 20000+i
            int offset = 20000;

            parent = new int[40000 + 1];
            size = new int[40000 + 1];

            for (int i = 1; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;

                parent[i + offset] = i + offset;
                size[i + offset] = 0;
            }


            ArrayList<ArrayList<Integer>> friends = new ArrayList<>();
            ArrayList<ArrayList<Integer>> enemies = new ArrayList<>();

            for (int j = 0; j < 2*n; j++) {
                friends.add(new ArrayList<>());
                enemies.add(new ArrayList<>());
            }

            for (int i = 0; i < m; i++) {

                rel = s.next();
                c1 = s.nextInt();
                c2 = s.nextInt();

                if (rel.equals("F")) {
                    addRelation(friends, c1, c2);
                    setFriends(c1, c2);
                } else if (rel.equals("A")) {
                    addRelation(enemies, c1, c2);
                    setEnemies(c1, c2);
                }
            }

            for(int j = 0; j < friends.size(); j++){
                for(int k = 0; k < friends.get(j).size(); k++){
                    setFriends(j, friends.get(j).get(k));
                }
            }

            for(int j = 0; j < enemies.size(); j++){
                for(int k = 0; k < enemies.get(j).size(); k++){
                    setEnemies(j, enemies.get(j).get(k));
                }
            }

            if (size[parent[1]] > n / 2) {
                System.out.println("Case #" + test + ": " + "yes");
            } else {
                System.out.println("Case #" + test + ": " + "no");
            }
        }
        s.close();
    }
}

