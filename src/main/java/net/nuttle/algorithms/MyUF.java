package net.nuttle.algorithms;

import java.util.ArrayList;
import java.util.List;

public class MyUF {

  private int n;
  
  public static void main(String[] args) {
    MyUF uf = new MyUF(10);
    uf.union(2, 9);
    uf.union(3, 4);
    uf.union(4,  9);
    uf.union(5,  6);
    uf.union(6,  5);
    uf.union(9, 7);
    uf.fillOut();
    System.out.println(uf.toString());
  }
  
  List<Node> lists;

  public MyUF(int n) {
    this.n = n;
    lists = new ArrayList<>(n);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < lists.size(); i++) {
      Node n = lists.get(i);
      while (n != null) {
        sb.append(n.getId()).append(" ");
        n = n.getNext();
      }
      sb.append("\n");
    }
    return sb.toString();
  }
  
  public void fillOut() {
    for (int i = 0; i < n; i++) {
      findNode(i);
    }
  }
  
  public boolean connected(int p, int q) {
    Node n1 = findNode(p);
    return n1.contains(q);
  }
  
  private Node findNode(int p) {
    for (int i = 0; i < lists.size(); i++) {
      Node n = lists.get(i);
      if (n.contains(p)) {
        return n;
      }
    }
    Node n = new Node(p);
    lists.add(n);
    return n;
  }
  
  public void union(int p, int q) {
    Node np = findNode(p);
    if (np.contains(q)) {
      return;
    }
    Node nq = findNode(q);
    lists.remove(nq.getHead());
    np.append(nq);
  }
  
  public static class Node {
    private Node next;
    private Node prev;
    private int id;
    
    public Node(int id) {
      this.id = id;
    }
    
    public void setNext(Node next) {
      this.next = next;
    }
    
    public void setPrev(Node prev) {
      this.prev = prev;
    }
    
    public Node getNext() {
      return next;
    }
    
    public Node getPrev() {
      return prev;
    }
    
    public boolean contains(int id) {
      Node n = this.getHead();
      while (n != null) {
        if (n.id == id) {
          return true;
        }
        n = n.getNext();
      }
      return false;
    }
    
    public int getId() {
      return id;
    }
    
    public Node getTail() {
      Node tail = this;
      while (tail.getNext() != null) {
        tail = tail.getNext();
      }
      return tail;
    }
    
    public Node getHead() {
      Node head = this;
      while (head.getPrev() != null) {
        head = head.getPrev();
      }
      return head;
    }
    
    public void append(Node n) {
      this.getTail().setNext(n);
      n.getHead().setPrev(this);
    }
  }
}
