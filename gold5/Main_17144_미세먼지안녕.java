import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int R, C, T;
    static int up, down;
    static int[][] map;
    static Queue<int[]> queue;
    static int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        map = new int[R][C];
        queue = new LinkedList<>();
        boolean flag = true;
        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < C; j++) {
                int dust = Integer.parseInt(st.nextToken());
                switch (dust) {
                    case 0:
                        break;
                    case -1:
                        if (flag) {
                            up = i;
                            flag = false;
                        } else {
                            down = i;
                        }
                        break;
                    default:
                        queue.offer(new int[]{i, j});
                        break;
                }
                map[i][j] = dust;
            }
        }
        // 처리
        for (int t = 0; t < T; t++) {
            int[][] sumMap = new int[R][C];
            int size = queue.size();
            for (int q = 0; q < size; q++) {
                int[] cur = queue.poll();
                int row = cur[0];
                int col = cur[1];
                int spreadDust = 0;
                int cnt = 0;
                for (int i = 0; i < 4; i++) {
                    int nr = row + dir[i][0];
                    int nc = col + dir[i][1];

                    if (isOut(nr, nc) || map[nr][nc] == -1) {
                        continue;
                    }

                    spreadDust = map[row][col] / 5;
                    sumMap[nr][nc] += spreadDust;
                    cnt++;
                }
                sumMap[row][col] += map[row][col] - spreadDust * cnt;
            }
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    map[i][j] = sumMap[i][j];
                }
            }

            for (int i = up - 1; i > 0; i--) {
                map[i][0] = map[i - 1][0];
            }
            for (int i = 0; i < C - 1; i++) {
                map[0][i] = map[0][i + 1];
            }
            for (int i = 0; i < up; i++) {
                map[i][C - 1] = map[i + 1][C - 1];
            }
            for (int j = C - 1; j > 0; j--) {
                map[up][j] = map[up][j - 1];
            }

            for (int i = down + 1; i < R - 1; i++) {
                map[i][0] = map[i + 1][0];
            }
            for (int j = 0; j < C - 1; j++) {
                map[R - 1][j] = map[R - 1][j + 1];
            }
            for (int i = R - 1; i > down; i--) {
                map[i][C - 1] = map[i - 1][C - 1];
            }
            for (int j = C - 1; j > 0; j--) {
                map[down][j] = map[down][j - 1];
            }

            map[up][0] = -1;
            map[down][0] = -1;

            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (map[i][j] > 0) {
                        queue.offer(new int[]{i, j});
                    }
                }
            }
        }

        int result = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                result += map[i][j];
            }
        }
        System.out.println(result+2);
    }

    private static boolean isOut(int row, int col) {
        return 0 > row || row >= R || 0 > col || col >= C;
    }
}
