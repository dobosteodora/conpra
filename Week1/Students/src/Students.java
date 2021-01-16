import java.util.Scanner;

public class Students {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int i = 1; i <= t; i++) {

            int numberLines = s.nextInt();
            String finalLine = "\n";

            for (int j = 1; j <= numberLines; j++) {
                String line = s.nextLine();
                if (line.contains("entin")) {
                    line = line.replaceAll("entin", "ierende");
                }
                if (line.contains("enten")) {
                    line = line.replaceAll("enten", "ierende");
                }
                if (line.contains("ent")) {
                    line = line.replaceAll("ent", "ierender");
                }
                finalLine += line;

                if (j != numberLines) {
                    finalLine += '\n';
                }
            }
            System.out.println("Case #" + i + ": " + finalLine);
        }
        s.close();
    }
}
