import java.util.Scanner;

public class SodaSlurper {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int e, f, c;

        for (int test = 1; test <= t; test++) {
            e = s.nextInt();
            f = s.nextInt();
            c = s.nextInt();

            int result = 0;
            int buy = (e + f) / c;
            int rest = (e + f) % c;

            result += buy;
            rest += buy;

            while (rest >= c) {
                buy = rest / c;
                result += buy;
                rest -= buy * c;
                rest += buy;
            }
            System.out.println("Case " + "#" + test + ": " + result);
        }
        s.close();
    }
}
