import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Pos {
        long[] values;

        public Pos(long[] values) {
            this.values = values;
        }
    }

    static int N;
    static long[] A;
    static Pos pos;
    static long max = Long.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        N = Integer.parseInt(br.readLine());
        A = new long[N];
        pos = new Pos(new long[]{0, 0, 0});
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Long.parseLong(st.nextToken());
        }

        // 처리
        Arrays.sort(A); // 우선 정렬을 해야함

        // 기준이 되는 포인터
        for (int idx = 0; idx < N; idx++) {
            // 기준을 0으로 시작해서 left, right 를 투 포인터로 잡고 시작
            int left = idx + 1, right = N - 1;
            // 값을 비교해 나가본다
            while (left < right) {
                long sum = A[idx] + A[left] + A[right];
                long value = Math.abs(sum);

                if(value < max){
                    pos.values[0] = A[left];
                    pos.values[1] = A[idx];
                    pos.values[2] = A[right];
                    max = value;
                }

                // left, right 포인터 이동
                if(sum < 0){    // 합이 - => 큰쪽으로 이동시켜서 sum이 0에 가깝게
                    left++;
                }else{          // 합이 + => 작은쪽으로 이동시켜서 sum이 0에 가깝게
                    right--;
                }
            }
        }

        // 출력
        Arrays.sort(pos.values);
        System.out.println(pos.values[0] + " " + pos.values[1] + " " + pos.values[2]);
    }
}
