import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int size, min;
    static int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static Queue<int[]> posQueue;
    static char[][] map;
    static boolean[][] visit;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int T = 1;
        String input;
        while ((input = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(input);
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            map = new char[N][M];
            visit = new boolean[N][M];
            posQueue = new LinkedList<>();
            for (int i = 0; i < N; i++) {
                char[] chars = br.readLine().toCharArray();
                for (int j = 0; j < M; j++) {
                    switch (chars[j]) {
                        case '*':
                            break;
                        case '.': // 시작이 가능한 점 위치 넣어두기
                            posQueue.offer(new int[]{i, j});
                            break;
                    }
                    map[i][j] = chars[j];
                }
            }
            min = Integer.MAX_VALUE;
            size = posQueue.size();
            // 저장 해놓은 위치 뽑아서 구슬 굴리기
            while(!posQueue.isEmpty()){
                int[] pos = posQueue.poll();
                dfs(pos[0], pos[1], 0);
            }
            System.out.println("Case " + T++ + ": " + (min == Integer.MAX_VALUE ? -1 : min));
        }
    }

    private static void dfs(int row, int col, int cnt) {
        // 해당 위치 방문 처리
        visit[row][col] = true;
        // 방문한 곳이 구슬이 굴러간 개수와 같으면
        int visitSize = countVisit();
        if(visitSize == size){
            min = Math.min(min, cnt);
        }
        for (int d = 0; d < 4; d++) {
            // 공이 나아갈 범위 설정
            int dist = 1;
            int nr = row + dir[d][0] * dist;
            int nc = col + dir[d][1] * dist;

            // 밖으로 나갔거나, 벽이거나, 이미 방문했다면
            if (isOut(nr, nc) || map[nr][nc] == '*' || visit[nr][nc]) {
                continue;
            }

            // 범위 안이며, 길이고, 방문하지 않았다면
            while(isIn(nr, nc) && map[nr][nc] == '.' && !visit[nr][nc]){
                dist++;
                nr = row + dir[d][0] * dist;
                nc = col + dir[d][1] * dist;
            }

            // dist가 한개더 증가해 더해졌기 때문에 1번 빼줌
            nr -= dir[d][0];
            nc -= dir[d][1];


            checkVisit(row, col, nr, nc, d, true); // 방문체크 시작 -> 끝으로 방향으로 방문처리
            dfs(nr, nc, cnt + 1);
            checkVisit(nr, nc, row, col, (d + 2) % 4, false); // 방문체크 해제 -> 끝 -> 시작으로 방향으로 방문해제
        }
    }

    private static int countVisit() {
        int size = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(visit[i][j]){
                    size++;
                }
            }
        }
        return size;
    }

    private static void checkVisit(int row, int col, int nr, int nc, int direction, boolean check) {
        visit[row][col] = check;
        visit[nr][nc] = check;
        int curRow = row;
        int curCol = col;
        int dist = 1;
        while (curRow != nr || curCol != nc) {
            visit[curRow][curCol] = check;
            curRow = row + dir[direction][0] * dist;
            curCol = col + dir[direction][1] * dist++;
        }
    }

    private static boolean isIn(int row, int col) {
        return 0 <= row && row < N && 0 <= col && col < M;
    }

    private static boolean isOut(int row, int col) {
        return row < 0 || N <= row || col < 0 || M <= col;
    }
}
