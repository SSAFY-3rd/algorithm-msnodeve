import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] populationMap;
    static int result = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        N = Integer.parseInt(br.readLine());
        populationMap = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                populationMap[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 처리
        for (int x = 1; x <= N; x++) {
            for (int y = 1; y <= N; y++) {
                for (int d1 = 1; d1 <= N; d1++) {
                    for (int d2 = 1; d2 <= N; d2++) {
                        if (isRange(x, y, d1, d2)) {
                            int[][] map = new int[N + 1][N + 1];
                            draw5(map, x, y, d1, d2);
                            draw1(map, x, y, d1, d2);
                            draw2(map, x, y, d1, d2);
                            draw3(map, x, y, d1, d2);
                            draw4(map, x, y, d1, d2);
                            result = Math.min(result, getPopulation(map));
                        }
                    }
                }
            }
        }

        // 출력
        System.out.println(result);
    }

    // 그 지역의 최대 최소 인구수 차의 최소 값
    private static int getPopulation(int[][] map) {
        int[] list = new int[5];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                list[map[i][j] - 1] += populationMap[i][j];
            }
        }
        Arrays.sort(list);
        return list[4] - list[0];
    }

    // 4번 선거구: x+d2 < r ≤ N, y-d1+d2 ≤ c ≤ N
    private static void draw4(int[][] map, int x, int y, int d1, int d2) {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (map[i][j] != 5 && x + d2 < i && i <= N && y - d1 + d2 <= j && j <= N) {
                    map[i][j] = 4;
                }
            }
        }
    }

    // 3번 선거구: x+d1 ≤ r ≤ N, 1 ≤ c < y-d1+d2
    private static void draw3(int[][] map, int x, int y, int d1, int d2) {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (map[i][j] != 5 && x + d1 <= i && i <= N && 1 <= j && j < y - d1 + d2) {
                    map[i][j] = 3;
                }
            }
        }
    }

    // 2번 선거구: 1 ≤ r ≤ x+d2, y < c ≤ N
    private static void draw2(int[][] map, int x, int y, int d1, int d2) {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (map[i][j] != 5 && 1 <= i && i <= x + d2 && y < j && j <= N) {
                    map[i][j] = 2;
                }
            }
        }
    }

    // 1번 선거구: 1 ≤ r < x+d1, 1 ≤ c ≤ y
    private static void draw1(int[][] map, int x, int y, int d1, int d2) {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (map[i][j] != 5 && 1 <= i && i < x + d1 && 1 <= j && j <= y) {
                    map[i][j] = 1;
                }
            }
        }
    }

    // 다음 칸은 경계선이다.
    private static void draw5(int[][] map, int x, int y, int d1, int d2) {
        for (int i = 0; i <= d1; i++) { // (x, y), (x+1, y-1), ..., (x+d1, y-d1)
            map[x + i][y - i] = 5;
        }
        for (int i = 0; i <= d2; i++) { // (x, y), (x+1, y+1), ..., (x+d2, y+d2)
            map[x + i][y + i] = 5;
        }
        for (int i = 0; i <= d2; i++) { // (x+d1, y-d1), (x+d1+1, y-d1+1), ... (x+d1+d2, y-d1+d2)
            map[x + d1 + i][y - d1 + i] = 5;
        }
        for (int i = 0; i <= d1; i++) { // (x+d2, y+d2), (x+d2+1, y+d2-1), ..., (x+d2+d1, y+d2-d1)
            map[x + d2 + i][y + d2 - i] = 5;
        }

        // 경계선 그리고 난 뒤 경계선 내부 채우기
        for (int i = 1; i <= N; i++) {
            int[] f = new int[2];
            int cnt = 0;
            for (int j = 1; j <= N; j++) {
                if (map[j][i] == 5) {
                    f[cnt] = j;
                    cnt++;
                }
            }
            if (cnt == 2) {
                for (int j = f[0]; j <= f[1]; j++) {
                    map[j][i] = 5;
                }
            }
        }
    }

    // 기준점 (x, y)와 경계의 길이 d1, d2를 정한다.
    private static boolean isRange(int x, int y, int d1, int d2) {
        return d1 >= 1 && d2 >= 1 && // d1, d2 ≥ 1
                1 <= x && x < x + d1 + d2 && x + d1 + d2 <= N && // 1 ≤ x < x+d1+d2 ≤ N
                1 <= y - d1 && y - d1 < y && y < y + d2 && y + d2 <= N; // 1 ≤ y-d1 < y < y+d2 ≤ N
    }
}