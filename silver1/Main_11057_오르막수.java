import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int N;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        N = Integer.parseInt(br.readLine());
        dp = new int[N + 2][10];

        // 초기화
        for (int i = 0; i < 10; i++) {
            dp[1][i] = 1;
        }

        for (int n = 2; n <= N + 1; n++) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j <= i; j++) {
                    dp[n][i] += dp[n - 1][j];
                }
                dp[n][i] %= 10007;
            }
        }

        System.out.println(dp[N + 1][9]);
    }
}
