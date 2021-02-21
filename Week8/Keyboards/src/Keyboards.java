import java.math.BigInteger;
import java.util.*;

public class Keyboards {
    static BigInteger s1, s2, c1, c2, n;
    static BigInteger[] extended;
    static boolean impossible;

    static BigInteger gcd(BigInteger a, BigInteger b) {
        BigInteger gcd = a.gcd(b);
        return gcd;
    }

    static BigInteger[] eea(BigInteger a, BigInteger b) {
        BigInteger q, m, n, r;
        BigInteger[] res = new BigInteger[2];
        BigInteger ca = a;
        BigInteger cb = b;

        BigInteger x = new BigInteger("0");
        BigInteger y = new BigInteger("1");
        BigInteger u = new BigInteger("1");
        BigInteger v = new BigInteger("0");

        while (!ca.equals(new BigInteger("0"))) {
            q = cb.divide(ca);
            r = cb.mod(ca);
            m = x.subtract(u.multiply(q));
            n = y.subtract(v.multiply(q));
            cb = ca;
            ca = r;
            x = u;
            y = v;
            u = m;
            v = n;
        }
        res[0] = x;
        res[1] = y;
        return res;
    }

    static BigInteger[] combine(int test, BigInteger aPer, BigInteger aPhase, BigInteger bPer, BigInteger bPhase) {
        BigInteger[] result = new BigInteger[2];

        BigInteger g = gcd(aPer, bPer);
        extended = eea(aPer, bPer);
        BigInteger dif = aPhase.subtract(bPhase);
        BigInteger mul = dif.divide(g);
        BigInteger rem = dif.mod(g);

        if (!rem.equals(new BigInteger("0"))) {
            impossible = true;
            System.out.println("Case " + "#" + test + ": " + "impossible");
        } else {
            BigInteger combinedPer = aPer.divide(g).multiply(bPer);
            BigInteger combinedPhase = aPhase.subtract(extended[0].multiply(mul).multiply(aPer)).mod(combinedPer);
            result[0] = combinedPer;
            result[1] = combinedPhase;
        }
        return result;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            s1 = s.nextBigInteger();
            c1 = s.nextBigInteger();
            s2 = s.nextBigInteger();
            c2 = s.nextBigInteger();
            n = s.nextBigInteger();

            impossible = false;
            BigInteger res[] = combine(test, c1, s1.multiply(new BigInteger("-1")).mod(c1), c2,
                    s2.multiply(new BigInteger("-1")).mod(c2));

            if (!impossible) {
                BigInteger period = res[0];
                BigInteger phase = res[1];
                BigInteger x = phase.multiply(new BigInteger("-1")).mod(period);
                while (x.compareTo(n) < 0 || x.compareTo(s1) < 0 || x.compareTo(s2) < 0) {
                    x = x.add(period);
                }
                System.out.println("Case " + "#" + test + ": " + x);
            }
        }
        s.close();
    }
}
