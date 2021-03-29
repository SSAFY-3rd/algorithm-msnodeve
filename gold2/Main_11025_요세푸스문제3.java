import java.io.*;
import java.util.*;

public class Main {
    static int N, K, result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        result = 0;
        for (int i = 1; i <= N; i++) {
            result = (result + K) % i;
        }

        System.out.println((result + 1));
    }
}
 