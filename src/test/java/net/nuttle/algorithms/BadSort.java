package net.nuttle.algorithms;

import net.nuttle.algorithms.sort.AbstractSort;
import net.nuttle.algorithms.sort.Sort;

/**
 * Subclass of AbstractTest whose sort algorithm is always wrong, to test 
 * AbstractSort.testSort.
 * @author dnuttle
 *
 * @param <T>
 */
public class BadSort<T extends Comparable<T>> extends AbstractSort<T> implements Sort<T> {

  /**
   * Ensures that sort order is false, so long as there are at least 2 distinct values in items.
   */
  public void sort(T[] items) {
    int n = items.length;
    int curr = 0;
    for (int i = 1; i < n; i++) {
      if (less(items[curr], items[i])) {
        swap(curr, i, items);
        return;
      }
    }
  }
}
