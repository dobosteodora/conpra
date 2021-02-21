import java.util.*;

//topoSort algorithm inspired from: https://www.geeksforgeeks.org/all-topological-sorts-of-a-directed-acyclic-graph/

public class StoryTime {

    static int[] nrChapters;
    static int totalChapters;
    static int count;
    static HashMap<Integer, ArrayList<Integer>> adj;
    static HashMap<Integer, Integer> inDegreeCopy;
    static int n;

    static void addEdge(int src, int dest) {
        ArrayList<Integer> list = new ArrayList();
        if (adj.get(src).size() != 0) {
            list.addAll(adj.get(src));
        }
        list.add(dest);
        adj.replace(src, list);
    }

    static void allTopologicalSortsUtil(HashMap<Integer, Boolean> visited, HashMap<Integer, Integer> inDegree,
                                        ArrayList<Integer> stack) {

        boolean flag = false;

        Iterator it = adj.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry mapElement = (Map.Entry) it.next();
            int chapter = ((int) mapElement.getKey());

            if ((!visited.get(chapter) && inDegree.get(chapter) == 0 && stack.size() == 0) ||
                    (!visited.get(chapter) && inDegree.get(chapter) == 0 &&
                            stack.size() > 0 && chapter / 100 != stack.get(stack.size() - 1) / 100)) {

                visited.replace(chapter, true);
                stack.add(chapter);

                ArrayList<Integer> list = new ArrayList<>();
                list.addAll(adj.get(chapter));

                for (int i = 0; i < list.size(); i++) {
                    int deg = inDegree.get(list.get(i));
                    deg--;
                    inDegree.replace(list.get(i), deg);
                }
                allTopologicalSortsUtil(visited, inDegree, stack);


                visited.replace(chapter, false);
                stack.remove(stack.size() - 1);

                for (int i = 0; i < list.size(); i++) {
                    int deg = inDegree.get(list.get(i));
                    deg++;
                    inDegree.replace(list.get(i), deg);
                }
                flag = true;
            }
        }
        if (!flag && stack.size() == totalChapters) {
            count++;
        }
    }

    static void allTopologicalSorts() {

        HashMap<Integer, Boolean> visited = new HashMap<>();

        Iterator it = adj.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry mapElement = (Map.Entry) it.next();
            int chapter = ((int) mapElement.getKey());
            visited.put(chapter, false);
        }

        HashMap<Integer, Integer> inDegree = new HashMap<>();
        inDegree.putAll(inDegreeCopy);

        ArrayList<Integer> stack = new ArrayList<>();

        allTopologicalSortsUtil(visited, inDegree, stack);
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int m;
        int c, p, d, q;

        for (int test = 1; test <= t; test++) {

            n = s.nextInt();
            m = s.nextInt();

            totalChapters = 0;
            nrChapters = new int[n];
            for (int i = 0; i < n; i++) {
                nrChapters[i] = s.nextInt();
                totalChapters += nrChapters[i];
            }

            adj = new HashMap<>();
            inDegreeCopy = new HashMap<>();

            int first = 100;
            for (int i = 0; i < n; i++) {
                for (int j = 1; j <= nrChapters[i]; j++) {
                    adj.put(first + j, new ArrayList<>());
                    inDegreeCopy.put(first + j, 0);

                    if (j != 1) {
                        addEdge(first + j - 1, first + j);
                        int degree = adj.get(first + j).size();
                        degree++;
                        inDegreeCopy.replace(first + j, degree);
                    }
                }
                first += 100;
            }

            for (int i = 0; i < m; i++) {
                c = s.nextInt();
                p = s.nextInt();
                d = s.nextInt();
                q = s.nextInt();
                int before = 100 * c + p;
                int after = 100 * d + q;
                addEdge(before, after);

                int degree = inDegreeCopy.get(after);
                degree++;
                inDegreeCopy.replace(after, degree);
            }

            count = 0;
            allTopologicalSorts();

            System.out.println("Case " + "#" + test + ": " + count);
        }
        s.close();
    }
}
