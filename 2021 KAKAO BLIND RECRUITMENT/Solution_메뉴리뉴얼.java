import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    static class Word implements Comparable<Word> {
        String word;
        int count;

        public Word(String word, int count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public int compareTo(Word o) {
            return Integer.compare(o.count, this.count);
        }
    }

    static TreeSet<String> result = new TreeSet<>();
    static PriorityQueue<Word>[] pqs;
    static char[] chars;
    static int idx;

    public static void main(String[] args) throws Exception {
        System.out.println(Arrays.toString(solution(new String[]{"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"}, new int[]{2, 3, 4})));
    }

    public static String[] solution(String[] orders, int[] course) {
        // 길이 및 오름차순 정렬
        for (int i = 0; i < orders.length; i++) {
            char[] charArr = orders[i].toCharArray();
            Arrays.sort(charArr);
            orders[i] = new String(charArr);
        }
        Arrays.sort(orders, Comparator.comparingInt(String::length));

        pqs = new PriorityQueue[course.length];
        for (int i = 0; i < pqs.length; i++) {
            pqs[i] = new PriorityQueue<>();
        }
        // course 값의 개수 만큼 조합 시작
        for (int i = 0; i < course.length; i++) {
            for (int j = 0; j < orders.length; j++) {
                chars = new char[course[i]];
                combination(0, 0, course[i], orders[j], orders);
            }
            idx++;
        }

        for (int i = 0; i < idx; i++) {
            int max = 0;
            String str = "";
            while (!pqs[i].isEmpty()) {
                Word word = pqs[i].poll();
                if (word.count == 1 || max > word.count) {
                    break;
                }

                if (max < word.count) { // 처음
                    str = word.word;
                    max = word.count;
                    result.add(word.word);
                } else if (max == word.count && !str.equals(word.word)) { // 개수는 같으나 단어가 다르면
                    str = word.word;
                    result.add(word.word);
                }
            }
        }

        String[] answer = new String[result.size()];
        int i = 0;
        for (String s : result) {
            answer[i++] = s;
        }

        return answer;
    }

    private static void combination(int cnt, int start, int r, String order, String[] orders) {
        if (cnt == r) {
            // 검색 시작
            int count = 0;
            for (int i = 0; i < orders.length; i++) {
                boolean flag = true;
                for (int j = 0; j < chars.length; j++) {
                    if (!orders[i].contains(chars[j] + "")) {
                        flag = false;
                    }
                }
                if (flag) {
                    count++;
                }
            }
            pqs[idx].offer(new Word(new String(chars), count));
            return;
        }
        for (int i = start; i < order.length(); i++) {
            chars[cnt] = order.charAt(i);
            combination(cnt + 1, i + 1, r, order, orders);
        }
    }
}