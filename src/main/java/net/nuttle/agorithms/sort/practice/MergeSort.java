package net.nuttle.agorithms.sort.practice;

import edu.princeton.cs.algs4.StdOut;
import net.nuttle.algorithms.sort.AbstractSort;
import net.nuttle.algorithms.sort.Sort;
import net.nuttle.algorithms.sort.SortException;

public class MergeSort<T extends Comparable<T>> extends AbstractSort<T> implements Sort<T> {

  public static void main(String[] args) throws SortException {
    int n = 1000000;
    Sort<Integer> s = new MergeSort<>();
    StdOut.printf("Sorted %d integers in %.3f seconds%n", n, testSort(s, n));
  }
  
  public void sort(T[] items) {
    @SuppressWarnings("unchecked")
    T[] aux = (T[]) new Comparable[items.length];
    sort(items, aux, 0, items.length - 1);
  }
  
  private void sort(T[] a, T[] aux, int lo, int hi) {
    if (hi <= lo) return;
    int mid = lo + (hi - lo) / 2;
    sort(a, aux, lo, mid);
    sort(a, aux, mid + 1, hi);
    merge(a, aux, lo, mid, hi);
  }
  
  private void merge(T[] a, T[] aux, int lo, int mid, int hi) {
    for (int k = lo; k <= hi; k++) {
      aux[k] = a[k];
    }
    int i = lo;
    int j = mid + 1;
    for (int k = lo; k <= hi; k++) {
      if (i > mid) a[k] = aux[j++];
      else if (j > hi) a[k] = aux[i++];
      else if (less(aux[j], aux[i])) a[k] = aux[j++];
      else a[k] = aux[i++];
    }
  }
}
