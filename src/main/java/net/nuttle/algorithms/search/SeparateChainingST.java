package net.nuttle.algorithms.search;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Symbol table using separate chaining
 * @author Dan
 *
 */
public class SeparateChainingST<K, V> {
  private int m;
  private Node<K, V>[] table;
  
  public static void main(String[] args) {
    int m = 100;
    int n = 100000;
    SeparateChainingST<String, String> table = new SeparateChainingST<>(m);
    for (int i = 0; i < n; i++) {
      StringBuilder rnd = new StringBuilder();
      for (int j = 0; j < 16; j++) {
        char c = (char) (32 + StdRandom.uniform(95));
        rnd.append(c);
      }
      //StdOut.println(rnd.toString());
      String s = "Some string value " + i;
      table.put(s, s);
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < m; i++) {
      if (table.table[i] == null) {
        sb.append("0 ");
      } else {
        int count = 0;
        Node<String, String> curr = table.table[i];
        while (curr != null) {
          count++;
          curr = curr.getNext();
        }
        sb.append(count).append(" ");
      }
    }
    StdOut.println(sb.toString());
  }
  
  /**
   * Naive implementation: Must specify size of hash table, and it doesn't change.
   * @param m
   */
  @SuppressWarnings("unchecked")
  public SeparateChainingST(int m) {
    this.m = m;
    table = new Node[m];
  }
  
  private int hash(K key) {
    return (key.hashCode() & 0x7FFFFFFF) % m;
  }
  
  public void put(K key, V value) {
    int hash = hash(key);
    Node<K, V> n = new Node<>(key, value);
    if (table[hash] == null) {
      table[hash] = n;
    } else {
      n.setNext(table[hash]);
      table[hash] = n;
    }
  }
  
  public V get(K key) {
    int hash = hash(key);
    Node<K, V> curr = table[hash];
    while (curr != null && !curr.getKey().equals(key)) {
      curr = curr.getNext();
    }
    if (curr != null) return curr.getValue();
    return null;
  }
  
  private static class Node<K, V> {
    K key;
    V value;
    Node<K, V> next;
    public Node(K key, V value) {
      this.key = key;
      this.value = value;
    }
    public K getKey() {
      return key;
    }
    public V getValue() {
      return value;
    }
    public void setNext(Node<K, V> n) {
      this.next = n;
    }
    public Node<K, V> getNext() {
      return this.next;
    }
  }
}
