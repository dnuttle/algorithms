package net.nuttle.algorithms.search;

import java.util.Comparator;

/**
 * An implementation of binary search with features missing from the course's implementation.
 * 1) It is generic, allowing searching of any Comparable array.
 * 2) If an element is not found, then the index returned is -(insertion point) - 1.
 * E.g. if it is -3, the element, if inserted, would be inserted at position 2.
 * Similar to Collection's binarySearch.
 * 3) It allows for the use of an alternate comparator.  This means the array being searched
 * must be sorted by the order of that comparator.
 * @author dnuttle
 *
 */
public class BinarySearch {

  public static <T extends Comparable<T>> int binarySearch(T val, T[] vals) {
    return binarySearch(val, vals, null);
  }
  
  public static <T extends Comparable<T>> int binarySearch(T val, T[] vals, Comparator<T> com) {
    int lo = 0;
    int hi = vals.length - 1;
    while (lo <= hi) {
      int mid = lo + (hi - lo) / 2;
      int diff = com == null ? val.compareTo(vals[mid]) : com.compare(val, vals[mid]);
      if  (diff == 0) return mid; else if (diff < 0) hi = mid - 1; else lo = mid + 1;
    }
    return -lo - 1;
  }
}
