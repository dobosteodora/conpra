import java.util.Scanner;

public class ContactList {

    static final int ALPHABET_SIZE = 52;
    static TrieNode root;
    static int count;

    static class TrieNode {
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];

        boolean isEndOfWord;

        public TrieNode() {
            isEndOfWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++)
                children[i] = null;
        }
    }

    static void insert(String key) {
        int level;
        int length = key.length();
        int index;

        TrieNode pCrawl = root;

        for (level = 0; level < length; level++) {
            if (level == 0) {
                index = key.charAt(level) - 'A';
                index += 26;
            } else {
                index = key.charAt(level) - 'a';
            }

            if (pCrawl.children[index] == null) {
                pCrawl.children[index] = new TrieNode();
            }

            pCrawl.isEndOfWord = false;
            pCrawl = pCrawl.children[index];
        }

        int i = 0;
        for (i = 0; i < ALPHABET_SIZE; i++) {
            if (pCrawl.children[i] != null) {
                break;
            }
        }

        if (i == ALPHABET_SIZE) {
            pCrawl.isEndOfWord = true;
        }
    }

    static void count(TrieNode root, char str[], int level) {

        if (root.isEndOfWord) {
            str[level] = '\0';
            count++;
        }

        int i;
        for (i = 0; i < ALPHABET_SIZE; i++) {

            if (root.children[i] != null) {
                if (i < 26) {
                    str[level] = (char) (i + 'a');
                } else {
                    str[level] = (char) (i + 39);
                }
                count(root.children[i], str, level + 1);
            }
        }
    }


    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        int t = s.nextInt();
        int n;
        String x;

        for (int test = 1; test <= t; test++) {
            n = s.nextInt();

            root = new TrieNode();
            String[] keys = new String[n];

            for (int i = 0; i < n; i++) {
                x = s.next();
                keys[i] = x;
            }

            count = 0;

            for (int i = 0; i < n; i++) {
                insert(keys[i]);
            }

            char[] str = new char[600];
            count(root, str, 0);

            int res = n - count;

            System.out.println("Case " + "#" + test + ": " + res);
        }
        s.close();
    }
}
