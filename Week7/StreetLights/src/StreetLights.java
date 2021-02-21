import java.util.*;

public class StreetLights {
    static ArrayList<Integer> lights;

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int l, n, d;

        for (int test = 1; test <= t; test++) {
            l = s.nextInt();
            n = s.nextInt();
            d = s.nextInt();

            lights = new ArrayList<>();

            for (int i = 1; i <= n; i++) {
                int light = s.nextInt();
                lights.add(light);
            }

            Collections.sort(lights);

            boolean impossible = false;
            int nr = 0;

            if (n == 0 || d == 0) {
                impossible = true;
            } else {
                if (lights.get(0) - d > 0) {
                    impossible = true;
                } else {
                    int answer;
                    answer = 0;

                    for (int i = 0; i < n - 1 && answer < l; i++) {
                        if (answer < lights.get(i) - d) {
                            impossible = true;
                            break;
                        } else if (answer >= lights.get(i) - d && answer < lights.get(i + 1) - d) {
                            answer = lights.get(i) + d;
                            nr++;
                        }
                    }
                    if (answer >= lights.get(n - 1) - d && answer < l) {
                        answer = lights.get(n - 1) + d;
                        nr++;
                    }
                    if (answer < l) {
                        impossible = true;
                    }
                }
            }
            if (impossible) {
                System.out.println("Case " + "#" + test + ": " + "impossible");
            } else {
                System.out.println("Case " + "#" + test + ": " + nr);
            }
        }
        s.close();
    }
}
