package net.nuttle.algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;
import net.nuttle.algorithms.util.Stopwatch;

/**
 * Examines an array of doubles and finds the largest value difference in a streak.
 * The values must not be from the same element; the ending value sought is the "best"
 * value (though it may actually be less than the starting value, if they're all negative).
 * @author Dan
 *
 */
public class StockAnalyzer {

  public static void main(String[] args) {
    List<Double> opens = new ArrayList<>();
    String csvFile = System.getProperty("user.dir") + "/src/test/resources/DJIA.csv";
    String line;
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      //read and discard header line
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] fields = line.split(",");
        opens.add(Double.parseDouble(fields[1]));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    Collections.reverse(opens);
    Double[] arrOpens = opens.toArray(new Double[0]);
    Stopwatch s = Stopwatch.getStartedInstance();
    double result = bruteForce(arrOpens);
    s.stop();
    StdOut.printf("Found answer %.4f  in %.4f seconds%n", result, s.elapsedSecs());
    s = Stopwatch.getStartedInstance();
    result = quick(arrOpens);
    s.stop();
    StdOut.printf("Found answer %.4f  in %.4f seconds%n", result, s.elapsedSecs());
  }

  /**
   * Run time proportional to ~O(n^2)
   * @param opens
   * @return
   */
  public static double bruteForce(Double[] opens) {
    //int loIdx=0;
    //int hiIdx=1;
    if (opens.length < 2) throw new IllegalStateException("Array must have at least two values");
    double max = opens[1] - opens[0];
    for (int i = 0; i < opens.length; i++) {
      for (int j = i + 1; j < opens.length; j++) {
        double curr = opens[j] - opens[i];
        if (curr > max) {
          max = curr;
          //loIdx = i;
          //hiIdx = j;
        }
      }
    }
    //StdOut.println("lo:" + loIdx + "(" + opens[loIdx] + "),  hi:" + hiIdx + " (" + opens[hiIdx] + ")");
    return max;
  }
  
  /**
   * Run time proportional to ~O(n)
   * @param opens
   * @return
   */
  public static double quick(Double[] opens) {
    if (opens.length < 2) throw new IllegalStateException("Array must have at least two values");
    Streak best = new Streak(opens[0], opens[1]);
    Streak curr = new Streak(opens[0], opens[1]);
    int currLoIdx = 0;
    int currHiIdx = 1;
    for (int i = 0; i < opens.length; i++) {
      double open = opens[i];
      Streak today = new Streak(opens[currLoIdx], opens[i]);
      if (today.compareTo(best) > 0 && i > currLoIdx) {
        curr.setEnd(open);
        currHiIdx = i;
        best = new Streak(opens[currLoIdx], opens[currHiIdx]);
      }
      if (open < opens[currLoIdx] && i < opens.length -1) {
        currLoIdx = i;
        currHiIdx = i + 1;
      }
    }
    return best.diff;
  }
  
  private static class Streak implements Comparable<Streak> {
    private Double start;
    private Double end;
    private double diff;
    public Streak(Double start, Double end) {
      this.start = start;
      this.end = end;
      diff = end - start;
    }
    
    @Override
    public int compareTo(Streak that) {
      double cmp = diff - that.diff;
      if (cmp > 0.0) return 1;
      else if (cmp < 0.0) return -1;
      else return 0;
    }
    
    public void setEnd(Double end) {
      this.end = end;
      diff = this.end - start;
    }
  }
}
