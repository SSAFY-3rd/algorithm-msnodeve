import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static Queue<int[]> clouds, copyClouds;
    static int[][] map;
    static int[][] windsProcess;
    static int[][] windsDir = {{0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}};
    static int[][] hallDir = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        clouds = new LinkedList<>();
        copyClouds = new LinkedList<>();
        windsProcess = new int[M][2];
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            windsProcess[i][0] = Integer.parseInt(st.nextToken()) - 1;
            windsProcess[i][1] = Integer.parseInt(st.nextToken());
        }

        // 처리
        clouds.offer(new int[]{N - 1, 0});
        clouds.offer(new int[]{N - 2, 0});
        clouds.offer(new int[]{N - 1, 1});
        clouds.offer(new int[]{N - 2, 1});
        solution();

        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result += map[i][j];
            }
        }

        // 출력
        System.out.println(result);
    }

    private static void solution() {
        for (int m = 0; m < M; m++) {
            // 각 구름 이동 및 물 1 증가
            int size = clouds.size();
            for (int i = 0; i < size; i++) {
                int[] curCloud = clouds.poll();
                int nr = (curCloud[0] + windsDir[windsProcess[m][0]][0] * windsProcess[m][1]) % N;
                int nc = (curCloud[1] + windsDir[windsProcess[m][0]][1] * windsProcess[m][1]) % N;
                nr = (nr + N) % N;
                nc = (nc + N) % N;
                map[nr][nc]++;
                clouds.offer(new int[]{nr, nc});
            }

            // 웅덩이 더해주기
            size = clouds.size();
            for (int i = 0; i < size; i++) {
                int[] curCloud = clouds.poll();
                int count = 0;
                for (int d = 0; d < 4; d++) {
                    int nr = curCloud[0] + hallDir[d][0];
                    int nc = curCloud[1] + hallDir[d][1];

                    if (isOut(nr, nc)) {
                        continue;
                    }

                    if (map[nr][nc] >= 1) {
                        count++;
                    }
                }
                map[curCloud[0]][curCloud[1]] += count;
                clouds.offer(curCloud);
            }

            copyClouds = new LinkedList<>();
            // 웅덩이 2이상인 곳 -2 및 구름생성
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] >= 2) {
                        boolean flag = true;
                        for (int[] cloud : clouds) {
                            if (cloud[0] == i && cloud[1] == j) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            map[i][j] -= 2;
                            copyClouds.offer(new int[]{i, j});
                        }
                    }
                }
            }

            clouds = copyClouds;
        }
    }

    private static boolean isOut(int nr, int nc) {
        return nr < 0 || nc < 0 || nr >= N || nc >= N;
    }
}

