import java.util.Comparator;

public class TeamsComparator implements Comparator<int[]> {
    @Override
    public int compare(int[] team1, int[] team2) {
        for (int i = 0; i < team1.length; i++) {
            if (team1[i] != team2[i]) {
                return team2[i] - team1[i];
            }
        }
        return 0;
    }
}
