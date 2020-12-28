import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N, M, knownAlphabet;
    static int[] words;
    static StringBuilder sb;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        knownAlphabet = 0b111111111111111111111111111;
        words = new int[N];
        for (int i = 0; i < N; i++) {
            int value = 0;
            char[] chars = br.readLine().toCharArray();
            for (int j = 0; j < chars.length; j++) {
                value = value | (1 << (chars[j] - 'a'));
            }
            words[i] = value;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            String command1 = st.nextToken();
            String command2 = st.nextToken();
            switch (command1){
                case "1": // 잊기
                    knownAlphabet = knownAlphabet & ~(1 << (command2.charAt(0) - 'a'));
                    break;
                case "2": // 기억해내기
                    knownAlphabet = knownAlphabet | (1 << (command2.charAt(0) - 'a'));
                    break;
            }

            int cnt = 0;
            for (int n = 0; n < N; n++) {
                if((words[n] & knownAlphabet) == words[n]){
                    cnt++;
                }
            }
            sb.append(cnt).append('\n');
        }

        System.out.println(sb.toString());
    }
}
