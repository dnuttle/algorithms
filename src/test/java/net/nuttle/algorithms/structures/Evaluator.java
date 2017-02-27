package net.nuttle.algorithms.structures;

import net.nuttle.algorithms.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Simple parser/interpreter for mathematical expressions.
 * Could use validation, and a whole lot more operators,
 * but the Djikstra algorithm is elegantly simple.
 * @author dnuttle
 *
 */
public class Evaluator {

  public static void main(String[] args) {
    //There must be parentheses surrounding every infix.
    String s = "(3+(4*2))";
    StdOut.println(evaluate(s));
  }
  
  public static double evaluate(String expr) {
    Stack<String> ops = new Stack<>();
    Stack<Double> vals = new Stack<>();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < expr.length(); i++) {
      String s = expr.substring(i, i+1);
      if (s.matches("\\(|\\)|\\+|\\-|\\*|\\/")) sb.append(" ").append(s).append(" ");
      else sb.append(s);
    }
    String[] s = sb.toString().trim().split("\\s+");
    for (int i = 0; i < s.length; i++) {
      String sub = s[i];
      if (sub.equals("(") || sub.equals(" ")) ;
      else if (sub.equals("+") || sub.equals("-") || sub.equals("*") || sub.equals("/")) ops.push(sub);
      else if (sub.equals(")")) {
        double v = vals.pop();
        String op = ops.pop();
        if (op.equals("+")) v = v + vals.pop();
        else if (op.equals("-")) v = v - vals.pop();
        else if (op.equals("*")) v = v * vals.pop();
        else if (op.equals("/")) v = v / vals.pop();
        vals.push(v);
      } else {
        vals.push(Double.parseDouble(sub));
      }
    }
    return vals.pop();
  }
}
