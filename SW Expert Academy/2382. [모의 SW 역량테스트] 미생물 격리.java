import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

class Solution {
    static class Cell implements Comparable<Cell> {
        int row, col, value, direction;

        public Cell(int row, int col, int value, int direction) {
            this.row = row;
            this.col = col;
            this.value = value;
            this.direction = direction;
        }

        @Override
        public int compareTo(Cell o) {
            return Integer.compare(o.value, this.value);
        }

        @Override
        public String toString() {
            return "{" +
                    "r=" + row +
                    ", c=" + col +
                    ", v=" + value +
                    ", d=" + direction +
                    '}';
        }
    }

    static int N, M, K, result;
    static ArrayList<Cell>[][] map;
    static Queue<Cell> queue;
    static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int T = Integer.parseInt(br.readLine());
        for (int testcase = 1; testcase <= T; testcase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            result = 0;
            queue = new LinkedList<>();
            map = new ArrayList[N][N];
            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                int row = Integer.parseInt(st.nextToken());
                int col = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                int direction = Integer.parseInt(st.nextToken()) - 1;
                queue.offer(new Cell(row, col, value, direction));
            }

            // 처리
            result = solution();

            // 출력
            System.out.println("#" + testcase + " " + result);
        }
    }

    private static int solution() {
        for (int m = 0; m < M; m++) {

            // 초기화
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    map[i][j] = new ArrayList<>();
                }
            }

            // 이동
            while (!queue.isEmpty()) {
                Cell cell = queue.poll();
                int nr = cell.row + dir[cell.direction][0];
                int nc = cell.col + dir[cell.direction][1];

                if (nr == 0 || nc == 0 || nr == N - 1 || nc == N - 1) { // 색약처리 된 곳이라면
                    switch (cell.direction) { // 방향 전환
                        case 0:
                            cell.direction = 1;
                            break;
                        case 1:
                            cell.direction = 0;
                            break;
                        case 2:
                            cell.direction = 3;
                            break;
                        case 3:
                            cell.direction = 2;
                            break;
                    }
                    cell.value /= 2;
                }
                cell.row = nr;
                cell.col = nc;
                map[nr][nc].add(cell);
            }

            // 처리
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j].size() > 1) {
                        Collections.sort(map[i][j]);
                        Cell cell = map[i][j].get(0);
                        for (int k = 1; k < map[i][j].size(); k++) {
                            cell.value += map[i][j].get(k).value;
                        }
                        map[i][j].clear();
                        map[i][j].add(cell);
                        queue.offer(cell);
                    } else if (map[i][j].size() == 1) {
                        queue.offer(map[i][j].get(0));
                    }
                }
            }
        }

        int count = 0;
        for (Cell cell : queue) {
            count += cell.value;
        }

        return count;
    }
}