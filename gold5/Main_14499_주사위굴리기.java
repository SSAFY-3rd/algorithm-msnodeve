import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_14499_주사위굴리기 {

    static int N, M, x, y, K; // 지도의 크기, 주사위 위치, 명령어 수
    static int[] dice = {0, 0, 0, 0, 0, 0}; // 아래, 뒤, 오른쪽, 왼쪽, 앞, 위
    static int[][] map;
    static int[][] dir = {{0, 0}, {0, 1}, {0, -1}, {-1, 0}, {1, 0}}; // 1:동, 2:서, 3:북, 4:남

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        // 처리
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            // 명령어 받기
            int command = Integer.parseInt(st.nextToken());

            // 바깥인 경우 명령 무시
            if (isWall(command)) {
                continue;
            }

            // 이동
            x += dir[command][0];
            y += dir[command][1];

            // 명령어 수행
            moveDice(command);

            // 바닥 검사 / 수행
            copy();

            // 결과 저장
            sb.append(dice[5]).append('\n');
        }

        // 출력
        System.out.println(sb.toString());
    }

    private static void copy() {
        if (map[x][y] == 0) {
            map[x][y] = dice[0];
        } else {
            dice[0] = map[x][y];
            map[x][y] = 0;
        }
    }

    private static boolean isWall(int command) {
        int nextX = x + dir[command][0];
        int nextY = y + dir[command][1];
        return nextX < 0 || nextY < 0 || N <= nextX || M <= nextY;
    }

    private static void moveDice(int command) {
        int tempNumber = dice[0];
        switch (command) {
            case 1: // 동
                dice[0] = dice[2];
                dice[2] = dice[5];
                dice[5] = dice[3];
                dice[3] = tempNumber;
                break;
            case 2: // 서
                dice[0] = dice[3];
                dice[3] = dice[5];
                dice[5] = dice[2];
                dice[2] = tempNumber;
                break;
            case 3: // 북
                dice[0] = dice[1];
                dice[1] = dice[5];
                dice[5] = dice[4];
                dice[4] = tempNumber;
                break;
            case 4: // 남
                dice[0] = dice[4];
                dice[4] = dice[5];
                dice[5] = dice[1];
                dice[1] = tempNumber;
                break;
        }
    }
}
