package net.nuttle.algorithms.search;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import net.nuttle.algorithms.util.Stopwatch;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class ThreeSum {

  public static int bruteForce(int[] values) {
    int count = 0;
    int n = values.length;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        for (int k = j + 1; k < n; k++) {
          if (values[i] + values[j] + values[k] == 0) count++;
        }
      }
    }
    return count;
  }
  
  public static void main(String[] args) {
    String[] urls = {"http://algs4.cs.princeton.edu/14analysis/1Kints.txt",
        "http://algs4.cs.princeton.edu/14analysis/2Kints.txt",
        "http://algs4.cs.princeton.edu/14analysis/4Kints.txt",
        "http://algs4.cs.princeton.edu/14analysis/8Kints.txt",
        "http://algs4.cs.princeton.edu/14analysis/16Kints.txt"};
    SimpleRegression r = new SimpleRegression();
    r.addData(3, Math.log(64)/Math.log(2));
    r.addData(4, Math.log(592)/Math.log(2));
    System.out.println("Intercept: " + r.getIntercept());
    System.out.println("Slope: " + r.getSlope());
    if (true) return;
    for (int i = 0; i < urls.length; i++) {
      int[] values = (new In(urls[i])).readAllInts();
      int n = values.length;
      Stopwatch s = Stopwatch.getStartedInstance();
      int count = bruteForce(values);
      s.stop();
      double elapsed = s.elapsedSecs();
      StdOut.printf("Found %d sums in %d values in %.3f seconds%n", count, n, elapsed);
    }
  }
}
