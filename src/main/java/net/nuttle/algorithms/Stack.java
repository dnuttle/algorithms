package net.nuttle.algorithms;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<Item> implements Iterable<Item> {
  Node<Item> head;
  int n;
  public void push(Item item) {
    Node<Item> node = new Node<>(item);
    node.next = head;
    head = node;
    n++;
  }
  public Item pop() {
    if (head == null) throw new NoSuchElementException("stack is empty");
    Item item = head.value;
    head = head.next;
    n--;
    return item;
  }
  public boolean isEmpty() {
    return head == null;
  }
  public int size() {
    return n;
  }
  public Item peek() {
    if (head == null) throw new NoSuchElementException("stack is empty");
    return head.value;
  }
  @Override
  public Iterator<Item> iterator() {
    return new StackIterator(head);
  }
  private static class Node<Item> {
    Node<Item> next;
    Item value;
    public Node(Item value) {
      this.value = value;
    }
  }
  private class StackIterator implements Iterator<Item> {
    Node<Item> node;
    public StackIterator(Node<Item> first) {
      this.node = first;
    }
    public boolean hasNext() {
      return node != null;
    }
    public Item next() {
      Item item = node.value;
      node = node.next;
      return item;
    }
  }
}
