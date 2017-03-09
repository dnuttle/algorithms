package net.nuttle.algorithms.shuffle;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;
import net.nuttle.algorithms.sort.Sorter;

public class Shuffler {
  
  public static void main(String[] args) {
    int unshuffled = 0;
    for (int i = 0; i < 10; i++) {
      String[] ref = {"a", "b", "c", "d", "e"};
      String[] values = {"a", "b", "c", "d", "e"};
      knuthShuffle(values);
      if (Arrays.equals(values,  ref)) unshuffled++;
      StringBuilder sb = new StringBuilder();
      for (String value : values) {
        sb.append(value).append(" ");
      }
      System.out.println(sb.toString());
    }
    System.out.println("Unshuffled instances: " + unshuffled);
  }

  /*
   * Ineffecient; requires a sort that is N log N, plus N time to reorder based on sort.
   */
  public static <T extends Comparable<T>> void arrayShuffle(T[] values) {
    int n = values.length;
    @SuppressWarnings("unchecked")
    Pair<T>[] pairs = new Pair[n];
    for (int i = 0; i < n; i++) {
      pairs[i] = new Pair<T>(values[i]);
    }
    Sorter.mergeSort(pairs);
    for (int i = 0; i < n; i++) {
      values[i] = pairs[i].value;
    }
  }
  
  public static <T extends Comparable<T>> void knuthShuffle(T[] values) {
    int n = values.length;
    for (int i = 0; i < n; i++) {
      int x = StdRandom.uniform(i + 1);
      T tmp = values[x];
      values[x] = values[i];
      values[i] = tmp;
    }
  }
  
  private static class Pair<T> implements Comparable<Pair<T>> {
    private T value;
    private Double random;
    Pair(T value) {
      this.value = value;
      random = StdRandom.uniform();
    }
    @Override
    public int compareTo(Pair that) {
      if (random > that.random) return 1;
      else if (random < that.random) return -1;
      return 0;
    }
  }
}
