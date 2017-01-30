package net.nuttle.algorithms.sort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class ShellSort<T extends Comparable<T>> extends AbstractSort<T> implements Sort<T> {

  public static void main(String[] args) {
    int n = 1000000;
    Integer[] values = new Integer[n];
    for (int i = 0; i < n; i++) {
      values[i] = StdRandom.uniform(n);
    }
    Sort<Integer> sorter = new ShellSort<>();
    long start = System.currentTimeMillis();
    sorter.sort(values);
    StdOut.println("Elapsed: " + (System.currentTimeMillis() - start));
    for (int i = 0; i < n-1; i++) {
      if (less(values[i+1], values[i])) {
        System.out.println("Problem at " + i);
      }
    }
  }
  
  public void sort(T[] items) {
    int n = items.length;
    int h = 1;
    while (h < n /3) h = 3*h + 1;
    while (h >= 1) {
      for (int i = h; i < n; i++) {
        for (int j = i; j >= h && less(items[j], items[j-h]); j -= h) {
          swap(j, j-h, items);
        }
      }
      h = h / 3;
    }
  }
}
