import java.util.ArrayList;
import java.util.Scanner;

public class Poker {
    static int n, a, b, p;
    static ArrayList<Tournament> tournaments;
    static int[] dp;

    static class Tournament {
        int start;
        int end;
        int prize;

        Tournament(int start, int end, int prize) {
            this.start = start;
            this.end = end;
            this.prize = prize;
        }
    }


    static int bestForTime(int t) {

        if (t == 0) {
            return 0;
        }

        dp[t] = bestForTime(t - 1);

        for (int i = 0; i < tournaments.size(); i++) {
            if (tournaments.get(i).end == t) {
                dp[t] = Math.max(dp[t], tournaments.get(i).prize + dp[tournaments.get(i).start - 1]);
            }
        }

        return dp[t];
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();

            tournaments = new ArrayList<>();
            int maxEnd = -1;

            for (int i = 1; i <= n; i++) {
                a = s.nextInt();
                b = s.nextInt();
                p = s.nextInt();
                tournaments.add(new Tournament(a, b, p));
                if (b > maxEnd) {
                    maxEnd = b;
                }
            }

            dp = new int[maxEnd + 1];

            System.out.println("Case " + "#" + test + ": " + bestForTime(maxEnd));
        }
        s.close();
    }
}
