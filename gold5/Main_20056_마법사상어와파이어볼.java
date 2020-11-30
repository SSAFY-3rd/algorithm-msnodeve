import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_20056_마법사상어와파이어볼 {

    static class Fireball {
        // r, c: 파이어볼의 위치
        // m: 질량
        // s: 속도
        // d: 방향
        int r, c, m, s, d;

        public Fireball(int r, int c, int m, int s, int d) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
        }

        @Override
        public String toString() {
            return "Fireball{" +
                    "r=" + r +
                    ", c=" + c +
                    ", m=" + m +
                    ", s=" + s +
                    ", d=" + d +
                    '}';
        }
    }

    static int N, M, K;
    static Queue<Fireball>[][] map;
    static Queue<Fireball> currentFireballs;
    static int[][] dir = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        currentFireballs = new LinkedList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            currentFireballs.offer(new Fireball(r - 1, c - 1, m, s, d));
        }

        // 처리
        for (int i = 0; i < K; i++) {
            // Fireball 방향, 속력으로 이동
            moveFireball();
            divideFireball();
        }

        // 출력
        System.out.println(getResult());
    }

    private static int getResult() {
        initMap();
        int result = 0;
        while (!currentFireballs.isEmpty()) {
            Fireball fireball = currentFireballs.poll();
            result += fireball.m;
        }
        return result;
    }

    private static void divideFireball() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 비어있다면 패스
                if (map[i][j].isEmpty()) {
                    continue;
                }

                // 해당 크기가 2이상인 경우 분할
                if (map[i][j].size() >= 2) {
                    int m = 0;
                    int s = 0;
                    boolean isOdd = true;
                    boolean isEven = true;
                    int fireballSize = map[i][j].size();
                    for (int k = 0; k < fireballSize; k++) {
                        Fireball fireball = map[i][j].poll();
                        m += fireball.m;
                        s += fireball.s;
                        if (fireball.d % 2 == 0) {
                            isEven = false;
                        } else {
                            isOdd = false;
                        }
                    }
                    int newD = isEven != isOdd ? 0 : 1;
                    m /= 5;
                    s /= fireballSize;

                    for (int k = 0; k < 4; k++) {
                        Fireball fireball = new Fireball(i, j, m, s, newD);
                        newD = newD + 2;
                        if (m != 0) {
                            currentFireballs.offer(fireball);
                        }
                    }
                } else {
                    currentFireballs.offer(map[i][j].poll());
                }
            }
        }
    }

    private static void moveFireball() {
        initMap();
        int size = currentFireballs.size();
        for (int i = 0; i < size; i++) {
            Fireball curFireball = currentFireballs.poll();
            // 다음 위치 확인, 현재 파이어볼 위치 + 방향으로 속도 만큼
            int nr = (curFireball.r + dir[curFireball.d][0] * curFireball.s) % N;
            int nc = (curFireball.c + dir[curFireball.d][1] * curFireball.s) % N;

            // - 인경우 N 더해줘야함
            if(nr < 0){
                nr += N;
            }
            if(nc < 0){
                nc += N;
            }

            curFireball.r = nr;
            curFireball.c = nc;
            map[nr][nc].offer(curFireball);
        }
    }

    private static void initMap() {
        // 맵 초기화 작업
        map = new Queue[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = new LinkedList();
            }
        }
    }
}
