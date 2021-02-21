import java.util.Scanner;

//method lcs from https://www.geeksforgeeks.org/longest-common-subsequence-dp-4/

public class Bracelets {
    static String a, b;

    static int lcs(char[] first, char[] second, int m, int n) {
        int L[][] = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0)
                    L[i][j] = 0;
                else if (first[i - 1] == second[j - 1])
                    L[i][j] = L[i - 1][j - 1] + 1;
                else
                    L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
            }
        }
        return L[m][n];
    }

    static String cut(String a, int k, boolean right) {
        String res = "";

        if (!right) {
            res += a.substring(k);
            res += a.substring(0, k);
        } else {
            String tmp = new StringBuilder(a.substring(0, k)).reverse().toString();
            res += tmp;
            tmp = new StringBuilder(a.substring(k)).reverse().toString();
            res += tmp;
        }
        return res;
    }

    static int solve(String a, String b, int m, int n) {
        int max = -1;

        char[] charA = new char[a.length()];
        for (int k = 0; k < a.length(); k++) {
            charA[k] = a.charAt(k);
        }

        char[] charB = new char[b.length()];

        for (int j = 0; j < n; j++) {
            //rotate right
            String tmpB = cut(b, j, false);
            for (int k = 0; k < b.length(); k++) {
                charB[k] = tmpB.charAt(k);
            }

            int res = lcs(charA, charB, m, n);
            if (res > max) {
                max = res;
            }


            //rotate left
            tmpB = cut(b, j, true);
            for (int k = 0; k < b.length(); k++) {
                charB[k] = tmpB.charAt(k);
            }

            res = lcs(charA, charB, m, n);
            if (res > max) {
                max = res;
            }
        }
        return max;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            a = s.next();
            b = s.next();

            int res = solve(a, b, a.length(), b.length());

            System.out.println("Case " + "#" + test + ": " + res);
        }
        s.close();
    }
}
