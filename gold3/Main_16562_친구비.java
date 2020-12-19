import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] root, A;
    static int N, M, k;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        root = new int[N + 1];
        A = new int[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
            root[i] = i;
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            union(a, b);
        }

        int result = 0;
        for (int i = 1; i <= N; i++) {
            int x = find(i);
            if (x != 0) {
                result += A[x];
                union(0, x);
            }
//            System.out.println(Arrays.toString(root));
        }

        System.out.println(k >= result ? result : "Oh no");
    }

    private static int find(int x) {
        if (root[x] == x) {
            return x;
        } else {
            return root[x] = find(root[x]);
        }
    }

    private static void union(int x, int y) {
        x = find(x);
        y = find(y);

        // 만약 비용이 x 친구가 더 작으면
        if (A[x] <= A[y]) {
            root[y] = x; // 비싼놈 버리고 그놈을 하나의 친구로 만들어버리기
        } else {
            root[x] = y;
        }
    }
}
