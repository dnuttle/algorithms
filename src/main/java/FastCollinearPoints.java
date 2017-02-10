import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
  
  private List<SegmentPoints> segpoints = new ArrayList<>();
  private LineSegment[] segments = new LineSegment[0];
  private static Stopwatch sorting = new Stopwatch();
  private static Stopwatch total = new Stopwatch();
  private static int sortCount;
  private static Point[] points;
  
  private static boolean debug = false;
  
  public static void main(String[] args) throws Exception {
    myMain();
  }
  
  
  private static void classMain() {
    StdDraw.setXscale(-300, 33068);
    StdDraw.setYscale(-300, 33068);
    StdDraw.enableDoubleBuffering();
    int[] values = StdIn.readAllInts();
    int n = values[0];
    points = new Point[n];
    for (int i = 1; i <= n*2; i += 2) {
      points[i / 2] = new Point(values[i], values[i+1]);
    }
    total.start();
    FastCollinearPoints fcp = new FastCollinearPoints(points);
    total.stop();
    StdOut.println("Line segments found: " + fcp.numberOfSegments());
    for (LineSegment segment : fcp.segments()) {
      StdOut.println(segment.toString());
      segment.draw();
    }
    for (int i = 0; i < points.length; i++) {
      points[i].draw();
    }
    StdDraw.show();
    StdOut.println("Sorting time: " + sorting.elapsedSecs());
    StdOut.println("Sorts: " + sortCount);
    StdOut.println("Total sort time/total items sorted (ms): " + (double) sorting.elapsed / ((double) sortCount * (double) n));
    StdOut.println("Total time: " + total.elapsedSecs());
  }
  
  private static void myMain() throws Exception {
    debug = false;
    BufferedReader reader = null;
    try {
      StdDraw.setXscale(-300, 33068);
      StdDraw.setYscale(-300, 33068);
      //StdDraw.setXscale(-300, 16000);
      //StdDraw.setYscale(4000, 19000);
      StdDraw.enableDoubleBuffering();
      String rscPath = System.getProperty("user.dir") + "/src/test/resources/collinear/";
      File file = new File(rscPath, "grid4x4.txt");
      reader = new BufferedReader(new FileReader(file));
      String line = reader.readLine().trim();
      int n = Integer.parseInt(line);
      points = new Point[n];
      int count = 0;
      while ((line = reader.readLine()) != null) {
        if (line.trim().length() == 0) {
          continue;
        }
        String[] vals = line.trim().split("\\s+");
        if (vals.length != 2) {
          for (String val : vals) {
            StdOut.println(val);
          }
          throw new Exception("Line had " + vals.length + " values:" + line);
        }
        Point p = new Point(Integer.parseInt(vals[0]), Integer.parseInt(vals[1]));
        points[count] = p;
        p.draw();
        count++;
      }
      total.start();
      FastCollinearPoints fcp = new FastCollinearPoints(points);
      total.stop();
      StdOut.println("Line segments found: " + fcp.numberOfSegments());
      for (LineSegment segment : fcp.segments()) {
        StdOut.println(segment.toString());
        segment.draw();
        //System.in.read();
        //Thread.sleep(3000);
      }
      StdDraw.show();
      StdOut.println("Sorting time: " + sorting.elapsedSecs());
      StdOut.println("Sorts: " + sortCount);
      StdOut.println("Total sort time/total items sorted (ms): " + (double) sorting.elapsed / ((double) sortCount * (double) n));
      StdOut.println("Total time: " + total.elapsedSecs());
    } finally {
      reader.close();
      
    }
  }
  
  
  
  
  public FastCollinearPoints(Point[] points) {
    Point[] aux = validateAndSort(points);
    if (points.length < 4) {
      return;
    }
    points = new Point[points.length];
    for (int j = 0; j < points.length; j++) {
      points[j] = aux[j];
    }
    List<Point> matches = new ArrayList<>();
    for (int i = 0; i < points.length - 1; i++) {
      Point curr = points[i];
      matches.clear();
      matches.add(curr);
      if (i > 0) {
        //when i==0, points are already set
        for (int m = i; m < points.length; m++) {
          aux[m] = points[m];
        }
      }
      sortPoints(aux, i+1, points.length, curr.slopeOrder());
      double slope = curr.slopeTo(aux[i+1]);
      for (int j = i+1; j < aux.length; j++) {
        if (slope == curr.slopeTo(aux[j])) {
          matches.add(aux[j]);
        } else {
          if (matches.size() >= 4) {
            addSegment(matches);
          }
          slope = curr.slopeTo(aux[j]);
          matches.clear();
          matches.add(curr);
          matches.add(aux[j]);
        }
      }
      if (matches.size() >= 4) {
        addSegment(matches);
        matches.clear();
      }
    }

    segments = new LineSegment[segpoints.size()];
    for (int i = 0; i < segpoints.size(); i++) {
      SegmentPoints pts = segpoints.get(i);
      LineSegment seg = new LineSegment(pts.p, pts.q);
      segments[i] = seg;
    }
    if (debug) {
      postDebug();
    }
  }
  
  private void addSegment(List<Point> points) {
    Collections.sort(points);
    Point p = points.get(0);
    Point q = points.get(points.size() - 1);
    setGreatest(p, q);
    if (debug) {
      validateSPSorted();
    }
  }
  
  private void validateSPSorted() {
    double slope = segpoints.get(0).slope();
    for (SegmentPoints sp : segpoints) {
      if (sp.slope() < slope) {
        StdOut.println("*********************");
        for (SegmentPoints sp2 : segpoints) {
          StdOut.println(sp2);
        }
        StdOut.println("*********************");
        throw new IllegalStateException("segpoints not sorted");
      }
      slope = sp.slope();
    }
  }
  
  /**
   * The goal here is to minimize the amount of time looking for an existing
   * line segment that is collinear with the two current points.
   * The list of existing segments is kept in natural order.
   * So use a binary search to find the 
   * @param p is a Point that is <= q
   * @param q is a Point that is >= p
   */
  private void setGreatest(Point p, Point q) {
    if (debug) {
      if (p.compareTo(q) > 0) {
        throw new IllegalStateException(" p > q! " + p + " " + q);
      }
    }
    //First, if no segment point has the slope of
    //these points, just add the SegmentPoints without further ado.
    double slope = p.slopeTo(q);
    int idx = binarySearchSegmentPoints(slope);
    if (idx < 0) {
      SegmentPoints pts = new SegmentPoints(p, q);
      if (debug) {
        StdOut.println("Add segment, new slope: " + pts + " added at " + (-idx-1));
        LineSegment ls = new LineSegment(p, q);
        ls.draw();
      }
      segpoints.add(-idx-1, pts);
      return;
    }
    //StdOut.println("slope: " + slope + ", idx: " + idx + ", SP: " + segpoints.get(idx));
    //First, ensure that we have the *first* instance of this slope
    //The binary search may have given us one of many matches, and not necessarily the first
    while (idx > 0 && segpoints.get(idx - 1).slope() == slope) {
      idx--;
    }
    //Now compare all SegmentPoints with equal slopes for collinearity
    //If none is found, insert a new one right after the last instance with equal slope (preserving stability)
    int matched = 0;
    for (int i = idx; i < segpoints.size(); i++) {
      SegmentPoints currPts = segpoints.get(i);
      if (slope != currPts.slope()) {
        //StdOut.println("No match found for " + p + ", " + q + " at i=" + i + " (slope of " + currPts.slope() + ")");
        //None of the SegmentPoints with the same slope are collinear; create new one at i + 1
        SegmentPoints pts = new SegmentPoints(p, q);
        if (debug) {
          StdOut.println("Add seg, none of " + matched + " w/equal slope collinear, seg: " + pts + " at " + i);
          LineSegment ls = new LineSegment(pts.p, pts.q);
          ls.draw();
        }
        segpoints.add(i, pts);
        return;
      }
      matched++;
      if ((p.slopeTo(currPts.p) == currPts.slope() || p.compareTo(currPts.p) == 0) 
          && (q.slopeTo(currPts.q) == currPts.slope() || q.compareTo(currPts.q) == 0)) {
        boolean change = false;
        Point min = currPts.p;
        Point max = currPts.q;
        if (getMin(p, currPts.p) == 0) {
          min = p;
          change = true;
        }
        if (getMin(q, currPts.q) == 1) {
          max = q;
          change = true;
        }
        if (!change) {
          if (debug) {
            StdOut.println("Subseg of curr seg: " + currPts);
          }
          return;
        }
        SegmentPoints newPts =  new SegmentPoints(min, max);
        segpoints.set(segpoints.indexOf(currPts), newPts);
        if (debug) {
          StdOut.println("Replacing seg " + currPts + " with " + newPts);
          LineSegment ls = new LineSegment(newPts.p, newPts.q);
          ls.draw();
        }
        return;
      }
    }
    //Reaching this point means we have exhausted the current segpoints with matching slope
    //Insert new one at end of list
    SegmentPoints pts = new SegmentPoints(p, q);
    if (debug) {
      StdOut.println("Adding seg after exhausting matches " + pts + " at " + segpoints.size());
      LineSegment ls = new LineSegment(pts.p, pts.q);
      ls.draw();
    }
    segpoints.add(segpoints.size(), pts);
  }
  
  private SegmentPoints getSegmentPoints(Point p, Point q) {
    if (getMin(p, q) == 0) {
      return new SegmentPoints(p, q);
    } else {
      return new SegmentPoints(q, p);
    }
  }
  
  private int getMin(Point p, Point q) {
    if (p.compareTo(q) > 0) {
      return 1;
    }
    return 0;
  }
  
  private int binarySearchSegmentPoints(double slope) {
    if (segpoints.size() == 0) {
      return -1;
    }
    int lo = 0;
    int hi = segpoints.size() - 1;
    int mid = lo + (hi - lo) / 2;
    while (true) {
      if (slope == segpoints.get(mid).slope()) {
        return mid;
      } else if (slope < segpoints.get(mid).slope()) {
        hi = mid - 1;
        mid = lo + (hi - lo) / 2;
      } else {
        lo = mid + 1;
        mid = lo + (hi - lo) / 2;
      }
      if (lo > hi) {
        if (hi < 0) {
          return -1;
        } else if (lo >= segpoints.size()) {
          return -segpoints.size()-1;
        } else if (lo == segpoints.size() - 1) {
          return -lo-1;
        } else {
          if (slope > segpoints.get(lo).slope()) {
            return -lo -2;
          } else {
            return -lo - 1;
          }
        }
        
      }
    }
  }
  
  public int numberOfSegments() {
    return segments.length;
  }
  
  public LineSegment[] segments() {
    LineSegment[] tmp = new LineSegment[segments.length];
    for (int i = 0; i < segments.length; i++) {
      tmp[i] = segments[i];
    }
    return tmp;
  }
  
  private void sortPoints(Point[] points, int start, int stop, Comparator<Point> c) {
    sorting.start();
    Arrays.sort(points, start, stop, c);
    sorting.stop();
    sortCount++;
  }
  
  private Point[] validateAndSort(Point[] points) {
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
    return tmpPoints;
  }
  
  private void postDebug() {
    Collections.sort(segpoints);
    for (int i = 0; i < segpoints.size() - 1; i++) {
      if (segpoints.get(i).compareTo(segpoints.get(i+1)) == 0) {
        throw new IllegalStateException("SegmentPoints instances equal: " + segpoints.get(i) + "  <->  " + segpoints.get(i+1));
      }
    }
  }
  
  private class SegmentPoints implements Comparable<SegmentPoints> {
    private Point p;
    private Point q;
    double slope;
    public SegmentPoints(Point p, Point q) {
      this.p = p;
      this.q = q;
      this.slope = p.slopeTo(q);
    }
    
    @Override
    public String toString() {
      return p.toString() + ", " + q.toString() + ", slope:" + slope;
    }
    
    /**
     * Returns 0 if collinear, otherwise 1
     */
    @Override
    public int compareTo(SegmentPoints that) {
      if (slope == p.slopeTo(that.p) && slope == q.slopeTo(that.q)) {
        return 0;
      }
      return 1;
      /*
      if (p.compareTo(that.p) == 0 && q.compareTo(that.q) == 0) {
        return 0;
      }
      return 1;
      */
    }
    
    public double slope() {
      return slope;
    }
    
  }
  
  private static void printPoints(Point[] points) {
    if (points.length > 100) return;
    for (Point p : points) {
      StdOut.print(p + " ");
    }
    StdOut.println("");
  }
  
  private static class Stopwatch {
    private long elapsed;
    private long start;
    
    public void start() {
      start = System.currentTimeMillis();
    }
    
    public void stop() {
      elapsed += System.currentTimeMillis() - start;
    }
    
    public double elapsedSecs() {
      return (double) elapsed / 1000.0;
    }
  }
}
