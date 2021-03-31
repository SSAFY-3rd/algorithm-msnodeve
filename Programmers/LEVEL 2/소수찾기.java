import java.util.TreeSet;

class Solution {
    boolean[] isNotPrime;
    TreeSet<Integer> answer;
    char[] number;
    boolean[] visit;
    int[] list;

    public int solution(String numbers) {
        isNotPrime = new boolean[100000001];
        answer = new TreeSet<>();
        isNotPrime[0] = isNotPrime[1] = true;
        for (int i = 2; i < 100000001; i++) {
            if (!isNotPrime[i]) {
                for (int j = i + i; j < 100000001; j += i) {
                    isNotPrime[j] = true;
                }
            }
        }
        number = numbers.toCharArray();
        for (int i = 1; i <= numbers.length(); i++) {
            list = new int[i];
            visit = new boolean[numbers.length()];
            combination(i, 0);
        }

        return answer.size();
    }

    private void combination(int goal, int cnt) {
        if (cnt == goal) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.length; i++) {
                sb.append(number[list[i]]);
            }
            if(!isNotPrime[Integer.parseInt(sb.toString())]){
                answer.add(Integer.parseInt(sb.toString()));
            }
            return;
        }
        for (int i = 0; i < number.length; i++) {
            if (!visit[i]) {
                list[cnt] = i;
                visit[i] = true;
                combination(goal, cnt + 1);
                visit[i] = false;
            }
        }
    }
}