import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream("Runner/sample.txt"));
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(solution(new int[]{95, 90, 99, 99, 80, 99}, new int[]{1, 1, 1, 1, 1, 1}));
    }

    public static ArrayList<Integer> solution(int[] progresses, int[] speeds) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayDeque<Integer> progress = new ArrayDeque<>();
        ArrayDeque<Integer> speed = new ArrayDeque<>();
        for (int i = 0; i < progresses.length; i++) {
            progress.offer(progresses[i]);
        }
        for (int i = 0; i < speeds.length; i++) {
            speed.offer(speeds[i]);
        }

        while (!progress.isEmpty()) {
            int size = progress.size();
            for (int i = 0; i < size; i++) {
                int prog = progress.poll();
                int spd = speed.poll();
                progress.offer(prog + spd);
                speed.offer(spd);
            }
            int count = 0;
            while (!progress.isEmpty() && progress.peek() >= 100) {
                progress.poll();
                speed.poll();
                count++;
            }

            if (count != 0) {
                result.add(count);
            }
        }

        return result;
    }
}
