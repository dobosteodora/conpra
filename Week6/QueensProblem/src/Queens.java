import java.util.Arrays;
import java.util.Scanner;

//idea inspired from https://www.geeksforgeeks.org/java-program-for-n-queen-problem-backtracking-3/

public class Queens {
    static int n;
    static boolean[] filledColumn;
    static boolean ok;
    static int[] whichCol;

    static boolean valid(int[][] painting, int row, int col) {
        int i, j;

        //check the row completely
        for (i = 0; i < n; i++)
            if (painting[row][i] == 1) {
                return false;
            }

        //check diagonals
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (painting[i][j] == 1) {
                    int deltaRow = Math.abs(i - row);
                    int deltaCol = Math.abs(j - col);
                    if (deltaRow == deltaCol) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static boolean solve(int[][] painting, int col, int t) {

        if (col >= n && !ok) {
            ok = true;
            printSolution(painting, t);
            return true;
        }

        if (!filledColumn[col]) {
            for (int i = 0; i < n; i++) {
                if (valid(painting, i, col)) {
                    painting[i][col] = 1;
                    filledColumn[col] = true;

                    if (solve(painting, col + 1, t)) {
                        if (!ok) {
                            printSolution(painting, t);
                        }
                        ok = true;
                        return true;
                    }
                    painting[i][col] = 0;
                    filledColumn[col] = false;
                }
            }
        } else {
            solve(painting, col + 1, t);
        }
        return false;
    }

    static boolean rowsOK(int[][] painting) {
        int sum;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int j = 0; j < n; j++) {
                if (painting[i][j] == 1) {
                    sum++;
                    if (sum > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static boolean columnsOK(int[][] painting) {
        int sum;
        for (int j = 0; j < n; j++) {
            sum = 0;
            for (int i = 0; i < n; i++) {
                if (painting[i][j] == 1) {
                    sum++;
                    if (sum > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static boolean diagonalsOK(int[][] painting) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && whichCol[i] != -1 && whichCol[j] != -1) {
                    int deltaRow = Math.abs(i - j);
                    int deltaCol = Math.abs(whichCol[i] - whichCol[j]);
                    if (deltaRow == deltaCol) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    static void printSolution(int[][] painting, int t) {
        System.out.println("Case #" + t + ": ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (painting[i][j] == 0) {
                    System.out.print(".");
                } else {
                    System.out.print("x");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {

            n = s.nextInt();
            int[][] painting = new int[n][n];
            filledColumn = new boolean[n + 1];
            whichCol = new int[n];
            Arrays.fill(whichCol, -1);

            for (int i = 0; i < n; i++) {
                String line = s.next();
                for (int j = 0; j < line.length(); j++) {
                    char c = line.charAt(j);
                    if (c == '.') {
                        painting[i][j] = 0;
                    } else {
                        painting[i][j] = 1;
                        filledColumn[j] = true;
                        whichCol[i] = j;
                    }
                }
            }

            if (rowsOK(painting) && columnsOK(painting) && diagonalsOK(painting)) {

                ok = false;

                solve(painting, 0, test);

                if (!ok) {
                    System.out.println("Case #" + test + ":");
                    System.out.println("impossible");
                }
            } else {
                System.out.println("Case #" + test + ":");
                System.out.println("impossible");
            }
        }
        s.close();
    }
}
