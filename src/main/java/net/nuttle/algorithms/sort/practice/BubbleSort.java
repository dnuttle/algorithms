package net.nuttle.algorithms.sort.practice;

import edu.princeton.cs.algs4.StdOut;
import net.nuttle.algorithms.sort.AbstractSort;
import net.nuttle.algorithms.sort.Sort;
import net.nuttle.algorithms.sort.SortException;

public class BubbleSort<T extends Comparable<T>> extends AbstractSort<T> implements Sort<T> {

  public static void main(String[] args) throws SortException {
    int n = 10000;
    Sort<Integer> s = new BubbleSort<>();
    StdOut.printf("Sorted %d integers in %.3f seconds %n", n, testSort(s, n));
  }
  
  public void sort(T[] items) {
    int n = items.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (less(items[i], items[j])) {
          swap(i, j, items);
        }
      }
    }
  }
}
