//import java.io.*;
//import java.util.*;
//
//public class Test {
//
//    private static StringBuilder sb = new StringBuilder();
//
//    public static int[] order;
//    public static Queue<Integer> queue;
//    public static ArrayList<ArrayList<Integer>> adj;
//    public static int pre[];
//    public static int i;
//
//    public static void topoSort(int vertices) {
//
//        for (int v = 0; v < vertices; v++) {
//            order[v] = Integer.MAX_VALUE;
//        }
//
//        queue = new LinkedList<>();
//        i = 1;
//
//        for (int v = 0; v < vertices; v++) {
//            if (pre[v] == 0) {
//                explore(v);
//            }
//        }
//        //System.out.println("OUT TOPO");
//    }
//
//    public static void explore(int v) {
//        if (order[v] == Integer.MAX_VALUE) {
//            queue.add(v);
//        }
//
//        while (!queue.isEmpty()) {
//            v = queue.poll();
//
//            order[v] = i;
//            i++;
//
//            for (int u = 0; u < adj.get(v).size(); u++) {
//                pre[adj.get(v).get(u)]--;
//                if (pre[adj.get(v).get(u)] == 0) {
//                    queue.add(adj.get(v).get(u));
//                }
//            }
//        }
//    }
//
//    public static void addEdge(int v, int w, ArrayList<ArrayList<Integer>> adj) {
//        adj.get(v).add(w);
//    }
//
//    public static void solve(BufferedReader in, int test, int t) throws NumberFormatException, IOException {
//
//        int n, k, rem, d;
//
//        String[] parts = in.readLine().split(" ");
//
//        n = Integer.parseInt(parts[0]);
//        k = Integer.parseInt(parts[1]);
//        rem = Integer.parseInt(parts[2]);
//        d = Integer.parseInt(parts[3]);
//
//        HashSet<Integer> keep = new HashSet();
//        HashSet<Integer> remove = new HashSet<>();
//
//        HashSet<Integer> dep = new HashSet();//packages that should be kept because they are an (indirect) dependency
//
//        //ArrayList<ArrayList<Integer>> forPredecessors = new ArrayList<>();
//
////        for (int j = 0; j < n; j++) {
////            forPredecessors.add(new ArrayList<>());
////        }
//
//
//        String[] stringKeep = in.readLine().split(" ");
//        for (int j = 0; j < k; j++) {
//            int x = Integer.parseInt(stringKeep[j]);
//            keep.add(x - 1);
//
//            //dep.add(x - 1);///////
//        }
//
//        String[] stringRemove = in.readLine().split(" ");
//        for (int j = 0; j < rem; j++) {
//            int x = Integer.parseInt(stringRemove[j]);
//            remove.add(x - 1);
//        }
//
//        adj = new ArrayList<>();
//        for (int j = 0; j < n; j++) {
//            adj.add(new ArrayList<>());
//        }
//
//        order = new int[n];
//        pre = new int[n];
//
//
//        for (int j = 0; j < d; j++) {
//            String[] string = in.readLine().split(" ");
//            int x = Integer.parseInt(string[0]);
//            int y = Integer.parseInt(string[1]);
//            addEdge(y - 1, x - 1, adj);
//
//            //addEdge(x - 1, y - 1, forPredecessors);
//
//            pre[x - 1]++;
//
//        }
//
//        topoSort(n);
//
//        /////////
//        for (int j = 0; j < n; j++) {
//            System.out.println("order " + (j + 1) + " : " + order[j]);
//        }
//
//        Iterator<Integer> iterator = keep.iterator();
//        while (iterator.hasNext()) {
//            int a = iterator.next();
//
//            Stack<Integer> stack = new Stack();
//            stack.push(a);
//
//            boolean ok = false;
//
////            while (!stack.empty()) {
////                int b = stack.pop();
////
////                if(!dep.contains(b)){
////                    dep.add(b);
////                    for (int u = 0; u < forPredecessors.get(b).size(); u++) {
////                        stack.add((forPredecessors.get(b).get(u)));
////                    }
////                     ok = true;
////                }
////            }
//        }
//
//        Iterator<Integer> itRemove = remove.iterator();
//        int x, y;
//
//        boolean ok = true;
//
//        //leads to timelimit!!!!!!!!!!
//
////        Iterator<Integer> itDep = dep.iterator();
////        while (itDep.hasNext()) {
////            y = itDep.next();
////            //p = order[y];
////            //System.out.println("keep: " + (y+1));
////            if (remove.contains(y)) {
////                sb.append("conflict");
////                ok = false;
////                break;
////            }
////        }
//
//        while (itRemove.hasNext()) {
//            x = itRemove.next();
//            Iterator<Integer> itDep = dep.iterator();
//            while (itDep.hasNext()) {
//                y = itDep.next();
//                System.out.println("order y: " + order[y] + " |order x: " + order[x]);
//                if (order[y] == order[x]) {
//                    sb.append("conflict");
//                    ok = false;
//                    break;
//                }
//            }
//        }
//
//        if (ok) {
//            sb.append("ok");
//        }
//        if (test != t) {
//            in.readLine();
//        }
//
//    }
//
//    public static void main(String[] args) throws IOException {
//        BufferedReader in;
//        in = new BufferedReader(new InputStreamReader(System.in));
//
//        int t = Integer.parseInt(in.readLine());
//
//        for (int test = 1; test <= t; test++) {
//            sb.append("Case #" + test + ": ");
//            solve(in, test, t);
//            sb.append("\r\n");
//        }
//
//        System.out.println(sb.toString());
//    }
//}
