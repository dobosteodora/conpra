import java.util.Scanner;

public class Trapped {

    static int[][] board;
    static int h, w;
    static int tools;
    static int[] dx;
    static int[] dy;
    static int collected;
    static boolean ok;

    static void createBorders() {
        for (int i = 0; i <= h + 1; i++) {
            board[i][0] = 5;
            board[i][w + 1] = 5;
        }
        for (int i = 0; i <= w + 1; i++) {
            board[0][i] = 5;
            board[h + 1][i] = 5;
        }
    }

    static void search(int x, int y, int test) {
        if (board[x][y] == 2) {

            collected++;
            if (collected == tools) {
                ok = true;
            } else {
                board[x][y] = 4;

                for (int dir = 0; dir < 4; dir++) {
                    if (board[x + dx[dir]][y + dy[dir]] != 4 && board[x + dx[dir]][y + dy[dir]] != 3 &&
                            board[x + dx[dir]][y + dy[dir]] != 5 && board[x + dx[dir]][y + dy[dir]] != 1) {
                        search(x + dx[dir], y + dy[dir], test);
                    }
                }

                board[x][y] = 2;
                collected--;
            }
        } else {
            if (!ok) {
                board[x][y] = 4;
                for (int dir = 0; dir < 4; dir++) {
                    if (!ok) {
                        if (board[x + dx[dir]][y + dy[dir]] != 4 && board[x + dx[dir]][y + dy[dir]] != 3 &&
                                board[x + dx[dir]][y + dy[dir]] != 5 && board[x + dx[dir]][y + dy[dir]] != 1) {
                            search(x + dx[dir], y + dy[dir], test);
                        }
                    }
                }
                if (!ok) {
                    board[x][y] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            w = s.nextInt();
            h = s.nextInt();

            board = new int[h + 2][w + 2];
            tools = 0;

            int xLea = 0, yLea = 0;

            for (int i = 1; i <= h; i++) {
                String line = s.next();
                for (int j = 0; j < line.length(); j++) {
                    char c = line.charAt(j);
                    if (c == 'L') {
                        board[i][j + 1] = 1;
                        xLea = i;
                        yLea = j + 1;
                    } else if (c == 'T') {
                        board[i][j + 1] = 2;
                        tools++;
                    } else if (c == '#') {
                        board[i][j + 1] = 3;
                    }
                }
            }

            dx = new int[]{-1, 0, 1, 0};
            dy = new int[]{0, 1, 0, -1};

            createBorders();

            collected = 0;
            ok = false;
            search(xLea, yLea, test);

            if (!ok) {
                System.out.println("Case " + "#" + test + ": " + "no");
            } else {
                System.out.println("Case " + "#" + test + ": " + "yes");
            }
        }
        s.close();
    }
}
