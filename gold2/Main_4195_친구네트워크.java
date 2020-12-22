import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int T, F, size;
    static HashMap<String, Integer> hashMap;
    static StringBuilder sb;
    static String[][] friendShip;
    static StringTokenizer st;
    static int[] root, rank, count;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        T = Integer.parseInt(br.readLine());
        sb = new StringBuilder();
        for (int t = 1; t <= T; t++) {
            F = Integer.parseInt(br.readLine());
            friendShip = new String[F + 1][2];
            hashMap = new HashMap<>();
            int cnt = 1;
            for (int i = 1; i < F + 1; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 2; j++) {
                    String friend = st.nextToken();
                    if (!hashMap.containsKey(friend)) {
                        hashMap.put(friend, cnt++);
                    }
                    friendShip[i][j] = friend;
                }
            }
            size = hashMap.size();
            root = new int[size + 1];
            rank = new int[size + 1];
            count = new int[size + 1];
            for (int i = 1; i < size + 1; i++) {
                root[i] = i;
                count[i] = 1;
            }
            for (int i = 1; i < F + 1; i++) {
                String friend1 = friendShip[i][0];
                String friend2 = friendShip[i][1];
                union(hashMap.get(friend1), hashMap.get(friend2));

                sb.append(count[find(hashMap.get(friend1))]).append('\n');
            }
        }
        System.out.println(sb.toString());
    }

    private static int getFriendCount(int x) {
        int group = find(root[x]);
        int cnt = 0;
        for (int i = 1; i < size + 1; i++) {
            if (find(root[i]) == group) {
                cnt++;
            }
        }
        return cnt;
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
            count[y] += count[x];
        } else {
            root[y] = x;
            count[x] += count[y];
            if (rank[x] == rank[y]) {
                rank[x]++;
            }
        }
    }
}
