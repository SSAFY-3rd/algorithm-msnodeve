import java.io.IOException;
import java.util.*;

public class Solution {
    static class Edge implements Comparable<Edge> {
        int idx, weight;

        public Edge(int idx, int weight) {
            this.idx = idx;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }

        @Override
        public String toString() {
            return "{" +
                    "i=" + idx +
                    ", w=" + weight +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream("Runner/sample.txt"));
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(solution(6, new int[][]{{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}}));
    }

    private static int solution(int n, int[][] edge) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        ArrayList<Edge>[] list = new ArrayList[n + 1];
        int[] D = new int[n + 1];
        boolean[] visit = new boolean[n + 1];
        int INF = 987_654_321;

        for (int i = 1; i <= n; i++) {
            D[i] = INF;
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < edge.length; i++) {
            int a = edge[i][0];
            int b = edge[i][1];
            list[a].add(new Edge(b, 1));
            list[b].add(new Edge(a, 1));
        }

        D[1] = 0;
        pq.offer(new Edge(1, 0));

        int max = 0;
        while (!pq.isEmpty()) {
            Edge curNode = pq.poll();
            if (visit[curNode.idx]) {
                continue;
            }
            visit[curNode.idx] = true;

            for (Edge nextNode : list[curNode.idx]) {
                if (visit[nextNode.idx]) {
                    continue;
                }
                if(D[nextNode.idx] > D[curNode.idx] + nextNode.weight){
                    D[nextNode.idx] =  D[curNode.idx] + nextNode.weight;
                    max = Math.max(max, D[nextNode.idx]);
                    pq.offer(new Edge(nextNode.idx, D[nextNode.idx]));
                }
            }
        }
        int answer = 0;
        for (int i = 1; i <= n; i++) {
//            System.out.print(D[i] + " ");
            if (max == D[i]) {
                answer++;
            }
        }
//        System.out.println();
        return answer;
    }
}
