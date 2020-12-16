import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class Pos {
        int row, col;

        public Pos(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "{" +
                    "r=" + row +
                    ", c=" + col +
                    '}';
        }
    }

    static int N, M, P;
    static Queue<Pos>[] list;
    static int[] sList;
    static int[] pList;
    static int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static char[][] map;
    static int commaCount;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        list = new Queue[P + 1];
        sList = new int[P + 1];
        pList = new int[P + 1];
        for (int i = 1; i <= P; i++) {
            list[i] = new LinkedList<>();
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= P; i++) {
            sList[i] = Integer.parseInt(st.nextToken());
        }
        map = new char[N][M];
        for (int i = 0; i < N; i++) {
            char[] chars = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                if('1' <= chars[j] && chars[j] <= '9'){
                    int num = chars[j] - '0';
                    pList[num]++;
                    list[num].offer(new Pos(i, j));
                }
                if(chars[j] == '.'){
                    commaCount++;
                }
                map[i][j] = chars[j];
            }
        }

        // 처리
        while (commaCount != 0) {
            spreadPlayer();
            boolean flag = true;
            for (int i = 1; i <= P; i++) {
                if(list[i].size() > 0){
                    flag = false;
                }
            }
            if(flag){
                break;
            }
        }

        //출력
        for (int i = 1; i <= P; i++) {
            System.out.print(pList[i] + " ");
        }
    }

    private static void spreadPlayer() {
        for (int p = 1; p <= P; p++) {
            Queue<Pos> queue = list[p];
            int end = 0;
            while(queue.size() > 0){
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Pos curPos = queue.poll();
                    for (int d = 0; d < 4; d++) {
                        int nr = curPos.row + dir[d][0];
                        int nc = curPos.col + dir[d][1];

                        if (isOut(nr, nc) || map[nr][nc] == '#' || map[nr][nc] != '.') {
                            continue;
                        }
                        commaCount--;
                        pList[p]++;
                        map[nr][nc] = (char)(p + '0');
                        list[p].offer(new Pos(nr, nc));
                    }
                }
                end++;
                if(end == sList[p]){
                    break;
                }
            }
        }
    }

    private static boolean isOut(int nr, int nc) {
        return nr < 0 || nc < 0 || nr >= N || nc >= M;
    }
}
