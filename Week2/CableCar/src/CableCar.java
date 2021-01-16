import java.util.Scanner;

public class CableCar {

    //return -1 if dist is too big and 1 if dist is too small and 0 otherwise
    public static int isPossible(double d, int p, int u, int v, double dist) {

        double tmp = 0;
        double prev = 0;
        int count = 2; //2 posts on 0 and d

        while (tmp < u) {
            prev = tmp;
            tmp += dist;
            count++;
        }
        if (tmp > u && tmp < v) {
            count--;
        } else {
            prev = tmp;
        }
        tmp = d;

        while (tmp > v && tmp > prev + dist) {
            tmp -= dist;
            count++;
        }

        if (tmp != v) {
            count--;
        }

        if (count > p) {
            return 1;
        }
        if (count < p) {
            return -1;
        }
        return 0;
    }

    public static double binarySearch(double d, int p, int u, int v) {
        double ans;

        ans = d / (p - 1.0);

        double lo = 0;
        double up = d / (p - 1.0);

        double mid;

        while (up - lo >= 0.0001) {

            mid = (lo + up) / 2.0;
            if (isPossible(d, p, u, v, mid) == -1) {//dist is too large
                up = mid;
                ans = mid;
            } else {
                lo = mid;
            }
        }
        return ans;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int length, numberPost, startCanyon, endCanyon;

        for (int i = 1; i <= t; i++) {
            length = s.nextInt();
            numberPost = s.nextInt();
            startCanyon = s.nextInt();
            endCanyon = s.nextInt();

            double result = binarySearch(length, numberPost, startCanyon, endCanyon);
            System.out.format("Case #%d: %.10f\n", i, result);
        }
        s.close();
    }
}
