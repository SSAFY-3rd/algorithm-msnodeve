import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class Shark {
        int idx, row, col, curDir;
        int[][] nextDir;

        public Shark(int idx, int row, int col) {
            this.idx = idx;
            this.row = row;
            this.col = col;
            nextDir = new int[5][5];
        }

        @Override
        public String toString() {
            String result = "";
            result += "[i=" + idx + ", r=" + row + ", c=" + col + ", cd=" + curDir + "]\n";
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= 4; j++) {
                    result += nextDir[i][j] + " ";
                }
                result += '\n';
            }
            return result;
        }
    }

    static class Smell {
        int idx, time;

        public Smell(int idx, int time) {
            this.idx = idx;
            this.time = time;
        }

        @Override
        public String toString() {
            return "[i=" + idx + ", t=" + time + "]";
        }
    }

    static int N, M, k, m;
    static Smell[][] map;
    static Shark[] sharks;
    static int[][] dir = { { 0, 0 }, { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    static int result;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        m = M;
        map = new Smell[N + 1][N + 1];
        sharks = new Shark[M + 1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int num = Integer.parseInt(st.nextToken());
                if (num != 0) {
                    sharks[num] = new Shark(num, i, j);
                }
                map[i][j] = new Smell(0, 0);
            }
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= M; i++) {
            sharks[i].curDir = Integer.parseInt(st.nextToken());
        }
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= 4; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 1; k <= 4; k++) {
                    sharks[i].nextDir[j][k] = Integer.parseInt(st.nextToken());
                }
            }
        }

        // 처리
        for (int i = 1; i <= M; i++) {
            Shark shark = sharks[i];
            map[shark.row][shark.col].idx = i;
            map[shark.row][shark.col].time = k;
        }
        for (int i = 1; i <= 1000; i++) {
            solve();
            if (m == 1) {
                result = i;
                break;
            }
        }

        // 출력
        System.out.println(result == 0 ? -1 : result);
    }

    private static void solve() {
        moveShark();
        minusSmell();
    }

    private static void minusSmell() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (map[i][j].idx != 0) {
                    map[i][j].time--;
                }
                if (map[i][j].time == 0) {
                    map[i][j].idx = 0;
                }
            }
        }
    }

    private static void moveShark() {
        Queue<Shark> queue = new LinkedList<>();
        for (int i = 1; i <= M; i++) {
            Shark shark = sharks[i];
            // 죽은 상어라면 패스
            if (shark == null || shark.idx == -1) {
                continue;
            }

            int possibleNextCount = 0;
            // 현재방향 기준으로 우선순위 방향 따져보기
            for (int d = 1; d <= 4; d++) {
                int nextDir = d;
                int nr = shark.row + dir[nextDir][0];
                int nc = shark.col + dir[nextDir][1];

                if (isOut(nr, nc)) {
                    continue;
                }

                // 빈공간 이라면
                if (map[nr][nc].idx == 0) {
                    possibleNextCount++;
                    break;
                }

                // 상어의 위치라면
//				if (map[nr][nc].idx != i && map[nr][nc].time == k) {
//					possibleNext = true;
//					int maxIdx = Math.max(map[nr][nc].idx, i);
//					sharks[maxIdx].idx = -1; // 상어 죽이기
//					if (maxIdx == i) { // 이동한 상어가 번호가 컸다면
//						shark = sharks[maxIdx];
//					} else { // 이동한 상어가 번호가 작았다면
//						shark.row = nr;
//						shark.col = nc;
//						shark.curDir = nextDir;
//					}
//					m--;
//					break;
//				}
            }

            // 빈공간이 1개 이상이라면
            if (possibleNextCount >= 1) {
                // 우선순위에 따라 방향이동
                for (int d = 1; d <= 4; d++) {
                    int nextDir = shark.nextDir[shark.curDir][d];
                    int nr = shark.row + dir[nextDir][0];
                    int nc = shark.col + dir[nextDir][1];
                    if (isOut(nr, nc)) {
                        continue;
                    }

                    // 빈공간 확인
                    if(map[nr][nc].idx == 0) {
                        shark.row = nr;
                        shark.col = nc;
                        shark.curDir = nextDir;
                        queue.offer(shark);
                        break;
                    }
                }
            }else { // 빈공간이 없다면
                // 나랑 같은 채취인지 확인
                for (int d = 1; d <= 4; d++) {
                    int nextDir = shark.nextDir[shark.curDir][d];
                    int nr = shark.row + dir[nextDir][0];
                    int nc = shark.col + dir[nextDir][1];

                    if (isOut(nr, nc)) {
                        continue;
                    }

                    // 같은 채취라면
                    if (map[nr][nc].idx == i) {
                        shark.row = nr;
                        shark.col = nc;
                        shark.curDir = nextDir;
                        queue.offer(shark);
                        break;
                    }
                }
            }

        }
        // 지금까지 넣어둔 상어들을 뽑아 Map에 넣기
        while(!queue.isEmpty()) {
            Shark shark = queue.poll();
            // 그냥 넣을 수 있는 곳
            if(map[shark.row][shark.col].idx == 0 || map[shark.row][shark.col].idx == shark.idx) {
                map[shark.row][shark.col].idx = shark.idx;
                map[shark.row][shark.col].time = k + 1;
                continue;
            }

            if(map[shark.row][shark.col].idx > shark.idx) {
                sharks[map[shark.row][shark.col].idx].idx = -1;
            }else {
                sharks[shark.idx].idx = -1;
            }
            m--;
        }
    }

    private static boolean isOut(int row, int col) {
        return 1 > row || row > N || 1 > col || col > N;
    }
}
