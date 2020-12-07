import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static class Fish {
        int row, col, direction;
        boolean live;

        public Fish(int row, int col, int direction, boolean live) {
            this.row = row;
            this.col = col;
            this.direction = direction;
            this.live = live;
        }
    }

    static int N = 4;
    static Fish[] fishesList;
    static int[][] map;
    static int result;
    static int[][] dir = {{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}}; // ↑, ↖, ←, ↙, ↓, ↘, →, ↗

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        fishesList = new Fish[17];
        map = new int[4][4];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int fishIdx = Integer.parseInt(st.nextToken()); // 물고기 번호
                int fishDirection = Integer.parseInt(st.nextToken()) - 1; // 물고기 방향
                map[i][j] = fishIdx;
                fishesList[fishIdx] = new Fish(i, j, fishDirection, true);
            }
        }

        // 처리
        int fishIdx = map[0][0];
        int direction = fishesList[map[0][0]].direction;
        fishesList[fishIdx].live = false;
        map[0][0] = -1; // 상어가 먹고 난 자리
        solve(0, 0, fishIdx, direction);

        // 출력
        System.out.println(result);
    }

    private static void solve(int row, int col, int sum, int direction) {
        result = Math.max(result, sum);

        // 배열 복사하기
        int[][] copyMap = new int[4][4];
        Fish[] copyFishesList = new Fish[17];
        copyList(copyMap, map, copyFishesList, fishesList);

        // 물고기 이동 시키기
        moveFishes();

        // 상어 이동 시키기 1~3까지가 최대로 움직일 수 있는 거리
        for (int i = 1; i < 4; i++) {
            int nr = row + dir[direction][0] * i;
            int nc = col + dir[direction][1] * i;

            // 밖으로 나갔거나 죽어있는 물고기라면
            if (isOut(nr, nc) || map[nr][nc] == 0) {
                continue;
            }

            int fishNumber = map[nr][nc];
            // 고기 상태 변경하기
            changeFishState(fishNumber, row, col, nr, nc, true);

            // 먹었으니 더들어가기
            solve(nr, nc, sum + fishNumber, fishesList[fishNumber].direction);

            // 고기 상태 되돌리기
            changeFishState(fishNumber, row, col, nr, nc, false);
        }
        // 한번 끝났으니까 배열 되돌리기
        copyList(map, copyMap, fishesList, copyFishesList);
    }

    private static void changeFishState(int idx, int row, int col, int nextRow, int nextCol, boolean check) {
        if (check) { // 먹었을때
            map[row][col] = 0; // 원래 상어의 위치 -1을 먹은 부분으로 고침
            map[nextRow][nextCol] = -1; // 상어의 위치로 변경
            fishesList[idx].live = false;
        } else {
            map[row][col] = -1; // 먹어 둔걸 다시 상어의 위치로
            map[nextRow][nextCol] = idx; // 죽은 고기를 다시 살려놓기
            fishesList[idx].live = true;
        }
    }

    private static void moveFishes() {
        // 물고기 1마리씩 이동 시키기
        for (int i = 1; i < 17; i++) {
            Fish fish = fishesList[i];

            // 죽어있는 물고기인 경우 패스
            if (!fishesList[i].live) {
                continue;
            }

            // 물고기의 다음 이동할 곳을 바라봄
            int fishRow = fish.row;
            int fishCol = fish.col;
            int fishDirection = fish.direction;
            int nr = fishRow + dir[fishDirection][0];
            int nc = fishCol + dir[fishDirection][1];

            // 물고기의 다음방향이 바깥으로 나갔거나, 상어의 위치라면
            if (isOut(nr, nc) || map[nr][nc] == -1) {
                int rotate = 0;
                while (rotate++ < 8) {
                    int nextFishDirection = (fishDirection + 1) % 8;
                    nr = fishRow + dir[nextFishDirection][0];
                    nc = fishCol + dir[nextFishDirection][1];

                    // 그 다음 방향도 밖으로 나갔거나, 상어의 위치라면
                    if (isOut(nr, nc) || map[nr][nc] == -1) {
                        fishDirection++;
                    } else {
                        swapFish(i, nr, nc, nextFishDirection);
                        break;
                    }
                }
            } else {
                swapFish(i, nr, nc, fishDirection);
            }
        }
    }

    private static void swapFish(int idx, int row, int col, int direction) {
        if(map[row][col] == 0){ // 다음 위치가 죽은 물고기라면
            map[fishesList[idx].row][fishesList[idx].col] = 0;
            map[row][col] = idx;
            fishesList[idx].row = row;
            fishesList[idx].col = col;
            fishesList[idx].direction = direction;
        }else {
            int fishRow = fishesList[idx].row;
            int fishCol = fishesList[idx].col;
            Fish tempFish = new Fish(fishesList[idx].row, fishesList[idx].col, fishesList[idx].direction, fishesList[idx].live);
            fishesList[idx].row = row;
            fishesList[idx].col = col;
            fishesList[idx].direction = direction;
            fishesList[map[row][col]].row = tempFish.row;
            fishesList[map[row][col]].col = tempFish.col;

            int tempFishIdx = map[row][col];
            map[row][col] = map[fishRow][fishCol];
            map[fishRow][fishCol] = tempFishIdx;
        }
    }

    private static void copyList(int[][] copyMap, int[][] map, Fish[] copyFishesList, Fish[] fishesList) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                copyMap[i][j] = map[i][j];
            }
        }
        for (int i = 1; i < 17; i++) {
            copyFishesList[i] = new Fish(fishesList[i].row, fishesList[i].col, fishesList[i].direction, fishesList[i].live);
        }
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || N <= row || col < 0 || N <= col;
    }
}
