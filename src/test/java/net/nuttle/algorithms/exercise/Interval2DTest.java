package net.nuttle.algorithms.exercise;


import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.Interval2D;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdRandom;

public class Interval2DTest {

  @Test
  public void test() {
    Interval2D ivl = new Interval2D(new Interval1D(0.1, 0.2), new Interval1D(0.1, 0.2));
    for (int k = 0; k < 10000; k++) {
      int hit = 0;
      for (int i = 0; i < 10000; i++) {
        Point2D point = new Point2D(StdRandom.uniform(), StdRandom.uniform());
        if (ivl.contains(point)) {
          hit++;
        }
      }
      double expected = 0.01;
      //This can fail on very rare occasions
      Assert.assertEquals(expected, (double) hit / 10000, 1.0e-2);
    }
  }
}
