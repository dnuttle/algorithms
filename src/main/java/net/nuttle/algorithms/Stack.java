package net.nuttle.algorithms;

public class Stack<Item> {

  Node head;

  public static void main(String[] args) {
    Stack<String> s = new Stack<>();
    long start = System.currentTimeMillis();
    int count = 0;
    s.push("start");
    for (int i = 0; i < 10000; i++) {
      s.push("abcdefghijklmmnopqrstuvwxyz0123456789" + i);
      s.pop();
      count++;
    }
    System.out.println("Done " + (System.currentTimeMillis() - start) + " " + count);
    System.out.println(s.head.value);
  }
  
  
  public void push(Item item) {
    Node n = new Node(item);
    n.next = head;
    head = n;
  }
  
  public Item pop() {
    Item item = head.value;
    head = head.next;
    head.next = null;
    return item;
  }
  private class Node {
    Node next;
    Item value;
    
    public Node(Item value) {
      this.value = value;
    }
    
  }
}
