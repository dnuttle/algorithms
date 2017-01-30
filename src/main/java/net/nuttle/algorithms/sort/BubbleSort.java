package net.nuttle.algorithms.sort;

import edu.princeton.cs.algs4.StdOut;

public class BubbleSort<T extends Comparable<T>> extends AbstractSort<T> implements Sort<T> {

  public static void main(String[] args) throws SortException {
    int n = 10000;
    Sort<Integer> s = new BubbleSort<>();
    StdOut.println("Sorted " + n + " integers in " + testSort(s, n) + " seconds");
  }
  
  public void sort(T[] values) {
    int n = values.length;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (less(values[j], values[i])) {
          swap(j, i, values);
        }
      }
    }
  }
}
