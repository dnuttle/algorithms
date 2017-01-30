package net.nuttle.algorithms;

import net.nuttle.algorithms.sort.AbstractSort;
import net.nuttle.algorithms.sort.Sort;
import net.nuttle.algorithms.sort.SortException;

import org.junit.Assert;
import org.junit.Test;

public class AbstractSortTest {

  @Test
  public void testIsSorted() {
    String[] items = new String[3];
    items[0] = "d";
    items[1] = "a";
    items[2] = "b";
    Assert.assertFalse(AbstractSort.isSorted(items));
    
    Sort<String> s = new BadSort<>();
    s.sort(items);
    Assert.assertFalse(AbstractSort.isSorted(items));
  }
  
  @Test(expected=SortException.class) 
  public void testTestSortException() throws Exception {
    Sort<Integer> s = new BadSort<>();
    AbstractSort.testSort(s, 100);
  }
}
