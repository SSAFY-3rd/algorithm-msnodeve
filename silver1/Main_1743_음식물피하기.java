import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1743_음식물피하기 {
    static int N, M, K;
    static int[][] dir = {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
    static boolean[][] visit;
    static boolean[][] map;
    static int cnt;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        visit = new boolean[N + 1][M + 1];
        map = new boolean[N + 1][M + 1];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            map[r][c] = true;
        }

        // 처리
        int result = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                cnt = 1;
                if (map[i][j] && !visit[i][j]) { // 아직 방문하지 않았다면
                    result = Math.max(result, findTrash(i, j));
                }
            }
        }

        // 출력
        System.out.println(result);
    }

    private static int findTrash(int row, int col) {
        visit[row][col] = true;
        for (int i = 0; i < 4; i++) {
            int nr = row + dir[i][0];
            int nc = col + dir[i][1];
            if (isIn(nr, nc) && !visit[nr][nc] && map[nr][nc]) {
                findTrash(nr, nc);
                cnt++;
            }
        }
        return cnt;
    }

    private static boolean isIn(int row, int col) {
        return row > 0 && col > 0 && row <= N && col <= M;
    }
}
