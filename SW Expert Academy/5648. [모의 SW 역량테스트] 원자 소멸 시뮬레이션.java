import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
    static class Atom {
        int x, y, d, e;

        public Atom(int x, int y, int d, int e) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.e = e;
        }

        @Override
        public String toString() {
            return "A{" +
                    "x=" + x +
                    ", y=" + y +
                    ", d=" + d +
                    ", e=" + e +
                    '}';
        }
    }

    static Iterator<Atom> iterator;
    static Atom atom;
    static int result;
    static int[][] map = new int[4001][4001];
    static int[][] dir = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}}; // 상(아래), 하(위), 좌, 우

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("Runner/sample.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int T = Integer.parseInt(br.readLine());
        for (int testcase = 1; testcase <= T; testcase++) {
            result = 0;
            int n = Integer.parseInt(br.readLine());
            Queue<Atom> atoms = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                atoms.offer(new Atom(2 * (x + 1000), 2 * (y + 1000), d, e));
            }

            // 처리
            while (atoms.size() != 0) {
                iterator = atoms.iterator();
                // 원자 이동
                while (iterator.hasNext()) {
                    atom = iterator.next();
                    atom.x += dir[atom.d][0];
                    atom.y += dir[atom.d][1];
                    // 밖으로 나갔다면
                    if (isOut(atom)) {
                        iterator.remove(); // 제거
                    }
                }
                // 충돌 처리
                for (Atom atom : atoms) {
                    map[atom.x][atom.y] = 0;
                }
                for (Atom atom : atoms) {
                    map[atom.x][atom.y] += atom.e;
                }
                iterator = atoms.iterator();
                while (iterator.hasNext()) {
                    atom = iterator.next();
                    if (map[atom.x][atom.y] != atom.e) {
                        result += atom.e;
                        iterator.remove();
                    }
                }
            }

            // 출력
            System.out.println("#" + testcase + " " + result);
        }
    }

    private static boolean isOut(Atom atom) {
        return atom.x < 0 || atom.x > 4000 || atom.y < 0 || atom.y > 4000;
    }
}