package net.nuttle.algorithms.search;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class SymbolTableTest {

  @Test
  public void testPutGet() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    tbl.put("a", "vala");
    tbl.put("b", "valb");
    tbl.put("c", "valc");
    Assert.assertEquals("vala", tbl.get("a"));
    Assert.assertEquals("valb", tbl.get("b"));
    Assert.assertEquals("valc", tbl.get("c"));
    Assert.assertNull(tbl.get("d"));
  }
  
  @Test
  public void testDelete() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    tbl.put("a", "vala");
    tbl.put("b", "valb");
    tbl.put("c", "valc");
    tbl.delete("d");
    Assert.assertEquals(3,  tbl.size());
    tbl.delete("b");
    Assert.assertEquals(2, tbl.size());
    tbl.delete("a");
    Assert.assertEquals(1,  tbl.size());
    Assert.assertEquals("c", tbl.min());
  }
  
  @Test
  public void testDeleteMin() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    tbl.put("a", "vala");
    tbl.put("c", "valc");
    tbl.put("e", "e");
    tbl.deleteMin("b");
    Assert.assertEquals("c", tbl.min());
    tbl.delete("e");
    Assert.assertEquals(1, tbl.size());
    Assert.assertEquals("c", tbl.min());
  }
  
  @Test
  public void testRank() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    tbl.put("a", "vala");
    tbl.put("b", "valb");
    tbl.put("c", "valc");
    Assert.assertEquals(0,  tbl.rank("a"));
    Assert.assertEquals(1, tbl.rank("b"));
    Assert.assertEquals(2, tbl.rank("c"));
    Assert.assertEquals(3, tbl.rank("d"));
    Assert.assertEquals(3, tbl.rank("z"));
  }
  
  @Test
  public void testFloor() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    tbl.put("a", "vala");
    tbl.put("b", "valb");
    tbl.put("d", "vald");
    Assert.assertEquals("a", tbl.floor("a"));
    Assert.assertEquals("b", tbl.floor("b"));
    Assert.assertEquals("b", tbl.floor("c"));
    Assert.assertEquals("d", tbl.floor("d"));
  }
 
  @Test
  public void testMin() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    tbl.put("a", "vala");
    tbl.put("b", "valb");
    tbl.put("c", "valc");
    Assert.assertEquals("a", tbl.min());
    tbl.delete("a");
    Assert.assertEquals("b",  tbl.min());
  }

  @Test
  public void testMax() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    tbl.put("a", "vala");
    tbl.put("b", "valb");
    tbl.put("c", "valc");
    Assert.assertEquals("c", tbl.max());
    tbl.delete("c");
    Assert.assertEquals("b",  tbl.max());
  }

  @Test(expected=NullPointerException.class)
  public void testMinNull() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    tbl.min();
  }
  
  @Test(expected=NullPointerException.class)
  public void testMaxNull() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    tbl.max();
  }
  
  @Test
  public void testSize() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    Assert.assertEquals(0, tbl.size());
    tbl.put("a", "vala");
    Assert.assertEquals(1, tbl.size());
    tbl.put("b", "valb");
    Assert.assertEquals(2, tbl.size());
    tbl.delete("a");
    Assert.assertEquals(1, tbl.size());
    tbl.delete("b");
    Assert.assertEquals(0, tbl.size());
  }
  
  @Test
  public void testIsEmpty() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    Assert.assertTrue(tbl.isEmpty());
    tbl.put("a", "a");
    Assert.assertFalse(tbl.isEmpty());
    tbl.put("b", "b");
    tbl.delete("a");
    Assert.assertFalse(tbl.isEmpty());
    tbl.delete("b");
    Assert.assertTrue(tbl.isEmpty());
  }
  
  @Test
  public void testKeys() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    tbl.put("g", "g");
    tbl.put("a", "a");
    tbl.put("z", "z");
    tbl.put("d", "d");
    tbl.put("c", "c");
    List<String> l = (List<String>) tbl.<String>keys();
    Assert.assertEquals(5,  tbl.size());
    Assert.assertTrue(l.get(0).equals("a"));
    Assert.assertTrue(l.get(1).equals("c"));
    Assert.assertTrue(l.get(2).equals("d"));
    Assert.assertTrue(l.get(3).equals("g"));
    Assert.assertTrue(l.get(4).equals("z"));
  }

  @Test
  public void testContains() {
    SymbolTable<String, String> tbl = new SymbolTable<>();
    Assert.assertFalse(tbl.contains("a"));
    tbl.put("a", "vala");
    Assert.assertTrue(tbl.contains("a"));
    Assert.assertFalse(tbl.contains("b"));
    tbl.put("b", "valb");
    Assert.assertTrue(tbl.contains("b"));
    tbl.delete("b");
    Assert.assertFalse(tbl.contains("b"));
  }

}
