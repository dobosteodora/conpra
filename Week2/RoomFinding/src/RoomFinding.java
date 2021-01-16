import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//idea inspired from https://stackoverflow.com/questions/13507925/nth-smallest-element-in-a-union-of-an-array-of-intervals-with-repetition

public class RoomFinding {

    public static long lowest(ArrayList<ArrayList<Integer>> intervals, long x) {
        long sum = 0;

        for (ArrayList<Integer> list : intervals) {
            if (x > list.get(1)) {
                sum += list.get(1) - list.get(0) + 1;
            } else if ((x >= list.get(0) && x < list.get(1)) || (x > list.get(0) && x <= list.get(1))) {
                sum += x - list.get(0);
            }
        }
        return sum;
    }

    public static long highest(ArrayList<ArrayList<Integer>> intervals, long x) {
        long sum = 0;

        for (ArrayList<Integer> list : intervals) {
            if (x > list.get(1)) {
                sum += list.get(1) - list.get(0) + 1;
            } else if ((x >= list.get(0) && x < list.get(1)) || (x > list.get(0) && x <= list.get(1))) {
                sum += x - list.get(0) + 1;
            }
        }
        return sum;
    }

    public static long binarySearch(long left, long right, int k, ArrayList<ArrayList<Integer>> intervals) {
        if (left > right) {
            return -1;
        }

        long mid = left + (right - left) / 2;

        long leftIndex = lowest(intervals, mid);
        long rightIndex = highest(intervals, mid);

        if (k > leftIndex && k <= rightIndex) {
            return mid;
        }

        if (k <= leftIndex) {
            return binarySearch(left, mid - 1, k, intervals);
        }
        if (k > rightIndex) {
            return binarySearch(mid + 1, right, k, intervals);
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        int[] lines, lowest, highest;
        int stations, friends, result;

        ArrayList<ArrayList<Integer>> intervals;

        for (int i = 1; i <= t; i++) {
            stations = s.nextInt();
            friends = s.nextInt();

            lines = new int[friends];
            intervals = new ArrayList<>();
            lowest = new int[stations];
            highest = new int[stations];

            for (int k = 0; k < stations; k++) {
                ArrayList interval = new ArrayList();

                int low = s.nextInt();
                lowest[k] = low;
                interval.add(low);

                int up = s.nextInt();
                highest[k] = up;
                interval.add(up);

                intervals.add(interval);
            }

            for (int k = 0; k < friends; k++) {
                lines[k] = s.nextInt();
            }

            Arrays.sort(lowest);
            Arrays.sort(highest);

            System.out.println("Case #" + i + ": ");

            for (int k = 0; k < lines.length; k++) {
                result = (int) binarySearch(lowest[0], highest[stations - 1], lines[k], intervals);
                System.out.println(result);
            }
        }
        s.close();
    }
}

