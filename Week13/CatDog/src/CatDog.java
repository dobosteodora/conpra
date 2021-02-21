import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//solution heavily inspired from http://icpc.cs.uchicago.edu/tryouts2015/pset/index.html
//http://2008.nwerc.eu/problems/nwerc08-solutions.pdf

public class CatDog {

    static class Preference {
        int love;
        int hate;

        Preference(int love, int hate) {
            this.love = love;
            this.hate = hate;
        }
    }

    static boolean augment(int current, ArrayList<ArrayList<Integer>> edges, boolean[] used, int[] match) {
        if (used[current]) {
            return false;
        } else {
            used[current] = true;
        }

        for (int i = 0; i < edges.get(current).size(); i++) {
            int next = edges.get(current).get(i);
            if (match[next] == -1 || augment(match[next], edges, used, match)) {
                match[next] = current;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int c, d, v;

        for (int test = 1; test <= t; test++) {
            c = s.nextInt();
            d = s.nextInt();
            v = s.nextInt();

            String love, hate;
            int l, h;

            ArrayList<Preference> pref = new ArrayList<>();
            ArrayList<Preference> loveCats = new ArrayList<>();
            ArrayList<Preference> loveDogs = new ArrayList<>();

            for (int i = 0; i < v; i++) {
                love = s.next();
                hate = s.next();

                l = Integer.parseInt(love.substring(1));
                h = Integer.parseInt(hate.substring(1));

                Preference p = new Preference(l, h);
                pref.add(p);
                if (love.charAt(0) == 'C') {
                    loveCats.add(p);
                } else {
                    loveDogs.add(p);
                }
            }

            ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
            for (int i = 0; i < loveCats.size(); i++) {
                edges.add(new ArrayList<>());
            }

            for (int i = 0; i < loveCats.size(); i++) {
                for (int j = 0; j < loveDogs.size(); j++) {
                    if (loveCats.get(i).love == loveDogs.get(j).hate || loveCats.get(i).hate == loveDogs.get(j).love)
                        edges.get(i).add(j);
                }
            }

            int[] match = new int[loveDogs.size()];
            Arrays.fill(match, -1);

            int flowVal = loveCats.size() + loveDogs.size();

            for (int i = 0; i < loveCats.size(); i++) {
                boolean[] used = new boolean[loveCats.size()];
                if (augment(i, edges, used, match)) {
                    flowVal--;
                }
            }
            System.out.println("Case " + "#" + test + ": " + flowVal);
        }
        s.close();
    }
}
