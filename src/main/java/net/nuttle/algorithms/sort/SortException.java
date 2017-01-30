package net.nuttle.algorithms.sort;

public class SortException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -5141890448013003990L;

  public SortException(String msg) {
    super(msg);
  }
  
  public SortException(String msg, Throwable t) {
    super(msg, t);
  }
}
