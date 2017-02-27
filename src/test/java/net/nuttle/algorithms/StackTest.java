package net.nuttle.algorithms;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;


public class StackTest {

  @Test
  public void test() {
    Stack<Integer> stack = new Stack<>();
    stack.push(1);
    stack.push(2);
    stack.pop();
    stack.push(3);
    stack.push(4);
    stack.pop();
    stack.push(5);
    Assert.assertEquals(3, stack.size());
    Assert.assertEquals(5, (int) stack.pop());
    Assert.assertEquals(2, stack.size());
    Assert.assertEquals(3, (int) stack.pop());
    Assert.assertEquals(1, stack.size());
    Assert.assertEquals(1, (int) stack.pop());
    Assert.assertEquals(0, stack.size());
    Assert.assertTrue(stack.isEmpty());
  }
  
  @Test(expected=NoSuchElementException.class) 
  public void testPopEmpty() {
    Stack<Integer> stack = new Stack<>();
    stack.pop();
  }
  
  @Test
  public void testIterator() {
    Stack<Integer> stack = new Stack<>();
    stack.push(1);
    stack.push(2);
    stack.push(3);
    Iterator<Integer> it = stack.iterator();
    Assert.assertEquals(3, (int) it.next());
    Assert.assertEquals(2, (int) it.next());
    Assert.assertEquals(1, (int) it.next());
    Assert.assertFalse(it.hasNext());
  }
  
  @Test
  public void testPeek() {
    Stack<Integer> stack = new Stack<>();
    stack.push(1);
    Assert.assertEquals(1,  (int) stack.peek());
    stack.push(2);
    Assert.assertEquals(2,  (int) stack.peek());
    stack.push(3);
    Assert.assertEquals(3,  (int) stack.peek());
  }
  
  @Test(expected=NoSuchElementException.class) 
  public void testPeekEmpty() {
    Stack<Integer> stack = new Stack<>();
    stack.peek();
  }
  
}
