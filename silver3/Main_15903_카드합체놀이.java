import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.LongStream;

public class Main {
    static int n, m;
    static long[] nums;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        nums = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        // 처리
        for (int i = 0; i < m; i++) {
            // 소팅
            Arrays.sort(nums);
            // 작은 두 수
            long num1 = nums[0];
            long num2 = nums[1];
            // 숫자 놀이
            nums[0] = nums[1] = num1 + num2;
        }

        // stream으로 합 구하기
        System.out.println(LongStream.of(nums).sum());
    }
}
