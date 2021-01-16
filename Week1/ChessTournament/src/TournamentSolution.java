import java.util.*;

//idea inspired from https://stackoverflow.com/questions/18129807/in-java-how-do-you-sort-one-list-based-on-another

public class TournamentSolution {

    public static void reverse(int[] array) {
        int helper1 = array[4];
        array[4] = array[0];
        array[0] = helper1;
        helper1 = array[3];
        array[3] = array[1];
        array[1] = helper1;
    }

    public static void orderTeam(int team, int[][] teams) {
        Arrays.sort(teams[team]);
        reverse(teams[team]);
        Arrays.sort(teams, new TeamsComparator());
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int i = 1; i <= t; i++) {

            int n = s.nextInt(); 
            int[][] teams = new int[n][5];

            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 5; k++) {
                    teams[j][k] = s.nextInt();
                }
                orderTeam(j, teams);
            }

            System.out.println("Case #" + i + ":");
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 5; k++) {
                    System.out.print(teams[j][k]);
                    if (k != 4) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }
        s.close();
    }
}
