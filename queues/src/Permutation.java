import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        if (k == 0) {
            return;
        }
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        for (int i = 1; !StdIn.isEmpty(); i++) {
            String s = StdIn.readString();
            if (i <= k) {
                queue.enqueue(s);
            } else if (StdRandom.uniform() < (double) k / i) {
                queue.dequeue();
                queue.enqueue(s);
            }
        }
        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}
