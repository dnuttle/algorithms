package net.nuttle.agorithms.sort.practice;

import edu.princeton.cs.algs4.StdOut;
import net.nuttle.algorithms.sort.AbstractSort;
import net.nuttle.algorithms.sort.Sort;
import net.nuttle.algorithms.sort.SortException;

public class ShellSort<T extends Comparable<T>> extends AbstractSort<T> implements Sort<T> {

  public static void main(String[] args) throws SortException {
    int n = 1000000;
    Sort<Integer> s = new ShellSort<>();
    StdOut.printf("Sorted %d integers in %.3f seconds%n", n, testSort(s, n));
  }
  
  public void sort(T[] items) {
    int n = items.length;
    int h = 1;
    while (h < n / 3) h = h * 3 + 1;
    while (h >= 1) {
      for (int i = h; i < n; i++) {
        for (int j = i; j >= h && less(items[j], items[j - h]); j -= h) {
          swap(j, j-h, items);
        }
      }
      h /= 3;
    }
  }
}