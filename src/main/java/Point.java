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
    StdDraw.setPenRadius(0.002);
    StdDraw.setPenColor();
    StdDraw.point(x, y);
  }
  
  private void drawBig() {
    StdDraw.setPenRadius(0.015);
    StdDraw.setPenColor(Color.RED);
    StdDraw.point(x, y);
    StdDraw.setPenColor();
  }
  
  public void drawTo(Point that) {
    StdDraw.setPenRadius(0.001);
    StdDraw.setPenColor(Color.RED);
    StdDraw.line(x, y, that.x, that.y);
  }
  
  private void drawToBig(Point that) {
    StdDraw.setPenRadius(0.005);
    StdDraw.setPenColor(Color.RED);
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
      //Two points are identical
      return Double.NEGATIVE_INFINITY;
    }
    if (x == that.x) {
      //Two points have the same x value but different y values
      return Double.POSITIVE_INFINITY;
    }
    double slope = (double)(y - that.y) / (double)(x - that.x); 
    if (slope == 0) {
      return (1.0 - 1.0) / 1.0;
    }
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
      double slope1 = p.slopeTo(p1);
      double slope2 = p.slopeTo(p2);
      if (slope1 > slope2) {
        return 1;
      } else if (slope1 < slope2) {
        return -1;
      }
      return 0;
    }
  }
}
