import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, Q;
    static int[][] map;
    static int rc;
    static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    static boolean[][] visit;
    static Queue<int[]> reduceQueue;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        rc = (int) Math.pow(2, N);
        map = new int[rc][rc];
        for (int i = 0; i < rc; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < rc; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 처리
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < Q; i++) {
            int L = Integer.parseInt(st.nextToken());
            solve((int) Math.pow(2, L));
        }

        // 최대 ice 찾기
        int maxIce = 0;
        int result = 0;
        visit = new boolean[rc][rc];
        for (int i = 0; i < rc; i++) {
            for (int j = 0; j < rc; j++) {
                if(map[i][j] > 0) {
                    result += map[i][j];
                }
                if (map[i][j] > 0 && !visit[i][j]) {
                    maxIce = Math.max(maxIce, findMaxIce(i, j));
                }
            }
        }

        System.out.println(result);
        System.out.println(maxIce);
    }

    private static int findMaxIce(int row, int col) {
        int cnt = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] { row, col });
        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            row = cur[0];
            col = cur[1];
            for (int d = 0; d < 4; d++) {
                int nr = row + dir[d][0];
                int nc = col + dir[d][1];
                if (isOut(nr, nc) || visit[nr][nc] || map[nr][nc] == 0) {
                    continue;
                }

                cnt++;
                visit[nr][nc] = true;
                queue.offer(new int[] {nr, nc});
            }
        }
        return cnt;
    }

    private static void solve(int L) {
        // 배열 돌리기
        rotateMap(L);

        // 얼음 녹이기
        reduceQueue = new LinkedList<>();
        for (int i = 0; i < rc; i++) {
            for (int j = 0; j < rc; j++) {
                findIce(i, j);
            }
        }
        while (!reduceQueue.isEmpty()) {
            int[] cur = reduceQueue.poll();
            if (map[cur[0]][cur[1]] > 0) {
                map[cur[0]][cur[1]]--;
            }
        }
    }

    private static void findIce(int row, int col) {
        int count = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] { row, col });

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            row = cur[0];
            col = cur[1];
            for (int d = 0; d < 4; d++) {
                int nr = row + dir[d][0];
                int nc = col + dir[d][1];
                if (isOut(nr, nc)) {
                    continue;
                }

                if (map[nr][nc] >= 1) {
                    count++;
                }
            }
            if (count < 3) {
                reduceQueue.offer(new int[] { row, col });
            }
        }
    }

    private static boolean isOut(int nr, int nc) {
        return nr < 0 || nc < 0 || nr >= rc || nc >= rc;
    }

    private static void rotateMap(int l) {
        if (l == 1) {
            return;
        }

        for (int i = 0; i < rc; i = i + l) {
            for (int j = 0; j < rc; j = j + l) {
                rotate(i, j, l);
            }
        }
    }

    private static void rotate(int row, int col, int l) {
        int[][] tempMap1 = new int[l][l];
        for (int i = row; i < row + l; i++) {
            for (int j = col; j < col + l; j++) {
                tempMap1[i - row][j - col] = map[i][j];
            }
        }
        int[][] tempMap2 = new int[l][l];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                tempMap2[i][j] = tempMap1[l - 1 - j][i];
            }
        }
        for (int i = row; i < row + l; i++) {
            for (int j = col; j < col + l; j++) {
                map[i][j] = tempMap2[i - row][j - col];
            }
        }
    }
}
