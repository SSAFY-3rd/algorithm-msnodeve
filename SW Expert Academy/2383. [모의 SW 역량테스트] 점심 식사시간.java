import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

class Solution {
    static class Person implements Comparable<Person> {
        int row, col, dist, time;

        public Person(int row, int col, int dist, int time) {
            this.row = row;
            this.col = col;
            this.dist = dist;
            this.time = time;
        }

        @Override
        public int compareTo(Person o) {
            return Integer.compare(this.dist, o.dist);
        }
    }

    static int N, result;
    static int[][] map;
    static ArrayList<Person> persons;
    static int[][] exits;
    static int[] number;
    static boolean[] selected;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int T = Integer.parseInt(br.readLine());
        for (int testcase = 1; testcase <= T; testcase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            result = Integer.MAX_VALUE;
            map = new int[N][N];
            exits = new int[2][];
            persons = new ArrayList<>();
            boolean iFlag = true;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if (map[i][j] == 1) {
                        persons.add(new Person(i, j, 0, 0));
                    } else if (map[i][j] != 0) {
                        if (iFlag) {
                            exits[0] = new int[]{i, j, map[i][j]};
                            iFlag = false;
                        } else {
                            exits[1] = new int[]{i, j, map[i][j]};
                        }
                    }
                }
            }

            // 처리
            for (int i = 0; i <= persons.size(); i++) {
                number = new int[i];
                combination(0, 0, i);
            }

            // 출력
            System.out.println("#" + testcase + " " + result);
        }
    }

    private static void combination(int start, int cnt, int n) {
        if (cnt == n) {
            selected = new boolean[persons.size()];
            for (int i = 0; i < number.length; i++) {
                selected[number[i]] = true;
            }
            solution();
            return;
        }

        for (int i = start; i < persons.size(); i++) {
            number[cnt] = i;
            combination(i + 1, cnt + 1, n);
        }
    }

    private static void solution() {
        PriorityQueue<Person> pq0 = new PriorityQueue<>();
        PriorityQueue<Person> pq1 = new PriorityQueue<>();

        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) { // 0번
                Person person = new Person(persons.get(i).row, persons.get(i).col, persons.get(i).dist, persons.get(i).time);
                int dist = Math.abs(exits[0][0] - person.row) + Math.abs(exits[0][1] - person.col);
                person.dist = dist;
                pq0.offer(person);
            } else { // 1번
                Person person = new Person(persons.get(i).row, persons.get(i).col, persons.get(i).dist, persons.get(i).time);
                int dist = Math.abs(exits[1][0] - person.row) + Math.abs(exits[1][1] - person.col);
                person.dist = dist;
                pq1.offer(person);
            }
        }

        Queue<Person> q0 = new LinkedList<>();
        Queue<Person> q1 = new LinkedList<>();

        int time;
        for (time = 1; ; time++) { // 1초 부터 시작

            if(pq0.isEmpty() && pq1.isEmpty() && q0.isEmpty() && q1.isEmpty()){
                break;
            }

            // 계단에서 내려버리기
            int size0 = q0.size();
            int size1 = q1.size();
            for (int i = 0; i < size0; i++) {
                Person person = q0.poll();
                if(++person.time != exits[0][2]) { // 계단 시간이랑 맞으면
                    q0.offer(person);
                }
            }
            for (int i = 0; i < size1; i++) {
                Person person = q1.poll();
                if(++person.time != exits[1][2]){ // 계단 시간이랑 맞으면
                    q1.offer(person);
                }
            }

            // 대기자 뽑아내기
            while(q0.size() < 3 && !pq0.isEmpty() && pq0.peek().dist <= time){
                q0.offer(pq0.poll());
            }
            while(q1.size() < 3 && !pq1.isEmpty() && pq1.peek().dist <= time){
                q1.offer(pq1.poll());
            }
        }

        result = Math.min(result, time);
    }
}