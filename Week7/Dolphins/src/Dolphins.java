import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

//idea from https://www.topcoder.com/community/competitive-programming/tutorials/greedy-is-good/

public class Dolphins {

    static int n, m;
    static ArrayList<Integer> freq;
    static ArrayList<String> humanSequences;
    static ArrayList<String> miceSequences;

    static int getScore(int[] score, ArrayList<Integer> freq) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += score[i] * freq.get(i);
        }
        return sum;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();
            m = s.nextInt();

            s.nextLine();
            humanSequences = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                String seq = s.nextLine();
                humanSequences.add(seq);
            }
            miceSequences = new ArrayList<>();
            for (int i = 1; i <= m; i++) {
                String seq = s.nextLine();
                miceSequences.add(seq);
            }

            //0: AA, 1: CC, 2: GG, 3: TT, 4: AC+CA, 5: AG + GA, 6: AT + TA, 7: CG + GC, 8: CT + TC and 9: GT + TG
            freq = new ArrayList<>();
            int[] freqArray = new int[10];

            for (int i = 0; i < humanSequences.size(); i++) {
                for (int j = 0; j < miceSequences.size(); j++) {
                    for (int k = 0; k < miceSequences.get(j).length(); k++) {

                        char c1 = humanSequences.get(i).charAt(k);
                        char c2 = miceSequences.get(j).charAt(k);

                        if (c1 == 'A') {
                            if (c2 == 'A') {
                                freqArray[0]++;
                            } else if (c2 == 'C') {
                                freqArray[4]++;
                            } else if (c2 == 'G') {
                                freqArray[5]++;
                            } else if (c2 == 'T') {
                                freqArray[6]++;
                            }
                        } else if (c1 == 'C') {
                            if (c2 == 'A') {
                                freqArray[4]++;
                            } else if (c2 == 'C') {
                                freqArray[1]++;
                            } else if (c2 == 'G') {
                                freqArray[7]++;
                            } else if (c2 == 'T') {
                                freqArray[8]++;
                            }
                        } else if (c1 == 'G') {
                            if (c2 == 'A') {
                                freqArray[5]++;
                            } else if (c2 == 'C') {
                                freqArray[7]++;
                            } else if (c2 == 'G') {
                                freqArray[2]++;
                            } else if (c2 == 'T') {
                                freqArray[9]++;
                            }
                        } else if (c1 == 'T') {
                            if (c2 == 'A') {
                                freqArray[6]++;
                            } else if (c2 == 'C') {
                                freqArray[8]++;
                            } else if (c2 == 'G') {
                                freqArray[9]++;
                            } else if (c2 == 'T') {
                                freqArray[3]++;
                            }
                        }
                    }
                }
            }

            ArrayList<Integer> tmp = new ArrayList<>();
            for (int i = 4; i < 10; i++) {
                tmp.add(freqArray[i]);
            }

            Comparator<Integer> comparator = Comparator.comparing(e -> -e);
            tmp.sort(comparator);

            for (int i = 0; i < 4; i++) {
                freq.add(freqArray[i]);
            }

            for (int i = 4; i < 10; i++) {
                freq.add(tmp.get(i - 4));
            }

            int[] score = new int[10];
            int best = Integer.MIN_VALUE;
            for (score[0] = 0; score[0] <= 10; score[0]++) {
                for (score[1] = 0; score[1] <= 10; score[1]++) {
                    for (score[2] = 0; score[2] <= 10; score[2]++) {
                        for (score[3] = 0; score[3] <= 10; score[3]++) {
                            if ((score[0] + score[1] + score[2] + score[3]) % 2 == 0) {
                                score[4] = 10;
                                score[5] = 10;
                                score[6] = 10 - (score[0] + score[1] + score[2] + score[3]) / 2;
                                score[7] = -10;
                                score[8] = -10;
                                score[9] = -10;
                                best = Math.max(best, getScore(score, freq));
                            }
                        }
                    }
                }
            }
            System.out.println("Case " + "#" + test + ": " + best);
        }
        s.close();
    }
}
