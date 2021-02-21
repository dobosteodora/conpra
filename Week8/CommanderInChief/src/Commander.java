import java.util.ArrayList;
import java.util.Scanner;

public class Commander {
    static int n;
    static ArrayList<Long> numbers;

    static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        } else return gcd(b, a % b);
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            numbers = new ArrayList<>();

            for (int i = 1; i <= n; i++) {
                long a = s.nextLong();
                numbers.add(a);
            }

            long tmp = gcd(numbers.get(0), numbers.get(1));

            for (int i = 2; i < numbers.size(); i++) {
                tmp = gcd(numbers.get(i), tmp);
            }
            System.out.println("Case " + "#" + test + ": " + tmp);
        }
        s.close();
    }
}
