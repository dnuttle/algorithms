package net.nuttle.algorithms;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Using linked list is not efficient.
 * @author Dan
 *
 * @param <Item>
 */
public class RandomizedQueueLL<Item> implements Iterable<Item> {

  private int size;
  private Node<Item> head;
  private Node<Item> tail;
  
  public RandomizedQueueLL() {}
  
  public static void main(String[] args) {
    RandomizedQueueLL<String> q = new RandomizedQueueLL<>();
    q.enqueue("abc");
    q.enqueue("def");
    q.enqueue("ghi");
    StdOut.println(q.dequeue());
    StdOut.println(q.dequeue());
    StdOut.println(q.dequeue());
    StdOut.println(q.size());
    
    q.enqueue("abc");
    q.enqueue("def");
    q.enqueue("ghi");
    Iterator<String> it = q.iterator();
    while (it.hasNext()) {
      StdOut.println(it.next());
    }
    StdOut.println(q.size());
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
    Node<Item> n = new Node<>(item);
    if (head == null) {
      head = n;
      tail = n;
      size = 1;
    } else {
      n.setPrev(tail);
      tail.setNext(n);
      tail = n;
      size++;
    }
  }
  
  public Item dequeue() {
    if (size == 0) {
      throw new NoSuchElementException("queue is empty");
    }
    int idx = StdRandom.uniform(size);
    Node<Item> curr;
    if (size >= 10 && idx > size / 2) {
      curr = tail;
      for (int i = 0; i < idx; i++) {
        curr = curr.getPrev();
      }
    } else {
      curr = head;
      for (int i = 0; i < idx; i++) {
        curr = curr.getNext();
      }
    }
    if (curr.getPrev() != null) {
      curr.getPrev().setNext(curr.getNext());
    }
    if (curr.getNext() != null) {
      curr.getNext().setPrev(curr.getPrev());
    }
    if (curr == head) {
      head = curr.getNext();
    }
    if (curr == tail) {
      tail = curr.getPrev();
    }
    curr.setNext(null);
    curr.setPrev(null);
    size--;
    return curr.getItem();
  }
  
  public Item sample() {
    if (size == 0) {
      throw new NoSuchElementException("queue is empty");
    }
    int idx = StdRandom.uniform(size);
    Node<Item> curr;
    if (idx < size / 2) {
      curr = head;
      for (int i = 0; i < idx; i++) {
        curr = curr.getNext();
      }
    } else {
      curr = tail;
      for (int i = 0; i < idx; i++) {
        curr = curr.getPrev();
      }
    }
    return curr.getItem();
  }
  
  public Iterator<Item> iterator() {
    int[] indexes = new int[size];
    for (int i = 0; i < size; i++) {
      indexes[i] = i;
    }
    StdRandom.shuffle(indexes);
    Iterator<Item> it = new Iterator<Item>() {
      
      //The index in indexes
      int currIdx = 0;
      //Points at a particular item in the queue
      int currNodeIdx = 0;
      Node<Item> currNode = head;
      @Override
      public boolean hasNext() {
        return currIdx < size;
      }
      
      @Override
      public Item next() {
        //System.out.println("Start currIdx: " + currIdx + " size: " + size);
        if (currIdx == size) {
          throw new NoSuchElementException("iterator exhaused");
        }
        int distance = Math.abs(currNodeIdx - indexes[currIdx]);
        //System.out.println("currNodeIdx: " + currNodeIdx + " indexes[currIdx]: " + indexes[currIdx] + " distance: " + distance);
        int startIdx;
        boolean forward = true;
        if (distance > size / 2) {
          //Start from head
          startIdx = 0;
          currNode = head;
          distance = indexes[currIdx];
        } else {
          startIdx = currNodeIdx;
          if (currNodeIdx > indexes[currIdx]) {
            forward = false;
          }
        }
        //System.out.println("startIdx: " + startIdx + " distance: " + distance);
        for (int i = 0; i < distance; i++) {
          if (forward) {
            //System.out.println("forward");
            //System.out.println("i: " + i);
            //System.out.println("Item: " + currNode.getItem());
            currNode = currNode.getNext();
          } else {
            //System.out.println("backward");
            currNode = currNode.getPrev();  
          }
        }
        currNodeIdx = indexes[currIdx];
        currIdx++;
        return currNode.getItem();
      }
    };
    return it;
  }
  
  private static class Node<Item> {
    private Item item;
    private Node<Item> next;
    private Node<Item> prev;
    
    public Node(Item item) {
      this.item = item;
    }
    
    public Item getItem() {
      return item;
    }
    
    public void setNext(Node<Item> next) {
      this.next = next;
    }
    
    public Node<Item> getNext() {
      return next;
    }
    
    public void setPrev(Node<Item> prev) {
      this.prev = prev;
    }
    
    public Node<Item> getPrev() {
      return prev;
    }
  }

}
