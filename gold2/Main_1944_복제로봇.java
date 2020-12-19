import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Node implements Comparable<Node> {
        int startVertex, endVertex, dist;

        public Node(int startVertex, int endVertex, int dist) {
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            return this.dist - o.dist;
        }

        @Override
        public String toString() {
            return "{" +
                    "sv=" + startVertex +
                    ", ev=" + endVertex +
                    ", d=" + dist +
                    '}';
        }
    }

    static char[][] map;
    static int N, M;
    static boolean[][] visit;
    static ArrayList<Node>[] list;
    static int[][] inputs;
    static int[] robotLocation;
    static int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static int result;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][N];
        inputs = new int[M + 2][2];
        robotLocation = new int[2];
        list = new ArrayList[M + 2];
        for (int i = 1; i < M + 2; i++) {
            list[i] = new ArrayList<>();
        }
        int cnt = 1;
        for (int i = 0; i < N; i++) {
            char[] chars = br.readLine().toCharArray();
            for (int j = 0; j < N; j++) {
                switch (chars[j]) {
                    case 'S':
                        robotLocation[0] = i;
                        robotLocation[1] = j;
                    case 'K':
                        inputs[cnt][0] = i;
                        inputs[cnt][1] = j;
                        chars[j] = (char) (cnt++ + '0');
                        break;
                    case '1':
                        chars[j] = '*';
                        break;
                }
                map[i][j] = chars[j];
            }
        }

        // 처리
        // inputs의 0번째부터 하나하나씩 거리 계산해두기
        for (int i = 1; i < M + 2; i++) {
            visit = new boolean[N][N];
            bfs(i);
        }

        // 각 노드에서 각 정점들이 이동이 가능한지 체크
        if (check()) {
            mst();
        } else {
            result = -1;
        }

        // 출력
        System.out.println(result);
    }

    private static boolean check() {
        for (int i = 1; i < M + 2; i++) {
            if (list[i].size() != M) { // 자기자신을 제외한 나머지가 M 개가 아니라면 갈수없는 곳이 존재한다는 뜻
                return false;
            }
        }
        return true;
    }

    private static void mst() {
        // 프림 알고리즘 적용
        boolean[] nodeVisit = new boolean[M + 2];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        // 시작 노드 설정
        pq.offer(new Node(map[robotLocation[0]][robotLocation[1]] - '0', map[robotLocation[0]][robotLocation[1]] - '0', 0));
        while (!pq.isEmpty()) {
            Node curNode = pq.poll();
            // 아직 방문하지 않았다면
            if (!nodeVisit[curNode.startVertex]) {
                nodeVisit[curNode.startVertex] = true;
                result += curNode.dist;

                // 지금 노드에서 연결된 모든 간선들 확인해서 방문하지 않은 곳이라면 pq에 넣기
                for (int i = 0; i < list[curNode.startVertex].size(); i++) {
                    Node nextNode = list[curNode.startVertex].get(i);
                    if (!nodeVisit[nextNode.endVertex]) {
                        pq.offer(new Node(nextNode.endVertex, nextNode.endVertex, nextNode.dist));
                    }
                }
            }
        }
    }

    private static void bfs(int i) {
        Queue<int[]> queue = new LinkedList<>();
        int[] curNode = new int[]{inputs[i][0], inputs[i][1], 0};
        queue.offer(curNode);
        visit[curNode[0]][curNode[1]] = true;

        while (!queue.isEmpty()) {
            curNode = queue.poll();
            // 자기자신이 아니며, 다른 번호를 발견했다면
            if (i + '0' != map[curNode[0]][curNode[1]] && '1' <= map[curNode[0]][curNode[1]] && map[curNode[0]][curNode[1]] <= M + '1') {
                list[i].add(new Node(i, map[curNode[0]][curNode[1]] - '0', curNode[2]));
            }

            for (int d = 0; d < 4; d++) {
                int nr = curNode[0] + dir[d][0];
                int nc = curNode[1] + dir[d][1];

                // 밖으로 나갔거나 이미 방문했거나 벽이라면
                if (isOut(nr, nc) || visit[nr][nc] || map[nr][nc] == '*') {
                    continue;
                }

                visit[nr][nc] = true;
                queue.offer(new int[]{nr, nc, curNode[2] + 1});
            }
        }
    }

    private static boolean isOut(int nr, int nc) {
        return nr < 0 || nc < 0 || nr >= N || nc >= N;
    }
}
