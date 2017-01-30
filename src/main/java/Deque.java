
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Node head;
  private Node tail;
  private int size = 0;
  
  public Deque() {
    
  }
  
  @Override
  public Iterator<Item> iterator() {
    Iterator<Item> it = new Iterator<Item>() {
      Node curr = null;
      int idx = 0;
      public boolean hasNext() {
        return idx < size;
      }
      
      public Item next() {
        Node next;
        if (curr == null) {
          next = head;
        } else {
          next = curr.next;
        }
        if (next == null) {
          throw new NoSuchElementException("No such element");
        }
        idx++;
        curr = next;
        return next.item;
      }
    };
    return it;
  }

  public boolean isEmpty() {
    return size() == 0;
  }
  
  public int size() {
    return size;
  }
  
  public void addFirst(Item item) {
    if (item == null) {
      throw new NullPointerException("item cannot be null");
    }
    if (head == null) {
      head = new Node(item);
      tail = head;
      size = 1;
      return;
    }
    Node n = new Node(item);
    n.next = head;
    head.prev = n;
    head = n;
    size++;
  }
  
  public void addLast(Item item) {
    if (item == null) {
      throw new NullPointerException("item cannot be null");
    }
    if (head == null) {
      head = new Node(item);
      tail = head;
      size = 1;
      return;
    }
    Node n = new Node(item);
    tail.next = n;
    n.prev = tail;
    tail = n;
    size++;
  }
  
  public Item removeFirst() {
    if (head == null) {
      throw new NoSuchElementException("No such element");
    }
    Node ret = head;
    Node n = head.next;
    head = n;
    size--;
    if (size == 0) {
      tail = null;
    }
    return ret.item;
  }
  
  public Item removeLast() {
    if (head == null) {
      throw new NoSuchElementException("No such element");
    }
    Node ret = tail;
    Node n = tail.prev;
    tail = n;
    size--;
    if (size == 0) {
      head = null;
    }
    return ret.item;
  }
  
  public static void main(String[] args) {
    Deque<String> d = new Deque<>();
    d.addFirst("abc");
    d.addFirst("def");
    d.addFirst("ghi");
    d.addLast("last");
    Iterator<String> it = d.iterator();
    while (it.hasNext()) {
      System.out.println(it.next());
    }
  }
  
  private class Node {
    private Item item;
    private Node next;
    private Node prev;
    
    public Node(Item item) {
      this.item = item;
    }
  }
}
