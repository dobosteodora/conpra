import java.util.HashMap;
import java.util.Scanner;

public class Fractals {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, d;
        double a;
        String word;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            d = s.nextInt();
            a = s.nextDouble();
            word = s.next();

            System.out.println("Case " + "#" + test + ": ");

            HashMap<Character, String> productions = new HashMap<>();

            for (int i = 0; i < n; i++) {
                String line = s.next();

                char c = line.charAt(0);

                String r = "";

                for (int j = 3; j < line.length(); j++) {
                    r += line.charAt(j);
                }
                productions.put(c, r);
            }

            String res = "";
            String tmp = word;

            for (int i = 1; i <= d; i++) {
                for (int j = 0; j < tmp.length(); j++) {
                    if (tmp.charAt(j) != '+' && tmp.charAt(j) != '-') {
                        res += productions.get(tmp.charAt(j));
                    } else {
                        res += tmp.charAt(j);
                    }
                }
                tmp = res;
                res = "";
            }

            double x = 0;
            double y = 0;
            System.out.println(x + " " + y);

            double angle = 0;

            for (int i = 0; i < tmp.length(); i++) {
                if (tmp.charAt(i) != '+' && tmp.charAt(i) != '-') {

                    x += Math.cos(Math.toRadians(angle));
                    y += Math.sin(Math.toRadians(angle));

                    System.out.println(x + " " + y);
                } else if (tmp.charAt(i) == '-') {
                    angle -= a;

                } else if (tmp.charAt(i) == '+') {
                    angle += a;
                }
            }
        }
        s.close();
    }
}
