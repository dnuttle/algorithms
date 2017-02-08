package net.nuttle.algorithms.search;

public class BinarySearch {

  public static void main(String[] args) {
    Integer[] values = new Integer[1000];
    for (int i = 0; i < 1000; i++) {
      values[i] = i;
    }
    
    System.out.println(search(12, values));
    System.out.println(search(-10, values));
    System.out.println(search(999, values));
  }
  
  public static <T extends Comparable<T>> int search(T value, T[] items) {
    int lo = 0;
    int hi = items.length;
    int mid = lo + (hi - lo) / 2;
    
    while (true) {
      if (items[mid].equals(value)) {
        return mid;
      }
      if (value.compareTo(items[mid]) < 0) {
        hi = mid - 1;
        mid = lo + (hi - lo) / 2;
      } else {
        lo = mid + 1;
        mid = lo + (hi - lo) / 2;
      }
      if (lo >= hi || hi <= lo) {
        return -1;
      }
    }
  }
}
