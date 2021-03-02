import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Solution {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력 출력
        System.out.println(solution(br.readLine()));
    }

    public static String solution(String new_id) {
        // 1단계
        new_id = new_id.toLowerCase();

        // 2단계
        new_id = new_id.replaceAll("[^\\w.\\-_]", "");

        // 3단계
        new_id = new_id.replaceAll("\\.+", ".");

        // 4단계
        if (new_id.length() > 0 && new_id.charAt(0) == '.') {
            StringBuilder sb = new StringBuilder(new_id);
            sb.deleteCharAt(0);
            new_id = sb.toString();
        }
        if (new_id.length() > 0 && new_id.charAt(new_id.length() - 1) == '.') {
            StringBuilder sb = new StringBuilder(new_id);
            sb.deleteCharAt(sb.length() - 1);
            new_id = sb.toString();
        }

        // 5단계
        if (new_id.length() == 0) {
            new_id = "a";
        }

        // 6단계
        if (new_id.length() >= 16) {
            new_id = new_id.substring(0, 15);
        }
        if (new_id.charAt(new_id.length() - 1) == '.') {
            StringBuilder sb = new StringBuilder(new_id);
            sb.deleteCharAt(sb.length() - 1);
            new_id = sb.toString();
        }

        // 7단계
        while (new_id.length() <= 2) {
            new_id += new_id.charAt(new_id.length() - 1);
        }
        return new_id;
    }
}
