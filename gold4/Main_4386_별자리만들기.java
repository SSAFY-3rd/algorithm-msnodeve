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
        double dist;

        public Node(int startVertex, int endVertex, double dist) {
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.dist = dist;
        }

        @Override
        public String toString() {
            return "{" +
                    "i=" + startVertex +
                    ", e=" + endVertex +
                    ", v=" + dist +
                    '}';
        }

        @Override
        public int compareTo(Node node) {
            return Double.compare(this.dist, node.dist);
        }
    }

    static ArrayList<Node>[] list;
    static int n;
    static double[] x, y;
    static double result;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        n = Integer.parseInt(br.readLine());
        list = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
        }
        x = new double[n];
        y = new double[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            x[i] = Double.parseDouble(st.nextToken());
            y[i] = Double.parseDouble(st.nextToken());
        }

        // 처리
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dist = Math.sqrt(Math.pow(x[j] - x[i], 2) + Math.pow(y[j] - y[i], 2));
                list[i].add(new Node(i, j, dist));
                list[j].add(new Node(j, i, dist));
            }
        }
        mst();

        // 출력
        System.out.printf("%.2f", result);
    }

    private static void mst() {
        boolean[] visit = new boolean[n];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Node node = list[0].get(0);
        pq.offer(new Node(node.startVertex, node.startVertex, 0));
        while (!pq.isEmpty()) {
            Node curNode = pq.poll();
            if (!visit[curNode.startVertex]) {
                visit[curNode.startVertex] = true;
                result += curNode.dist;

                for (int i = 0; i < list[curNode.startVertex].size(); i++) {
                    Node nextNode = list[curNode.startVertex].get(i);
                    if (!visit[nextNode.endVertex]) {
                        pq.offer(new Node(nextNode.endVertex, nextNode.endVertex, nextNode.dist));
                    }
                }
            }
        }
    }
}
