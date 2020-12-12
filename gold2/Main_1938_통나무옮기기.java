import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] B, E;
    static boolean[][][] visit;
    static int[][] map;
    static int[][] dir = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    static int[][] rotate = { { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 } };
    private static int result;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        visit = new boolean[3][N][N]; // 회전 / 가로 / 세로
        B = new int[][] { { -1, -1 }, { -1, -1 }, { -1, -1 }, { 0 } };
        E = new int[][] { { -1, -1 }, { -1, -1 }, { -1, -1 } };
        for (int i = 0; i < N; i++) {
            String[] str = br.readLine().split("");
            for (int j = 0; j < N; j++) {
                String s = str[j];
                switch (s) {
                    case "B":
                        for (int k = 0; k < 3; k++) {
                            if (B[k][0] == -1) {
                                B[k][0] = i;
                                B[k][1] = j;
                                break;
                            }
                        }
                        break;
                    case "E":
                        for (int k = 0; k < 3; k++) {
                            if (E[k][0] == -1) {
                                E[k][0] = i;
                                E[k][1] = j;
                                break;
                            }
                        }
                        break;
                    case "1":
                        map[i][j] = 1;
                        break;
                }
            }
        }
        // 처리
        solve();

        // 출력
        System.out.println(result);
    }

    private static void solve() {
        for (int i = 0; i < 3; i++) {
            visit[i][B[1][0]][B[1][1]] = true;
        }
        Queue<int[][]> queue = new LinkedList<>();
        queue.offer(B);

        while (!queue.isEmpty()) {
            int[][] curPos = queue.poll();
            if (check(curPos, E)) {
                result = curPos[3][0];
                return;
            }
            int cnt = curPos[3][0];
            // 이동
            for (int i = 0; i < 4; i++) {
                int[][] nextPos = new int[4][2];
                for (int j = 0; j < 3; j++) {
                    nextPos[j][0] = curPos[j][0] + dir[i][0];
                    nextPos[j][1] = curPos[j][1] + dir[i][1];
                }

                // 밖으로 나갔다면
                if (isOut(nextPos) || isTree(nextPos)) {
                    continue;
                }

                if (curPos[0][0] == curPos[1][0]) { // 같은 가로라인에 있다면
                    if (!visit[1][nextPos[1][0]][nextPos[1][1]]) {
                        visit[1][nextPos[1][0]][nextPos[1][1]] = true;
                        nextPos[3][0] = cnt + 1;
                        queue.offer(nextPos);
                    }
                } else { // 같은 세로라인에 있다면
                    if (!visit[2][nextPos[1][0]][nextPos[1][1]]) {
                        visit[2][nextPos[1][0]][nextPos[1][1]] = true;
                        nextPos[3][0] = cnt + 1;
                        queue.offer(nextPos);
                    }
                }
            }
            // 회전
            // 중심을 기준으로 8방향 확인
            if (isRotate(curPos[1][0], curPos[1][1]) && !visit[0][curPos[1][0]][curPos[1][1]]) {
                visit[0][curPos[1][0]][curPos[1][1]] = true;
                curPos[3][0] = cnt + 1;
                if (curPos[0][0] == curPos[1][0]) { // 같은 라인에 있다는 말
                    curPos[0][0]--;
                    curPos[0][1]++;
                    curPos[2][0]++;
                    curPos[2][1]--;
                } else {
                    curPos[0][0]++;
                    curPos[0][1]--;
                    curPos[2][0]--;
                    curPos[2][1]++;
                }
                queue.offer(curPos);
            }
        }
    }

    private static boolean isRotate(int row, int col) {
        for (int i = 0; i < 8; i++) {
            int nr = row + rotate[i][0];
            int nc = col + rotate[i][1];
            // 밖으로 나간 경우 | 1 인경우;
            if (nr < 0 || N <= nr || nc < 0 || N <= nc || map[nr][nc] == 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean check(int[][] pos, int[][] e) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (pos[i][j] != e[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isTree(int[][] pos) {
        for (int i = 0; i < 3; i++) {
            if (map[pos[i][0]][pos[i][1]] == 1) {
                return true;
            }
        }
        return false;
    }

    private static boolean isOut(int[][] pos) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (pos[i][j] < 0 || pos[i][j] >= N) {
                    return true;
                }
            }
        }
        return false;
    }
}
