package net.nuttle.algorithms.search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Implementation of a symbol table using an unbalanced binary search tree.
 * It's all fun and games, so long as the puts are random, until the deletions start.
 * @author dnuttle
 *
 * @param <K> is the type of the keys
 * @param <V> is the type of the values
 */
public class SymbolTable<K extends Comparable<K>, V> {
  
  private Node root;
  

  public static void main(String[] args) throws Exception {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    tbl.put("f", "f");
    tbl.put("q", "q");
    tbl.put("r", "r");
    tbl.put("l", "l");
    tbl.put("s", "s");
    tbl.put("d", "d");
    tbl.put("a", "a");
    tbl.put("g", "g");
    tbl.put("a",  "last value set");
    for (String key : tbl.keys()) {
      StdOut.println(key);
    }
    StdOut.println(tbl.delete("a"));
    if (true) {
      return;
    }
    /**
     * Demonstrates how Hibbard deltion makes the tree unbalanced.  The ratio of nodes 
     * on one side to the other is consistently large after a large number of deletes,
     * even if only to delete a value and then put it right back.
     */
    for (int k = 0; k < 20; k++) {
      SymbolTable<Integer, Integer> st2 = new SymbolTable<>();
      for (int i = 0; i < 10000; i++) {
        int val = StdRandom.uniform(1000);
        st2.put(val, i);
      }
      for (int i = 0; i < 1000000; i++) {
        int val = StdRandom.uniform(1000);
        st2.delete(val);
        st2.put(val,  i);
      }
      if (st2.size(st2.root.right) == 0) {
        StdOut.println("Ratio: NaN");
      } else {
        double ratio = (double) st2.size(st2.root.left) / (double) st2.size(st2.root.right);
        if (ratio < 1) {
          ratio = 1.0 / ratio;
        }
        StdOut.println("Ratio: " + String.format("%.3f", ratio));
      }
    }
  }
  
  
  
  public void put(K key, V value) {
    root = put(root, key, value);
  }
  
  private Node put(Node x, K key, V value) {
    if (x == null) return new Node(key, value, 1);
    int cmp = key.compareTo(x.key);
    if (cmp < 0) x.left = put(x.left, key, value);
    else if (cmp > 0) x.right = put(x.right, key, value);
    else x.value = value;
    x.n = 1 + size(x.left) + size(x.right);
    return x;
  }
  
  public V get(K key) {
    return get(root, key);
  }
  
  private V get(Node x, K key) {
    if (x == null) return null;
    int cmp = key.compareTo(x.key);
    if (cmp < 0) return get(x.left, key);
    else if (cmp > 0) return get(x.right, key);
    else return x.value;
  }
  
  public int rank(K key) {
    return rank(key, root);
  }
  
  private int rank(K key, Node x) {
    if (x == null) return 0;
    int cmp = key.compareTo(x.key);
    if (cmp < 0) return rank(key, x.left);
    else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
    else return size(x.left);
  }
  
  public void deleteMin(K key) {
    root = deleteMin(root);
  }
  
  public Node deleteMin(Node x) {
    if (x.left == null) return x.right;
    x.left = deleteMin(x.left);
    x.n = 1 + size(x.left) + size(x.right);
    return x;
  }
  
  /**
   * My implementation differs from the book's in that it returns the value 
   * of the deleted node, if the node is found, otherwise returns null.
   * @param key
   * @return
   */
  public V delete(K key) {
    ValueWrapper av = new ValueWrapper();
    root = delete(root, key, av);
    return av.value;
  }
  
  private Node delete(Node x, K key, ValueWrapper av) {
    if (x == null) return null;
    int cmp = key.compareTo(x.key);
    if (cmp < 0) x.left = delete(x.left, key, av);
    else if (cmp > 0) x.right = delete(x.right, key, av);
    else {
      av.setValue(x.value);
      if (x.right == null) return x.left;
      if (x.left == null) return x.right;
      Node t = x;
      x = min(t.right);
      x.right = deleteMin(t.right);
      x.left = t.left;
    }
    x.n = size(x.left) + size(x.right) + 1; 
    return x;
  }
  
  public K min() {
    return min(root).key;
  }
  
  private Node min(Node x) {
    if (x.left == null) return x;
    return min(x.left);
  }
  
  public K max() {
    return max(root).key;
  }
  
  private Node max(Node x) {
    if (x.right == null) return x;
    return max(x.right);
  }
  
  public K floor(K key) {
    Node x= floor(root, key);
    if (x == null) return null;
    return x.key;
  }
  
  private Node floor(Node x, K key) {
    if (x == null) return null;
    int cmp = (key.compareTo(x.key));
    if (cmp == 0) return x;
    if (cmp < 0) return floor(x.left, key);
    Node t = floor(x.right, key);
    if (t != null) return t;
    else return x;
  }
  
  public boolean contains(K key) {
    return contains(root, key);
  }
  
  private boolean contains(Node x, K key)  {
    if (x == null)  return false;
    int cmp = key.compareTo(x.key);
    if (cmp < 0) return contains(x.left, key);
    else if (cmp > 0) return contains(x.right, key);
    else return true;
  }
  
  public boolean isEmpty() {
    return size(root) == 0;
  }
  
  public int size() {
    return size(root);
  }
  
  private int size(Node x) {
    if (x == null) return 0;
    return x.n;
  }
  
  public Iterable<K> keys() {
    List<K> keys = new ArrayList<>();
    keys(root, keys);
    return keys;
  }
  
  private Node keys(Node x, List<K> keys) {
    if (x == null) return null;
    keys(x.left, keys);
    keys.add(x.key);
    keys(x.right, keys);
    return x;
  }
  
  private class Node {
    Node left;
    Node right;
    K key;
    V value;
    int n;
    Node(K key, V value, int n) {
      this.key = key;
      this.value = value;
      this.n = n;
    }
  }
  
  private class ValueWrapper {
    private V value;
    public void setValue(V value) {
      this.value = value;
    }
  }

}
