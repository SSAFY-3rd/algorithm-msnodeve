import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
    static int N, result;
    static int[][][] warmHall;
    static int[][][] shapes = {{{}},
            {{0, 2}, {1, 3}, {2, 1}, {3, 0}},
            {{0, 1}, {1, 3}, {2, 0}, {3, 2}},
            {{0, 3}, {1, 2}, {2, 0}, {3, 1}},
            {{0, 2}, {1, 0}, {2, 3}, {3, 1}},
            {{0, 2}, {1, 3}, {2, 0}, {3, 1}}};
    static int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // 상, 우, 하, 좌
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int T = Integer.parseInt(br.readLine());
        for (int testcase = 1; testcase <= T; testcase++) {
            result = 0;
            N = Integer.parseInt(br.readLine());
            map = new int[N + 2][N + 2];
            warmHall = new int[11][2][2];
            boolean[] flag = new boolean[11];
            for (int i = 1; i <= N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= N; j++) {
                    int value = Integer.parseInt(st.nextToken());
                    map[i][j] = value;
                    if (6 <= value && value <= 10) {// 웜홀
                        if (!flag[value]) {
                            warmHall[value][0][0] = i;
                            warmHall[value][0][1] = j;
                            flag[value] = true;
                        } else {
                            warmHall[value][1][0] = i;
                            warmHall[value][1][1] = j;
                        }
                    }
                }
            }

            // 처리
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (map[i][j] == 0) {// 빈공간인지
                        for (int k = 0; k < 4; k++) { // 4방향 돌기
                            go(i, j, k);
                        }
                    }
                }
            }

            // 출력
            System.out.println("#" + testcase + " " + result);
        }
    }

    private static void go(int r, int c, int k) {
        int row = r;
        int col = c;
        int count = 0;
        while (true) {
            row = row + dir[k][0];
            col = col + dir[k][1];

            if (isOut(row, col)) { // 벽을 만났을 때
                k = shapes[5][k][1]; // 방향 전환
                count++;
            } else if (1 <= map[row][col] && map[row][col] <= 5) { // 블록일 경우
                k = shapes[map[row][col]][k][1]; // 방향 전환
                count++;
            } else if(6 <= map[row][col] && map[row][col] <= 10){ // 웜홀일 경우
                int tr = row;
                int tc = col;
                row = (warmHall[map[tr][tc]][0][0] == tr && warmHall[map[tr][tc]][0][1] == tc) ? warmHall[map[tr][tc]][1][0] : warmHall[map[tr][tc]][0][0];
                col = (warmHall[map[tr][tc]][0][0] == tr && warmHall[map[tr][tc]][0][1] == tc) ? warmHall[map[tr][tc]][1][1] : warmHall[map[tr][tc]][0][1];
            }

            if ((row == r && col == c) || map[row][col] == -1) { // 제자리로 돌아왔거나 블랙홀인 경우 종료
                result = Math.max(result, count);
                break;
            }
        }
    }

    private static boolean isOut(int row, int col) {
        return row <= 0 || col <= 0 || row > N || col > N;
    }
}