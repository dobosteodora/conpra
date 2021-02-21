import java.util.Scanner;

public class EulerLine {
    static double[] xT = new double[3];
    static double[] yT = new double[3];

    public static double[] centroid() {
        double[] res = new double[2];

        for (int i = 0; i < 3; i++) {
            res[0] += xT[i];
            res[1] += yT[i];
        }

        res[0] /= 3.0;
        res[1] /= 3.0;
        return res;
    }

    public static double[] orthocenter() {
        double[] res = new double[2];

        double div1 = ((xT[2] - xT[1]) * (yT[2] - yT[0]) - (yT[2] - yT[1]) * (xT[2] - xT[0]));

        if (div1 != 0) {
            res[0] = ((xT[1] * (xT[0] - xT[2]) + yT[1] * (yT[0] - yT[2])) * (yT[2] - yT[1]) -
                    (yT[2] - yT[0]) * (xT[0] * (xT[1] - xT[2]) + yT[0] * (yT[1] - yT[2]))) /
                    ((xT[2] - xT[1]) * (yT[2] - yT[0]) - (yT[2] - yT[1]) * (xT[2] - xT[0]));
        } else {
            res[0] = ((xT[1] * (xT[0] - xT[2]) + yT[1] * (yT[0] - yT[2])) * (yT[2] - yT[1]) -
                    (yT[2] - yT[0]) * (xT[0] * (xT[1] - xT[2]) + yT[0] * (yT[1] - yT[2]))) /
                    1;
        }

        double div2 = ((yT[2] - yT[1]) * (xT[2] - xT[0]) - (xT[2] - xT[1]) * (yT[2] - yT[0]));

        if (div2 != 0) {
            res[1] = ((xT[1] * (xT[0] - xT[2]) + yT[1] * (yT[0] - yT[2])) * (xT[2] - xT[1]) -
                    (xT[2] - xT[0]) * (xT[0] * (xT[1] - xT[2]) + yT[0] * (yT[1] - yT[2]))) /
                    ((yT[2] - yT[1]) * (xT[2] - xT[0]) - (xT[2] - xT[1]) * (yT[2] - yT[0]));
        } else {
            res[1] = ((xT[1] * (xT[0] - xT[2]) + yT[1] * (yT[0] - yT[2])) * (xT[2] - xT[1]) -
                    (xT[2] - xT[0]) * (xT[0] * (xT[1] - xT[2]) + yT[0] * (yT[1] - yT[2]))) /
                    1;
        }

        return res;
    }

    public static double[] circumcenter() {
        double[] res = new double[2];

        double D = 2 * (xT[0] * (yT[1] - yT[2]) + xT[1] * (yT[2] - yT[0]) + xT[2] * (yT[0] - yT[1]));

        if (D != 0) {
            res[0] = ((Math.pow(xT[0], 2) + Math.pow(yT[0], 2)) * (yT[1] - yT[2]) +
                    (Math.pow(xT[1], 2) + Math.pow(yT[1], 2)) * (yT[2] - yT[0]) +
                    (Math.pow(xT[2], 2) + Math.pow(yT[2], 2)) * (yT[0] - yT[1])) / D;

            res[1] = ((Math.pow(xT[0], 2) + Math.pow(yT[0], 2)) * (xT[2] - xT[1]) +
                    (Math.pow(xT[1], 2) + Math.pow(yT[1], 2)) * (xT[0] - xT[2]) +
                    (Math.pow(xT[2], 2) + Math.pow(yT[2], 2)) * (xT[1] - xT[0])) / D;
        } else {
            res[0] = ((Math.pow(xT[0], 2) + Math.pow(yT[0], 2)) * (yT[1] - yT[2]) +
                    (Math.pow(xT[1], 2) + Math.pow(yT[1], 2)) * (yT[2] - yT[0]) +
                    (Math.pow(xT[2], 2) + Math.pow(yT[2], 2)) * (yT[0] - yT[1])) / 1;

            res[1] = ((Math.pow(xT[0], 2) + Math.pow(yT[0], 2)) * (xT[2] - xT[1]) +
                    (Math.pow(xT[1], 2) + Math.pow(yT[1], 2)) * (xT[0] - xT[2]) +
                    (Math.pow(xT[2], 2) + Math.pow(yT[2], 2)) * (xT[1] - xT[0])) / 1;
        }
        return res;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        double x, y;

        for (int test = 1; test <= t; test++) {


            for (int i = 0; i < 3; i++) {
                xT[i] = s.nextDouble();
                yT[i] = s.nextDouble();
            }

            double[] centroid = centroid();
            double[] orthocenter = orthocenter();
            double[] circumcenter = circumcenter();

            System.out.println("Case " + "#" + test + ": ");
            System.out.println(centroid[0] + " " + centroid[1]);
            System.out.println(orthocenter[0] + " " + orthocenter[1]);
            System.out.println(circumcenter[0] + " " + circumcenter[1]);

        }
        s.close();
    }
}
