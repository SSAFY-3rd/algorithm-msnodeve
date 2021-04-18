import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

class Solution {
    static int N, M, result;
    static ArrayList<int[]> homes;

    public static void main(String[] args) throws Exception {
//        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int T = Integer.parseInt(br.readLine());
        for (int testcase = 1; testcase <= T; testcase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            result = 0;
            homes = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    if (Integer.parseInt(st.nextToken()) == 1) {
                        homes.add(new int[]{i, j});
                    }
                }
            }

            // 처리
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int k = 1; k <= 21; k++) {
                        solution(i, j, k);
                    }
                }
            }

            System.out.println("#" + testcase + " " + result);
        }
    }

    private static void solution(int i, int j, int k) {
        int count = 0;
        for (int[] homePos : homes) {
            int dist = Math.abs(i - homePos[0]) + Math.abs(j - homePos[1]);
            if (dist < k) {
                count++;
            }
        }
        if (M * count - ((2 * k * k) + (-2 * k) + 1) >= 0) {
            result = Math.max(result, count);
        }
    }
}