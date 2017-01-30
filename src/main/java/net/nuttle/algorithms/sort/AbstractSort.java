package net.nuttle.algorithms.sort;

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
  
  public static <T> boolean isSorted(T[] items) {
    int n = items.length;
    for (int i = 1; i < items.length; i++) {
      if (less(i, i-1)) {
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
    Integer[] values = new Integer[n];
    for (int i = 0; i < n; i++) {
      values[i] = StdRandom.uniform(n);
    }
    Stopwatch sw = new Stopwatch();
    sorter.sort(values);
    double elapsed = sw.elapsedTime();
    if (!isSorted(values)) {
      throw new SortException("Sorter failed to sort properly");
    }
    return elapsed;
  }
  
}
