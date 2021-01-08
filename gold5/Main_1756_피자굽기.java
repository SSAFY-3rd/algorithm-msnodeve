import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N, D;
    static int[] list, pizza;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("Runner/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        D = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        list = new int[D];
        pizza = new int[N];
        st = new StringTokenizer(br.readLine());
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < D; i++) {
            min = Math.min(Integer.parseInt(st.nextToken()), min);
            list[i] = min;
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            pizza[i] = Integer.parseInt(st.nextToken());
        }

        int start = 0, end = D;
        for (int i = 0; i < N; i++) {
            while(start < end){
                int mid = (start + end) / 2;
                if(list[mid] < pizza[i]){
                    end = mid;
                }else{
                    start = mid + 1;
                }
            }
            end--;
            if(end < 0){
                break;
            }
            start = 0;
        }
        System.out.println(end+1);
    }
}
