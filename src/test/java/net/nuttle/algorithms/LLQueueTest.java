package net.nuttle.algorithms;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

public class LLQueueTest {

  @Test
  public void test() {
    LLQueue<Integer> queue = new LLQueue<>();
    Assert.assertTrue(queue.isEmpty());
    Assert.assertEquals(0, queue.size());
    queue.enqueue(1);
    queue.enqueue(2);
    Assert.assertEquals(2, queue.size());
    Assert.assertEquals(1,  (int) queue.dequeue()); 
    Assert.assertEquals(2,  (int) queue.dequeue()); 
    Assert.assertTrue(queue.isEmpty());
    Assert.assertEquals(0, queue.size());
  }
  
  @Test(expected=NoSuchElementException.class)
  public void dequeueEmpty() {
    LLQueue<Integer> queue = new LLQueue<>();
    queue.dequeue();
  }
  
  @Test
  public void testIterator() {
    LLQueue<Integer> queue = new LLQueue<>();
    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);
    Iterator<Integer> it = queue.iterator();
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(1, (int) it.next());
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(2, (int) it.next());
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(3, (int) it.next());
    Assert.assertFalse(it.hasNext());
  }
  
}
