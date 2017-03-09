package net.nuttle.algorithms.search;

import org.junit.Test;

import edu.princeton.cs.algs4.StdOut;


public class TwoSumTest {

  @Test
  public void test1() {
    int[] vals = {-2, -1, -1, 0, 0, 0, 0, 1, 1, 2, 2};
    StdOut.println(TwoSum.faster(vals));
    //Assert.assertEquals(6,  TwoSum.faster(vals));
  }
}
