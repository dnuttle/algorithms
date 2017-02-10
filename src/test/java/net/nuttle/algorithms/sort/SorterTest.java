package net.nuttle.algorithms.sort;

import org.junit.Assert;
import org.junit.Test;

public class SorterTest {

  @Test
  public void quickSortTest() {
    String[] vals = {"X", "F", "D", "Z", "E", "G", "I", "A", "H", "Z", "A"};
    String[] sorted = {"A", "A", "D", "E", "F", "G", "H", "I", "X", "Z", "Z"};
    Sorter.quickSort(vals);
    Assert.assertArrayEquals(sorted, vals);
  }
}
