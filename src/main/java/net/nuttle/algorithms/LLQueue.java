package net.nuttle.algorithms;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LLQueue<Item> implements Iterable<Item> {

  private Node<Item> head;
  private Node<Item> tail;
  private int n;
  
  public void enqueue(Item item) {
    Node<Item> node = new Node<>(item);
    if (tail != null) tail.next = node;
    tail = node;
    if (head == null) head = tail;
    n++;
  }
  
  public Item dequeue() {
    Node<Item> node = head;
    if (node != null) {
      if (head == tail) {
        tail = node.next;
      }
      head = node.next;
      n--;
      return node.value;
    } else {
      throw new NoSuchElementException("queue is empty");
    }
  }
  
  public int size() {
    return n;
  }
  
  public boolean isEmpty() {
    return n == 0;
  }
  
  public Iterator<Item> iterator() {
    return new QueueIterator(head);
  }
  
  private static class Node<Item> {
    Node(Item item) {
      this.value = item;
    }
    private Item value;
    Node<Item> next;
  }
  
  private class QueueIterator implements Iterator<Item> {
    Node<Item> node;
    QueueIterator(Node<Item> head) {
      this.node = head;
    }
    @Override
    public boolean hasNext() {
      return node != null;
    }
    
    @Override
    public Item next() {
      Item item = node.value;
      node = node.next;
      return item;
    }
  }
}
