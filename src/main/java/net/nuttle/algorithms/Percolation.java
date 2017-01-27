package net.nuttle.algorithms;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private int n1;
  private boolean[] open;
  private int openSites;
  private WeightedQuickUnionUF uf;
  private int head;
  private int tail;
  private boolean percolates;
  
  /**
   * Non-empty description.
   * @param n1 non-empty description.
   */
  public Percolation(int n1) {
    if (n1 <= 0) {
      throw new IllegalArgumentException("n1 must be > 0");
    }
    this.n1 = n1;
    // next-to-last is the top or head
    // last is bottom or tail
    uf = new WeightedQuickUnionUF(n1 * n1 + 2);
    open = new boolean[n1 * n1 + 2];
    head = n1 * n1;
    tail = n1 * n1 + 1;
  }
  
  /**
   * Desc
   * @param args desc.
   */
  public static void main(String[] args) {
    final Stopwatch s = new Stopwatch();
    int n1 = 1000;
    Percolation p1 = new Percolation(n1);
    while (!p1.percolates()) {
      int i1 = StdRandom.uniform(n1 * n1);
      int row = i1 / n1 + 1;
      int col = i1 % n1 + 1;
      p1.open(row, col);
    }
    StdOut.println("Done: " + ((double) p1.openSites / (double) (n1 * n1)));
    StdOut.println(s.elapsedTime() + " secs");
  }

  private int getIdx(int row, int col) {
    return (row - 1) * n1 + (col - 1);
  }
  
  private void validate(int row, int col) {
    if (row <= 0 || row > n1 || col <= 0 || col > n1) {
      throw new IndexOutOfBoundsException("row and col must be <= n (" + row + ", " + col + ")");
    }
  }
  
  
  /**
   * desc.
   * @param row desc
   * @param col desc
   */
  public void open(int row, int col) {
    validate(row, col);
    int idx = getIdx(row, col);
    
    if (row > 1 && isOpen(row - 1, col)) {
      uf.union(idx, idx - n1);
    }
    if (row < n1 && isOpen(row + 1, col)) {
      uf.union(idx, idx + n1);
    }
    if (col > 1 && isOpen(row, col - 1)) {
      uf.union(idx, idx - 1);
    }
    if (col < n1 && isOpen(row, col + 1)) {
      uf.union(idx, idx + 1);
    }
    if (row == 1) {
      uf.union(idx, head);
    }
    if (row == n1) {
      //uf.union(idx, tail);
    }
    if (!open[idx]) {
      openSites++;
      open[idx] = true;
    }
  }
  
  public boolean isOpen(int row, int col) {
    validate(row, col);
    return open[getIdx(row, col)];
  }

  /**
   * desc.
   * @param row desc
   * @param col desc
   * @return desc
   */
  public boolean isFull(int row, int col) {
    validate(row, col);
    if (!isOpen(row, col)) {
      return false;
    }
    int idx = getIdx(row, col);
    return uf.connected(idx, head);
  }
  
  public int numberOfOpenSites() {
    return openSites;
  }
  
  /**
   * desc.
   * @return desc
   */
  public boolean percolates() {
    if (percolates) {
      return true;
    }
    for (int i1 = 1; i1 <= n1; i1++) {
      if (isFull(n1, i1)) {
        percolates = true;
        return true;
      }
    }
    return false;
  }
}
