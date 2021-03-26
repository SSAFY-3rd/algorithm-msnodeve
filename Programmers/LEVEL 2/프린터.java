import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream("Runner/sample.txt"));
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(solution(new int[]{1, 1, 9, 1, 1, 1}, 0));
    }

    public static int solution(int[] priorities, int location) {
        int answer = 0;
        HashMap<Integer, Integer> rank = new HashMap<>();
        Queue<Integer> output = new LinkedList<>();
        for (int i = 0; i < priorities.length; i++) {
            rank.put(i, priorities[i]);
            output.offer(i);
        }

        while (true) {
            int paper = output.poll();
            int curPaperRank = rank.get(paper);
            int count = 0;
            for (Integer nextPaper : output) {
                if (rank.get(nextPaper) > curPaperRank) {
                    output.offer(paper);
                    break;
                }
                count++;
            }
            if (count == output.size()) { // 뒤에 아무것도 없다면
                answer++;
                if(location == paper){
                    return answer;
                }
            }
        }
    }
}
