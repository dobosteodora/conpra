import java.util.Scanner;

public class FamilyPictures {

    static double determinant(double[][] matrix) {
        double determinant = 0;

        determinant += matrix[0][0] * (matrix[1][1] * matrix[2][2]);
        determinant += matrix[0][1] * (matrix[1][2] * matrix[2][0]);
        determinant += matrix[0][2] * (matrix[1][0] * matrix[2][1]);

        determinant -= matrix[0][2] * (matrix[1][1] * matrix[2][0]);
        determinant -= matrix[0][0] * (matrix[1][2] * matrix[2][1]);
        determinant -= matrix[2][2] * (matrix[0][1] * matrix[1][0]);

        return determinant;
    }

    static double[][] inverse(double[][] matrix) {
        double determinant = determinant(matrix);
        double[][] newMatrix = new double[3][3];

        if (determinant != 0) {

            newMatrix[0][0] = ((matrix[1][1] * matrix[2][2]) - (matrix[1][2] * matrix[2][1]));
            newMatrix[0][1] = ((matrix[0][2] * matrix[2][1]) - (matrix[0][1] * matrix[2][2]));
            newMatrix[0][2] = ((matrix[0][1] * matrix[1][2]) - (matrix[0][2] * matrix[1][1]));

            newMatrix[1][0] = ((matrix[1][2] * matrix[2][0]) - (matrix[1][0] * matrix[2][2]));
            newMatrix[1][1] = ((matrix[0][0] * matrix[2][2]) - (matrix[0][2] * matrix[2][0]));
            newMatrix[1][2] = ((matrix[0][2] * matrix[1][0]) - (matrix[0][0] * matrix[1][2]));

            newMatrix[2][0] = ((matrix[1][0] * matrix[2][1]) - (matrix[1][1] * matrix[2][0]));
            newMatrix[2][1] = ((matrix[0][1] * matrix[2][0]) - (matrix[0][0] * matrix[2][1]));
            newMatrix[2][2] = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    newMatrix[i][j] /= determinant;
                }
            }
        }
        return newMatrix;
    }

    static double[] multiplyVector(double[][] matrix, double[] vector) {
        double[] result = new double[3];
        result[0] = matrix[0][0] * vector[0] + matrix[0][1] * vector[1] + matrix[0][2] * vector[2];
        result[1] = matrix[1][0] * vector[0] + matrix[1][1] * vector[1] + matrix[1][2] * vector[2];
        result[2] = matrix[2][0] * vector[0] + matrix[2][1] * vector[1] + matrix[2][2] * vector[2];
        return result;
    }

    static double[][] multiplyMatrices(double[][] a, double[][] b) {
        double[][] res = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return res;
    }


    static double distance(double x1, double y1, double x2, double y2) {
        double res;
        res = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return res;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        double ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy;

        for (int test = 1; test <= t; test++) {
            ax = s.nextDouble();
            ay = s.nextDouble();
            bx = s.nextDouble();
            by = s.nextDouble();
            cx = s.nextDouble();
            cy = s.nextDouble();
            dx = s.nextDouble();
            dy = s.nextDouble();
            ex = s.nextDouble();
            ey = s.nextDouble();
            fx = s.nextDouble();
            fy = s.nextDouble();


            //compute M1
            double[][] m1Help = new double[3][3];
            m1Help[0][0] = 0;
            m1Help[1][0] = 0;
            m1Help[2][0] = 1;

            m1Help[0][1] = 1;
            m1Help[1][1] = 0;
            m1Help[2][1] = 1;

            m1Help[0][2] = 0;
            m1Help[1][2] = 1;
            m1Help[2][2] = 1;

            double[][] m1HelpInverted = inverse(m1Help);

            double[] array1 = new double[3];
            array1[0] = 1;
            array1[1] = 1;
            array1[2] = 1;

            double[] lambda1 = multiplyVector(m1HelpInverted, array1);

            double[][] m1 = new double[3][3];
            m1[0][0] = lambda1[0] * 0;
            m1[1][0] = lambda1[0] * 0;
            m1[2][0] = lambda1[0] * 1;

            m1[0][1] = lambda1[1] * 1;
            m1[1][1] = lambda1[1] * 0;
            m1[2][1] = lambda1[1] * 1;

            m1[0][2] = lambda1[2] * 0;
            m1[1][2] = lambda1[2] * 1;
            m1[2][2] = lambda1[2] * 1;


            //compute M2
            double[][] m2Help = new double[3][3];
            m2Help[0][0] = ax;
            m2Help[1][0] = ay;
            m2Help[2][0] = 1;

            m2Help[0][1] = bx;
            m2Help[1][1] = by;
            m2Help[2][1] = 1;

            m2Help[0][2] = dx;
            m2Help[1][2] = dy;
            m2Help[2][2] = 1;

            double[][] m2HelpInverted = inverse(m2Help);

            double[] array2 = new double[3];
            array2[0] = cx;
            array2[1] = cy;
            array2[2] = 1;

            double[] lambda2 = multiplyVector(m2HelpInverted, array2);

            double[][] m2 = new double[3][3];
            m2[0][0] = lambda2[0] * ax;
            m2[1][0] = lambda2[0] * ay;
            m2[2][0] = lambda2[0] * 1;

            m2[0][1] = lambda2[1] * bx;
            m2[1][1] = lambda2[1] * by;
            m2[2][1] = lambda2[1] * 1;

            m2[0][2] = lambda2[2] * dx;
            m2[1][2] = lambda2[2] * dy;
            m2[2][2] = lambda2[2] * 1;


            double[][] m2Inverted = inverse(m2);

            double[][] transfMatrixReversed = multiplyMatrices(m1, m2Inverted);

            double[] headProjection = new double[3];
            headProjection[0] = ex;
            headProjection[1] = ey;
            headProjection[2] = 1;

            double[] head = multiplyVector(transfMatrixReversed, headProjection);
            
            double[] clockProjection = new double[3];
            clockProjection[0] = fx;
            clockProjection[1] = fy;
            clockProjection[2] = 1;

            double[] clock = multiplyVector(transfMatrixReversed, clockProjection);

            //projection of the clock onto (a,b) in the original picture
            double clockProjX = clock[0];
            double clockProjY = 0;
            double distClockProjected = distance(clock[0] / clock[2], clock[1] / clock[2], clockProjX/clock[2], clockProjY);

            double headProjX = head[0];
            double headProjY = 0;
            double distHeadProjected = distance(head[0]/head[2], head[1]/head[2], headProjX/head[2], headProjY);

            double res = distHeadProjected / distClockProjected;
            System.out.println("Case " + "#" + test + ": " + res);

        }
        s.close();
    }
}
