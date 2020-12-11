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
    static class Position {
        int row, col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "[r=" + row + ", c=" + col + "]";
        }
    }

    static int N, M, H;
    static boolean[][] map;
    static ArrayList<Position> list;
    static int[] nums;
    static int n, r, size, result = Integer.MAX_VALUE;
    static boolean check;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        map = new boolean[H + 1][N + 1];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            map[a][b] = true;
        }
        // 처리
        list = new ArrayList<>();
        for (int i = 1; i < H + 1; i++) {
            for (int j = 1; j < N; j++) {
                if (!map[i][j] && !map[i][j - 1] && !map[i][j + 1]) {
                    list.add(new Position(i, j));
                }
            }
        }
        size = list.size();
        for (int i = 0; i <= 3; i++) {
            nums = new int[i];
            combination(0, 0, i);
            if(result != Integer.MAX_VALUE) {
                break;
            }
        }
        // 출력
        System.out.println(result == Integer.MAX_VALUE ? -1 : result);
    }

    private static void combination(int start, int cnt, int dest) {
        if (cnt == dest) {
            solve(nums);
            return;
        }
        for (int i = start; i < size; i++) {
            nums[cnt] = i;
            combination(i + 1, cnt + 1, dest);
        }
    }

    private static void solve(int[] nums) {
        boolean[][] cMap = copyMap(map);
        for (int num : nums) {
            Position pos = list.get(num);
            cMap[pos.row][pos.col] = true;
        }
        boolean flag = true;
        for (int cPos = 1; cPos <= N; cPos++) {
            // 시작지점
            int row = 0;
            int col = cPos;
            Queue<Position> queue = new LinkedList<>();
            queue.offer(new Position(row, col));

            while (!queue.isEmpty()) {
                Position curPos = queue.poll();
                row = curPos.row;
                col = curPos.col;
                if (row == H) {
                    if (cPos != col) {
                        flag = false;
                    }
                    break;
                }
                boolean left = false, right = false;
                int nr = row + 1;
                int nc = col;
                if (cMap[nr][nc]) { // 오른쪽으로 이동해야한다는 뜻
                    nc = col + 1;
                    right = true;
                } else {
                    nc = col - 1;
                    if (cMap[nr][nc]) { // 왼쪽으로 이동해야한다는 뜻
                        left = true;
                    }
                }
                if (!left && !right) { // 둘다 아닌 경우
                    nr = row + 1;
                    nc = col;
                }
                queue.offer(new Position(nr, nc));
            }
            if (!flag) {
                break;
            }
        }
        if (flag) {
            result = Math.min(result, nums.length);
        }
    }

    private static boolean[][] copyMap(boolean[][] map) {
        boolean[][] cMap = new boolean[H + 1][N + 1];
        for (int i = 0; i < H + 1; i++) {
            for (int j = 0; j < N + 1; j++) {
                cMap[i][j] = map[i][j];
            }
        }
        return cMap;
    }
}
