import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N;
    static int[] num, D;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        N = Integer.parseInt(br.readLine());
        num = new int[N + 1];
        D = new int[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < N + 1; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j <= i; j++) {
                D[i] = Math.max(D[i], num[j] + D[i - j]);
            }
        }

        System.out.println(D[N]);
    }
}
