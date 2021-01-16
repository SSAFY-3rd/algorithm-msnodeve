import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static class Node implements Comparable<Node> {
        int startVertex, endVertex, value;

        public Node(int startVertex, int endVertex, int value) {
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.value = value;
        }

        @Override
        public int compareTo(Node node) {
            return Integer.compare(this.value, node.value);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "startVertex=" + startVertex +
                    ", endVertex=" + endVertex +
                    ", value=" + value +
                    '}';
        }
    }

    static class Planet {
        int idx, x, y, z;

        public Planet(int idx, int x, int y, int z) {
            this.idx = idx;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return "Planet{" +
                    "idx=" + idx +
                    ", x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }

    static int[] root, rank;
    static Planet[] planets;
    static int N;
    static PriorityQueue<Node> pq;

    // 38,146MB 이상 먹음

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        N = Integer.parseInt(br.readLine());
        planets = new Planet[N];
        root = new int[N];
        rank = new int[N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            planets[i] = new Planet(i, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            root[i] = i;
        }

        // 처리
        pq = new PriorityQueue<>();
        Comparator<Planet> cp = Comparator.comparingInt(value -> value.x);
        Arrays.sort(planets, cp);
        for (int i = 1; i < N; i++) {
            pq.offer(new Node(planets[i - 1].idx, planets[i].idx, Math.abs(planets[i].x - planets[i - 1].x)));
        }
        cp = Comparator.comparingInt(value -> value.y);
        Arrays.sort(planets, cp);
        for (int i = 1; i < N; i++) {
            pq.offer(new Node(planets[i - 1].idx, planets[i].idx, Math.abs(planets[i].y - planets[i - 1].y)));
        }
        cp = Comparator.comparingInt(value -> value.z);
        Arrays.sort(planets, cp);
        for (int i = 1; i < N; i++) {
            pq.offer(new Node(planets[i - 1].idx, planets[i].idx, Math.abs(planets[i].z - planets[i - 1].z)));
        }

        long result = 0;
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int x = find(node.startVertex);
            int y = find(node.endVertex);

            if (x == y) {
                continue;
            }

            result += node.value;
            union(x, y);
        }

        // 출력
        System.out.println(result);
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
