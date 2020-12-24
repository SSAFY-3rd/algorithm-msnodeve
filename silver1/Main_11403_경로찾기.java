import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] map;
    static StringBuilder sb;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        N = Integer.parseInt(br.readLine());
        sb = new StringBuilder();
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int num = Integer.parseInt(st.nextToken());
                map[i][j] = num == 0 ? Integer.MAX_VALUE : 1;
            }
        }

        // 처리
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                if (i == k) {
                    continue;
                }
                for (int j = 0; j < N; j++) {
                    if (map[i][k] == Integer.MAX_VALUE || map[k][j] == Integer.MAX_VALUE) {
                        continue;
                    }
                    map[i][j] = Math.min(map[i][j], map[i][k] + map[k][j]);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(map[i][j] == Integer.MAX_VALUE ? '0' : '1').append(' ');
            }
            sb.append('\n');
        }

        System.out.println(sb.toString());
    }
}
