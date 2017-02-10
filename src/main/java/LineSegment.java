import edu.princeton.cs.algs4.StdDraw;

public class LineSegment {

  private Point p;
  private Point q;
  
  public LineSegment(Point p, Point q) {
    this.p = p;
    this.q = q;
  }
  
  public void draw() {
    p.draw();
    q.draw();
    p.drawTo(q);
  }
  private void drawBig() {
    p.draw();
    q.draw();
    p.drawTo(q);
  }
  
  
  @Override
  public String toString() {
    return p.toString() + " -> " + q.toString();
  }
}
