package net.nuttle.algorithms;

import edu.princeton.cs.algs4.QuickFindUF;

public class Sandbox {

  public static void main(String[] args) {
    //StdOut.printf("String is %09.4fabc\n", 16.1);
    /*
    while (!StdIn.isEmpty()) {
      StdOut.println(StdIn.readLine());
    }
    */
    //StdOut.println(StdIn.readInt());
    
    /*
    List<Integer> vals = new ArrayList<Integer>();
    while (!StdIn.isEmpty()) {
      vals.add(Integer.parseInt(StdIn.readLine()));
    }
    int[] a = ArrayUtils.toPrimitive(vals.toArray(new Integer[0]));
    int i = BinarySearch.indexOf(a, 100);
    StdOut.printf("Index is %d", i);
    */
    /*
    In in = new In("C:/Users/Dan/javalib/algs4-data/tinyT.txt");
    String s = in.readAll();
    StdOut.println(s);
    in.close();
    */
    testQUF();
  }
  
  public static void testQUF() {
    QuickFindUF uf = new QuickFindUF(100);
    uf.union(3, 12);
    uf.union(3,  19);
    uf.union(19,  21);
    System.out.println(uf.toString());
  }
  
  public static double sqrt(double c) {
    if (c < 0) {
      return Double.NaN;
    }
    double t = c;
    double err = 1e-15;
    while (Math.abs(t - c/t) > err * t) {
      t = (c/t + t) / 2.0;
      System.out.println(t);
    }
    return t;
  }
}