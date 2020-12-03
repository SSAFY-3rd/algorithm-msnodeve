import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_14500_테트로미노 {
    static int N, M;
    static int[][] map;
    static boolean[][] visit;
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static int result;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        visit = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                // 처리 1: 1개의 점을 기준으로 4개 카운트 해서 검색
                findShape1(i, j, 0, 0);
                // ㅗ 모양 처리
                findShape2(i, j);
            }
        }


        // 출력
        System.out.println(result);
    }

    private static void findShape2(int row, int col) {
        int wing = 4; // 4방향 검색
        int min = Integer.MAX_VALUE;
        int sum = map[row][col]; // 중심 값

        for (int i = 0; i < 4; i++) {
            int nr = row + dir[i][0];
            int nc = col + dir[i][1];

            if(isOut(nr, nc)){ // 밖으로 나갔다면
                wing--;
                continue;
            }

            if(wing < 3){ // 날개 2개 이하면 ㅗ 모양 아님
                return;
            }

            min = Math.min(min, map[nr][nc]); // 최소 값 찾아서 넣음
            sum += map[nr][nc];
        }

        if(wing == 4){ // 날개 4개라면 최소값을 뺌
            sum -= min;
        }
        result = Math.max(result, sum); // 값을 result 랑 비교
    }

    // 4개 카운트 하면서 탐색
    private static void findShape1(int row, int col, int sum, int cnt) {
        if (cnt == 4) {
            result = Math.max(result, sum);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nr = row + dir[i][0];
            int nc = col + dir[i][1];

            if (isOut(nr, nc) || visit[nr][nc]) {
                continue;
            }
            visit[nr][nc] = true;
            findShape1(nr, nc, sum + map[nr][nc], cnt + 1);
            visit[nr][nc] = false;
        }
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || N <= row || col < 0 || M <= col;
    }
}
