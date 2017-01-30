
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] items;
  private int size;
  private int head;
  private int tail;
  
  @SuppressWarnings("unchecked")
  public RandomizedQueue() {
    items = (Item[]) new Object[1];
  }
  
  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();
    for (int i = 0; i < 1000; i++) {
      RandomizedQueue<String> q = new RandomizedQueue<>();
      /*
      q.print();
      q.enqueue("a"); //[a]
      q.print();
      q.enqueue("b"); //[a b]
      q.print();
      q.enqueue("c"); //[a b c *]
      q.print();
      q.enqueue("d"); //[a b c d]
      q.print();
      q.dequeue();    //[* b c d]
      q.print();
      q.dequeue();    //[* * c d]
      q.print();
      q.enqueue("e"); //[e * c d]
      q.print();
      q.enqueue("f"); //[e f c d]
      q.print();
      q.dequeue();    //[e f * d]
      q.print();
      q.dequeue();    //[e f * *]
      q.print();
      q.dequeue();    //[* f]
      q.print();
      q.dequeue();    //[]
      q.print();
      q.enqueue("g"); //[g]
      q.print();
      q.enqueue("h"); //[g h]
      q.print();
      q.dequeue();    //[* h]
      q.print();
      */
      int size = 4000;
      for (int j = 0; j < size; j++) {
        q.enqueue("a");
      }
      if (q.size() != size) {
        System.out.println("SIZE: " + q.size());
      }
      for (int j = 0; j < size; j++) {
        q.dequeue();
        if (q.size() != size - j - 1) {
          throw new Exception("Wrong size");
        }
      }
      if (q.size() != 0) {
        System.out.println("SIZE: " + q.size());
      }
      for (int j = 0; j < size; j++) {
        q.enqueue("a");
      }
      if (q.size() != size) {
        System.out.println("SIZE: " + q.size());
      }
      for (String s: q) {
        
      }
    }
    StdOut.println(System.currentTimeMillis() - start);
  }
  
  private void sample10() {
    if (size == 0) {
      return;
    }
    for (int i = 0; i < 10; i++) {
      StdOut.print(sample() + " ");
    }
    StdOut.print("\n");
  }
  
  private void print() {
    if (true) return;
    StringBuilder sb = new StringBuilder("[");
    String prefix = "";
    for (Item i : this) {
      sb.append(prefix).append(i);
      prefix = ", ";
    }
    sb.append("], [");
    prefix = "";
    for (int i = 0; i < items.length; i++) {
      if (items[i] == null) {
        sb.append(prefix).append("*");
      } else {
        sb.append(prefix);
        if (i == head) {
          sb.append("{h}");
        }
        if (i == tail) {
          sb.append("{t}");
        }
        sb.append(items[i]);
      }
      prefix = ", ";
    }
    sb.append("]");
    StdOut.println(sb.toString());
    sample10();
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
    if (size == items.length) {
      resize(2 * items.length);
    }
    if (size == 0) {
      head = 0;
      tail = 0;
    } else {
      if (head == items.length - 1) {
        head = 0;
      } else {
        head++;
      }
    }
    items[head] = item;
    size++;
  }
  
  public Item dequeue() {
    if (size == 0) {
      throw new NoSuchElementException("queue is empty");
    }

    Item item;
    int index = StdRandom.uniform(size);
    if (tail <= head) {
      item = items[tail + index];
      for (int i = tail + index; i < head; i++) {
        items[i] = items[i + 1];
      }
      items[head] = null;
      head--;
    } else {
      //tail is to the right of head
      if (index >= items.length - tail) {
        //Index is to the left of tail
        item = items[index - (items.length - tail)];
        if (head == 0) {
          items[head] = null;
          head = tail;
        } else {
          for (int i = index - (items.length - tail); i < head; i++) {
            items[i] = items[i + 1];
          }
          items[head] = null;
          head--;
        }
      } else {
        //Index is to the right of tail
        item = items[tail + index];
        for (int i = items.length - 1; i >= tail + index; i--) {
          items[i] = items[i-1];
        }
        if (size > index) {
          items[items.length] = items[0];
          for (int i = 0; i < head; i++) {
            items[i] = items[i + 1];
          }
        }
        if (head == 0) {
          head = tail;
        } else {
          head--;
        }
      }
    }
    size--;
    
    
    if (size > 0 && size <= items.length / 4) {
      resize(items.length / 2);
    }
    return item;
  }
  
  public Item sample() {
    if (size == 0) {
      throw new NoSuchElementException("queue is empty");
    }
    int index = StdRandom.uniform(size);
    if (tail <= head) {
      return items[tail + index];
    } else {
      if (index >= items.length - tail) {
        return items[index - (items.length - tail)];
      } else {
        return items[tail + index];
      }
    }
  }
  
  private Iterator<Item> iterator2() {
    return new Iterator<Item>() {
      int curr = tail;
      int count = 0;
      public boolean hasNext() {
        return count < size;
      }
      public Item next() {
        if (count == size) {
          throw new NoSuchElementException("queue is empty");
        }
        Item item;
        if (curr < items.length) {
          item = items[curr];
          curr++;
        } else {
          item = items[0];
          curr = 1;
        }
        count++;
        return item;
      }
    };
  }
  
  public Iterator<Item> iterator() {
    return new ItemIterator();
  }
  
  @SuppressWarnings("unchecked")
  private void resize(int capacity) {
    Item[] tmp = (Item[]) new Object[capacity];
    if (tail > head) {
      for (int i = head; i < items.length; i++) {
        tmp[i - head] = items[i];
      }
      for (int i = 0; i < tail; i++) {
        tmp[i + head] = items[i];
      }
    } else {
      for (int i = tail; i < tail + size; i++) {
        tmp[i - tail] = items[i];
      }
    }
    head = size - 1;
    tail = 0;
    items = tmp;
  }
  
  private class ItemIterator implements Iterator<Item> {
    int[] indexes;
    int curr;
    int count;
    public ItemIterator() {
      if (size == 0) {
        return;
      }
      indexes = new int[size];
      if (tail <= head) {
        for (int i = tail; i <= head; i++) {
          indexes[i - tail] = i;
        }
      } else {
        int idx = 0;
        for (int i = tail; i < items.length; i++) {
          indexes[i - tail] = i;
          idx++;
        }
        for (int i = 0; i <= head; i++) {
          indexes[i + idx] = i;
        }
      }
      StdRandom.shuffle(indexes);
    }
    @Override
    public boolean hasNext() {
      return count < size;
    }
    @Override
    public Item next() {
      if (count == size) {
        throw new NoSuchElementException("queue is empty");
      }
      Item item = items[indexes[curr]];
      curr++;
      count++;
      return item;
    }
  }

}
