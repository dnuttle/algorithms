package net.nuttle.algorithms;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class ArrayQueue<Item> implements Iterable<Item> {
  private Item[] items;
  private int size;
  private int head;
  private int tail;
  
  @SuppressWarnings("unchecked")
  public ArrayQueue() {
    items = (Item[]) new Object[1];
  }
  
  public static void main(String[] args) {
    ArrayQueue<String> q = new ArrayQueue<>();
    q.enqueue("a"); //[a]
    q.print();
    q.enqueue("b"); //[a b]
    q.print();
    q.enqueue("c"); //[a b c *]
    q.print();
    q.enqueue("d"); //[a b c d]
    q.print();
    q.dequeue();    //[* b c d]
    q.print();
    q.dequeue();    //[* * c d]
    q.print();
    q.enqueue("e"); //[e * c d]
    q.print();
    q.enqueue("f"); //[e f c d]
    q.print();
    q.dequeue();    //[e f * d]
    q.print();
    q.dequeue();    //[e f * *]
    q.print();
    q.dequeue();    //[* f]
    q.print();
    q.dequeue();    //[]
    q.print();
    q.enqueue("g");
    q.print();
    q.enqueue("h");
    q.print();
    q.dequeue();
    q.print();
  }
  
  private void print() {
    StringBuilder sb = new StringBuilder("[");
    String prefix = "";
    for (Item i : this) {
      sb.append(prefix).append(i);
      prefix = ", ";
    }
    sb.append("], [");
    prefix = "";
    for (int i = 0; i < items.length; i++) {
      if (items[i] == null) {
        sb.append(prefix).append("*");
      } else {
        sb.append(prefix).append(items[i]);
      }
      prefix = ", ";
    }
    sb.append("], length=").append(items.length);
    StdOut.println(sb.toString());
  }
  
  public boolean isEmpty() {
    return size == 0;
  }
  
  public int size() {
    return size;
  }
  
  public void enqueue(Item item) {
    if (item == null) {
      throw new NullPointerException("item must not be null");
    }
    if (size == items.length) {
      resize(2 * items.length);
    }
    if (size == 0) {
      head = 0;
      tail = 0;
    } else {
      if (head == items.length - 1) {
        head = 0;
      } else {
        head++;
      }
    }
    items[head] = item;
    size++;
  }
  
  public Item dequeue() {
    if (size == 0) {
      throw new NoSuchElementException("queue is empty");
    }
    Item item = items[tail];
    items[tail] = null;
    if (tail == items.length - 1) {
      tail = 0;
    } else {
      tail++;
    }
    size--;
    if (size > 0 && size <= items.length / 4) {
      resize(items.length / 2);
    }
    return item;
  }
  
  public Item sample() {
    if (size == 0) {
      throw new NoSuchElementException("queue is empty");
    }
    return null;
  }
  
  public Iterator<Item> iterator() {
    final int currInit = tail;
    return new Iterator<Item>() {
      int curr = currInit;
      int count = 0;
      public boolean hasNext() {
        return count < size;
      }
      public Item next() {
        if (count == size) {
          throw new NoSuchElementException("queue is empty");
        }
        Item item;
        if (curr < items.length) {
          item = items[curr];
          curr++;
        } else {
          item = items[0];
          curr = 1;
        }
        count++;
        return item;
      }
    };
  }
  
  @SuppressWarnings("unchecked")
  private void resize(int capacity) {
    Item[] tmp = (Item[]) new Object[capacity];
    if (tail > head) {
      for (int i = head; i < items.length; i++) {
        tmp[i - head] = items[i];
      }
      for (int i = 0; i < tail; i++) {
        tmp[i + head] = items[i];
      }
    } else {
      for (int i = tail; i < tail + size; i++) {
        tmp[i - tail] = items[i];
      }
    }
    head = size - 1;
    tail = 0;
    items = tmp;
  }

}
