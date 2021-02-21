import java.util.ArrayList;
import java.util.Scanner;

//method solve() inspired from https://www.geeksforgeeks.org/lcm-of-given-array-elements/

public class Candies {
    static int n;
    static int[] numbers;
    static ArrayList<Long> array;
    static long result;
    static int N;

    static long solve() {
        long lcm = 1;
        int divisor = 2;

        while (true) {
            int counter = 0;
            boolean divisible = false;

            for (int i = 0; i < array.size(); i++) {
                if (array.get(i) == 0) {
                    return 0;
                } else if (array.get(i) < 0) {
                    array.set(i, array.get(i) * (-1));
                }
                if (array.get(i) == 1) {
                    counter++;
                }
                if (array.get(i) % divisor == 0) {
                    divisible = true;
                    array.set(i, array.get(i) / divisor);
                }
            }

            if (divisible) {
                lcm = lcm * divisor;
            } else {
                divisor++;
            }
            if (counter == array.size()) {
                return lcm;
            }
        }
    }

    static void power2() {
        int res = 1;
        for (int i = 1; i <= n; i++) {
            res *= 2;
        }
        N = res;
    }

    static void sum() {
        for (int i = 1; i < N; i++) {
            long tmp = 1;

            String bin = Integer.toBinaryString(i);
            while (bin.length() < n) {
                bin = 0 + bin;
            }

            for (int j = 0; j < bin.length(); j++) {
                char c = bin.charAt(j);
                int nr = Integer.parseInt("" + c);
                tmp += nr * numbers[j];
            }
            if (!array.contains(tmp)) {
                array.add(tmp);
            }
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();

            numbers = new int[n];
            int index = 0;

            for (int i = 1; i <= n; i++) {
                int nr = s.nextInt();
                numbers[index] = nr;
                index++;
            }

            array = new ArrayList<>();

            power2();
            sum();
            result = solve();

            System.out.println("Case " + "#" + test + ": " + result);
        }
        s.close();
    }
}
