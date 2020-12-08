import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Position {
        int row, col;
        boolean isFire;

        public Position(int row, int col, boolean isFire) {
            this.row = row;
            this.col = col;
            this.isFire = isFire;
        }
    }

    static int R, C;
    static boolean[][] visit;
    static Queue<Position> queue;
    static int result;
    static int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        visit = new boolean[R][C];
        queue = new LinkedList<>();
        int r = 0, c = 0;
        for (int i = 0; i < R; i++) {
            char[] str = br.readLine().toCharArray();
            for (int j = 0; j < C; j++) {
                switch (str[j]) {
                    case 'J': // 지훈이
                        r = i;
                        c = j;
                        visit[i][j] = true;
                        break;
                    case '#': // 벽
                        visit[i][j] = true;
                        break;
                    case 'F': // 불
                        queue.offer(new Position(i, j, true));
                        visit[i][j] = true;
                        break;
                }
            }
        }
        // 처리
        // 제일 뒤 지훈이 넣기
        queue.offer(new Position(r, c, false));
        String ret = findExit() ? result + "" : "IMPOSSIBLE";

        // 출력
        System.out.println(ret);
    }

    private static boolean findExit() {
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Position curPosition = queue.poll();
                for (int j = 0; j < 4; j++) {
                    int nr = curPosition.row + dir[j][0];
                    int nc = curPosition.col + dir[j][1];

                    // 불인 경우
                    if (curPosition.isFire) {
                        // 밖이거나, 이미 방문했고, 벽인경우
                        if (isOut(nr, nc) || visit[nr][nc]) {
                            continue;
                        }
                        visit[nr][nc] = true;
                        queue.offer(new Position(nr, nc, true));
                    } else { // 지훈이인 경우
                        // 바깥이라면 탈출구
                        if (isOut(nr, nc)) {
                            result++;
                            return true;
                        }
                        // 방문을 했거나 벽이거나 불이라면 패스
                        if (visit[nr][nc]) {
                            continue;
                        }
                        visit[nr][nc] = true;
                        queue.offer(new Position(nr, nc, false));
                    }
                }
            }
            result++;
        }
        return false;
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || R <= row || col < 0 || C <= col;
    }
}
