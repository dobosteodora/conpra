import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

//inspired from https://fishi.devtail.io/weblog/2015/06/25/computing-large-binomial-coefficients-modulo-prime-non-prime/

public class SoftSkills {
    static long m, n;
    static long modulo;
    static long[] num;
    static long[] rem;

    static long modExp(long b, long e, long mod) {
        BigInteger bBig = new BigInteger("" + b);
        BigInteger eBig = new BigInteger("" + e);
        BigInteger modBig = new BigInteger("" + mod);
        BigInteger resBig = bBig.modPow(eBig, modBig);
        return resBig.longValue();
    }

    static long factExp(long n, long p) {
        long e = 0;
        long u = p;
        long t = n;
        while (u <= t) {
            e += t / u;
            u *= p;
        }
        return e;
    }

    static long[] convert(long n, long b) {
        long nCopy = n;
        int count = 0;

        while (n > 0) {
            n /= b;
            count++;
        }

        long[] d = new long[count];
        for (int i = 0; i < d.length; i++) {
            d[i] = nCopy % b;
            nCopy /= b;
        }
        return d;
    }

    static long fermat(long n, long k, long p) {
        long numDegree = factExp(n, p) - factExp(n - k, p);
        long denDegree = factExp(k, p);
        if (numDegree > denDegree) {
            return 0;
        }

        if (k > n) {
            return 0;
        }

        long num = 1;
        for (long i = n; i > n - k; i--) {
            long cur = i;
            while (cur % p == 0) {
                cur /= p;
            }
            num = (num * cur) % p;
        }

        long denom = 1;
        for (long i = 1; i < k + 1; i++) {
            long cur = i;
            while (cur % p == 0) {
                cur /= p;
            }
            denom = (denom * cur) % p;
        }
        return (num * modExp(denom, p - 2, p)) % p;
    }

    static long lucas(long n, long k, long p) {
        long[] np = convert(n, p);
        long[] kp = convert(k, p);

        long binom = 1;
        for (int i = np.length - 1; i >= 0; i--) {
            long ni = np[i];
            long ki = 0;
            if (i < kp.length) {
                ki = kp[i];
            }
            binom = (binom * fermat(ni, ki, p)) % p;
        }
        return binom;
    }

    static long inverse(long a, long m) {
        long m0 = m, t, q;
        long x0 = 0, x1 = 1;

        if (m == 1) {
            return 0;
        }
        while (a > 1) {
            q = a / m;
            t = m;
            m = a % m;
            a = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }
        if (x1 < 0) {
            x1 += m0;
        }
        return x1;
    }

    static long solveChinese() {
        long prod = 1;
        for (int i = 0; i < num.length; i++){
            prod *= num[i];
        }

        long result = 0;

        for (int i = 0; i < num.length; i++) {
            long pp = prod / num[i];
            result += rem[i] * inverse(pp, num[i]) * pp;
        }
        return result % prod;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            n = s.nextLong();
            m = s.nextLong();

            modulo = 22309287;//2*3...*23 => 9 prime numbers in the product
            ArrayList<Long> primes = new ArrayList<>();
            primes.add(2L);
            primes.add(3L);
            primes.add(5L);
            primes.add(7L);
            primes.add(11L);
            primes.add(13L);
            primes.add(17L);
            primes.add(19L);
            primes.add(23L);

            num = new long[9];
            rem = new long[9];
            for (int i = 0; i < 9; i++) {
                num[i] = primes.get(i);
                rem[i] = lucas(n, m, num[i]);
            }

            long res = solveChinese();

            System.out.println("Case " + "#" + test + ": " + res);
        }
        s.close();
    }
}

