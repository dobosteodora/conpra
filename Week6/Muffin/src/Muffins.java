import java.util.*;

public class Muffins {

    static HashSet<Integer> dishes;
    static HashSet<Integer> solution;
    static int m, n;
    static boolean ok;
    static HashSet<Integer> allSatisfied;
    static HashSet<Integer> notSatisfied;
    static ArrayList<ArrayList<Integer>> judgesPreferences;
    static HashMap<Integer, Integer> judgesMap; //key: index of the judge; value: number of his preferences
    static ArrayList<Integer> judges;

    static boolean otherDishInSolution(int dish) {
        if (solution.contains(-dish)) {
            return true;
        }
        return false;
    }

    static boolean solve(int index) {

        int judge = judges.get(index);

        if (!ok && !allSatisfied.contains(judge)) {
            for (int j = 0; j < judgesPreferences.get(judge - 1).size() && !ok; j++) {

                int dish = judgesPreferences.get(judge - 1).get(j);

                if (!otherDishInSolution(dish) && !solution.contains(dish)) {
                    solution.add(dish);
                    allSatisfied.add(judge);

                    HashSet<Integer> satisfied = new HashSet<>();
                    satisfied.add(judge);
                    notSatisfied.remove(judge);

                    Iterator<Integer> it = notSatisfied.iterator();
                    while (it.hasNext()) {
                        int tmp = it.next();
                        if (!allSatisfied.contains(tmp) && judgesPreferences.get(tmp - 1).contains(dish)) {
                            allSatisfied.add(tmp);
                            satisfied.add(tmp);
                        }
                    }

                    notSatisfied.removeAll(satisfied);

                    if (allSatisfied.size() == n) {
                        ok = true;
                        return true;
                    }
                    if (index <= judges.size() - 2) {
                        solve(index + 1);
                        solution.remove(dish);
                        allSatisfied.removeAll(satisfied);
                        notSatisfied.addAll(satisfied);
                    }
                }
            }
        } else if (!ok && index <= judges.size() - 2) {
            solve(index + 1);
        }
        return false;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {

            m = s.nextInt();
            n = s.nextInt();

            dishes = new HashSet<>();

            boolean impossible = false;
            judgesPreferences = new ArrayList<>();
            judgesMap = new HashMap<>();
            judges = new ArrayList<>();

            for (int i = 1; i <= n; i++) {
                boolean added = false;
                ArrayList<Integer> pref = new ArrayList<>();
                int number = s.nextInt();
                while (number != 0) {
                    pref.add(number);
                    dishes.add(number);
                    number = s.nextInt();
                    added = true;
                }
                if (!added) {
                    impossible = true;
                } else {
                    judgesPreferences.add(pref);
                    judgesMap.put(i, pref.size());
                    judges.add(i);
                }
            }

            solution = new HashSet<>();
            ok = false;

            if (impossible) {
                System.out.println("Case " + "#" + test + ": " + "no");
            } else {

                allSatisfied = new HashSet<>();
                notSatisfied = new HashSet<>();

                notSatisfied.addAll(judges);

                //sort judges in ascending order depending on the number of preferences
                Comparator<Integer> comparatorJudges = Comparator.comparing(e -> judgesMap.get(e));
                judges.sort(comparatorJudges);

                if (allSatisfied.size() == n) {
                    System.out.println("Case " + "#" + test + ": " + "yes");
                } else {

                    solve(0);

                    if (ok) {
                        System.out.println("Case " + "#" + test + ": " + "yes");
                    } else {
                        System.out.println("Case " + "#" + test + ": " + "no");
                    }
                }
            }
        }
        s.close();
    }
}
