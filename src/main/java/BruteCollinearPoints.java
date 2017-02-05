import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

  private LineSegment[] segments;
  private List<LineSegment> segmentList = new ArrayList<>();
  
  public BruteCollinearPoints(Point[] points) {
    if (points == null) {
      throw new NullPointerException("points array must not be null");
    }
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) {
        throw new NullPointerException("elements of points array must not be null");
      }
      //points[i].draw();
      //StdOut.println(points[i].toString());
    }
    for (int i = 0; i < points.length - 3; i++) {
      for (int j = i + 1; j < points.length - 2; j++) {
        if (points[i].compareTo(points[j]) == 0) {
          throw new IllegalArgumentException("points cannot be duplicates");
        }
        for (int k = j + 1; k < points.length - 1; k++) {
          for (int l = k + 1; l < points.length; l++) {
            double slope = points[i].slopeTo(points[j]);
            if (points[j].slopeTo(points[k]) == slope && 
                points[k].slopeTo(points[l]) == slope) {
              /*
              StdOut.println("i: " + points[i].toString());
              StdOut.println("j: " + points[j].toString() + ", " + points[i].slopeTo(points[j]));
              StdOut.println("k: " + points[k].toString() + ", " + points[j].slopeTo(points[k]));
              StdOut.println("l: " + points[l].toString() + ", " + points[k].slopeTo(points[l]));
              StdOut.println("Slope: " + slope);
              */
              addSegment(points[i], points[j], points[k], points[l]);
            }
          }
        }
      }
    }
    this.segments = segmentList.toArray(new LineSegment[0]);
    segmentList = null;
  }
  
  private void addSegment(Point i, Point j, Point k, Point l) {
    Point min = i;
    if (j.compareTo(min) < 0) {
      min = j;
    }
    if (l.compareTo(min) < 0) {
      min = l;
    }
    if (k.compareTo(min) < 0) {
      min = k;
    }
    Point max = i;
    if (j.compareTo(max) > 0) {
      max = j;
    }
    if (k.compareTo(max) > 0) {
      max = k;
    }
    if (l.compareTo(max) > 0) {
      max = l;
    }
    LineSegment ls = new LineSegment(min, max);
    for (LineSegment curr : segmentList) {
      if (curr.equals(ls)) {
        return;
      }
    }
    segmentList.add(ls);
  }
  
  public static void main(String[] args) {
    StdDraw.setScale(0, 50000);
    int[] values = StdIn.readAllInts();
    int n = values[0];
    List<Point> points = new ArrayList<>();
    for (int i = 1; i <= n*2; i += 2) {
      points.add(new Point(values[i], values[i+1]));
    }
    BruteCollinearPoints bcp = new BruteCollinearPoints(points.toArray(new Point[0]));
    StdOut.println("Line segments found: " + bcp.numberOfSegments());
    for (LineSegment segment : bcp.segments()) {
      StdOut.println(segment.toString());
      segment.draw();
    }
  }
  
  public int numberOfSegments() {
    return segments.length;
  }
  
  public LineSegment[] segments() {
    return segments;
  }
}
