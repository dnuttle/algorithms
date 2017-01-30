package net.nuttle.algorithms.sort;

import edu.princeton.cs.algs4.StdOut;

public class InsertionSort<T extends Comparable<T>> extends AbstractSort<T> implements Sort<T> {

  public static void main(String[] args) throws SortException {
    String[] strings = new String[4];
    strings[0] = "d";
    strings[1] = "c";
    strings[2] = "b";
    strings[3] = "a";
    InsertionSort<String> sort = new InsertionSort<>();
    sort.sort(strings);
    for (String s : strings) {
      StdOut.println(s);
    }
    
    Sort<Integer> si = new InsertionSort<>();
    int n = 10000;
    StdOut.println("Sorted " + n + " integers in " + testSort(si, n) + " seconds");
  }
  
  public void sort(T[]  items) {
    int n = items.length;
    for (int i = 0; i < n; i++) {
      for (int j = i; j > 0; j--) {
        if (less(items[j], items[j-1])) {
          swap(j, j-1, items);
        } else {
          break;
        }
      }
    }
  }
  
}
