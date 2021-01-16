import java.util.Scanner;

public class Relativity {
    public static void main(String[] args) {

        long c = 299792458;
        long cSquare = c * c;

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        for (int i = 1; i <= t; i++) {
            int m = s.nextInt();
            System.out.println("Case #" + i + ": " + m * cSquare);
        }
        s.close();
    }
}
