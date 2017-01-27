package net.nuttle.algorithms;

public interface Percolator {

  void open(int row, int col);

  boolean isOpen(int row, int col);
  
  boolean isFull(int row, int col);
  
  boolean percolates();
  
  int numberOfOpenSites();
}
