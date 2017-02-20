package net.nuttle.algorithms.search;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class SymbolTable<K extends Comparable<K>, V> {
  
  private Node root;
  

  public static void main(String[] args) {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    tbl.put("f", "f");
    tbl.put("q", "q");
    tbl.put("r", "r");
    tbl.put("l", "l");
    tbl.put("s", "s");
    tbl.put("d", "d");
    tbl.put("a", "a");
    tbl.put("g", "g");
    for (String key : tbl.keys()) {
      StdOut.println(key);
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
    int cmp = x.key.compareTo(key);
    if (cmp < 0) return get(x.left, key);
    else if (cmp > 0) return get(x.right, key);
    else return x.value;
  }
  
  public void deleteMin(K key) {
    root = deleteMin(root);
  }
  
  public Node deleteMin(Node x) {
    if (x.left == null) return x.right;
    x = deleteMin(x.left);
    x.n = 1 + size(x.left) + size(x.right);
    return x;
  }
  
  public void delete(K key) {
    root = delete(root, key);
  }
  
  private Node delete(Node x, K key) {
    if (x == null) return null;
    int cmp = key.compareTo(x.key);
    if (cmp < 0) x.left = delete(x.left, key);
    else if (cmp > 0) x.right = delete(x.right, key);
    else {
      if (x.right == null) return x.left;
      if (x.left == null) return x.right;
      Node t = x;
      x = min(t.right);
      x.right = deleteMin(t.right);
      x.left = t.left;
    }
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
    int cmp = x.key.compareTo(key);
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
  
  private Node maxList(Node x, List<K> keys) {
    if (x.right == null) return x;
    keys.add(x.key);
    return max(x.right);
  }
  
  private Node minList(Node x, List<K> keys) {
    if (x.left == null) return x;
    keys.add(x.key);
    return max(x.left);
  }

  private Iterable<K> keys() {
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

}
