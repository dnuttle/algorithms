import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;


public class Solver {

  //private static String rscPath = null;

  private List<Board> solution;
  
  public Solver(Board initial) {
    MinPQ<SearchNode> pq = new MinPQ<>();
    MinPQ<SearchNode> pqTwin = new MinPQ<>();
    pq = new MinPQ<>();
    pqTwin = new MinPQ<>();
    pq.insert(new SearchNode(initial, null));
    pqTwin.insert(new SearchNode(initial.twin(), null));
    int n = initial.dimension();
    solve(pq, pqTwin);
  }
  
  private void solve(MinPQ<SearchNode> pq, MinPQ<SearchNode> pqTwin) {
    while (true) {
      SearchNode min = pq.delMin();
      SearchNode minTwin = pqTwin.delMin();
      if (min.board.isGoal()) {
        //debug("FOUND SOLUTION");
        solution = new ArrayList<>();
        SearchNode curr = min;
        while(curr != null) {
          solution.add(curr.board);
          curr = curr.prev;
        }
        Collections.reverse(solution);
        pq = null;
        pqTwin = null;
        return;
      }
      if (minTwin.board.isGoal()) {
        //debug("FOUND TWIN'S SOLUTION");
        pq = null;
        pqTwin = null;
        return;
      }
      for (Board neighbor : min.board.neighbors()) {
        if (min.prev != null && min.prev.board.equals(neighbor)) {
          continue;
        }
        //debug("Neighbor: " + neighbor.manhattan() + ", " + moves);
        //debug(neighbor);
        SearchNode sn = new SearchNode(neighbor, min);
        pq.insert(sn);
      }
      for (Board neighbor : minTwin.board.neighbors()) {
        if (minTwin.prev != null && minTwin.prev.board.equals(neighbor)) {
          continue;
        }
        SearchNode sn = new SearchNode(neighbor, minTwin);
        pqTwin.insert(sn);
      }
    }
  }
  
  public boolean isSolvable() {
    if (solution != null && solution.size() > 0) {
      return solution.get(solution.size() - 1).isGoal();
    }
    return false;
  }
  
  public int moves() {
    if (solution == null) return -1;
    return solution.size() - 1;
  }
  
  public Iterable<Board> solution() {
    return solution;
  }
  
  public static void main(String[] args) {
    myMain2(args);
  }
  
  private static void myMain(String[] args) {
    //debug = true;
    int[][] blocks = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    //blocks = new int[][]{{0, 1, 3, 9}, {4, 2, 5, 10}, {7, 8, 6, 11}, {12, 13, 14, 15}};
    //blocks = new int[][]{{2, 1, 3}, {4, 0, 5}, {7, 8, 6}}; //no solution
    blocks = new int[][]{{4, 1, 3}, {7, 2, 6}, {0, 5, 8}};
    Board b = new Board(blocks);
    StdOut.println(b);
    Solver s = new Solver(b);
    if (s.isSolvable()) {
      StdOut.println("Solved in " + s.moves() + " moves");
    } else {
      StdOut.println("No solution possible");
    }
  }
  
  private static void testFile(String file) {
    String rscPath = System.getProperty("user.dir") + "/src/test/resources/8puzzle/";
    In in = new In(rscPath + file);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);
    StdOut.println(file);
    StdOut.println(initial);
    
    // solve the puzzle
    Solver solver = new Solver(initial);
    if (!solver.isSolvable()) {
      StdOut.println("Could not solve " + file);
    } else {
      int boards = 0;
      for (Board b : solver.solution()) {
        boards++;
      }
      StdOut.println("File " + file + " solved in " + solver.moves() + " moves, " + boards + " boards");
    }
  }
  
  private static void myMain2(String[] args) {
    String rscPath = System.getProperty("user.dir") + "/src/test/resources/8puzzle/";
    for (int idx = 0; idx < 51; idx++) {
      String file = null;
      try {
        file = "puzzle" + String.format("%02d", idx) + ".txt";
        testFile(file);
      } catch (Throwable t) {
        StdOut.println("FAILED on file " + file + ", " + t.getClass().getName());
        t.printStackTrace();
      }
    }
    String file = rscPath + "puzzle11.txt";
    In in = new In(file);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
  
  private static void classMain(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
  
  private void debug(Object msg) {
    StdOut.println(msg.toString());
  }
  
  private static class SearchNode implements Comparable<SearchNode> {
    Board board;
    SearchNode prev;
    int moves;
    int manhattan;
    
    public SearchNode(Board board, SearchNode prev) {
      this.board = board;
      this.prev = prev;
      moves = 0;
      SearchNode tmp = prev;
      while (tmp != null) {
        moves++;
        tmp = tmp.prev;
      }
      manhattan = board.manhattan();
    }

    
    @Override
    public int compareTo(SearchNode node) {
      if (manhattan + moves == node.manhattan + node.moves) {
        return 0;
      }
      if (manhattan + moves > node.manhattan + node.moves) {
        return 1;
      }
      return -1;
      
    }
  }
  
}
