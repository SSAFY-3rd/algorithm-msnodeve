import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class Main {
    static class Vertex {
        int id, weight;

        public Vertex(int id, int weight) {
            this.id = id;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "{" +
                    "i=" + id +
                    ", w=" + weight +
                    '}';
        }
    }

    static ArrayList<Vertex>[] vertexes;
    static boolean[] visit;
    static int result;
    static int V;
    static int maxNodeId;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        V = Integer.parseInt(br.readLine());
        vertexes = new ArrayList[V + 1]; // 0은 쓰지 않을 것임

        for (int i = 1; i <= V; i++) {
            vertexes[i] = new ArrayList<Vertex>();
        }

        for (int i = 1; i <= V; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int startV = Integer.parseInt(st.nextToken());
            while (st.hasMoreTokens()) {
                int finishV = Integer.parseInt(st.nextToken());
                if (finishV == -1) {
                    break;
                }
                int value = Integer.parseInt(st.nextToken());

                vertexes[startV].add(new Vertex(finishV, value));
            }
        }

        // 처리
        visit = new boolean[V + 1];
        DFS(new Random().nextInt(V) + 1, 0); // 일단 초기 아무 곳을 잡아도된다.
        result = 0;
        visit = new boolean[V + 1];
        DFS(maxNodeId, 0);

        // 출력
        System.out.println(result);
    }

    private static void DFS(int id, int cost) {
        visit[id] = true;
        ArrayList<Vertex> arrayList = vertexes[id];
        for (int i = 0; i < vertexes[id].size(); i++) {
            if (!visit[arrayList.get(i).id]) { // 아직 방문하지 않았다면
                visit[arrayList.get(i).id] = true;
                DFS(arrayList.get(i).id, cost + arrayList.get(i).weight);
                visit[arrayList.get(i).id] = false;
            }
        }

        if (result < cost) {
            result = cost;
            maxNodeId = id;
        }
    }
}