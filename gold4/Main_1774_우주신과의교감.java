import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static class Node implements Comparable<Node> {

        int startVertex, endVertex;
        double value;
        public Node(int startVertex, int endVertex, double value) {
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.value = value;
        }

        @Override
        public String toString() {
            return "N{" +
                    "s=" + startVertex +
                    ", e=" + endVertex +
                    ", v=" + value +
                    '}';
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(this.value, o.value);
        }

    }

    static int N, M;
    static PriorityQueue<Node> pq;
    static ArrayList<Node>[] list;
    static int[] root, rank, x, y;
    static double result;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        pq = new PriorityQueue<>();
        list = new ArrayList[N + 1];
        root = new int[N + 1];
        rank = new int[N + 1];
        x = new int[N + 1];
        y = new int[N + 1];

        for (int i = 1; i < N + 1; i++) {
            root[i] = i;
            list[i] = new ArrayList<>();
        }

        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
        }

        // 처리
        for (int i = 1; i < M + 1; i++) {
            st = new StringTokenizer(br.readLine());
            union(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        for (int i = 1; i < N + 1; i++) {
            for (int j = i + 1; j < N + 1; j++) {
                double value = Math.sqrt(Math.pow(Math.abs(x[i] - x[j]), 2) + Math.pow(Math.abs(y[i] - y[j]), 2));
                pq.offer(new Node(i, j, value));
            }
        }

        while(!pq.isEmpty()){
            Node node = pq.poll();
            int start = node.startVertex;
            int end = node.endVertex;

            int x = find(start);
            int y = find(end);
            if(x == y){
                continue;
            }

            union(x, y);
            result += node.value;
        }

        // 출력
        System.out.printf("%.2f", result);
    }

    private static int find(int x) {
        if (root[x] == x) {
            return x;
        }
        return root[x] = find(root[x]);
    }

    private static void union(int x, int y) {
        x = find(x);
        y = find(y);

        if (x == y) {
            return;
        }

        if (rank[x] < rank[y]) {
            root[x] = y;
        } else {
            root[y] = x;
            if (rank[x] == rank[y]) {
                rank[x]++;
            }
        }
    }
}
