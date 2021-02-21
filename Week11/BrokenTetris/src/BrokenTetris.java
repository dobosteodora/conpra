import java.util.Scanner;

public class BrokenTetris {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, k, w, h, p;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            k = s.nextInt();

            int[] array = new int[2 * n + 2];

            System.out.print("Case " + "#" + test + ": ");

            int max = 0;

            for (int i = 0; i < k; i++) {
                w = s.nextInt();
                h = s.nextInt();
                p = s.nextInt();

                int val = 0;

                for (int l = 2 * p + 1; l <= 2 * (w + p) - 1; l++) {
                    if (array[l] > val) {
                        val = array[l];
                    }
                }
                for (int l = 2 * p + 1; l <= 2 * (w + p) - 1; l++) {
                    array[l] = h + val;
                    if (max < array[l]) {
                        max = array[l];
                    }
                }
                System.out.print(max + " ");
            }
            System.out.println();
        }
        s.close();
    }
}
