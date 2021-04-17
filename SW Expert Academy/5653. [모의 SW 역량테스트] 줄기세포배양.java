import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {
    static class Cell {
        int row, col, time, age, status;

        public Cell(int row, int col, int age) {
            this.row = row;
            this.col = col;
            this.age = age;
            this.time = age;
            this.status = 0;
        }

        public void next() {
            switch (this.status) {
                case 0: // 비활성화 되어있는 상태
                    if (--this.time == 0) {
                        this.status = 1;
                    }
                    break;
                case 1:
                    if (++this.time == this.age) {
                        this.status = -1;
                    }
                    break;
            }
        }
    }

    static int result, K, N, M, tN, tM;
    static int[][] map;
    static boolean[][] visit;
    static Queue<Cell> queue;
    static int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // input
        int T = Integer.parseInt(br.readLine());
        for (int testcase = 1; testcase <= T; testcase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            tN = (400 + N) / 2 - N;
            tM = (400 + M) / 2 - M;

            queue = new LinkedList<>();
            map = new int[400][400];
            visit = new boolean[400][400];

            for (int i = 0; i < 400; i++) {
                for (int j = 0; j < 400; j++) {
                    map[i][j] = 0;
                }
            }

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    int age = Integer.parseInt(st.nextToken());
                    if (age != 0) {
                        map[i + tN][j + tM] = age;
                        visit[i + tN][j + tM] = true;
                        queue.offer(new Cell(i + tN, j + tM, age));
                    }
                }
            }

            // process
            result = solution();

            // output
            System.out.println("#" + testcase + " " + result);

        }
    }

    private static int solution() {
        for (int k = 0; k < K; k++) {
            int size = queue.size();
            for (Cell cell : queue) {
                if (cell.status == 1) {
                    check(cell);
                }
            }

            for (int t = 0; t < size; t++) {
                Cell cell = queue.poll();
                if (cell.status == 1) {
                    for (int i = 0; i < 4; i++) {
                        int nr = cell.row + dir[i][0];
                        int nc = cell.col + dir[i][1];

                        if (visit[nr][nc] || map[nr][nc] != cell.age) {
                            continue;
                        }

                        queue.offer(new Cell(nr, nc, map[nr][nc]));
                        visit[nr][nc] = true;
                    }
                }

                cell.next();

                if (cell.status == -1) {
                    continue;
                }
                queue.offer(cell);
            }
        }
        return queue.size();
    }

    private static void check(Cell cell) {
        for (int i = 0; i < 4; i++) {
            int nr = cell.row + dir[i][0];
            int nc = cell.col + dir[i][1];

            if (visit[nr][nc]) {
                continue;
            }

            if (map[nr][nc] < cell.age) {
                map[nr][nc] = cell.age;
            }
        }
    }
}