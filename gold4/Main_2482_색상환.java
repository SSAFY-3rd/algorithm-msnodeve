import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[][] colors;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int N = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());
        colors = new int[N + 1][N + 1];

        for (int i = 0; i < N + 1; i++) {
            colors[i][0] = 1;
            colors[i][1] = i;
        }

        for (int i = 4; i < N + 1; i++) {
            for (int j = 2; j < N + 1; j++) {
                colors[i][j] = (colors[i - 2][j - 1] + colors[i - 1][j]) % 1_000_000_003;
            }
        }

        System.out.println(colors[N][K]);
    }
}
