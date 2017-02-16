import edu.princeton.cs.algs4.StdOut;

public class Board {

  private int [][] blocks;
  private int n;
  private int manhattan;
  private int hamming;
  private Board twin;
  
  public Board(int[][] blocks) {
    this.n = blocks.length;
    this.blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        this.blocks[i][j] = blocks[i][j];
      }
    }
    init();
  }
  
  public int dimension() {
    return n;
  }
  
  private void init() {
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (blocks[i][j] == 0) {
          continue;
        } else {
          int actualRow = (blocks[i][j] - 1) / n; 
          int expectedRow = i;
          int actualCol = blocks[i][j] - actualRow * n;
          int expectedCol = j + 1;
          int rowDiff = Math.abs(actualRow - expectedRow);
          int colDiff = Math.abs(actualCol - expectedCol);
          manhattan += rowDiff;
          manhattan += colDiff;
          hamming += actualRow == expectedRow ? 0 : 1;
          hamming += actualCol == expectedCol ? 0 : 1;
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
    return manhattan() == 0;
  }
  
  public Board twin() {
    if (twin == null) {
      int[][] b = new int[n][n];
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          b[i][j] = blocks[i][j];
        }
      }
      for (int i = 0; i < n; i++) {
        if (b[0][i] != 0) {
          if (b[1][i] != 0) {
            swap(0, i, 1, i, b);
          } else {
            if (i < n - 1 && b[0][i+1] != 0) {
              swap(0, i, 0, i + 1, b);
            } else {
              swap(0, i, 1, i, b);
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
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (this.blocks[i][j] != b.blocks[i][j]) {
          return false;
        }
      }
    }
    return true;
  }
  
  public Iterable<Board> neighbors() {
    return null;
  }
  
  public String toString() {
    int numDigits = (int) Math.floor(Math.log10((double) n)) + 1;
    String format = "%" + (numDigits + 1) + "d";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        sb.append(String.format(format,  blocks[i][j]));
      }
      sb.append("\n");
    }
    return sb.toString();
  }
  
  public static void main(String[] args) {
    int[][] blocks = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    blocks = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
    
    Board b = new Board(blocks);
    System.out.println(b.manhattan());
    System.out.println(b.hamming());
    
    System.out.println(b.toString());
    System.out.println(b.twin().toString());
  }
}
