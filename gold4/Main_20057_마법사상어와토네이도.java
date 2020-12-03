import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_20057_마법사상어와토네이도 {
    static int N, result, row, col, curDirection;
    static int[][] dir = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    static int[][][] tornadoes;
    static int[][] map;
    static boolean[][] visit;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 초기화 작업
        initTornadoes();

        // 입력
        N = Integer.parseInt(br.readLine());
        row = col = N / 2;
        map = new int[N][N];
        visit = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        visit[row][col] = true;
        // 처리
        while (row != 0 || col != 0) {
            int nr = row + dir[curDirection][0];
            int nc = col + dir[curDirection][1];
            visit[nr][nc] = true;
            row = nr;
            col = nc;
            // 여기까지 이동한 상태이므로, 모래 흩뿌리기 시작
            spreadSend(row, col);

            int nd = (1 + curDirection) % 4;
            nr += dir[nd][0];
            nc += dir[nd][1];
            if (!visit[nr][nc]) {
                curDirection = nd;
            }
        }

        // 출력
        System.out.println(result);
    }

    private static void spreadSend(int row, int col) {
        int allSend = 0;
        int r, c;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                r = row - (2 - i);
                c = col - (2 - j);
                int send = tornadoes[curDirection][i][j] * map[row][col] / 100;
                allSend += send;
                if(isOut(r, c)) {
                    result += send;
                }else {
                    map[row - (2 - i)][col - (2 - j)] += send;
                }
            }
        }
        r = row + dir[curDirection][0];
        c = col + dir[curDirection][1];
        if(isOut(r, c)) {
            result += map[row][col] - allSend;
        }else{
            map[r][c] += map[row][col] - allSend;
        }
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || N <= row || col < 0 || N <= col;
    }

    private static void initTornadoes() {
        tornadoes = new int[4][5][5];
        tornadoes[0][0][2] = 2;
        tornadoes[0][4][2] = 2;
        tornadoes[0][1][1] = 10;
        tornadoes[0][3][1] = 10;
        tornadoes[0][1][2] = 7;
        tornadoes[0][3][2] = 7;
        tornadoes[0][1][3] = 1;
        tornadoes[0][3][3] = 1;
        tornadoes[0][2][0] = 5;

        // 각각 회전
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    tornadoes[i + 1][j][k] = tornadoes[i][k][4 - j];
                }
            }
        }
    }
}
