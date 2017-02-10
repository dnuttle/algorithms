package net.nuttle.algorithms.sort;

import static net.nuttle.algorithms.sort.AbstractSort.less;
import static net.nuttle.algorithms.sort.AbstractSort.swap;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class Sorter {

  public static void main(String[] args) throws SortException {
    int n = 10000;
    Integer[] ints = getRandoms(n);
    Stopwatch s = new Stopwatch();
    //bubbleSort(ints);
    //StdOut.printf("Sorted %d integers with bubble sort in %.3f seconds%n", n, s.elapsedTime());
    //ints = getRandoms(n);
    //s = new Stopwatch();
    selectionSort(ints);
    StdOut.printf("Sorted %d integers with selection sort in %.3f seconds%n", n, s.elapsedTime());
    ints = getRandoms(n);
    s = new Stopwatch();
    insertionSort(ints);
    StdOut.printf("Sorted %d integers with insertion sort in %.3f seconds%n", n, s.elapsedTime());
    ints = getRandoms(n);
    s = new Stopwatch();
    shellSort(ints);
    StdOut.printf("Sorted %d integers with shell sort in %.3f seconds%n", n, s.elapsedTime());
    ints = getRandoms(n);
    s = new Stopwatch();
    mergeSort(ints);
    StdOut.printf("Sorted %d integers with merge sort in %.3f seconds%n", n, s.elapsedTime());
  }

  private static Integer[] getRandoms(int n) {
    Integer[] ints = new Integer[n];
    for (int i = 0; i < n; i++) {
      ints[i] = StdRandom.uniform(n);
    }
    return ints;
  }
  
  
  public static <T extends Comparable<T>> void bubbleSort(T[] items) {
    int n = items.length;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (less(items[j], items[i])) {
          swap(j, i, items);
        }
      }
    }
  }
  
  public static <T extends Comparable<T>> void selectionSort(T[] items) {
    int n = items.length;
    for (int i = 0; i < n; i++) {
      int min = i;
      for (int j = i; j < n; j++) {
        if (less(items[j], items[min])) {
          min = j;
        }
      }
      if (min != i) {
        swap(i, min, items);
      }
    }
  }
  
  public static <T extends Comparable<T>> void insertionSort(T[] items) {
    int n = items.length;
    for (int i = 0; i < n; i++) {
      for (int j = i; j >= 1; j--) {
        if (less(items[j], items[j-1])) {
          swap(j, j-1, items);
        } else {
          break;
        }
      }
    }
  }
  
  public static <T extends Comparable<T>> void shellSort(T[] items) {
    int n = items.length;
    int h = 1;
    while (h < n / 3) h = h * 3 + 1;
    while (h >= 1) {
      for (int i = h; i < n; i++) {
        for (int j = i; j >= h && less(items[j], items[j - h]); j -= h) {
          swap(j, j-h, items);
        }
        
      }
      h = h / 3;
    }
  }
  
  public static <T extends Comparable<T>> void mergeSort(T[] items) {
    int n = items.length;
    T[] aux = (T[]) new Comparable[n];
    sort(items, aux, 0, n - 1);
  }
  
  private static <T extends Comparable<T>> void sort(T[] a, T[] aux, int lo, int hi) {
    if (hi <= lo) return;
    int mid = lo + (hi - lo) / 2;
    sort(a, aux, lo, mid);
    sort(a, aux, mid + 1, hi);
    merge(a, aux, lo, mid, hi);
  }
  
  private static <T extends Comparable<T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {
    for (int k = lo; k <= hi; k++) {
      aux[k] = a[k];
    }
    int i = lo;
    int j = mid + 1;
    for (int k = lo; k <= hi; k++) {
      if (i > mid) a[k] = aux[j++];
      else if (j > hi) a[k] = aux[i++];
      else if (less(aux[j], aux[i])) a[k] = aux[j++];
      else a[k] = a[i++];
    }
  }
  
  public static <T extends Comparable<T>> void quickSort(T[] items) {
    
    
  }
  
  private static <T extends Comparable<T>> int partition(T[] items, int lo, int hi) {
    int i = lo;
    int j = hi + 1;
    T v = items[lo];
    
    while (true) {
      while (less(items[++i], v)) if (i==hi) break;
      while (less(v, items[--j])) if (j==lo) break;
      if (i >= j) break;
      swap(i, j, items);
    }
    swap(lo, j, items);
    return j;
  }
}
