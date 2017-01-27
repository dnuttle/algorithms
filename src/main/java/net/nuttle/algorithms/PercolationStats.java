package net.nuttle.algorithms;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private int trials;
  private double[] threshold;
  
  /**
   * Non-empty description.
   * @param n1 non-empty description.
   * @param trials non-empty description.
   */
  public PercolationStats(int n1, int trials) {
    if (n1 <= 0 || trials <= 0) {
      throw new IllegalArgumentException("n and trials must both be >= 0");
    }
    this.trials = trials;
    threshold = new double[trials];
    for (int i = 0; i < trials; i++) {
      Percolation p1 = new Percolation(n1);
      while (!p1.percolates()) {
        int idx = StdRandom.uniform(n1 * n1);
        int row = idx / n1 + 1;
        int col = idx % n1 + 1;
        p1.open(row, col);
      }
      threshold[i] = ((double) p1.numberOfOpenSites() / (double) (n1 * n1));
    }
  }
  
  /**
   * Non-empty description.
   * @param args description.
   */
  public static void main(String[] args) {
    int n1 = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    PercolationStats stats = new PercolationStats(n1, trials);
    StdOut.printf("%-25s = %f\n", "mean", stats.mean());
    StdOut.printf("%-25s = %f\n", "stddev", stats.stddev());
    StdOut.printf("%-25s = %f, %f\n", "95% confidence interval =", 
        stats.confidenceLo(), stats.confidenceHi());
  }
  
  public double mean() {
    return StdStats.mean(threshold);
  }
  
  public double stddev() {
    return StdStats.stddev(threshold);
  }
  
  public double confidenceLo() {
    return mean() - (1.96 * stddev()) / Math.sqrt(trials);
  }
  
  public double confidenceHi() {
    return mean() + (1.96 * stddev()) / Math.sqrt(trials);
  }
}
