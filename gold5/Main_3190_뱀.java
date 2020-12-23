import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Snake {
        int row, col;

        public Snake(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "{" +
                    "r=" + row +
                    ", c=" + col +
                    '}';
        }
    }

    static int N, K, L;
    static boolean[][] map, snakeBody;
    static String[][] directions;
    static int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static Queue<Snake> queue;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());
        map = new boolean[N + 1][N + 1];
        snakeBody = new boolean[N + 1][N + 1];
        for (int i = 0; i < K; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            map[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = true;
        }
        L = Integer.parseInt(br.readLine());
        directions = new String[L][2];
        for (int i = 0; i < L; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            directions[i][0] = st.nextToken();
            directions[i][1] = st.nextToken();
        }

        // 처리
        queue = new LinkedList<>();
        int row = 1, col = 1, curDir = 1;
        snakeBody[row][col] = true;
        queue.offer(new Snake(row, col));
        int dCount = 0;
        int time;
        for (time = 0; time <= 10000; time++) {
            // 시간 체크 및 방향 전환
            if (dCount < L && time == Integer.parseInt(directions[dCount][0])) {
                switch (directions[dCount][1]) {
                    case "L":
                        curDir = (curDir + 3) % 4;
                        break;
                    case "D":
                        curDir = (curDir + 1) % 4;
                        break;
                }
                dCount++;
            }

            int nr = row + dir[curDir][0];
            int nc = col + dir[curDir][1];
            if (isOut(nr, nc) || snakeBody[nr][nc]) {
                break;
            }

            // 사과다
            if (map[nr][nc]) {
                map[nr][nc] = false;
            } else {
                Snake tail = queue.poll();
                snakeBody[tail.row][tail.col] = false;
            }
            snakeBody[nr][nc] = true;
            row = nr;
            col = nc;
            queue.offer(new Snake(row, col));

        }

        System.out.println(time + 1);
    }

    private static boolean isOut(int nr, int nc) {
        return nr < 1 || nc < 1 || nr > N || nc > N;
    }
}
