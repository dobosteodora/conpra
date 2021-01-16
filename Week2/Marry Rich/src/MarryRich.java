import java.util.*;

public class MarryRich {

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

    //the following method is inspired from https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
    public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm) {
        List<Map.Entry<Integer, Integer>> list =
                new LinkedList<Map.Entry<Integer, Integer>>(hm.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
////////////////////////////////////////////////////////

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        int money[], persons[];
        boolean married[];
        int numberPersons, numberRelations, numberMarriages;
        int tmp1, tmp2;

        for (int i = 1; i <= t; i++) {

            HashMap<Integer, Integer> personsMoney = new HashMap<Integer, Integer>();

            numberPersons = s.nextInt();
            numberRelations = s.nextInt();
            numberMarriages = s.nextInt();

            parent = new int[numberPersons + 1];
            size = new int[numberPersons + 1];
            married = new boolean[numberPersons + 1];

            for (int j = 1; j < numberPersons; j++) {
                personsMoney.put(j, s.nextInt());
                parent[j] = j;
                size[j] = 1;
            }
            parent[numberPersons] = numberPersons;
            size[numberPersons] = 1;

            ArrayList<ArrayList<Integer>> relations = new ArrayList<>();
            for (int j = 0; j <= numberPersons; j++) {
                relations.add(new ArrayList<Integer>());
            }

            for (int j = 0; j < numberRelations; j++) {
                addRelation(relations, s.nextInt(), s.nextInt());
            }

            for (int j = 0; j < numberMarriages; j++) {
                tmp1 = s.nextInt();
                tmp2 = s.nextInt();
                union(tmp1, tmp2);
                married[tmp1] = true;
                married[tmp2] = true;
            }

            for (int j = 0; j < relations.size(); j++) {
                for (int k = 0; k < relations.get(j).size(); k++) {
                    union(j, relations.get(j).get(k));
                }
            }

            if (married[numberPersons]) {
                System.out.println("Case #" + i + ": " + "impossible");
            } else {
                Map<Integer, Integer> sortedPersonsByMoney = sortByValue(personsMoney);

                boolean solved = false;
                int gain = 0;

                for (Map.Entry<Integer, Integer> en : sortedPersonsByMoney.entrySet()) {
                    if (find(en.getKey()) != find(numberPersons) && !married[en.getKey()]) {
                        solved = true;
                        gain = en.getValue();
                        break;
                    }
                }
                if (solved) {
                    System.out.println("Case #" + i + ": " + gain);
                } else {
                    System.out.println("Case #" + i + ": " + "impossible");
                }
            }
        }
        s.close();
    }
}
