package net.nuttle.algorithms.sort;

public interface Sort<T extends Comparable<T>> {

  public void sort(T[] items);
  
}
