import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    static class TrieNode {
        public TreeMap<String, TrieNode> childNodes;

        public TrieNode() {
            this.childNodes = new TreeMap<>();
        }

        @Override
        public String toString() {
            return "T{" +
                    "c=" + childNodes +
                    '}';
        }
    }

    static TrieNode trieNode;
    static int N;
    static StringBuilder sb;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        sb = new StringBuilder();
        trieNode = new TrieNode();
        N = Integer.parseInt(br.readLine());
        for (int n = 0; n < N; n++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());

            TrieNode tempNode = trieNode;
            for (int depth = 0; depth < K; depth++) {
                String str = st.nextToken();
                if (!tempNode.childNodes.containsKey(str)) {
                    tempNode.childNodes.put(str, new TrieNode());
                }
                tempNode = tempNode.childNodes.get(str);
            }
        }

        DFS(trieNode, 0);
        System.out.println(sb.toString());
    }

    private static void DFS(TrieNode trieNode, int depth) {
        Iterator<String> keys = trieNode.childNodes.keySet().iterator();
        while (keys.hasNext()) {
            for (int i = 0; i < depth; i++) {
                sb.append("--");
            }
            String key = keys.next();
            sb.append(key);
            sb.append('\n');
            DFS(trieNode.childNodes.get(key), depth + 1);
        }
    }
}
