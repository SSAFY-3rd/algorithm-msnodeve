import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Solution {
    static class BC {
        int idx, row, col, c, p;

        public BC(int idx, int col, int row, int c, int p) {
            this.idx = idx;
            this.row = row;
            this.col = col;
            this.c = c;
            this.p = p;
        }
    }

    static int result;
    static int[][] dir = {{0, 0}, {-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static BC[] bcs;
    static int M, A;
    static int[] userA, userB;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int T = Integer.parseInt(br.readLine());
        for (int testcase = 1; testcase <= T; testcase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            result = 0;
            M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());
            bcs = new BC[A];
            userA = new int[M + 1];
            userB = new int[M + 1];
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= M; i++) {
                userA[i] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= M; i++) {
                userB[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < A; i++) {
                st = new StringTokenizer(br.readLine());
                bcs[i] = new BC(i,
                        Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()));
            }

            int[] posA = new int[]{1, 1};
            int[] posB = new int[]{10, 10};
            for (int m = 0; m <= M; m++) {
                posA[0] += dir[userA[m]][0];
                posA[1] += dir[userA[m]][1];
                posB[0] += dir[userB[m]][0];
                posB[1] += dir[userB[m]][1];

                ArrayList<Integer> BCA = new ArrayList<>();
                ArrayList<Integer> BCB = new ArrayList<>();
                for (int i = 0; i < A; i++) {
                    if (Math.abs(bcs[i].row - posA[0]) + Math.abs(bcs[i].col - posA[1]) <= bcs[i].c) {
                        BCA.add(i);
                    }
                    if (Math.abs(bcs[i].row - posB[0]) + Math.abs(bcs[i].col - posB[1]) <= bcs[i].c) {
                        BCB.add(i);
                    }
                }
                if (idDuplicate(BCA, BCB)) {
                    int max = 0;
                    for (int i = 0; i < A; i++) {
                        for (int j = 0; j < A; j++) {
                            if(BCA.contains(i) && BCB.contains(j)){
                                if(i == j) {
                                    max = Math.max(max, bcs[i].p/2 + bcs[j].p/2);
                                }else{
                                    max = Math.max(max, bcs[i].p + bcs[j].p);
                                }
                            }
                        }
                    }
                    result += max;
                } else {
                    int max = 0;
                    for (Integer i : BCA) {
                        max = Math.max(max, bcs[i].p);
                    }
                    result += max;
                    max = 0;
                    for (Integer i : BCB) {
                        max = Math.max(max, bcs[i].p);
                    }
                    result += max;
                }
            }

            // 출력
            System.out.println("#" + testcase + " " + result);
        }
    }

    private static boolean idDuplicate(ArrayList<Integer> BCA, ArrayList<Integer> BCB) {
        for (Integer i : BCA) {
            if (BCB.contains(i)) {
                return true;
            }
        }
        return false;
    }
}