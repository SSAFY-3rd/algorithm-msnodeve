import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {
    static String submarine;
    static String pattern;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        submarine = br.readLine();
        pattern="(100+1+|01)+";
        System.out.println(submarine.matches(pattern) ? "SUBMARINE" : "NOISE");
    }
}
