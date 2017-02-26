package net.nuttle.algorithms;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


public class StockAnalyzerTest {

  /**
   * All values rise steadily
   */
  @Test
  public void test1() {
    Double[] values = {100.0, 101.0, 102.0, 103.0, 104.0};
    double result = StockAnalyzer.quick(values);
    double expected = 4.0;
    runTest(values, expected);
  }
  
  /**
   * All values fall
   */
  @Test
  public void test2() {
    Double[] values = {104.0, 100.0, 97.0, 96.0, 90.0};
    double expected = -1.0;
    runTest(values, expected);
  }
  
  /**
   * Three rising sequences, largest first
   */
  @Test
  public void test3() {
    Double[] values = {100.0, 110.0, 105.0, 108.0, 101.0, 105.0};
    double expected = 10.0;
    runTest(values, expected);
  }
  
  /**
   * Three rising sequences, largest last
   */
  @Test
  public void test4() {
    Double[] values = {100.0, 101.0, 79.0, 82.0, 90.0, 65.0, 70.0};
    double expected = 11.0;
    runTest(values, expected);
  }
  
  /**
   * Three rising sequence, largest in middle
   */
  @Test
  public void test5() {
    Double[] values = {100.0, 101.0, 79.0, 82.0, 110.0, 60.0, 85.0, 86.0};
    double expected = 31.0;
    runTest(values, expected);
  }
  
  /**
   * Rise and fall but only one min, at start
   */
  @Test
  public void test6() {
    Double[] values = {40.0, 45.0, 48.0, 41.0, 42.0, 43.0, 40.5, 40.9, 56.0, 49.0, 57.0, 40.0, 40.0};
    double expected = 17.0;
    runTest(values, expected);
  }
  
  private void runTest(Double[] values, double expected) {
    double result = StockAnalyzer.bruteForce(values);
    Assert.assertEquals(expected, result, 1.0e-15);
    result = StockAnalyzer.quick(values);
    Assert.assertEquals(expected, result, 1.0e-15);
  }
}
