package net.nuttle.algorithms;

import java.util.Comparator;

import net.nuttle.algorithms.search.BinarySearch;

import org.junit.Assert;
import org.junit.Test;

public class BinarySearchTest {

  @Test
  public void validateTest() {
    Integer[] vals = {0, 2, 4};
    Assert.assertEquals(-1, BinarySearch.binarySearch(-1000, vals));
    Assert.assertEquals(-1, BinarySearch.binarySearch(-1, vals));
    Assert.assertEquals(0, BinarySearch.binarySearch(0, vals));
    Assert.assertEquals(-2, BinarySearch.binarySearch(1, vals));
    Assert.assertEquals(1, BinarySearch.binarySearch(2, vals));
    Assert.assertEquals(-3, BinarySearch.binarySearch(3, vals));
    Assert.assertEquals(2, BinarySearch.binarySearch(4, vals));
    Assert.assertEquals(-4, BinarySearch.binarySearch(5, vals));
    Assert.assertEquals(-4, BinarySearch.binarySearch(1000, vals));
  }
  
  @Test
  public void testAltComparator() {
    Integer[] vals = {4, 2, 0};
    Comparator<Integer> com = new ReverseComparator<>();
    Assert.assertEquals(-1, BinarySearch.binarySearch(1000, vals, com));
    Assert.assertEquals(-1, BinarySearch.binarySearch(5, vals, com));
    Assert.assertEquals(0, BinarySearch.binarySearch(4, vals, com));
    Assert.assertEquals(1, BinarySearch.binarySearch(2, vals, com));
    Assert.assertEquals(-3, BinarySearch.binarySearch(1, vals, com));
    Assert.assertEquals(2, BinarySearch.binarySearch(0, vals, com));
    Assert.assertEquals(-4, BinarySearch.binarySearch(-1, vals, com));
    Assert.assertEquals(-4, BinarySearch.binarySearch(-1000, vals, com));
  }
  
  @Test
  public void testEmptyList() {
    Integer[] vals = {};
    Assert.assertEquals(-1, BinarySearch.binarySearch(100, vals));
  }
  
  private static class ReverseComparator<T extends Comparable<T>> implements Comparator<T> {
    public int compare(T t1, T t2) {
      int diff = t1.compareTo(t2);
      return -diff;
    }
  }
}
