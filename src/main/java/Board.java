import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class Board {

  private char [][] blocks;
  private int manhattan;
  private int hamming;
  private Board twin;
  private List<Board> neighbors;
  
  public Board(int[][] blocks) {
    this.blocks = new char[blocks.length][blocks.length];
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        this.blocks[i][j] = (char) blocks[i][j];
      }
    }
    init();
  }
  
  public int dimension() {
    return blocks.length;
  }
  
  private void init() {
    manhattan = 0;
    hamming = 0;
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        if (blocks[i][j] == 0) {
          continue;
        } else {
          int actualRow = (blocks[i][j] - 1) / blocks.length; 
          int expectedRow = i;
          int actualCol = blocks[i][j] - actualRow * blocks.length;
          int expectedCol = j + 1;
          int rowDiff = Math.abs(actualRow - expectedRow);
          int colDiff = Math.abs(actualCol - expectedCol);
          manhattan += rowDiff;
          manhattan += colDiff;
          hamming += (actualRow == expectedRow && actualCol == expectedCol) ? 0 : 1;
        }
      }
    }
  }
  
  public int hamming() {
    return hamming;
  }
  
  public int manhattan() {
    return manhattan;
  }
  
  public boolean isGoal() {
    return manhattan == 0;
  }
  
  public Board twin() {
    if (twin == null) {
      int[][] b = new int[blocks.length][blocks.length];
      for (int i = 0; i < blocks.length; i++) {
        for (int j = 0; j < blocks.length; j++) {
          b[i][j] = blocks[i][j];
        }
      }
      for (int i = 0; i < blocks.length; i++) {
        if (b[0][i] != 0) {
          if (b[1][i] != 0) {
            swap(0, i, 1, i, b);
            break;
          } else {
            if (i < blocks.length - 1 && b[0][i+1] != 0) {
              swap(0, i, 0, i + 1, b);
              break;
            } else {
              swap(0, i, 1, i, b);
              break;
            }
          }
        }
      }
      twin = new Board(b);
    }
    return twin;
  }
  
  private void swap(int i, int j, int k, int l, int[][] b) {
    int tmp = b[i][j];
    b[i][j] = b[k][l];
    b[k][l] = tmp;
  }
  
  public boolean equals(Object y) {
    if (!(y instanceof Board)) {
      return false;
    }
    Board b = (Board) y;
    if (b.dimension() != this.dimension()) {
      return false;
    }
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        if (this.blocks[i][j] != b.blocks[i][j]) {
          return false;
        }
      }
    }
    return true;
  }
  
  public Iterable<Board> neighbors() {
    if (neighbors == null) {
      findNeighbors();
    }
    return neighbors;
  }
  
  private void findNeighbors() {
    neighbors = new ArrayList<>();
    int zeroRow = -1;
    int zeroCol = -1;
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        if (blocks[i][j] == 0) {
          zeroRow = i;
          zeroCol = j;
          break;
        }
      }
    }
    if (zeroRow < 0 || zeroCol < 0) {
      throw new IllegalStateException("No zero value found");
    }
    if (zeroRow > 0) {
      int[][] cloneBlocks = cloneBlocks();
      swap(zeroRow, zeroCol, zeroRow - 1, zeroCol, cloneBlocks);
      Board neighbor = new Board(cloneBlocks);
      neighbors.add(neighbor);
    }
    if (zeroRow < blocks.length - 1) {
      int[][] cloneBlocks = cloneBlocks();
      swap(zeroRow, zeroCol, zeroRow + 1, zeroCol, cloneBlocks);
      Board neighbor = new Board(cloneBlocks);
      neighbors.add(neighbor);
    }
    if (zeroCol > 0) {
      int[][] cloneBlocks = cloneBlocks();
      swap(zeroRow, zeroCol, zeroRow, zeroCol - 1, cloneBlocks);
      Board neighbor = new Board(cloneBlocks);
      neighbors.add(neighbor);
    }
    if (zeroCol < blocks.length - 1) {
      int[][] cloneBlocks = cloneBlocks();
      swap(zeroRow, zeroCol, zeroRow, zeroCol + 1, cloneBlocks);
      Board neighbor = new Board(cloneBlocks);
      neighbors.add(neighbor);
    }
    neighbors = Collections.unmodifiableList(neighbors);
  }
  
  private int[][] cloneBlocks() {
    int[][] clone = new int[blocks.length][blocks.length];
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        clone[i][j] = blocks[i][j];
      }
    }
    return clone;
  }
  
  public String toString() {
    String format = "%2d ";
    //checklist says format must be %2d; what if dimension is greater than 2?  Guess we'll find out. Deliberate red herring?
    //format = "%2d";
    StringBuilder sb = new StringBuilder();
    sb.append(blocks.length).append("\n");
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        sb.append(String.format(format,  (int) blocks[i][j]));
      }
      sb.append("\n");
    }
    return sb.toString();
  }
  
  public static void main(String[] args) {
    int[][] blocks = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    blocks = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
    //blocks = new int[][]{{2, 1, 3}, {4, 0, 5}, {7, 8, 6}};
    blocks = new int[][]{{2, 1, 3}, {4, 8, 5}, {7, 0, 6}};
    blocks = new int[][]{{3, 6, 5}, {7, 4, 8}, {0, 2, 1}};
    
    
    Board b = new Board(blocks);
    StdOut.println(b.manhattan());
    StdOut.println(b.hamming());
    StdOut.println(b);
    StdOut.println(b.twin());
    
    StdOut.println("Neighbors");
    for (Board board : b.neighbors()) {
      StdOut.println(board.manhattan());
      StdOut.println(board);
    }
  }
}
