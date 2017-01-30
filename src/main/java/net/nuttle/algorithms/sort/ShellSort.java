package net.nuttle.algorithms.sort;

import edu.princeton.cs.algs4.StdOut;

public class ShellSort<T extends Comparable<T>> extends AbstractSort<T> implements Sort<T> {

  public static void main(String[] args) throws SortException {
    int n = 40000;
    Sort<Integer> s = new ShellSort<>();
    StdOut.printf("Sorted %d integers in %f seconds", n, testSort(s, n));
  }
  
  public void sort(T[] items) {
    int n = items.length;
    int h = 1;
    while (h < n /3) h = 3*h + 1;
    while (h >= 1) {
      for (int i = h; i < n; i += h) {
        for (int j = i; j >= h && less(items[j], items[j-h]); j -= h) {
          swap(j, j-h, items);
        }
      }
      h = h / 3;
    }
  }
}
