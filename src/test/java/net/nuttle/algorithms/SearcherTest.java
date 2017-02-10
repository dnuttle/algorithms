package net.nuttle.algorithms;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

import net.nuttle.algorithms.search.Searcher;

public class SearcherTest {
  
  @Test
  public void testBinarySearch() {
    Integer[] vals = {0, 2, 4};
    Assert.assertEquals(-1, Searcher.binarySearch(-10, vals));
    Assert.assertEquals(0, Searcher.binarySearch(0, vals));
    Assert.assertEquals(-2, Searcher.binarySearch(1, vals));
    Assert.assertEquals(1, Searcher.binarySearch(2, vals));
    Assert.assertEquals(-3, Searcher.binarySearch(3, vals));
    Assert.assertEquals(2, Searcher.binarySearch(4, vals));
    Assert.assertEquals(-4, Searcher.binarySearch(5, vals));
  }

  @Test
  public void testBinarySearchComparator() {
    Integer[] vals = {4, 2, 0};
    Comparator<Integer> c = new Comparator<Integer>() {
      @Override
      public int compare(Integer i1, Integer i2) {
        return i2 - i1;
      }
    };
    Assert.assertEquals(-4, Searcher.binarySearch(-10, vals, c));
    Assert.assertEquals(2, Searcher.binarySearch(0, vals, c));
    Assert.assertEquals(-3, Searcher.binarySearch(1, vals, c));
    Assert.assertEquals(1, Searcher.binarySearch(2, vals, c));
    Assert.assertEquals(-2, Searcher.binarySearch(3, vals, c));
    Assert.assertEquals(0, Searcher.binarySearch(4, vals, c));
    Assert.assertEquals(-1, Searcher.binarySearch(5, vals, c));
    
  }
}
