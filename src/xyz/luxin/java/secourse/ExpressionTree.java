package xyz.luxin.java.secourse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * .
 * @author .
 */

public class ExpressionTree {

  /**
   * .
   */
  public transient AbstractExpression exp;
  /**
   * .
   */
  public transient ExpressionTree left;
  /**
   * .
   */
  public transient ExpressionTree right;
  
  //public ExpressionTree() {
  //this.exp = null;
  //this.left = null;
  //this.right = null;
  //}
  
  /**
   * .
   * @ param t
   * @ return
   * @ throws ExpressionException
   */
  public static Polynomial obtainPolynomial(final ExpressionTree tt01) throws ExpressionException  {
    if (tt01 != null) {
      obtainPolynomial(tt01.left);
      obtainPolynomial(tt01.right);
      if (tt01.exp instanceof Operator) {
        if (!(tt01.left.exp instanceof Polynomial) || !(tt01.right.exp instanceof Polynomial)) {
          throw new ExpressionException("Internal Error");
        }
        tt01.exp = Polynomial.arithmetic((Polynomial)tt01.left.exp, (Polynomial)tt01.right.exp, 
     (Operator)tt01.exp);
        //tt01.left = null;
        //tt01.right = null;
        return (Polynomial)tt01.exp;
      } else if (tt01.exp instanceof Polynomial) {
        if (tt01.left != null || tt01.right != null) {
          throw new ExpressionException("Internal Error");
        }
        return (Polynomial)tt01.exp;
      }
    }
    return null;
  }
  
  /** 
   * .
   * @ param tt
   * @ param expString
   * @ throws ExpressionException
   */
  
  public static void createTree(final ExpressionTree tt00,final String expString) 
      throws ExpressionException {
    String expString1 = expString;
    char[] chars = expString1.toCharArray();
    
    //no bracket
    if (!expString1.contains("(") && !expString1.contains(")")) {
      tt00.exp = new Polynomial(expString1, true);
      return;
    }
    
    //check only both ends bracket
    Pattern pp00 = Pattern.compile("\\s*\\(([^\\(\\)]*)\\)\\s*");
    Matcher mm00 = pp00.matcher(expString1);
    if (mm00.matches()) {
      tt00.exp = new Polynomial(mm00.group(1), true);
      return;
    }
    
    //remove both ends paired bracket
    pp00 = Pattern.compile("\\s*\\((.*)\\)\\s*");
    mm00 = pp00.matcher(expString1);
    if (mm00.matches()) {
      //check paired
      boolean flag = true;
      final char[] tmpChars = mm00.group(1).toCharArray();
      int count = 0;
      
      for (int i = 0; i < mm00.group(1).length(); i++) {
        if (tmpChars[i] == '(') {
          count++;
        }
        if (tmpChars[i] == ')') {
          count--;
        }
        if (count < 0) {
          flag = false;
        }
      }
      if (count != 0) {
        throw new ExpressionException("Bracket Error");
      }
      
      if (flag) {
        expString1 = mm00.group(1);
        chars = expString1.toCharArray();
      }
    }
    
    //find available op
    int opIndex = -1;
    int opLevel = 0;
    int level = 0;
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '+' && level + 3  > opLevel) {
        opIndex = i;
        opLevel = level + 3;
      }
      if (chars[i] == '*' && level + 2  > opLevel) {
        opIndex = i;
        opLevel = level + 2;
      }
      if (chars[i] == '^' && level + 1 > opLevel) {
        opIndex = i;
        opLevel = level + 1;
      }
      if (chars[i] == '(') {
        level = level - 4;
      }
      if (chars[i] == ')') {
        level = level + 4;
      }
    }
    
    if (opIndex == -1) {
      throw new ExpressionException("Format Error");
    } else {
      tt00.exp = new Operator(chars[opIndex]);
      tt00.left = new ExpressionTree();
      tt00.right = new ExpressionTree();
      final String left = expString1.substring(0, opIndex);
      final String right = expString1.substring(opIndex + 1, expString1.length());
      createTree(tt00.left, left);
      createTree(tt00.right, right);
    }
  }

  /**
   * .
   * @ param tt
   */
  public static void preOrder(final ExpressionTree tt00) {
    if (null != tt00) {
      System.out.print(tt00.exp + " ");
      preOrder(tt00.left);
      preOrder(tt00.right);
    }
  }
  /**
   * .
   * @ param t
   */
  
  public static void midOrder(final ExpressionTree tt0) {
    if (null != tt0) {
      System.out.print("(");
      midOrder(tt0.left);
      System.out.print(tt0.exp);
      midOrder(tt0.right);
      System.out.print(")");
    }
  }

  /**
   * .
   * @ param t
   */
  public static void lastOrder(final ExpressionTree tt0) {
    if (null != tt0) {
      lastOrder(tt0.left);
      lastOrder(tt0.right);
      System.out.print(tt0.exp + " ");
    }
  }
  
}