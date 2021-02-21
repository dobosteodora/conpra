import java.util.*;

//inspired from https://runestone.academy/runestone/books/published/pythonds/Recursion/DynamicProgramming.html

public class MakingChange {
    static int n, c;
    static int[] coins;
    static int[] minCoins, coinsUsed;
    static HashMap<Integer, Integer> count;

    static int makeChange() {

        for (int cents = 1; cents <= c; cents++) {
            int coinCount = cents;
            int newCoin = 1;

            for (int j = 1; j <= n; j++) {

                int coin = coins[j];
                if (cents - coin >= 0) {
                    if (minCoins[cents - coin] + 1 < coinCount) {
                        coinCount = minCoins[cents - coin] + 1;
                        newCoin = coin;
                    }
                }
            }
            minCoins[cents] = coinCount;
            coinsUsed[cents] = newCoin;
        }
        return minCoins[c];
    }

    static void count() {
        int coin = c;
        while (coin > 0) {
            int thisCoin = coinsUsed[coin];
            int tmp = count.get(thisCoin);
            count.replace(thisCoin, tmp + 1);
            coin -= thisCoin;
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            c = s.nextInt();

            coins = new int[n + 1];
            count = new HashMap<>();

            for (int i = 1; i <= n; i++) {
                coins[i] = s.nextInt();
                count.put(coins[i], 0);
            }

            minCoins = new int[c + 1];
            coinsUsed = new int[c + 1];

            System.out.print("Case " + "#" + test + ": ");
            makeChange();
            count();

            for (int i = 1; i <= n; i++) {
                System.out.print(count.get(coins[i]) + " ");
            }
            System.out.println();
        }
        s.close();
    }
}
