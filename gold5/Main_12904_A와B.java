import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {
    static String S, T;
    static int result;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        S = br.readLine();
        T = br.readLine();

        DFS(S);

        System.out.println(result);

    }

    private static void DFS(String str) {
        if (T.equals(str)) {
            result = 1;
        }

        String nextStr = new StringBuilder(str).reverse().toString();
        if (T.contains(str) || T.contains(nextStr)) {
            DFS(str + "A");
            DFS(nextStr + "B");
        }

    }
}