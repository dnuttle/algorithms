package net.nuttle.algorithms.search;

import java.util.Comparator;

public class Searcher {
  
  public static <T extends Comparable<T>> int binarySearch(T val, T[] vals) {
    return binarySearch(val, vals, null);
  }
  
  public static <T extends Comparable<T>> int binarySearch(T val, T[] vals, Comparator<T> com) {
    int lo = 0;
    int hi = vals.length - 1;
    return binarySearch(val, vals, lo, hi, com);
  }
  
  public static <T extends Comparable<T>> int binarySearch(T val, T[] vals, int lo, int hi, Comparator<T> com) {
    while (hi >= lo) {
      int mid = lo + (hi - lo) / 2;
      int diff = com == null ? val.compareTo(vals[mid]) : com.compare(val, vals[mid]);
      if (diff > 0) lo = mid + 1;
      else if (diff < 0) hi = mid - 1;
      else return mid;
    }
    return -lo - 1;
    
  }
  
}
