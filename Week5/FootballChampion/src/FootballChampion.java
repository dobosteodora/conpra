import java.util.*;

// idea from https://www.cs.princeton.edu/~wayne/papers/baseball_talk.pdf
//https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
// code inspired from https://github.com/arnabgho/Baseball-Elimination/blob/master/BaseballElimination.java

public class FootballChampion {

    static int[][] residual;
    static int INF = Integer.MAX_VALUE;
    static int f;
    static int V;
    static int[] order;
    static int[] parent;
    static Stack<Integer> stack;
    static int index;
    static int[] win;
    static int[] remaining;
    static int[][] gameVertex;
    static int[] teamVertex;
    static int[][] games;
    static boolean possible;
    static int sum;
    static int maxWin;

    static boolean dfs(int s, int t) {

        stack.clear();
        stack.push(s);

        for (int i = 0; i < V; i++) {
            parent[s] = -1;
        }
        for (int i = 0; i < V; i++) {
            order[i] = INF;
        }

        int v;
        index = 1;

        while (!stack.isEmpty()) {
            v = stack.pop();

            if (order[v] == INF) {
                order[v] = index;

                if (v == t) {
                    return true;
                }
                index++;

                for (int i = 0; i < residual.length; i++) {
                    if (order[i] == INF && residual[v][i] > 0) {
                        stack.add(i);
                        parent[i] = v;
                    }
                }
            }
        }
        return false;
    }

    static int fordFulkerson(int s, int t) {
        f = 0;
        int u, v;

        while (dfs(s, t)) {

            //find minimum residual capacity in the path discovered by DFS
            int pathFlow = INF;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, residual[u][v]);
            }

            //update residual graph
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                residual[u][v] -= pathFlow;
                residual[v][u] += pathFlow;
            }
            f += pathFlow;
        }
        return f;
    }

    static int returnMaxFlow(int team, int n) {

        int s = 0;
        int pos = 1;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (i != team && j != team) {
                    gameVertex[i][j] = pos;
                    gameVertex[j][i] = pos;
                    pos++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (i != team) {
                teamVertex[i] = pos;
                pos++;
            }
        }

        int t = pos;
        sum = 0;

        residual = new int[t + 1][t + 1];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (i != team && j != team) {
                    sum += games[i][j];
                    pos = gameVertex[i][j];
                    residual[s][pos] = games[i][j];
                    residual[pos][teamVertex[i]] = INF;
                    residual[pos][teamVertex[j]] = INF;
                }
            }
        }

        int total = win[team] + remaining[team];

        for (int i = 0; i < n; i++) {
            if (i != team) {
                if (total >= win[i]) {
                    residual[teamVertex[i]][t] = total - win[i];
                } else {
                    possible = false;
                }
            }
        }
        return fordFulkerson(s, t);
    }

    static boolean canWin(int team, int n) {

        possible = true;
        int maxFlow = returnMaxFlow(team, n);

        if (!possible) {
            return false;
        } else if (maxFlow == sum) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n, m;

        for (int test = 1; test <= t; test++) {

            n = s.nextInt();
            m = s.nextInt();

            V = 2 + n * (n + 1) / 2;

            residual = new int[V][V];

            win = new int[n];
            games = new int[n][n];
            remaining = new int[n];

            maxWin = -1;

            for (int i = 0; i < n; i++) {
                int w = s.nextInt();
                win[i] = w;
                if (win[i] > maxWin) {
                    maxWin = win[i];
                }
            }

            //read matches to be played
            for (int i = 1; i <= m; i++) {
                int t1 = s.nextInt();
                int t2 = s.nextInt();
                games[t1 - 1][t2 - 1]++;
                games[t2 - 1][t1 - 1]++;
            }

            int tmp;
            for (int team = 0; team < n; team++) {
                tmp = 0;
                for (int i = 0; i < n; i++) {
                    tmp += games[team][i];
                }
                remaining[team] = tmp;
            }

            order = new int[V];
            parent = new int[V];
            stack = new Stack<>();

            gameVertex = new int[n][n];
            teamVertex = new int[n];

            String result = "";
            for (int i = 0; i < n - 1; i++) {

                if (win[i] + remaining[i] < maxWin) {
                    result += "no ";
                } else {
                    for (int j = 0; j < gameVertex.length; j++) {
                        for (int k = 0; k < gameVertex.length; k++) {
                            gameVertex[j][k] = 0;
                        }
                    }

                    Arrays.fill(teamVertex, 0);

                    if (canWin(i, n)) {
                        result += "yes ";
                    } else {
                        result += "no ";
                    }
                }
            }
            //last team
            if (win[n - 1] + remaining[n - 1] < maxWin) {
                result += "no";
            } else {
                for (int j = 0; j < gameVertex.length; j++) {
                    for (int k = 0; k < gameVertex.length; k++) {
                        gameVertex[j][k] = 0;
                    }
                }
                Arrays.fill(teamVertex, 0);

                if (canWin(n - 1, n)) {
                    result += "yes";
                } else {
                    result += "no";
                }
            }
            System.out.println("Case #" + test + ": " + result);
        }
        s.close();
    }
}
