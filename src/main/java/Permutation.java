
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

  public static void main (String[] args) {
    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> q = new RandomizedQueue<String>();

    while (!StdIn.isEmpty()) {
      String s = StdIn.readString();
      q.enqueue(s);
    }
    
    int curr = 0;
    for (String s : q) {
      if (curr < k) {
        StdOut.println(s);
      } else {
        break;
      }
      curr++;
    }
  }
}
