import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        for (int i = 1; i <= t; i++) {

            String st = s.next();

            System.out.println("Case #" + i + ": " + "Hello "+ st + "!");
        }
        s.close();
    }
}