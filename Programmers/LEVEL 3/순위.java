import java.util.*;

class Solution {

    public static void main(String[] args) throws Exception {
//        System.setIn(new FileInputStream("Runner/sample.txt"));
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        System.out.println(solution(5, new int[][]{{4, 3}, {4, 2}, {3, 2}, {1, 2}, {2, 5}}));
    }

    public static int solution(int n, int[][] results) {

        int answer = 0;
        boolean[][] win = new boolean[n + 1][n + 1];
        boolean[][] lose = new boolean[n + 1][n + 1];
        for (int i = 0; i < results.length; i++) {
            int w = results[i][0];
            int l = results[i][1];
            win[w][l] = true;
            lose[l][w] = true;
        }

        for (int i = 1; i <= n; i++) { // 1번 부터 n 번까지 돌려 보기
            boolean[] victory1 = new boolean[n + 1];
            boolean[] victory2 = new boolean[n + 1];

            Queue<Integer> q1 = new LinkedList<>();
            q1.offer(i);

            while (!q1.isEmpty()) {
                int cur = q1.poll();
                for (int j = 1; j <= n; j++) {
                    if (!victory1[j] && win[cur][j]) {
                        victory1[j] = true;
                        q1.offer(j);
                    }
                }
            }

            Queue<Integer> q2 = new LinkedList<>();
            q2.offer(i);

            while (!q2.isEmpty()) {
                int cur = q2.poll();
                for (int j = 1; j <= n; j++) {
                    if (!victory2[j] && lose[cur][j]) {
                        victory2[j] = true;
                        q2.offer(j);
                    }
                }
            }

            victory1[i] = true;
            victory2[i] = true;

            if (check(victory1, victory2, n)) {
                answer++;
            }

        }
        return answer;
    }

    private static boolean check(boolean[] victory1, boolean[] victory2, int n) {
        for (int j = 1; j <= n; j++) {
            if (!victory1[j] && !victory2[j]) {
                return false;
            }
        }
        return true;
    }
}