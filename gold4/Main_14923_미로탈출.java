import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class HE {
        int row, col, cnt;
        boolean magic;

        public HE(int row, int col, boolean magic, int cnt) {
            this.row = row;
            this.col = col;
            this.magic = magic;
            this.cnt = cnt;
        }
    }

    static int N, M, hr, hc, er, ec;
    static int[][] map;
    static boolean[][][] visit; // [1][2][3] 1: magic 의 유무 2,3: 좌표
    static int[][] dir = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    static Queue<HE> queue;
    static int result;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        hr = Integer.parseInt(st.nextToken()) - 1;
        hc = Integer.parseInt(st.nextToken()) - 1;
        st = new StringTokenizer(br.readLine());
        er = Integer.parseInt(st.nextToken()) - 1;
        ec = Integer.parseInt(st.nextToken()) - 1;
        map = new int[N][M];
        visit = new boolean[2][N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        queue = new LinkedList<>();

        solve();

        System.out.println(result == 0 ? -1 : result);
    }

    private static void solve() {
        queue.offer(new HE(hr, hc, true, 0));
        visit[1][hr][hc] = true;

        while (!queue.isEmpty()) {
            HE curHE = queue.poll();

            // 출구라면
            if (curHE.row == er && curHE.col == ec) {
                result = curHE.cnt;
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nr = curHE.row + dir[i][0];
                int nc = curHE.col + dir[i][1];

                // 바깥이며, 이미 방문했던 곳이라면
                if (isOut(nr, nc)) {
                    continue;
                }

                // 길이며 magic 의 유무에 상관없이 방문하지 않았다면
                if (map[nr][nc] == 0 && !visit[curHE.magic ? 1 : 0][nr][nc]) {
                    queue.offer(new HE(nr, nc, curHE.magic, curHE.cnt + 1));
                    visit[curHE.magic ? 1 : 0][nr][nc] = true;
                }
                // 미사용, 벽, 방문안했으면
                else if (curHE.magic && map[nr][nc] == 1 && !visit[1][nr][nc]) {
                    queue.offer(new HE(nr, nc, false, curHE.cnt + 1));
                    visit[0][nr][nc] = true;
                }
            }
        }
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || N <= row || col < 0 || M <= col;
    }
}
