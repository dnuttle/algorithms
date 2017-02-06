import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

  private LineSegment[] segments = new LineSegment[1];
  private List<SegmentPoints> segpoints = new ArrayList<>();
  private int size;
  
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
    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0) {
          throw new IllegalArgumentException("points cannot be duplicates");
        }
        for (int k = j + 1; k < points.length; k++) {
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
    if (size < segments.length) {
      LineSegment[] tmp = new LineSegment[size];
      for (int i = 0; i < size; i++) {
        tmp[i] = segments[i];
      }
      segments = tmp;
    }
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
    for (SegmentPoints pts : segpoints) {
      if (pts.p.compareTo(min) == 0 && pts.q.compareTo(max) == 0) {
        return;
      }
    }
    LineSegment segment = new LineSegment(min, max);
    SegmentPoints pts = new SegmentPoints(min, max);
    segpoints.add(pts);
    if (size >= segments.length) {
      LineSegment[] tmp = new LineSegment[segments.length * 2];
      for (int m = 0; m < size; m++) {
        tmp[m] = segments[m];
      }
      segments = tmp;
    }
    segments[size] = segment;
    size++;
  }
  
  public static void main(String[] args) {
    StdDraw.setScale(0, 50000);
    int[] values = StdIn.readAllInts();
    int n = values[0];
    Point[] points = new Point[n];
    for (int i = 1; i <= n*2; i += 2) {
      points[i / 2] = new Point(values[i], values[i + 1]);
    }
    BruteCollinearPoints bcp = new BruteCollinearPoints(points);
    StdOut.println("Line segments found: " + bcp.numberOfSegments());
    for (LineSegment segment : bcp.segments()) {
      StdOut.println(segment.toString());
      segment.draw();
    }
  }
  
  public int numberOfSegments() {
    return size;
  }
  
  public LineSegment[] segments() {
    LineSegment[] ret = new LineSegment[segments.length];
    for (int i = 0; i < segments.length; i++) {
      ret[i] = segments[i];
    }
    return ret;
  }
  private class SegmentPoints implements Comparable<SegmentPoints> {
    private Point p;
    private Point q;
    public SegmentPoints(Point p, Point q) {
      this.p = p;
      this.q = q;
    }
    
    @Override
    public String toString() {
      return p.toString() + ", " + q.toString();
    }
    
    @Override
    public int compareTo(SegmentPoints that) {
      if (p.compareTo(that.p) == 0 && q.compareTo(that.q) == 0) {
        return 0;
      }
      return 1;
    }
    
  }


}
