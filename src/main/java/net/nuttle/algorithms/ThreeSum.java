package net.nuttle.algorithms;

import java.util.Arrays;

import net.nuttle.algorithms.search.Searcher;
import edu.princeton.cs.algs4.StdOut;

public class ThreeSum {

  public static void main(String[] args) {
    Integer[] vals = {-5, -3, -1, 0, 1, 2, 4};
    StdOut.println(find(vals));
  }
  
  /**
   * Flawed!  It's possible to have two negative and one positive value that sum to zero.
   * This needs to be generalized.
   * But in any case, order of growth is ~an^2, not sure what a is.
   * @param values
   * @return
   */
  public static int find(Integer[] values) {
    int matches = 0;
    Integer[] copy = Arrays.copyOf(values, values.length);
    values = copy;
    Arrays.sort(values);
    int n = values.length;
    int mid = Arrays.binarySearch(values, 0);
    if (mid < 0) mid = -mid - 1;
    for (Integer left = 0; left < mid; left++) {
      Integer curr = -1;
      for (Integer right = mid; right < n; right++) {
        if (values[right] > -values[left] / 2) break;
        if (values[right] == curr) continue; //Only consider a distinct value once
        else curr = values[right];
        if (values[left] + values[right] == 0) {
          matches++;
          continue;
        }
        int idx = Searcher.binarySearch((Integer) (-values[left] - values[right]), values, mid, n - 1, null);
        if (idx < 0) continue;
        matches++;
      }
    }
    return matches;
  }
}
