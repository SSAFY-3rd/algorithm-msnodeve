import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class Virus {
        int row, col;

        public Virus(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "Virus [row=" + row + ", col=" + col + "]";
        }
    }

    static int N, n, r, min;
    static ArrayList<Virus> list;
    static int[][] map;
    static int[] nums;
    static int[][] dir = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
    static Queue<Virus> queue;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        list = new ArrayList<>();
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int num = Integer.parseInt(st.nextToken());
                if (num == 2) {
                    list.add(new Virus(i, j));
                }
                map[i][j] = num;
            }
        }
        min = Integer.MAX_VALUE;
        n = list.size(); // 바이러스 총 개수
        nums = new int[r]; // 뽑아야할 숫자 idx들
        combination(0, 0); // 조합 시작
        System.out.println(min == Integer.MAX_VALUE ? -1 : min);
    }

    private static void combination(int start, int cnt) {
        if (cnt == r) {
            // r개 만큼 뽑았다면
            queue = new LinkedList<>();
            min = Math.min(min, solve(nums));
            return;
        }

        for (int i = start; i < n; i++) {
            nums[cnt] = i;
            combination(i + 1, cnt + 1);
        }
    }

    private static int solve(int[] nums) {
        // 원본 배열 복사
        int[][] cMap = copyMap(map);
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            queue.offer(list.get(nums[i]));
        }

        while (!queue.isEmpty()) {
            if (checkMap(cMap)) {
                return result;
            }

            // 회차 마다 복제를 했을때
            int size = queue.size();
            for (int t = 0; t < size; t++) {
                Virus virus = queue.poll();
                cMap[virus.row][virus.col] = -1;
                for (int i = 0; i < 4; i++) {
                    int nr = virus.row + dir[i][0];
                    int nc = virus.col + dir[i][1];

                    // 이미 밖으로 나갔으며, 벽이며, 이미 복제 된 곳이라면
                    if (isOut(nr, nc) || cMap[nr][nc] == 1 || cMap[nr][nc] == -1) {
                        continue;
                    }

                    cMap[nr][nc] = -1;
                    queue.offer(new Virus(nr, nc));
                }
            }
            result++; // 회수 +1
        }

        // 복제 끝나고 나서 봤을때 복제 안된 곳이 있다면
        if (!checkMap(cMap)) {
            return Integer.MAX_VALUE;
        }
        return result;
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || N <= row || col < 0 || N <= col;
    }

    private static int[][] copyMap(int[][] map) {
        int[][] cMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cMap[i][j] = map[i][j];
            }
        }
        return cMap;
    }

    private static boolean checkMap(int[][] map) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
