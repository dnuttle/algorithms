package net.nuttle.algorithms.sort;

import static net.nuttle.algorithms.sort.AbstractSort.less;
import static net.nuttle.algorithms.sort.AbstractSort.swap;

import java.util.Arrays;

import net.nuttle.algorithms.util.Stopwatch;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Sorter {

  public static void main(String[] args) throws SortException {
    StdRandom.setSeed(1);
    int n = 10000;
    Integer[] ints;
    Integer[] ref = getShuffled(n);
    Arrays.sort(ref);
    Stopwatch s;
    
    ints = getShuffled(n);
    s = Stopwatch.getStartedInstance();
    bubbleSort(ints);
    s.stop();
    StdOut.printf("Sorted %d integers with bubble sort in %.3f seconds%n", n, s.elapsedSecs());
    if (!Arrays.equals(ref, ints)) {
      StdOut.println("Insertion sort failed");
    }

    ints = getShuffled(n);
    s = Stopwatch.getStartedInstance();
    selectionSort(ints);
    s.stop();
    StdOut.printf("Sorted %d integers with selection sort in %.3f seconds%n", n, s.elapsedSecs());
    if (!Arrays.equals(ref, ints)) {
      StdOut.println("Insertion sort failed");
    }

    ints = getShuffled(n);
    s = Stopwatch.getStartedInstance();
    insertionSort(ints);
    s.stop();
    StdOut.printf("Sorted %d integers with insertion sort in %.3f seconds%n", n, s.elapsedSecs());
    if (!Arrays.equals(ref, ints)) {
      StdOut.println("Insertion sort failed");
    }

    ints = getShuffled(n);
    s = Stopwatch.getStartedInstance();
    shellSort(ints);
    s.stop();
    StdOut.printf("Sorted %d integers with shell sort in %.3f seconds%n", n, s.elapsedSecs());
    if (!Arrays.equals(ref, ints)) {
      StdOut.println("Shell sort failed");
    }
    ints = getShuffled(n);
    s = Stopwatch.getStartedInstance();
    mergeSort(ints);
    s.stop();
    StdOut.printf("Sorted %d integers with merge sort in %.3f seconds%n", n, s.elapsedSecs());
    if (!Arrays.equals(ref, ints)) {
      StdOut.println("Merge sort failed");
    }
    ints = getShuffled(n);
    s = Stopwatch.getStartedInstance();
    quickSort(ints);
    s.stop();
    StdOut.printf("Sorted %d integers with quick sort in %.3f seconds%n", n, s.elapsedSecs());
    if (!Arrays.equals(ref, ints)) {
      StdOut.println("Insertion sort failed");
    }
    
  }
  
  private static Integer[] getRandoms(int n) {
    Integer[] ints = new Integer[n];
    for (int i = 0; i < n; i++) {
      ints[i] = StdRandom.uniform(n);
    }
    return ints;
  }
  
  private static Integer[] getShuffled(int n) {
    Integer[] ints = new Integer[n];
    for (int i = 0; i < n; i++) {
      ints[i] = i;
    }
    StdRandom.shuffle(ints);
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
      swap(i, min, items);
    }
  }
  
  /**
   * Did I come up with the optimization?  I can't remember.  But storing curr 
   * makes it significantly faster (though of course it's still quadratic).
   * So in the face of diminishing gains as n increases, the simpler version may be preferable.
   * Also:  If the list is already sorted, or nearly sorted, this would be a performance penalty,
   * because there would be unneeded array references.
   * @param items
   */
  public static <T extends Comparable<T>> void insertionSort(T[] items) {
    int n = items.length;
    for (int i = 0; i < n; i++) {
      T curr = items[i];
      for (int j = i; j >= 1 ; j--) {
        if (less(curr, items[j-1])) {
          items[j] = items[j-1];
          if (j == 1) {
            items[0] = curr;
          }
        } else {
          items[j] = curr;
          break;
        }
      }
    }
  }
  
  public static <T extends Comparable<T>> void insertionSortBasic(T[] items) {
    int n = items.length;
    for (int i = 0; i < n; i++) {
      for (int j = i; j >= 1; j--) {
        if (less(items[j], items[j-1])) swap(j, j-1, items);
        else break;
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
    @SuppressWarnings("unchecked")
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
      /*
       * As a mnemonic.
       * Think of logic here like the file-based mergesort in Iridium.  We open up readers
       * for two or more data files, and iterate through them, looking for the lowest current value
       * in each; that value is written, and that reader is advanced to the next value.
       * "if (i > mid)" is the same as "if this reader is exhausted"
       * "else if (j > hi)" is the same as "if that reader is exhausted"
       * "else if (less(aux[j], aux[i))" is the same as "if this reader has the lower value"
       * "else" is the same as "the only alternative I have left is, that reader has the lower value"
       */
      if (i > mid) a[k] = aux[j++];
      else if (j > hi) a[k] = aux[i++];
      else if (less(aux[j], aux[i])) a[k] = aux[j++];
      else a[k] = aux[i++];
    }
  }
  
  public static <T extends Comparable<T>> void quickSort(T[] items) {
    StdRandom.shuffle(items);
    sort(items, 0, items.length - 1);
    
  }
  
  private static <T extends Comparable<T>> void sort(T[] items, int lo, int hi) {
    if (hi <= lo) return;
    int j = partition(items, lo, hi);
    sort(items, lo, j-1);
    sort(items, j+1, hi);
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
  
  public static <T extends Comparable<T>> void heapSort(T[] items) {
    
  }
  
  private static <T extends Comparable<T>> void swim(int k, T[] items) {
    while (k > 1 && less(items[(k / 2) - 1], items[k - 1])) {
      swap((k / 2) - 1, k - 1, items);
      k = k / 2;
    }
  }
  
  private static <T extends Comparable<T>> void sink(int k, T[] items, int size) {
    while (2 * k <= size) {
      int j = 2 * k;
      if (j < size && less(items[j+1], items[j])) j++;
      if (!less(items[k-1], items[j-1])) break;
      swap(k-1, j-1, items);
      k = j;
    }
  }

  public static <T extends Comparable<T>> void printArray(T[] items) {
    if (items.length > 100) {
      StdOut.println("Array > 100, too long");
      return;
    }
    StringBuilder sb = new StringBuilder();
    for (T item : items) {
      sb.append(item).append(" :|: ");
    }
    StdOut.println(sb.toString());
  }
  
}
