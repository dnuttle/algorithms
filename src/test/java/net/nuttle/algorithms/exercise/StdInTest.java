package net.nuttle.algorithms.exercise;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class StdInTest {
  
  private static final String RSC_PATH = System.getProperty("user.dir") + "/src/test/resources/";

  public static void main(String[] args) throws Exception {
    File f1 = new File(RSC_PATH, "test1.txt");
    readFile(f1);
    File f2 = new File(RSC_PATH, "test2.txt");
    readFiles(f1, f2);
  }

  /**
   * An example of redirecting file to standard input so that StdIn reads it.
   * @param f
   * @throws FileNotFoundException
   */
  private static void readFile(File f) throws FileNotFoundException, IOException {
    FileInputStream is = new FileInputStream(f);
    System.setIn(is);
    String all = StdIn.readAll();
    StdOut.println(all);
    is.close();
  }
  
  private static void readFiles(File...files) throws FileNotFoundException, IOException {
    StringBuilder sb = new StringBuilder();
    for (File file : files) {
      In in = new In(file);
      sb.append(in.readAll()).append("\n");
    }
    StdOut.println(sb.toString());
  }
}
