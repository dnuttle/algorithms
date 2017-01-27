package net.nuttle.algorithms;

import edu.princeton.cs.algs4.StdRandom;

public class MyPercolation implements Percolator {

  private int n;
  private MyUF2 uf;
  boolean[] open;
  int openSites;
  int head;
  int tail;
  
  public MyPercolation(int n) {
    this.n = n;
    uf = new MyUF2(n * n + 2);
    head = n * n;
    tail = n * n + 1;
    open = new boolean[n * n + 2];
  }
  
  public static void main(String[] args) {
    int n = 100;
    Percolator p = new MyPercolation(n);
    while (!p.percolates()) {
      int row = StdRandom.uniform(1, n + 1);
      int col = StdRandom.uniform(1, n + 1);
      p.open(row, col);
    }
    System.out.println("Done");
    
  }
  
  private int getIdx(int row, int col) {
    return (n-1) * row + col - 1;
  }
  
  @Override
  public void open(int row, int col) {
    int idx = getIdx(row, col);
    //System.out.println(row + " " + col + " " + idx);
    if (!open[idx]) {
      open[idx] = true;
      openSites++;
    }
    if (row > 1 && isOpen(row-1, col)) {
      uf.union(idx, idx - n);
    }
    if (row < n && isOpen(row+1, col)) {
      uf.union(idx,  idx + n);
    }
    if (col > 1 && isOpen(row, col - 1)) {
      uf.union(idx,  idx - 1);
    }
    if (col < n && isOpen(row, col + 1)) {
      uf.union(idx,  idx + 1);
    }
    if (row == 1) {
      uf.union(idx, head);
    }
    if (row == n) {
      uf.union(idx, tail);
    }
  }
  
  @Override
  public boolean isOpen(int row, int col) {
    return open[getIdx(row, col)];
  }
  
  @Override
  public boolean isFull(int row, int col) {
    return uf.connected(head, getIdx(row, col));
  }
  
  @Override
  public boolean percolates() {
    return uf.connected(head, tail);
  }
  
  @Override
  public int numberOfOpenSites() {
    return openSites;
  }
  
}
