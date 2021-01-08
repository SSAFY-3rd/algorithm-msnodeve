import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int m, n;
    static int[] root, rank;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        root = new int[n];
        rank = new int[n];

        // 처리
        for (int i = 0; i < n; i++) {
            root[i] = i;
        }
        int result = -1;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if (find(x) == find(y)) {
                result = i + 1;
                break;
            }
            union(x, y);
        }

        // 출력
        System.out.println(result == -1 ? 0 : result);
    }

    private static int find(int x) {
        if (root[x] == x) {
            return x;
        }
        return root[x] = find(root[x]);
    }

    private static void union(int x, int y) {
        x = find(x);
        y = find(y);

        if (x == y) {
            return;
        }

        if (rank[x] < rank[y]) {
            root[x] = y;
        } else {
            root[y] = x;
            if (rank[x] == rank[y]) {
                rank[y]++;
            }
        }
    }
}
