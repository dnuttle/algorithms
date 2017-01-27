package net.nuttle.algorithms;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

public class DequeTest {

  @Test
  public void testIterator() {
    Deque<String> d = new Deque<>();
    Iterator<String> it = d.iterator();
    Assert.assertFalse(it.hasNext());
    d.addLast("end");
    d.addFirst("middle");
    d.addFirst("start");
    it = d.iterator();
    Assert.assertEquals("start", it.next());
    Assert.assertEquals("middle", it.next());
    Assert.assertEquals("end", it.next());
    Assert.assertFalse(it.hasNext());
  }
  
  @Test(expected=NullPointerException.class) 
  public void testAddFirstNull() {
    Deque<String> d = new Deque<>();
    d.addFirst(null);
  }

  @Test(expected=NullPointerException.class) 
  public void testAddLastNull() {
    Deque<String> d = new Deque<>();
    d.addLast(null);
  }
  
  @Test(expected=NoSuchElementException.class)
  public void testRemoveFirstFromEmptyCase1() {
    Deque<String> d = new Deque<>();
    d.removeFirst();
  }
  
  @Test(expected=NoSuchElementException.class)
  public void testRemoveFirstFromEmptyCase2() {
    Deque<String> d = new Deque<>();
    d.addFirst("abc");
    d.removeFirst();
    d.removeFirst();
  }
  
  @Test(expected=NoSuchElementException.class)
  public void testRemoveLastFromEmptyCase1() {
    Deque<String> d = new Deque<>();
    d.removeLast();
  }
  
  @Test(expected=NoSuchElementException.class)
  public void testRemoveLastFromEmptyCas2() {
    Deque<String> d = new Deque<>();
    d.addFirst("abc");
    d.removeLast();
    d.removeLast();
  }
  
  @Test(expected=UnsupportedOperationException.class)
  public void testIteratorRemove() {
    Deque<String> d = new Deque<>();
    d.addFirst("abc");
    Iterator<String> it = d.iterator();
    it.remove();
  }
  
  @Test(expected=NoSuchElementException.class)
  public void testNextEmptyIterator() {
    Deque<String> d = new Deque<>();
    Iterator<String> it = d.iterator();
    it.next();
  }
  
  @Test
  public void testSize() {
    Deque<String> d = new Deque<>();
    Assert.assertEquals(0, d.size());
    d.addFirst("abc");
    Assert.assertEquals(1,  d.size());
    d.addFirst("def");
    d.addFirst("ghi");
    Assert.assertEquals(3, d.size());
    d.removeFirst();
    d.removeFirst();
    Assert.assertEquals(1,  d.size());
    d.removeFirst();
    Assert.assertEquals(0, d.size());
  }
}
