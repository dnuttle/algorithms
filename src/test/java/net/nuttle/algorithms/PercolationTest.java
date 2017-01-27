package net.nuttle.algorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


public class PercolationTest {
  
  private static String rscPath = System.getProperty("user.dir") + "/src/test/resources/";
  
  @Test
  public void testInput1_No() throws Exception {
    Percolation p = setupTest("input1-no.txt", 1);
    Assert.assertFalse(p.percolates());
  }

  @Test
  public void testInput1() throws Exception {
    Percolation p = setupTest("input1.txt", 1);
    Assert.assertTrue(p.percolates());
  }

  @Test
  public void testInput10() throws Exception {
    Percolation p = setupTest("input10.txt", 10);
    Assert.assertTrue(p.percolates());
  }
  
  @Test
  public void testInput10_No() throws Exception {
    Percolation p = setupTest("input10-no.txt", 10);
    Assert.assertFalse(p.percolates());
  }
  
  @Test
  public void testInput2() throws Exception {
    Percolation p = setupTest("input2.txt", 2);
    Assert.assertTrue(p.percolates());
  }
  
  @Test
  public void testInput2_No() throws Exception {
    Percolation p = setupTest("input2-no.txt", 2);
    Assert.assertFalse(p.percolates());
  }
  
  @Test
  public void testInput20() throws Exception {
    Percolation p = new Percolation(20);
    String fileName = rscPath + "percolation/input20.txt";
    Stream<String> stream = Files.lines(Paths.get(fileName));
    AtomicInteger count = new AtomicInteger();
    stream.forEach(line -> {
      if (count.get() > 0) {
        String s = line.trim();
        String[] vals = s.split("\\s+");
        int row = Integer.parseInt(vals[0]);
        int col = Integer.parseInt(vals[1]);
        p.open(row, col);
        if (count.get() == 231) {
          //PercolationVisualizer.draw(p, 20);
          Assert.assertFalse(p.isFull(18,  1));
        }
      }
      count.incrementAndGet();
    });
    Assert.assertTrue(p.percolates());
  }

  @Test
  public void testInput3() throws Exception {
    Percolation p = setupTest("input3.txt", 3);
    Assert.assertTrue(p.percolates());
  }
  
  @Test
  public void testInput4() throws Exception {
    Percolation p = setupTest("input4.txt", 4);
    Assert.assertTrue(p.percolates());
  }
  
  @Test
  public void testInput5() throws Exception {
    Percolation p = setupTest("input5.txt", 5);
    Assert.assertTrue(p.percolates());
  }
  
  @Test
  public void testInput50() throws Exception {
    Percolation p = setupTest("input50.txt", 50);
    Assert.assertTrue(p.percolates());
  }
  
  @Test
  public void testInput6() throws Exception {
    Percolation p = setupTest("input6.txt", 6);
    Assert.assertTrue(p.percolates());
  }
  
  @Test
  public void testInput7() throws Exception {
    Percolation p = setupTest("input7.txt", 7);
    Assert.assertTrue(p.percolates());
  }
  
  @Test
  public void testInput8() throws Exception {
    Percolation p = setupTest("input8.txt", 8);
    Assert.assertTrue(p.percolates());
  }
  
  @Test
  public void testJerry47() throws Exception {
    Percolation p = setupTest("jerry47.txt", 47);
    Assert.assertTrue(p.percolates());
  }
  
  @Test
  public void testWayne98() throws Exception {
    Percolation p = setupTest("wayne98.txt", 98);
    Assert.assertTrue(p.percolates());
  }
  
  @Test
  public void testHeart25() throws Exception {
    Percolation p = setupTest("heart25.txt", 25);
    Assert.assertFalse(p.percolates());
  }
  
  private void open(Percolation p, String fileName) throws IOException {
    Stream<String> stream = Files.lines(Paths.get(fileName));
    AtomicInteger count = new AtomicInteger();
    stream.forEach(line -> {
      if (count.get() > 0) {
        String s = line.trim();
        if (s.length() > 0) {
          String[] vals = s.split("\\s+");
          int row = Integer.parseInt(vals[0]);
          int col = Integer.parseInt(vals[1]);
          p.open(row, col);
        }
      }
      count.incrementAndGet();
    });
    
  }
  
  @Test
  public void testGreeting57() throws IOException {
    Percolation p = setupTest("greeting57.txt", 57);
    Assert.assertFalse(p.percolates());
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void testConstructorNLow() throws Exception {
    Percolation p = new Percolation(0);
  }
  
  @Test(expected=IndexOutOfBoundsException.class)
  public void testOpenLowRow() throws Exception {
    Percolation p = new Percolation(2);
    p.open(0, 1);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testOpenLowCol() throws Exception {
    Percolation p = new Percolation(2);
    p.open(1, 0);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testOpenHighRow() throws Exception {
    Percolation p = new Percolation(2);
    p.open(3, 1);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testOpenHighCol() throws Exception {
    Percolation p = new Percolation(2);
    p.open(1, 3);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testIsOpenLowRow() throws Exception {
    Percolation p = new Percolation(2);
    p.isOpen(0, 1);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testIsOpenLowCol() throws Exception {
    Percolation p = new Percolation(2);
    p.isOpen(1, 0);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testIsOpenHighRow() throws Exception {
    Percolation p = new Percolation(2);
    p.isOpen(3, 1);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testIsOpenHighCol() throws Exception {
    Percolation p = new Percolation(2);
    p.isOpen(1, 3);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testIsFullLowRow() throws Exception {
    Percolation p = new Percolation(2);
    p.isFull(0, 1);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testIsFullLowCol() throws Exception {
    Percolation p = new Percolation(2);
    p.isFull(1, 0);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testIsFullHighRow() throws Exception {
    Percolation p = new Percolation(2);
    p.isFull(3, 1);
  }

  @Test(expected=IndexOutOfBoundsException.class)
  public void testIsFullHighCol() throws Exception {
    Percolation p = new Percolation(2);
    p.isFull(1, 3);
  }
  
  private Percolation setupTest(String fileName, int n) throws IOException {
    Percolation p = new Percolation(n);
    open(p, rscPath + "percolation/" + fileName);
    return p;
  }
  
}
