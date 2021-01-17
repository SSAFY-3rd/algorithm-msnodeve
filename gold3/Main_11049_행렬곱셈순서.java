import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static class Arr {
        int row, col;

        public Arr(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "{" +
                    "r=" + row +
                    ", c=" + col +
                    '}';
        }
    }

    static int N;
    static Arr[] arr;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        N = Integer.parseInt(br.readLine());
        dp = new int[N + 1][N + 1];
        arr = new Arr[N + 1];
        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            arr[i] = new Arr(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        System.out.println(find(1, N));
    }

    private static int find(int start, int end) {
        if (start == end) {
            return 0;
        }
        if (dp[start][end] != Integer.MAX_VALUE) {
            return dp[start][end];
        }

        for (int i = start; i < end; i++) {
            int cost = find(start, i) + arr[start].row * arr[i].col * arr[end].col + find(i + 1, end);
            dp[start][end] = Math.min(dp[start][end], cost);
        }
        return dp[start][end];
    }
}
