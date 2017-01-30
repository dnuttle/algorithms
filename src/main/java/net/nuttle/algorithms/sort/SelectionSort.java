package net.nuttle.algorithms.sort;

import edu.princeton.cs.algs4.StdOut;

public class SelectionSort<T extends Comparable<T>> extends AbstractSort<T> implements Sort<T> {

  public static void main(String[] args) {
    Sort<String> sorter = new SelectionSort<>();
    String[] strings = new String[4];
    strings[0] = "a";
    strings[1] = "d";
    strings[2] = "b";
    strings[3] = "c";
    sorter.sort(strings);
    for (String s : strings) {
      StdOut.println(s);
    }
  }
  
  public void sort(T[] items) {
    for (int i = 0; i < items.length; i++) {
      int min = i;
      for (int j = i + 1; j < items.length; j++) {
        if (less(items[j], items[min])) {
          min = j;
        }
      }
      if (min != i) {
        swap(min, i, items);
      }
    }
  }
}
