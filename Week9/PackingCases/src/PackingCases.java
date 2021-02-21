import java.util.Arrays;
import java.util.Scanner;

//idea from https://www.geeksforgeeks.org/box-stacking-problem-dp-22/

public class PackingCases {
    static Box[] rotations;

    static class Box implements Comparable<Box> {
        int w;
        int d;
        int h;
        int area;

        Box(int w, int d, int h) {
            this.w = w;
            this.d = d;
            this.h = h;
        }

        @Override
        public int compareTo(Box o) {
            return o.area - this.area;
        }
    }


    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            int height, n, x, y, z;

            height = s.nextInt();
            n = s.nextInt();

            rotations = new Box[3 * n];

            for (int i = 0; i < n; i++) {
                x = s.nextInt();
                y = s.nextInt();
                z = s.nextInt();

                //width<=depth
                if (x <= y) {
                    rotations[3 * i] = new Box(x, y, z);
                } else {
                    rotations[3 * i] = new Box(y, x, z);
                }

                if (x <= z) {
                    rotations[3 * i + 1] = new Box(x, z, y);
                } else {
                    rotations[3 * i + 1] = new Box(z, x, y);
                }

                if (y <= z) {
                    rotations[3 * i + 2] = new Box(y, z, x);
                } else {
                    rotations[3 * i + 2] = new Box(z, y, x);
                }
            }

            for (int i = 0; i < 3 * n; i++) {
                rotations[i].area = rotations[i].d * rotations[i].w;
            }

            Arrays.sort(rotations);

            int[] maxHeight = new int[3 * n];
            for (int i = 0; i < 3 * n; i++) {
                maxHeight[i] = rotations[i].h;
            }

            for (int i = 0; i < 3 * n; i++) {
                maxHeight[i] = 0;
                Box box = rotations[i];
                int val = 0;

                for (int j = 0; j < i; j++) {
                    Box prevBox = rotations[j];
                    if (box.d < prevBox.d && box.w < prevBox.w) {
                        val = Math.max(val, maxHeight[j]);
                    }
                }
                maxHeight[i] = val + box.h;
            }

            int max = -1;

            for (int i = 0; i < 3 * n; i++) {
                max = Math.max(max, maxHeight[i]);
            }

            if (max >= height) {
                System.out.println("Case " + "#" + test + ": " + "yes");
            } else {
                System.out.println("Case " + "#" + test + ": " + "no");
            }
        }
        s.close();
    }
}
