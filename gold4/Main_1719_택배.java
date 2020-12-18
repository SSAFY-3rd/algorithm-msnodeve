import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static class Node implements Comparable<Node> {
        int nodeIdx, weight;

        public Node(int nodeIdx, int weight) {
            this.nodeIdx = nodeIdx;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "{" +
                    "n=" + nodeIdx +
                    ", w=" + weight +
                    '}';
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight; // weight 기준으로 오름차순 정렬
        }
    }

    static ArrayList<Node>[] list;
    static int n, m;
    static int[] trace;
    static StringBuilder sb;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        list = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            list[a].add(new Node(b, w));
            list[b].add(new Node(a, w));
        }
        sb = new StringBuilder();
        // 경로 생성
        for (int i = 1; i <= n; i++) {
            dijkstra(i);
        }

        System.out.println(sb.toString());
    }

    private static void dijkstra(int startIdx) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        boolean[] visit = new boolean[n + 1];
        Node[] D = new Node[n + 1];
        trace = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            if (i == startIdx) {
                D[i] = new Node(i, 0);
                pq.offer(D[i]);
            } else {
                D[i] = new Node(i, Integer.MAX_VALUE);
            }
        }

        while (!pq.isEmpty()) {
            Node curNode = pq.poll();
            // 이미 방문한 곳이라면
            if (visit[curNode.nodeIdx]) {
                continue;
            }
            visit[curNode.nodeIdx] = true;

            // 연결되어 있는 곳 모두 보기
            for (Node nextNode : list[curNode.nodeIdx]) {
                // 다음 위치가 기록되어있는 것 보다 지금까지 온 거리와 다음 거리를 합했을때가 더 크면 이건 최단경로가 아님
                if (D[nextNode.nodeIdx].weight <= D[curNode.nodeIdx].weight + nextNode.weight) {
                    continue;
                }
                trace[nextNode.nodeIdx] = curNode.nodeIdx; // 이부분이 제일 중요한 부분 기록되었다면 현재위치를 기록
                D[nextNode.nodeIdx].weight = D[curNode.nodeIdx].weight + nextNode.weight;
                pq.offer(new Node(nextNode.nodeIdx, D[nextNode.nodeIdx].weight));
            }

        }

        for (int i = 1; i <= n; i++) {
            if (i == startIdx) {
                sb.append('-');
            } else {
                int next = find(i, startIdx);
                sb.append(next);
            }
            sb.append(' ');
        }
        sb.append('\n');
    }

    private static int find(int i, int startIdx) {
        if (trace[i] == startIdx) { // 다음을 바라봤는데 시작 위치라면
            return i;
        }
        return find(trace[i], startIdx); // 타고 들어가기
    }
}
