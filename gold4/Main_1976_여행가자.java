import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static int[][] map;
    static int[] root, rank, trace;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        map = new int[N + 1][N + 1];
        root = new int[N + 1];
        rank = new int[N + 1];
        trace = new int[M + 1];
        for (int i = 1; i < N + 1; i++) {
            root[i] = i;
        }

        for (int i = 1; i < N + 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 1; j < N + 1; j++) {
                if (Integer.parseInt(st.nextToken()) == 1) {
                    union(i, j);
                }
            }
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < M + 1; i++) {
            trace[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(check() ? "YES" : "NO");
    }

    private static boolean check() {
        int group = root[trace[1]];
        for (int i = 1; i < M + 1; i++) {
            if(group != root[trace[i]]){
                return false;
            }
        }
        return true;
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
                rank[x]++;
            }
        }
    }
}
