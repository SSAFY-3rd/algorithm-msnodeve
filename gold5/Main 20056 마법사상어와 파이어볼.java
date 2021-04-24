import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class FireBall {
        int row, col, m, s, d;

        public FireBall(int row, int col, int m, int s, int d) {
            this.row = row;
            this.col = col;
            this.m = m;
            this.s = s;
            this.d = d;
        }

        @Override
        public String toString() {
            return "{" +
                    "r=" + row +
                    ", c=" + col +
                    ", m=" + m +
                    ", s=" + s +
                    ", d=" + d +
                    '}';
        }
    }

    static int N, M, K, result;
    static ArrayList<FireBall>[][] map;
    static Queue<FireBall> queue;
    static int[][] dir = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        map = new ArrayList[N][N];
        queue = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            map[row][col].add(new FireBall(row, col, m, s, d));
        }

        // 처리
        for (int time = 0; time < K; time++) {
            initQueue(); // 맵에있는 파이어볼 Queue에 삽입 및 초기화
            moveFireBall(); // 파이어볼 이동
            distributeFireBall(); // 2개 이상의 파이어볼 분해
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < map[i][j].size(); k++) {
                    result += map[i][j].get(k).m;
                }
            }
        }

        // 출력
        System.out.println(result);
    }

    private static void distributeFireBall() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j].size() >= 2) { // 2개 이상의 파이어 볼만
                    int totalM = 0;
                    int totalS = 0;
                    int totalFireBall = map[i][j].size();
                    boolean odd = false;
                    boolean even = false;
                    for (int k = 0; k < map[i][j].size(); k++) {
                        FireBall fireBall = map[i][j].get(k);
                        totalM += fireBall.m;
                        totalS += fireBall.s;
                        if (fireBall.d % 2 == 0) {
                            even = true;
                        } else {
                            odd = true;
                        }
                    }
                    map[i][j].clear();

                    int m = totalM / 5;
                    int s = totalS / totalFireBall;
                    if (m == 0) {
                        continue;
                    } else {
                        if (even && odd) { // 둘다 섞여 있음
                            for (int k = 1; k <= 7; k += 2) {
                                map[i][j].add(new FireBall(i, j, m, s, k));
                            }
                        } else {
                            for (int k = 0; k <= 6; k += 2) {
                                map[i][j].add(new FireBall(i, j, m, s, k));
                            }
                        }
                    }
                }
            }
        }
    }

    private static void moveFireBall() {
        while (!queue.isEmpty()) {
            FireBall fireBall = queue.poll();

            fireBall.row = (fireBall.row + dir[fireBall.d][0] * fireBall.s) % N;
            fireBall.col = (fireBall.col + dir[fireBall.d][1] * fireBall.s) % N;

            if (fireBall.row < 0) {
                fireBall.row += N;
            }
            if (fireBall.col < 0) {
                fireBall.col += N;
            }
            fireBall.row %= N;
            fireBall.col %= N;

            map[fireBall.row][fireBall.col].add(fireBall);
        }
    }

    private static void initQueue() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < map[i][j].size(); k++) {
                    queue.offer(map[i][j].get(k));
                }
                map[i][j].clear();
            }
        }
    }
}