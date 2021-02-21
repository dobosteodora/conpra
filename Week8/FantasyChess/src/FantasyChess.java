import java.math.BigInteger;
import java.util.Scanner;

public class FantasyChess {
    static long a, b, x, y;

    static long gcd() {
        BigInteger aBig = new BigInteger("" + a);
        BigInteger bBig = new BigInteger("" + b);
        BigInteger gcd = aBig.gcd(bBig);
        return gcd.longValue();
    }

    static long[] eea() {
        long q, m, n, r;
        long[] res = new long[2];
        long ca = a;
        long cb = b;

        long x = 0, y = 1, u = 1, v = 0;
        while (ca != 0) {
            q = cb / ca;
            r = cb % ca;
            m = x - u * q;
            n = y - v * q;
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

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            a = s.nextLong();
            b = s.nextLong();
            x = s.nextLong();
            y = s.nextLong();

            long gcd;

            long mulX, mulY;
            long alpha = 0, beta = 0, gamma = 0, sigma = 0;

            long n1 = 0, n2 = 0, n3 = 0, n4 = 0, n5 = 0, n6 = 0, n7 = 0, n8 = 0;

            if (a == 0 && b == 0) {
                gcd = 1;
            } else {
                gcd = gcd();
            }

            if ((a + b) % 2 == 0 && (x + y) % 2 == 1) {
                System.out.println("Case " + "#" + test + ": ");
                System.out.println("impossible");
                System.out.println();
            } else if (a == 0 && b == 0) {
                if (x == 0 && y == 0) {
                    System.out.println("Case " + "#" + test + ": " + "0 0 0");
                    System.out.println();
                } else {
                    System.out.println("Case " + "#" + test + ": ");
                    System.out.println("impossible");
                    System.out.println();
                }
            } else if (x % gcd == 0 && y % gcd == 0) {
                boolean ok = false;
                if (a != 0 && b != 0) {
                    long[] eeaRes = eea();

                    mulX = x / gcd;
                    mulY = y / gcd;

                    alpha = eeaRes[0] * mulX;
                    beta = eeaRes[1] * mulX;

                    gamma = eeaRes[0] * mulY;
                    sigma = eeaRes[1] * mulY;

                    long ag = a / gcd;
                    long bg = b / gcd;
                    long aux = beta / ag;
                    beta = beta - aux * ag;
                    alpha = alpha + aux * bg;

                    aux = sigma / ag;
                    sigma = sigma - aux * ag;
                    gamma = gamma + aux * bg;

                    for (int c1 = 0; c1 <= 1 && !ok; c1++)
                        for (int c2 = 0; c2 <= 1 && !ok; c2++) {
                            long cn = beta, cm = alpha, cq = sigma, cp = gamma;
                            if (c1 == 1) {
                                cn -= ag;
                                cm += bg;
                            }
                            if (c2 == 1) {
                                cq -= ag;
                                cp += bg;
                            }
                            if ((cm + cq) % 2 == 0 && (cn + cp) % 2 == 0) {
                                ok = true;
                                alpha = cm;
                                beta = cn;
                                gamma = cp;
                                sigma = cq;
                            }
                        }
                } else if (a != 0) {
                    alpha = x / a;
                    gamma = y / a;
                    if (alpha % 2 == 0 && gamma % 2 == 0) {
                        ok = true;
                    }
                } else if (b != 0) {
                    beta = x / b;
                    sigma = y / b;
                    if (beta % 2 == 0 && sigma % 2 == 0) {
                        ok = true;
                    }
                }

                n1 = (alpha + sigma) / 2;
                n2 = alpha - n1;
                n5 = (gamma + beta) / 2;
                n6 = beta - n5;

                if (ok) {
                    System.out.println("Case " + "#" + test + ": ");
                    System.out.println(a + " " + b + " " + n1);
                    System.out.println(a + " " + -b + " " + n2);
                    System.out.println(b + " " + a + " " + n5);
                    System.out.println(b + " " + -a + " " + n6);
                    System.out.println();
                } else {
                    System.out.println("Case " + "#" + test + ": ");
                    System.out.println("impossible");
                    System.out.println();
                }
            } else {
                System.out.println("Case " + "#" + test + ": ");
                System.out.println("impossible");
                System.out.println();
            }
        }
        s.close();
    }
}
