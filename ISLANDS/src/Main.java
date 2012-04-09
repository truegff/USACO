import java.io.*;
import java.util.*;

/**
 * Tue Apr 9 2012
 * USACO 2012 US Open, Bronze Division
 * problem ISLANDS
 */
public class Main {

    public static HashMap<Integer, LinkedList<Bottom>> bottoms = new HashMap<Integer, LinkedList<Bottom>>();
    public static HashMap<Integer, LinkedList<Top>> tops = new HashMap<Integer, LinkedList<Top>>();

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("islands.in")));
        PrintWriter printWriter = new PrintWriter(new File("islands.out"));

        int N = Integer.parseInt(bufferedReader.readLine());

        int landscape[] = new int[N];

        for (int i = 0; i < N; i++) {
            landscape[i] = Integer.parseInt(bufferedReader.readLine());
        }


        for (int i = 0; i < N; i++) {
            int prev, next, current = landscape[i];

            if (i == 0) {
                prev = next = landscape[i + 1];
            } else if (i == N - 1) {
                prev = next = landscape[i - 1];
            } else {
                prev = landscape[i - 1];
                next = landscape[i + 1];
            }

            if (prev < current && next < current) {
                if (!tops.containsKey(current)) tops.put(current, new LinkedList<Top>());
                Top newTop = new Top();
                if (Bottom.lastBottom != null) {
                    newTop.left = Bottom.lastBottom;
                    Bottom.lastBottom.right = newTop;
                }
                tops.get(current).add(newTop);
                Top.lastTop = newTop;
            } else if (prev > current && next > current) {
                if (!bottoms.containsKey(current)) bottoms.put(current, new LinkedList<Bottom>());
                Bottom newBottom = new Bottom();
                if (Top.lastTop != null) {
                    newBottom.left = Top.lastTop;
                    Top.lastTop.right = newBottom;
                }
                bottoms.get(current).add(newBottom);
                Bottom.lastBottom = newBottom;
            } else if (prev > current && next == current) {
                while (i < N - 1 && landscape[i + 1] == current) {
                    i++;
                }
                if (i == N - 1 || landscape[i + 1] > current) {


                    if (!bottoms.containsKey(current)) bottoms.put(current, new LinkedList<Bottom>());
                    Bottom newBottom = new Bottom();
                    if (Top.lastTop != null) {
                        newBottom.left = Top.lastTop;
                        Top.lastTop.right = newBottom;
                    }
                    bottoms.get(current).add(newBottom);
                    Bottom.lastBottom = newBottom;

                }
            } else if (prev < current && next == current) {
                while (i < N - 1 && landscape[i + 1] == current) {
                    i++;
                }
                if (i == N - 1 || landscape[i + 1] < current) {


                    if (!tops.containsKey(current)) tops.put(current, new LinkedList<Top>());
                    Top newTop = new Top();
                    if (Bottom.lastBottom != null) {
                        newTop.left = Bottom.lastBottom;
                        Bottom.lastBottom.right = newTop;
                    }
                    tops.get(current).add(newTop);
                    Top.lastTop = newTop;

                }
            }


        }

        Arrays.sort(landscape);

        int maxActive = 0;

        int prev = 0;
        for (int i : landscape) {
            if (i != prev) {
                if (bottoms.containsKey(i))
                    for (Bottom b : bottoms.get(i)) {
                        if (b.right != null) b.right.activate();
                        if (b.left != null) b.left.activate();
                    }
                if (tops.containsKey(i))
                    for (Top t : tops.get(i)) {
                        t.sink();
                    }
                maxActive = Math.max(maxActive, Top.totalActive);
            }
        }

        printWriter.println(maxActive);
        printWriter.flush();
    }
}

class Top {
    static Top lastTop;
    static int totalActive = 0;
    boolean active;
    Bottom left;
    Bottom right;

    public void activate() {
        if (!active) {
            active = true;
            totalActive++;
        }
    }

    public void sink() {
        if (active) {
            active = false;
            totalActive--;
        }
    }
}

class Bottom {
    static Bottom lastBottom;
    Top left;
    Top right;
}