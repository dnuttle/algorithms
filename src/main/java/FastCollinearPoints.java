import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
  
  
  private List<LineSegment> segments = new ArrayList<>();
  private Map<SegmentPoints, LineSegment> segmap = new HashMap<>();
  
  public static void main(String[] args) {
    int[] values = StdIn.readAllInts();
    int n = values[0];
    Point[] points = new Point[n];
    for (int i = 1; i <= n*2; i += 2) {
      points[i / 2] = new Point(values[i], values[i+1]);
    }
    StdDraw.setScale(0, 50000);
    FastCollinearPoints fcp = new FastCollinearPoints(points);
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
    List<LineSegment> l = new ArrayList<>();
    Point[] tmpPoints = new Point[points.length];
    for (int j = 0; j < points.length; j++) {
      if (points[j] == null) {
        throw new NullPointerException("elements of points array must not be null");
      }
      tmpPoints[j] = points[j];
    }
    Arrays.sort(tmpPoints);
    for (int i = 0; i < tmpPoints.length - 1; i++) {
      if (tmpPoints[i].compareTo(tmpPoints[i+1]) == 0) {
        throw new IllegalArgumentException("points cannot be duplicates");
      }
    }
    for (int i = 0; i < points.length; i++) {
      //points[i].draw();
      //StdOut.println(points[i].toString());
      Point curr = points[i];
      //StdOut.println("**\n**CURR: " + curr.toString());
      Arrays.sort(tmpPoints, curr.slopeOrder());
      /*
      StdOut.print("SLOPES: ");
      for (Point p1 : others) {
        StdOut.print(curr.slopeTo(p1) + " ");
      }
      StdOut.println("");
      */
      double currSlope;
      currSlope = curr.slopeTo(tmpPoints[0]);
      int matchCount = 1;
      int startPoint = 0;
      for (int j = 1; j < tmpPoints.length; j++) {
        double thisSlope = curr.slopeTo(tmpPoints[j]);
        if (currSlope == thisSlope) {
          matchCount++;
        } else {
          if (matchCount >= 3) {
            addSegment(startPoint, j - 1, tmpPoints, curr);
          }
          currSlope = thisSlope;
          matchCount = 1;
          startPoint = j;
        }
        //StdOut.println("j=" + j + " " + others[j]);
        //StdOut.printf("matchCount %d, startPoint %d, currSlope %.3f, thisSlope %.3f%n", matchCount, startPoint, currSlope, thisSlope);
      }
      if (matchCount >= 3) {
        addSegment(startPoint, tmpPoints.length - 1, tmpPoints, curr);
      }
    }
    //StdOut.println("SIZE: " + size + " " + segments.length);
  }

  private void addSegment(int start, int end, Point[] points, Point curr) {
    /*
    StdOut.print("addSegment start=" + start + " end=" + end + " ");
    for(Point p : points) {
      StdOut.print(p + " ");
    }
    StdOut.println("");
    */
    Point[] tmp = new Point[end - start + 2];
    int count = 0;
    for (int i = start; i <= end; i++) {
      tmp[count] = points[i];
      count++;
    }
    tmp[tmp.length - 1] = curr;
    Arrays.sort(tmp);
    /*
    StdOut.println("Points in line segment");
    for (int i = 0; i < tmp.length; i++) {
      StdOut.printf("%s ", tmp[i]);
      count++;
    }
    StdOut.println("");
    */
    LineSegment segment = new LineSegment(tmp[0], tmp[tmp.length - 1]);
    SegmentPoints pts = new SegmentPoints(tmp[0], tmp[tmp.length - 1]);
    addSegment(segment, pts);
  }
    
  private void addSegment(LineSegment segment, SegmentPoints pts) {
    for (SegmentPoints p : segmap.keySet()) {
      if (p.compareTo(pts) == 0) {
        return;
      }
    }
    segmap.put(pts, segment);
    segments.add(segment);
  }

  public int numberOfSegments() {
    return segments.size();
  }
  
  public LineSegment[] segments() {
    return segments.toArray(new LineSegment[0]);
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
