import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
  
  
  private LineSegment[] segments = new LineSegment[1];
  private int size;
  
  public static void main(String[] args) {
    StdDraw.setScale(0, 50000);
    int[] values = StdIn.readAllInts();
    int n = values[0];
    List<Point> points = new ArrayList<>();
    for (int i = 1; i <= n*2; i += 2) {
      points.add(new Point(values[i], values[i+1]));
    }
    FastCollinearPoints fcp = new FastCollinearPoints(points.toArray(new Point[0]));
    StdOut.println("Line segments found: " + fcp.numberOfSegments());
    for (LineSegment segment : fcp.segments()) {
      StdOut.println(segment.toString());
      segment.draw();
    }
  }

  
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new NullPointerException("points array must not be null");
    }
    if (points.length < 4) {
      return;
    }
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) {
        throw new NullPointerException("elements of points array must not be null");
      }
      //points[i].draw();
      //StdOut.println(points[i].toString());
      Point curr = points[i];
      StdOut.println("CURR: " + curr.toString());
      Point[] others = new Point[points.length - 1];
      int counter = 0;
      for (int j = 0; j < points.length; j++) {
        if (i != j) {
          others[counter] = points[j];
          counter++;
        }
      }
      Arrays.sort(others, curr.slopeOrder());
      double currSlope;
      currSlope = curr.slopeTo(others[0]);
      int matchCount = 1;
      int startPoint = 0;
      for (int j = 1; j < others.length; j++) {
        double thisSlope = curr.slopeTo(others[j]);
        if (currSlope == thisSlope) {
          matchCount++;
        } else {
          if (matchCount >= 4) {
            addSegment(startPoint, j, others);
          }
          currSlope = thisSlope;
          matchCount = 1;
          startPoint = j;
        }
        //StdOut.println(others[j]);
        //StdOut.printf("matchCount %d, startPoint %d, currSlope %.3f, thisSlope %.3f%n", matchCount, startPoint, currSlope, thisSlope);
      }
    }
    LineSegment[] tmp = new LineSegment[size];
    for (int i = 0; i < size; i++) {
      tmp[i] = segments[i];
    }
    tmp = segments;
  }

  private void addSegment(int start, int end, Point[] points) {
    Point[] tmp = new Point[end - start + 1];
    int count = 0;
    StdOut.println("Points in line segment");
    for (int i = start; i <= end; i++) {
      tmp[count] = points[i];
      StdOut.printf("%s ", tmp[count]);
      count++;
    }
    StdOut.println("");
    Arrays.sort(tmp);
    LineSegment segment = new LineSegment(tmp[0], tmp[tmp.length - 1]);
    addSegment(segment);
  }
    
  private void addSegment(LineSegment segment) {
    if (size >= segments.length) {
      LineSegment[] tmp = new LineSegment[segments.length * 2];
      for (int i = 0; i < size; i++) {
        tmp[i] = segments[i];
      }
      segments = tmp;
    }
    segments[size] = segment;
    size++;
  }

  public int numberOfSegments() {
    return segments.length;
  }
  
  public LineSegment[] segments() {
    return segments;
  }
}
