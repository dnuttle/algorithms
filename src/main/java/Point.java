import java.awt.Color;
import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

  private int x;
  private int y;
  
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public void draw() {
    StdDraw.setPenRadius(0.01);
    StdDraw.point(x, y);
  }
  
  private void drawBig() {
    StdDraw.setPenRadius(0.02);
    StdDraw.setPenColor(Color.RED);
    StdDraw.point(x, y);
    StdDraw.setPenColor();
  }
  
  public void drawTo(Point that) {
    StdDraw.setPenRadius();
    StdDraw.line(x, y, that.x, that.y);
  }
  
  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
  
  @Override
  public int compareTo(Point that) {
    if (y < that.y || (y == that.y && x < that.x)) {
      return -1;
    }
    if (y == that.y && x == that.x) {
      return 0;
    }
    return 1;
  }
  
  public double slopeTo(Point that) {
    if (compareTo(that) == 0) {
      //StdOut.println("NEG");
      return Double.NEGATIVE_INFINITY;
    }
    if (x == that.x) {
      //StdOut.println("POS");
      return Double.POSITIVE_INFINITY;
    }
    double slope = ((double)y - (double)that.y) / ((double)x - (double)that.x); 
    if (slope == 0) {
      return (1.0 - 1.0) / 1.0;
    }
    //StdOut.printf("y: %d-%d, x: %d-%d, slope:%.3f%n", y, that.y, x, that.x, slope);
    return slope;
  }
  
  public Comparator<Point> slopeOrder() {
    return new SlopeComparator(this);
  }
  
  private class SlopeComparator implements Comparator<Point> {
    
    Point p;
    
    public SlopeComparator(Point p) {
      this.p = p;
      //StdOut.println("Comparator point: " + p);
    }
    @Override
    public int compare(Point p1, Point p2) {
      //StdOut.println("p1: " + p1 + "(" + p.slopeTo(p1) + ") p2: " + p2 + "(" + p.slopeTo(p2) + ")");
      if (p.slopeTo(p1) > p.slopeTo(p2)) {
        return 1;
      } else if (p.slopeTo(p1) < p.slopeTo(p2)) {
        return -1;
      }
      return 0;
    }
  }
}