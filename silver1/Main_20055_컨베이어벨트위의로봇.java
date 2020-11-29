import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_20055_컨베이어벨트위의로봇 {
    // 각 칸의 위치를 담을 객체
    static class Conveyor {
        boolean isRobot;
        int durability;

        public Conveyor() {
            isRobot = false;
            durability = 0;
        }

        @Override
        public String toString() {
            return "{" +
                    "r=" + (isRobot ? 'T' : 'F') +
                    ", d=" + durability +
                    '}';
        }
    }

    // 처음에는 컨베이어 객체배열을 돌리려고 했지만, 올리고 내리는 위치를 바꾸고자 함
    static int N, K, loadPoint, downPoint, count;
    static int result;
    static Conveyor[] conveyors;
    static Queue<Integer> robots; // 로봇의 위치를 저장할 순서기록 할 큐

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        conveyors = new Conveyor[2 * N];
        loadPoint = 0; // 초기 올라갈 위치
        downPoint = N-1; // 초기 내려갈 위치
        robots = new LinkedList<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 2 * N; i++) {
            conveyors[i] = new Conveyor();
            conveyors[i].durability = Integer.parseInt(st.nextToken());
        }

        // 처리
        while (K > count) {
            result++;
            rotateConveyor(); // 컨베이어 회전
            moveRobot(); // 로봇 이동
            loadRobot(); // 로봇 올리기
        }

        // 출력
        System.out.println(result);
    }

    private static void loadRobot() {
        if(conveyors[loadPoint].isRobot || conveyors[loadPoint].durability <= 0){
            return;
        }

        conveyors[loadPoint].isRobot = true;
        conveyors[loadPoint].durability--;
        robots.offer(loadPoint);

        if(conveyors[loadPoint].durability == 0){
            count++;
        }
    }

    private static void moveRobot() {
        // 현재 컨베이어 벨트에 올라가 있는 로봇들 하나씩 이동 시작
        int size = robots.size();
        for (int i = 0; i < size; i++) {
            int curPosition = robots.poll();
            int nextPosition = curPosition + 1;
            if(nextPosition > 2 * N - 1){
                nextPosition = 0;
            }

            // 다음 컨베이어 벨트에 로봇이 있거나 내구도가 0 이면 현재 로봇 그대로 다시 순서 넣어주기
            if(conveyors[nextPosition].isRobot || conveyors[nextPosition].durability <= 0){
                robots.offer(curPosition);
                continue;
            }

            // 그렇지 않을 경우
            conveyors[nextPosition].durability--;
            if(conveyors[nextPosition].durability == 0){
                count++;
            }
            conveyors[curPosition].isRobot = false;

            // 다음 위치가 내려갈 위치라면 그냥 넘김
            if(nextPosition == downPoint){
                continue;
            }

            // 다음 위치에 로봇 이동
            conveyors[nextPosition].isRobot = true;
            robots.offer(nextPosition);
        }
    }

    // 컨베이어 벨트가 시계방향으로 회전하기 때문에 오르고 내릴 위치가 반대방향으로 1칸씩 이동
    private static void rotateConveyor() {
        // 로봇을 올릴 위치를 한칸 뒤로
        loadPoint = loadPoint - 1;
        if (loadPoint == -1) {
            loadPoint = 2 * N - 1;
        }
        // 로봇을 내릴 위치를 한칸 뒤로
        downPoint = downPoint - 1;
        if (downPoint == -1) {
            downPoint = 2 * N - 1;
        }

        // 컨베이어가 회전 했을 때 내려갈 위치에 로봇이 있다면 내려가야지
        if(conveyors[downPoint].isRobot){
            conveyors[downPoint].isRobot = false;
            robots.poll(); // 내려가는 것은 결국 제일 앞에 있다는 뜻, 내보내기
        }
    }
}
