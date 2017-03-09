package net.nuttle.algorithms;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class GrahamScan {

  public static void main(String[] args) {
    StdDraw.setScale(0.0, 20.0);
    StdDraw.setPenRadius(0.007);
    //Need to find some data to test this
  }
  
  public static void scan(Point2D[] points) {
    Point2D p1 = new Point2D(10.0, 1.0);
    Point2D p2 = new Point2D(9.0, 10.0);
    p1.draw();
    p2.draw();
    double theta = angle(p1, p2);
    StdOut.println("Theta: " + theta * (180 / Math.PI));
  }
  
  private static double angle(Point2D p1, Point2D p2) {
    double yDelta = p2.y() - p1.y();
    double xDelta = p2.x() - p1.x();
    double hypotenuse = Math.sqrt(Math.pow(yDelta, 2) + Math.pow(xDelta, 2));
    StdOut.println(xDelta / hypotenuse);
    return Math.acos(xDelta / hypotenuse);
  }
}
