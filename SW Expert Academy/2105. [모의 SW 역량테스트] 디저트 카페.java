import sun.nio.cs.ext.MacHebrew;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

class Solution {
    static int N, result;
    static int[][] map;
    static boolean[][] visit;
    static boolean[] desert;
    static int[][] dir = {{1, -1}, {1, 1}, {-1, 1}, {-1, -1}};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int T = Integer.parseInt(br.readLine());
        for (int testcase = 1; testcase <= T; testcase++) {
            result = -1;
            N = Integer.parseInt(br.readLine());

            map = new int[N][N];
            visit = new boolean[N][N];
            desert = new boolean[101];
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            // 처리
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    visit[i][j] = true;
                    desert[map[i][j]] = true;
                    solution(i, j, 1, 0, i, j, desert);
                    desert[map[i][j]] = false;
                    visit[i][j] = false;
                }
            }

            // 출력
            System.out.println("#" + testcase + " " + result);
        }
    }

    private static void solution(int fRow, int fCol, int cnt, int d, int row, int col, boolean[] desert) {
        if (d == 4) {
            return;
        }
        int nr = row + dir[d][0];
        int nc = col + dir[d][1];

        if (isIn(nr, nc)) {
            if (visit[nr][nc] || desert[map[nr][nc]]) {
                if (fRow == nr && fCol == nc) {
                    result = Math.max(result, cnt);
                }
                return;
            }
            visit[nr][nc] = true;
            desert[map[nr][nc]] = true;
            solution(fRow, fCol, cnt + 1, d, nr, nc, desert);
            solution(fRow, fCol, cnt + 1, d + 1, nr, nc, desert);
            desert[map[nr][nc]] = false;
            visit[nr][nc] = false;
        }
    }

    private static boolean isIn(int nr, int nc) {
        return nr >= 0 && nc >= 0 && nr < N && nc < N;
    }
}