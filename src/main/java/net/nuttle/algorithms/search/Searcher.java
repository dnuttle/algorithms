package net.nuttle.algorithms.search;

import java.util.Comparator;

public class Searcher {
  
  public static <T extends Comparable<T>> int binarySearch(T val, T[] vals) {
    return binarySearch(val, vals, null);
  }
  
  public static <T extends Comparable<T>> int binarySearch(T val, T[] vals, Comparator<T> com) {
    int lo = 0;
    int hi = vals.length - 1;
    
    while (hi >= lo) {
      int mid = lo + (hi - lo) / 2;
      int diff = com == null ? val.compareTo(vals[mid]) : com.compare(val, vals[mid]);
      if (diff == 0) return mid;
      if (diff > 0) lo = mid + 1; 
      else hi = mid - 1;
    }
    return -lo - 1;
  }

}
