import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = StdIn.readString();
        int i = 1;
        while(! StdIn.isEmpty()) {
            i++;
            String s = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)) {
                champion = s;
            }
        }
        System.out.println(champion);
    }
}
