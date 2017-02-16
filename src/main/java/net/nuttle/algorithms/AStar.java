package net.nuttle.algorithms;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class AStar implements Observer {
  
  private List<Node> nodes = new ArrayList<>();
  private List<Node> openNodes = new ArrayList<>();
  private List<Node> closedNodes = new ArrayList<>();
  private List<Node> blockedNodes = new ArrayList<>();
  private Node start;
  private Node end;
  int rows;
  int cols;
  Font main = new Font("sans-serif", Font.PLAIN, 16);
  Font small = new Font("sans-serif", Font.PLAIN, 9);
  MinPQ<Node> pq = new MinPQ<>();
  
  @Override
  public void update(Observable n, Object o) {
    drawNode((Node) n);
  }

  public AStar(int rows, int cols, int startIdx, int endIdx) {
    this.rows = rows;
    this.cols = cols;
    for (int col = 0; col < 6; col++) {
      for (int row = 0; row < 10; row++) {
        Node n = new Node(row, col);
        nodes.add(n);
      }
    }
    start = nodes.get(startIdx);
    end = nodes.get(endIdx);
    start.calcCost(this.start, end, true);
    end.calcCost(start, end, true);
  }
  
  public void init(Observer o) {
    for (Node n : nodes) {
      n.addObserver(o);
    }
    blockedNodes.add(nodes.get(5));
    scoreNeighbors(start);
  }
  
  
  public static void main(String[] args) {
    AStar board = new AStar(6, 10, 12, 46);
    StdDraw.setCanvasSize(600, 600);
    StdDraw.setScale(0, 800);
    StdDraw.filledSquare(300, 300, 300);
    board.drawNodes();
    board.init(board);
    Node min = board.pq.delMin();
    StdOut.printf("Min node: %d, %d, %d", min.x, min.y, min.fcost);
    //board.scoreNeighbors(min);
    //board.start.calcCost(board.start, board.end);
  }
  
  private void drawNodes() {
    for (Node n : nodes) {
      drawNode(n);
    }
  }
  
  private void drawNode(Node n) {
    boolean drawScore = false;
    StdDraw.setPenColor(Color.WHITE);
    if (n == start || n == end) {
      StdDraw.setPenColor(Color.CYAN);
      drawScore = true;
    }
    if (isBlocked(n)) {
      StdDraw.setPenColor(Color.BLACK);
    }
    if (isOpen(n)) {
      StdDraw.setPenColor(Color.GREEN);
      drawScore = true;
    }
    if (isClosed(n)) {
      StdDraw.setPenColor(Color.RED);
      drawScore = true;
    }
    StdDraw.filledSquare(n.x * 50 + 50,  n.y * 50 + 50,  23);
    StdDraw.setPenColor();
    if (drawScore) {
      drawScore(n.gcost, n.hcost, n);
    }
  }
  
  private boolean isOpen(Node n) {
    return openNodes.contains(n);
  }
  
  private boolean isBlocked(Node n) {
    return blockedNodes.contains(n);
  }
  
  private boolean isClosed(Node n) {
    return closedNodes.contains(n);
  }
  
  private void drawScore(int gscore, int fscore, Node n) {
    //StdDraw.setFont(Font.font(8));
    StdDraw.setFont(small);
    StdDraw.text((n.x+1) * 50 - 12,  (n.y+1) * 50 +10,  "" + gscore);
    StdDraw.text((n.x+1) * 50 + 12,  (n.y+1) * 50 +10,  "" + fscore);
    StdDraw.setFont(main);
    StdDraw.text((n.x+1)*50, (n.y+1) * 50 -10, "" + (gscore + fscore));
  }
  
  
  private void scoreNeighbors(Node n) {
    if (n.x > 0) scoreNeighbor(n, getNode(n.y, n.x - 1));
    if (n.x < cols) scoreNeighbor(n, getNode(n.y, n.x + 1));
    if (n.y > 0) scoreNeighbor(n, getNode(n.y - 1, n.x));
    if (n.y < rows) scoreNeighbor(n, getNode(n.y + 1, n.x));
    if (n.x > 0 && n.y > 0) scoreNeighbor(n, getNode(n.y - 1, n.x - 1));
    if (n.x < cols && n.y > 0) scoreNeighbor(n, getNode(n.y - 1, n.x + 1));
    if (n.x < cols && n.y < rows) scoreNeighbor(n, getNode(n.y + 1, n.x + 1));
    if (n.x > 0 && n.y < rows) scoreNeighbor(n, getNode(n.y + 1, n.x - 1));
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        if (x == 0 && y == 0) continue;
        Node curr = getNode(n.y + y, n.x + x);
        if (openNodes.contains(curr)) {
          pq.insert(curr);
        }
      }
    }
    openNodes.remove(n);
    closedNodes.add(n);
  }
  
  private void scoreNeighbor(Node n, Node neighbor) {
    if (blockedNodes.contains(neighbor) || closedNodes.contains(neighbor)) return;
    if (!openNodes.contains(neighbor)) openNodes.add(neighbor);
    neighbor.calcCost(n, end, true);
  }


  private Node getNode(int row, int col) {
    int idx = row * cols + col;
    if (idx > nodes.size()) return null;
    return nodes.get(idx);
  }
  
  private static class Node extends Observable implements Comparable<Node> {
    int x;
    int y;
    int gcost;
    int hcost;
    int fcost;
    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }
    public void calcCost(Node start, Node end, boolean skipExtra) {
      int xdiff = Math.abs(x - start.x);
      int ydiff = Math.abs(y - start.y);
      int diags = Math.abs(xdiff - Math.abs(xdiff - ydiff));
      hcost = diags * 14;
      if (xdiff > diags) {
        hcost += (xdiff - diags) * 10 + (this == end || skipExtra ? 0 : start.hcost);
      } else {
        hcost += (ydiff - diags) * 10 + (this == end || skipExtra ? 0 : start.hcost); 
      }
      xdiff = Math.abs(x - end.x);
      ydiff = Math.abs(y - end.y);
      diags = Math.min(xdiff, ydiff);
      gcost = diags * 14;
      if (xdiff > diags) {
        gcost += (xdiff - diags) * 10 + (this == start || skipExtra ? 0 : start.gcost);
      } else {
        gcost += (ydiff - diags) * 10 + (this == start || skipExtra ? 0 : start.gcost); 
      }
      fcost = gcost + hcost;
      setChanged();
      notifyObservers();
    }

    @Override
    public int compareTo(Node n) {
      if (n == null)  return 1;
      return n.fcost - fcost;
    }
  }
}
