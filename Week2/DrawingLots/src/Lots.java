import java.util.Scanner;

public class Lots {

    public static double computePayoff(double probability, int[] valuePrizes, int cost) {
        float sum = 0;
        float result;

        for (int i = 0; i < valuePrizes.length; i++) {
            sum += valuePrizes[i] * Math.pow(probability, i + 1);
        }
        result = sum - cost;
        return result;
    }

    public static double binarySearch(int[] valuePrizes, int cost, double l, double r, double prev) {
        double mid = l + (r - l) / 2.0d;

        if (Math.abs(computePayoff(mid, valuePrizes, cost)) < Math.pow(10, -6)) {
            return mid;
        }
        if (computePayoff(mid, valuePrizes, cost) > 0) {
            return binarySearch(valuePrizes, cost, l, mid - Math.pow(10, -6), prev);
        } else {
            return binarySearch(valuePrizes, cost, mid + (float) 0.000001, r, mid);
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int numberPrizes, cost;
        int valuePrizes[];

        for (int i = 1; i <= t; i++) {
            numberPrizes = s.nextInt();
            cost = s.nextInt();

            valuePrizes = new int[numberPrizes];

            for (int j = 0; j < numberPrizes; j++) {
                valuePrizes[j] = s.nextInt();
            }

            if (computePayoff(1, valuePrizes, cost) <= 0) {
                System.out.format("Case #%d: %.10f\n", i, 1.0);
            } else {
                System.out.format("Case #%d: %.10f\n", i, binarySearch(valuePrizes, cost, 0, 1, 0));
            }
        }
        s.close();
    }
}
