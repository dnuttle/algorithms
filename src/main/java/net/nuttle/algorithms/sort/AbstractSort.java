package net.nuttle.algorithms.sort;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public abstract class AbstractSort<T extends Comparable<T>> implements Sort<T> {

  public static <T extends Comparable<T>> boolean less(T a, T b ) {
    return a.compareTo(b) < 0;
  }
  
  public static <T> void swap(int x, int y, T[] items) {
    T tmp = items[x];
    items[x] = items[y];
    items[y] = tmp;
  }
  
  public static <T extends Comparable<T>> boolean isSorted(T[] items) {
    int n = items.length;
    for (int i = 1; i < n; i++) {
      if (less(items[i], items[i-1])) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * Tests a sorter using Integers.  Returns the number of seconds elapsed.
   * @param sorter
   * @param n
   * @return
   * @throws SortException
   */
  public static double testSort(Sort<Integer> sorter, int n) throws SortException {
    return testSort(sorter, n, false);
  }
  
  public static double testSort(Sort<Integer> sorter, int n, boolean debug) throws SortException {
    Integer[] values = new Integer[n];
    Integer[] orig = new Integer[n];
    for (int i = 0; i < n; i++) {
      values[i] = i;
      orig[i] = i;
    }
    StdRandom.shuffle(values);
    if (debug) {
      printArray(values);
    }
    double elapsed = testSort(sorter, values);
    if (debug) {
      printArray(values);
    }
    if (!isSorted(values)) {
      throw new SortException("Sorter failed to sort properly");
    }
    if (!Arrays.equals(orig, values)) {
      throw new SortException("Sorter contains wrong values");
    }
    return elapsed;
  }
  
  private static double testSort(Sort<Integer> sorter, Integer[] values) {
    Stopwatch sw = new Stopwatch();
    sorter.sort(values);
    double elapsed = sw.elapsedTime();
    return elapsed;
  }

  public static <T extends Comparable<T>> void printArray(T[] items) {
    if (items.length > 100) {
      StdOut.println("Array > 100, too long");
      return;
    }
    StringBuilder sb = new StringBuilder();
    for (T item : items) {
      sb.append(item).append(" :|: ");
    }
    StdOut.println(sb.toString());
  }
  
}
