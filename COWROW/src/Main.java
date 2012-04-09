import java.io.*;
import java.util.HashSet;

/**
 * Tue Apr 9 2012
 * USACO 2012 US Open, Bronze Division
 * problem COWS IN A ROW
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("cowrow.in")));
        PrintWriter printWriter = new PrintWriter(new File("cowrow.out"));
        HashSet<Integer> distinctBreeds = new HashSet<Integer>();
        
        int N = Integer.parseInt(bufferedReader.readLine());
        
        int cows[] = new int[N];
        
        for (int i=0; i<N; i++) {
            cows[i]=Integer.parseInt(bufferedReader.readLine());
            distinctBreeds.add(cows[i]);
        }

        int maxSequence = 0;
        
        for (Integer excluded : distinctBreeds) {
            Sequence current = new Sequence();
            for (int i=0; i<N; i++) {
                if (cows[i]==excluded) continue;
                int currentBreedId = cows[i];
                if (current.breed != currentBreedId) {
                    maxSequence=Math.max(maxSequence, current.amount);
                    current=new Sequence();
                    current.breed = currentBreedId;
                }
                current.amount++;
            }
        }

        printWriter.println(maxSequence);
        printWriter.flush();
    }
            
}

class Sequence {
    int breed;
    int amount;
}