package net.nuttle.algorithms;

import edu.princeton.cs.algs4.StdOut;

public class Combinatorics {

  public static void main(String[] args) {
    int N = 6;
    int count = 0;
    //n is 3, the number of loops
    //N^n; order matters, with replications
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        for (int k = 0; k < N; k++) {
          //StdOut.println(i+":"+j+":"+k);
          count++;
        }
      }
    }
    StdOut.println(count);
    
    //N!/n!(N-n)!; order doesn't matter, without replication; N choose n
    N = 1000;
    count = 0;
    //How to do this?  Is it even relevant in terms of an algorithm?
    //0:1:2, 0:2:1 are distinct, but 0:0:1 doesn't occur (replication)
    //0:1:2, 0:1:3, 0:1:4, 0:1:5, 0:2:1, 0:2:2, etc.
    
    count = 0;
    //N choose n; order doesn't matter, without replications
    for (int i = 0; i < N; i++) {
      for (int j = i + 1; j < N; j++) {
        for (int k = j + 1; k < N; k++) {
          //StdOut.println(i+":"+j+":"+k);
          count++;
        }
      }
    }
    StdOut.println(count);

    //And what about order matters, with replication?
    //N!/(N-n)!
    //Does this ever make sense in an algorithm?
    N = 10;
    count = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        for (int k = 0; k < N; k++) {
          if (i != j && i != k && j != k) {
            count++;
          }
        }
      }
    }
    StdOut.println(count);
    
    //And what about this?
    //WHen N = 6 and n = 3, this is (6+5+4+3+2+1) + (5+4+3+2+1) + (4+3+2+1) + (3+2+1) + (2+1) + 1 
    //= 21 + 15 + 10 + 6 + 3 + 1 = 56
    //= 1/2(N)(N+1) + 1/2(N-1)(N) + 1/2(N-2)(N-1) + 1/2(N-3)(N-2) + 1/2(N-4)(N-3) + 1/2(N-5)(N-4)
    //Or 1/2(N^2 + N + N^2 - N + N^2 - 3N + 2 + N^2 - 5N + 6 + N^2 - 7N + 12 + N^2 - 9N + 20)
    //= 1/2(6N^2 - 24N + 40) = 3N^2 - 12N + 20
    //KEY POINT:  three loops, but ~quadratic performance, not cubic...when N=6
    //What is the formula for any N and n=3?
    //sum from i=1 to N of 1/2(N-i-1)(N-i)
    //let Z = 1/2 (N) (N+1)
    //What is 1/2 (Z) (Z+1)?
    //1/2 (Z)(Z+1) = 1/2 [1/2(N)(N+1)] [1/2((N)(N+1)) + 1 ]
    //= 1/2 [N^2/2 + N/2] [N^2/2 + N/2 + 1]
    //= 1/2 [N^4/4 + N^3/4 + N^2/2 + N^3/4 + N^2/4 + N/2]
    //= 1/2 [N^4/4 + N^3/2 + N^2/2 + N/2]
    //= N^4/8 + N^3/4 + N^2/4 + N/4
    //when N=1000
    //= 10^12/8 + 10^9/4 + 10^6/4 + 10^3/4
    /*
     * No!  Z = 1/2 (N-i)(N-i+1), but what is i?  i know you are, but what is i?
     * Anyway, the value when N=1000 is about 167 million, or ~N^3/6.
     */
    
    
    count = 0;
    N = 1000;
    for (int i = 0; i < N; i++) {
      for (int j = i; j < N; j++) {
        for (int k = j; k < N; k++) {
          count++;
          //StdOut.println(count + " " + i+":"+j+":"+k);
        }
      }
    }
    StdOut.println(count);

    
    //1/2(N)(N+1) = 1/2(N^2 + N)
    count = 0;
    for (int i = 0; i < N; i++) {
      for (int j = i; j < N; j++) {
        count++;
      }
    }
    StdOut.println(count);
  }
  
  
  
}
