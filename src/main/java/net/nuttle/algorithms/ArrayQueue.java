package net.nuttle.algorithms;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class ArrayQueue<Item> implements Iterable<Item> {
  private Item[] items;
  private int size;
  private int head;
  private int tail = 1;
  
  @SuppressWarnings("unchecked")
  public ArrayQueue() {
    items = (Item[]) new Object[1];
  }
  
  public static void main(String[] args) {
    RandomizedQueue<String> q = new RandomizedQueue<>();
    q.enqueue("a");
    q.enqueue("b");
    q.enqueue("c");
    q.enqueue("d");
    for (String s : q) {
      StdOut.println(s);
    }
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
    if (head < tail) {
      if (tail == items.length + 1) {
        tail = 0;
        items[items.length - 1] = item;
      } else {
        items[tail++ - 1] = item;
      }
    } else if (head == tail) {
      items[tail++] = item;
    } else {
      items[tail++] = item;
    }
    size++;
  }
  
  public Item dequeue() {
    if (size == 0) {
      throw new NoSuchElementException("queue is empty");
    }
    Item item;
    if (tail > 0) {
      item = items[tail - 1];
      items[tail - 1] = null;
      tail--;
    } else {
      item = items[items.length - 1];
      items[items.length - 1] = null;
      tail = items.length;
    }
    size--;
    if (size <= items.length / 4) {
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
    final int currInit = tail == 0 ? items.length - 1 : tail - 1;
    return new Iterator<Item>() {
      int curr = currInit;
      int count = 0;
      public boolean hasNext() {
        return count < size;
      }
      public Item next() {
        Item item;
        if (count == size) {
          throw new NoSuchElementException("queue is empty");
        }
        if (curr == 0) {
          item = items[items.length - 1];
        } else {
          item = items[curr - 1];
        }
        if (curr == 0) {
          curr = items.length;
        } else {
          curr--;
        }
        count++;
        return item;
      }
    };
  }
  
  @SuppressWarnings("unchecked")
  private void resize(int capacity) {
    Item[] tmp = (Item[]) new Object[capacity];
    if (head > tail) {
      for (int i = head; i < items.length; i++) {
        tmp[i - head] = items[i];
      }
      for (int i = 0; i < tail; i++) {
        tmp[i + head] = items[i];
      }
    } else {
      for (int i = 0; i < size; i++) {
        tmp[i] = items[i];
      }
    }
    head = 0;
    tail = size + 1;
    items = tmp;
  }

}
