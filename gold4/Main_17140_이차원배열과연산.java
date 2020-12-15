import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static class Number implements Comparable<Number> {
        int key, val;

        public Number(int key, int val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public int compareTo(Number number) {
            if (this.val == number.val) {
                return this.key - number.key;
            } else {
                return this.val - number.val;
            }
        }

        @Override
        public String toString() {
            return "Number [key=" + key + ", val=" + val + "]";
        }

    }

    static int r, c, k;
    static int R = 3, C = 3;
    static int[][] map = new int[101][101];
    static PriorityQueue<Number> pq;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= 3; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int result = 0;
        // 처리
        int i;
        for (i = 0; i <= 100; i++) {
            // 확인
            if (map[r][c] == k) {
                result = i;
                break;
            }
            // R연산
            if (R >= C) {
                calcR();
            }else {
                calcC();
            }
        }
        System.out.println(i == 101 ? -1 : result);
    }

    private static void calcC() {
        // 출력
        int tR = R;
        R = 0;
        for (int j = 1; j <= C; j++) {
            int[] r = new int[101];
            pq = new PriorityQueue<>();
            for (int i = 1; i <= tR; i++) {
                int num = map[i][j];
                r[num]++;
                map[i][j] = 0;
            }
            for (int i = 1; i <= 100; i++) {
                if (r[i] != 0) {
                    pq.offer(new Number(i, r[i]));
                }
            }
            int row = 1;
            while(!pq.isEmpty()) {
                Number number = pq.poll();
                map[row++][j] = number.key;
                map[row++][j] = number.val;
            }
            R = Math.max(R, row - 1);
        }
    }

    private static void calcR() {
        int tC = C;
        C = 0;
        for (int i = 1; i <= R; i++) {
            int[] c = new int[101];
            pq = new PriorityQueue<>();
            for (int j = 1; j <= tC; j++) {
                int num = map[i][j];
                c[num]++;
                map[i][j] = 0;
            }
            for (int j = 1; j <= 100; j++) {
                if (c[j] != 0) {
                    pq.offer(new Number(j, c[j]));
                }
            }
            int col = 1;
            while (!pq.isEmpty()) {
                Number number = pq.poll();
                map[i][col++] = number.key;
                map[i][col++] = number.val;
            }
            C = Math.max(C, col - 1);
        }
    }
}
