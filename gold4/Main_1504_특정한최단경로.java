import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
            return "Node{" +
                    "nodeIdx=" + nodeIdx +
                    ", weight=" + weight +
                    '}';
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight);
        }
    }
    static final int INF = 200000000;
    static ArrayList<Node>[] list;
    static int N, E;
    static int v1, v2;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        list = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            list[a].add(new Node(b, c));
            list[b].add(new Node(a, c));
        }
        st = new StringTokenizer(br.readLine());
        v1 = Integer.parseInt(st.nextToken());
        v2 = Integer.parseInt(st.nextToken());

        // 처리 및 출력
        System.out.println(solve());
    }

    private static int solve() {
        int result1 = 0, result2 = 0;
        result1 += dijkstra(1, v1);
        result1 += dijkstra(v1, v2);
        result1 += dijkstra(v2, N);

        result2 += dijkstra(1, v2);
        result2 += dijkstra(v2, v1);
        result2 += dijkstra(v1, N);

        if(result1 >= INF){
            result1 = -1;
        }
        if(result2 >= INF){
            result2 = -1;
        }

        return result1 == -1 && result2 == -1 ? -1 : Math.min(result1, result2);
    }

    private static int dijkstra(int startIdx, int endIdx) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        boolean[] visit = new boolean[N + 1];
        Node[] D = new Node[N + 1];
        for (int i = 1; i <= N; i++) {
            if(i == startIdx){
                D[i] = new Node(i, 0);
                pq.offer(D[i]);
            }else{
                D[i] = new Node(i, INF);
            }
        }

        while(!pq.isEmpty()){
            Node curNode = pq.poll();
            if(visit[curNode.nodeIdx]){
                continue;
            }
            visit[curNode.nodeIdx] = true;

            for(Node nextNode : list[curNode.nodeIdx]){
                if(D[nextNode.nodeIdx].weight <= D[curNode.nodeIdx].weight + nextNode.weight){
                    continue;
                }
                D[nextNode.nodeIdx].weight = D[curNode.nodeIdx].weight + nextNode.weight;
                pq.offer(new Node(nextNode.nodeIdx, D[nextNode.nodeIdx].weight));
            }
        }
        return D[endIdx].weight;
    }
}
