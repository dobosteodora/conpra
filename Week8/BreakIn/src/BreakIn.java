import java.math.BigInteger;
import java.util.Scanner;

public class BreakIn {
    static BigInteger y;
    static int n;

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            y = s.nextBigInteger();

            BigInteger ten = new BigInteger("10");
            BigInteger power = ten.pow(n);
            BigInteger res = y.modInverse(power);

            System.out.println("Case " + "#" + test + ": " + res);
        }
        s.close();
    }
}
