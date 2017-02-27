import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;


public class KdTree {

  private SET<Point2D> points;

  public static void main(String[] args) {
    
  }
  
  public KdTree() {
    points = new SET<>();
  }

  public boolean isEmpty() {
    return points.size() == 0;
  }
  
  public int size() {
    return points.size();
  }
  
  public void insert(Point2D p) {
    if (p == null) throw new NullPointerException("p must not be null");
    points.add(p);
  }
  
  public boolean contains(Point2D p) {
    if (p == null) throw new NullPointerException("p must not be null");
    return points.contains(p);
  }
  
  public void draw() {
    StdDraw.setPenRadius(0.004);
    for (Point2D point : points) {
      point.draw();
    }
  }
  
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) throw new NullPointerException("rect must not be null");
    List<Point2D> list = new ArrayList<>();
    for (Point2D p : points) {
      if (rect.contains(p)) {
        list.add(p);
      }
    }
    return list;
  }
  
  public Point2D nearest(Point2D p) {
    if (p == null) throw new NullPointerException("p must not be null");
    Point2D nearest = null;
    double min = Double.MAX_VALUE;
    if (points.size() == 0) return null;
    for (Point2D point : points) {
      double dist = point.distanceTo(p);
      if (dist < min) {
        nearest = point;
        min = dist;
      }
    }
    return nearest;
  }
}
