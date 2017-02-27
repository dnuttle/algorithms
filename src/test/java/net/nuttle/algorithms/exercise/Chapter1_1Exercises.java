package net.nuttle.algorithms.exercise;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.StdOut;

public class Chapter1_1Exercises {

  @Test
  public void test1_1_1() {
    Assert.assertEquals(7, (0 + 15) / 2);
    Assert.assertEquals(200.0000002, 2.0e-6 * 100000000.1, 1.0e-15);
    Assert.assertTrue(true && false || true && true); //&& precedence over || so equivalent to (true && false) || (true && true)
  }
  
  @Test
  public void test1_1_2() {
    Assert.assertEquals(1.618, (1 + 2.236)/2, 1.0e-15); //double
    Assert.assertEquals(10.0, 1 + 2 + 3 + 4.0, 1.0e-15); //double
    Assert.assertEquals(true, 4.1 >= 4); //boolean
    Assert.assertEquals("33", 1 + 2 + "3"); //String
    //Extra: any numeric expressions *before* the first string concatenation are considered numeric;
    //any *after* the first concatenation are seen as more strings
    Assert.assertEquals("3345", 1 + 2 + "3" + 4 + 5);
  }
  
  @Test
  public void test1_1_3() {
    int[] vals = {1, 2, 2};
    Assert.assertFalse(compareThree(vals));
    vals = new int[]{3,3,3};
    Assert.assertTrue(compareThree(vals));
  }
  
  private boolean compareThree(int[] vals) {
    if (vals.length!=3) {
      throw new IllegalStateException("vals must have three elements");
    }
    if (vals[0] == vals[1] && vals[0] == vals[2]) return true;
    else return false;
  }
  
  @Test
  public void test1_1_4() {
    int a = 0;
    int b = 1;
    int c;
    //a. compile error, "then" not legal
    //if (a > b) then c = 0;
    //b. Legal
    if (a > b) { c = 0; }
    //c. Legal
    if (a > b) c = 0;
    //d. ; required before else
    //if (a > b) then c = 0 else b = 0;
  }
  
  @Test
  public void test1_1_5() {
    //hard to know what "strictly between" means, I opt for exclusive on both sides
    double x = 0.0;
    double y = 0.0;
    Assert.assertEquals("false", strictlyBetween(0.0, 0.0, 0.0, 1.0));
    Assert.assertEquals("false", strictlyBetween(0.0, 0.5, 0.0, 1.0));
    Assert.assertEquals("false", strictlyBetween(0.5, 0.0, 0.0, 1.0));
    Assert.assertEquals("false", strictlyBetween(0.1, 1.0, 0.0, 1.0));
    Assert.assertEquals("false", strictlyBetween(1.0, 0.1, 0.0, 1.0));
    Assert.assertEquals("false", strictlyBetween(1.0, 1.0, 0.0, 1.0));
    Assert.assertEquals("true", strictlyBetween(0.1, 0.3, 0.0, 1.0));
  }
  
  private String strictlyBetween(double x, double y, double min, double max) {
    return x > min && x < max && y > min && y < max ? "true" : "false";
  }
  
  @Test
  public void test1_1_6() {
    StringBuilder sb = new StringBuilder();
    int f = 0;
    int g = 1;
    for (int i = 0; i <= 15; i++) {
      sb.append(f).append("-");
      f = f + g;
      g = f - g;
    }
    Assert.assertEquals("0-1-1-2-3-5-8-13-21-34-55-89-144-233-377-610-", sb.toString());
  }
  
  @Test
  public void test1_1_7() {
    double t = 9.0;
    while (Math.abs(t - 9.0/t) > 0.001) {
      t = (9.0/t + t) / 2.0;
    }
    double expected = 3.0;
    Assert.assertEquals(expected, t, 1.0e-4);  //not sure why 1.0e-4 and not 1.0e-3
    
    int sum = 0;
    for (int i = 1; i < 1000; i++) {
      for (int j = 0; j < i; j++) {
        sum++;
      }
    }
    //1 + 2 + 3 + 4 + ... + 999 = 1/2 (999 * 1000)
    int expectedInt = (999 * 1000) / 2;
    Assert.assertEquals(expectedInt, sum);
    
    sum = 0;
    int n = 1000;
    for (int i = 1; i < n; i *= 2) {
      for (int j = 0; j < 1000; j++) {
        sum++;
      }
    }
    //Use ln(n) / ln(2) to get log_2(n)
    expectedInt = 1000 * (int) Math.ceil((Math.log(n) / Math.log(2)));
    Assert.assertEquals(expectedInt, sum);
  }
  
}
