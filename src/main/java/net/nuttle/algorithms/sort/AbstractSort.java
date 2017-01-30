package net.nuttle.algorithms.sort;

public abstract class AbstractSort<T extends Comparable<T>> implements Sort<T> {

  public static <T extends Comparable<T>> boolean less(T a, T b ) {
    return a.compareTo(b) < 0;
  }
  
  public static <T> void swap(int x, int y, T[] items) {
    T tmp = items[x];
    items[x] = items[y];
    items[y] = tmp;
  }
  
}
