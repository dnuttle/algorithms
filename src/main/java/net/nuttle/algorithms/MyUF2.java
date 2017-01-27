package net.nuttle.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Union-find that is guaranteed to have trees with a depth no greater than 2.
 * @author Dan
 *
 */
public class MyUF2 {

  private List<Node> nodes;
  
  public static void main(String[] args) {
    MyUF2 uf = new MyUF2(10);
    System.out.println(uf.nodes.size());
    uf.union(2, 9);
    uf.union(3, 4);
    uf.union(4, 9);
    uf.union(5, 6);
    uf.union(6, 5);
    uf.union(9, 7);
    System.out.println(uf.connected(2, 9));
    System.out.println(uf.connected(9, 2));
    
    System.out.println("done");
  }
  
  public MyUF2(int n) {
    nodes = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      Node node = new Node(i);
      nodes.add(node);
    }
  }
  
  public void union(int val1, int val2) {
    Node n1 = find(val1);
    if (n1.contains(val2)) {
      return;
    }
    Node n2 = find(val2);
    if (n2.contains(val1)) {
      return;
    }
    n1.addChild(n2);
    nodes.remove(n2);
  }
  
  public boolean connected(int val1, int val2) {
    if (val1 == val2) {
      return true;
    }
    Node n = find(val1);
    if (n.contains(val2)) {
      return true;
    }
    n = find(val2);
    if (n.contains(val1)) {
      return true;
    }
    return false;
  }
  
  public Node find(int val) {
    for (Node node : nodes) {
      if (node.contains(val)) {
        return node;
      }
    }
    //return null;
    throw new IndexOutOfBoundsException("Didn't find node " + val);
  }

  public static class Node {
    List<Node> children = new ArrayList<>();
    Node parent = null;
    private int val;
    
    public boolean contains(int val) {
      if (this.val == val) {
        return true;
      }
      for (Node n : children) {
        if (n.val == val || n.contains(val)) {
          return true;
        }
      }
      return false;
    }
    
    public Node(int val) {
      this.val = val;
    }

    public void setParent(Node n) {
      parent = n;
    }
    
    public Node getParent() {
      return parent;
    }
    
    public void addChild(int val) {
      Node n = new Node(val);
      children.add(n);
      n.setParent(this);
    }
    
    public void addChild(Node n) {
      children.add(n);
      n.setParent(this);
      for (Node child : n.children) {
        child.setParent(this);
      }
      children.addAll(n.children);
      n.children.clear();
    }
    
    public Node removeChild(int val) {
      for (int i = 0; i < children.size(); i++) {
        if (children.get(i).val == val) {
          Node n = children.remove(i);
          n.setParent(null);
          return n;
        }
      }
      return null;
    }
  }
}
