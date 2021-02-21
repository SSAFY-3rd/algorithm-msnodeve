import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class Word {
        int w1, w2, w;

        public Word(int w1, int w2, int w) {
            this.w1 = w1;
            this.w2 = w2;
            this.w = w;
        }

        @Override
        public String toString() {
            return "{" +
                    "w1=" + w1 +
                    ", w2=" + w2 +
                    ", w3=" + w +
                    '}';
        }
    }

    static boolean[][] visit;
    static Queue<Word> queue;
    static char[] word1, word2, word;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int T = Integer.parseInt(br.readLine());
        for (int testcase = 1; testcase <= T; testcase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            word1 = (st.nextToken() + ' ').toCharArray();
            word2 = (st.nextToken() + ' ').toCharArray();
            word = (st.nextToken() + ' ').toCharArray();

            System.out.println(solveByBFS() ? "Data set " + testcase + ": " + "yes" : "Data set " + testcase + ": " + "no");
        }
    }

    private static boolean solveByBFS() {
        visit = new boolean[word1.length + 1][word2.length + 1];
        queue = new LinkedList<>();
        visit[0][0] = true;
        queue.offer(new Word(0, 0, 0));

        while (!queue.isEmpty()) {
            Word curWord = queue.poll();

            if (curWord.w == word.length) {
                return true;
            }

            if (!visit[curWord.w1 + 1][curWord.w2] && word1[curWord.w1] == word[curWord.w]) {
                visit[curWord.w1 + 1][curWord.w2] = true;
                queue.offer(new Word(curWord.w1 + 1, curWord.w2, curWord.w + 1));
            }
            if (!visit[curWord.w1][curWord.w2 + 1] && word2[curWord.w2] == word[curWord.w]) {
                visit[curWord.w1][curWord.w2 + 1] = true;
                queue.offer(new Word(curWord.w1, curWord.w2 + 1, curWord.w + 1));
            }
        }
        return false;
    }
}
