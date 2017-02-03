package net.nuttle.algorithms.practice;

import net.nuttle.algorithms.sort.AbstractSort;
import net.nuttle.algorithms.sort.Sort;
import net.nuttle.algorithms.sort.SortException;
import edu.princeton.cs.algs4.StdOut;

public class SelectionSort<T extends Comparable<T>> extends AbstractSort<T> implements Sort<T> {

  public static void main(String[] args) throws SortException {
    int n = 10000;
    Sort<Integer> s = new SelectionSort<>();
    StdOut.printf("Sorted %d integers in %.3f seconds%n", n, testSort(s, n));
  }
  
  public void sort(T[] items) {
    int n = items.length;
    
    for (int i = 0; i < n; i++) {
      int min = i;
      for (int j = i; j < n; j++) {
        if (less(items[j], items[min])) {
          min = j;
        }
      }
      if (min != i) {
        swap(min, i, items);
      }
    }
  }
}
