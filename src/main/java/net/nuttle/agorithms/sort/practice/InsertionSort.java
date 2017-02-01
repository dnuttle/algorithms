package net.nuttle.agorithms.sort.practice;

import edu.princeton.cs.algs4.StdOut;
import net.nuttle.algorithms.sort.AbstractSort;
import net.nuttle.algorithms.sort.Sort;
import net.nuttle.algorithms.sort.SortException;

public class InsertionSort<T extends Comparable<T>> extends AbstractSort<T> implements Sort<T> {

  public static void main(String[] args) throws SortException {
    int n = 10000;
    Sort<Integer> s = new InsertionSort<>();
    StdOut.printf("Sorted %d integers in %.3f seconds%n", n, testSort(s, n));
  }
  public void sort(T[] items) {
    int n = items.length;
    for (int i = 0; i < n; i++) {
      for (int j = i; j > 0; j--) {
        if (less(items[j], items[j - 1])) {
          swap(j, j-1, items);
        } else {
          break;
        }
      }
    }
  }
}
