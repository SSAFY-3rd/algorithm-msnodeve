import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class Shark {
        int r, c, s, d, z;
        public Shark(int r, int c, int s, int d, int z) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.d = d;
            this.z = z;
        }
    }

    static int R, C, M, result;
    static int[][] dir = { { 0, 0 }, { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } }; // 위 아래 오른쪽 왼쪽
    static int[][] map;
    static Queue<Shark> queue;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        queue = new LinkedList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            switch (d) {
                case 1:
                case 2:
                    s = s % (2 * R - 2);
                    break;
                case 3:
                case 4:
                    s = s % (2 * C - 2);
                    break;
            }
            queue.offer(new Shark(r, c, s, d, z));
        }

        // 처리
        map = new int[R + 1][C + 1];
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            Shark shark = queue.poll();
            map[shark.r][shark.c] = shark.z;
            queue.offer(shark);
        }

        solve();

        // 출력
        System.out.println(result);
    }

    private static void solve() {
        for (int i = 1; i <= C; i++) {
            // 낚시왕 해당 열 확인하고 상어 잡기
            catchShark(i);

            // 상어 이동시키기
            moveShark();

            // 상어 중복 체크
            checkShark();
        }
    }

    private static void checkShark() {
        HashMap<Integer, Shark> hashMap = new HashMap<>();
        while (!queue.isEmpty()) {
            Shark shark = queue.poll();
            int key = shark.r * 10000 + shark.c;
            if (hashMap.containsKey(key)) {
                Shark otherShark = hashMap.get(key);
                if (otherShark.z < shark.z) {
                    hashMap.put(key, shark);
                }
            } else {
                hashMap.put(key, shark);
            }
        }
        map = new int[R + 1][C + 1];
        hashMap.forEach((key, shark) -> {
            map[shark.r][shark.c] = shark.z;
            queue.offer(shark);
        });
    }

    private static void moveShark() {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            Shark shark = queue.poll();
            // 낚시왕이 잡은 물고기
            if (map[shark.r][shark.c] == -1) {
                continue;
            }
            for (int j = 0; j < shark.s; j++) {
                int nr = shark.r + dir[shark.d][0];
                int nc = shark.c + dir[shark.d][1];
                // 밖으로 나갔다면
                if (isOut(nr, nc)) {
                    switch (shark.d) {
                        case 1:
                            shark.d = 2;
                            break;
                        case 2:
                            shark.d = 1;
                            break;
                        case 3:
                            shark.d = 4;
                            break;
                        case 4:
                            shark.d = 3;
                            break;
                    }
                }
                nr = shark.r + dir[shark.d][0];
                nc = shark.c + dir[shark.d][1];
                shark.r = nr;
                shark.c = nc;
            }
            queue.offer(shark);
        }
    }

    private static boolean isOut(int row, int col) {
        return 1 > row || row > R || 1 > col || col > C;
    }

    private static void catchShark(int col) {
        for (int i = 1; i <= R; i++) {
            if (map[i][col] != 0) {
                result += map[i][col];
                map[i][col] = -1;
                break;
            }
        }
    }
}
