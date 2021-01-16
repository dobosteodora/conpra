import java.util.Scanner;

public class Watson {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        s.nextLine();

        int number1, number2;

        boolean plus, minus, times, toPower;
        int result;
        int j;
        String word;
        String line;

        for (int i = 1; i <= t; i++) {

            line = s.nextLine();
            word = "";
            plus = false;
            minus = false;
            times = false;
            toPower = false;
            result = 0;
            j = 0;

            //parse the first number
            while (j < line.length() && Character.isDigit(line.charAt(j))) {
                word += line.charAt(j);
                j++;
            }
            number1 = Integer.parseInt(word);

            while (j < line.length()) {

                word = "";

                if (line.charAt(j) == 'p') {
                    plus = true;
                } else if (line.charAt(j) == 'm') {
                    minus = true;
                } else if (line.charAt(j + 1) == 'i') {
                    times = true;
                } else {
                    toPower = true;
                }
                while (Character.isLetter(line.charAt(j))) {
                    j++;
                }

                //parse the next number
                while (j != line.length() && Character.isDigit(line.charAt(j))) {
                    word += line.charAt(j);
                    j++;
                }

                number2 = Integer.parseInt(word);

                if (plus) {
                    result = number1 + number2;
                } else if (minus) {
                    result = number1 - number2;
                } else if (times) {
                    result = number1 * number2;
                } else if (toPower) {
                    result = (int) Math.pow(number1, number2);
                }
                plus = false;
                minus = false;
                times = false;
                toPower = false;

                number1 = result;
            }
            System.out.println("Case #" + i + ": " + number1);
        }
        s.close();
    }
}
