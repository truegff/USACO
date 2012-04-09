import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Tue Apr 9 2012
 * USACO 2012 US Open, Bronze Division
 * problem THREE LINES
 */
public class Main {

    public static HashMap<Integer, Integer> distinctXs = new HashMap<Integer, Integer>();
    public static HashMap<Integer, Integer> distinctYs = new HashMap<Integer, Integer>();
    public static Cow cows[]; // = new Cow[N];

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("3lines.in")));
        PrintWriter printWriter = new PrintWriter(new File("3lines.out"));

        int N = Integer.parseInt(bufferedReader.readLine());

        cows = new Cow[N];

        StringTokenizer stk;
        int x, y;
        for (int i = 0; i < N; i++) {
            stk = new StringTokenizer(bufferedReader.readLine());
            x = Integer.parseInt(stk.nextToken());
            y = Integer.parseInt(stk.nextToken());

            cows[i] = new Cow(x, y);
        }

        for (int q = 0; q < 3; q++) {
            int maxX = 0, maxXcnt = 0;
            for (Integer i : distinctXs.keySet()) {
                int val = distinctXs.get(i);
                if (val > maxXcnt) {
                    maxX = i;
                    maxXcnt = val;
                }
            }
            int maxY = 0, maxYcnt = 0;
            for (Integer i : distinctYs.keySet()) {
                int val = distinctYs.get(i);
                if (val > maxYcnt) {
                    maxY = i;
                    maxYcnt = val;
                }
            }
            if (maxXcnt > maxYcnt) killAllX(maxX);
            else killAllY(maxY);
        }


        if (Cow.alive > 0) printWriter.println("0");
        else printWriter.println("1");
        printWriter.flush();
    }

    private static void killAllX(int maxX) {
        for (Cow cow : cows) {
            if (cow.x == maxX && !cow.dead) cow.playDead();
        }
    }

    private static void killAllY(int maxY) {
        for (Cow cow : cows) {
            if (cow.y == maxY && !cow.dead) cow.playDead();
        }
    }
}

class Cow {
    static int alive = 0;
    int x;
    int y;
    boolean dead;

    public Cow(int x, int y) {
        alive++;
        this.x = x;
        this.y = y;

        if (!Main.distinctXs.containsKey(x)) Main.distinctXs.put(x, 0);
        if (!Main.distinctYs.containsKey(y)) Main.distinctYs.put(y, 0);

        int a = Main.distinctXs.get(x);
        a++;
        Main.distinctXs.put(x, a);

        a = Main.distinctYs.get(y);
        a++;
        Main.distinctYs.put(y, a);
    }

    public void playDead() {
        alive--;
        dead = true;
        int a = Main.distinctXs.get(x);
        a--;
        Main.distinctXs.put(x, a);

        a = Main.distinctYs.get(y);
        a--;
        Main.distinctYs.put(y, a);
    }
}