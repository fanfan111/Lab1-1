package xyz.luxin.java.secourse;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * .
 * @author .
 */

public class Lab1 {
  
  /**
   * .
   * @ param exp
   * @ return
   * @ throws ExpressionException
   */
  public static Polynomial expression(final String exp) throws ExpressionException {
    
    final Polynomial poly = new Polynomial();
    poly.expressionBracket(exp);
    return poly;
  }
  /**
   * .
   * @param poly .
   * @param pairs .
   * @return .
   */
  
  public static Polynomial simplify(final Polynomial poly, final TreeMap<String, Integer> pairs) {
      
    return poly.simplify(pairs);
  }
  /**
   * .
   * @param poly .
   * @param var .
   * @return .
   */
  
  public static Polynomial derivative(final Polynomial poly,final String var) {
    
    return poly.derivative(var);
  }

  /**
   * .
   * @ param args
   */
  @SuppressWarnings("resource")
  public static void main(final String[] args) {
    Polynomial poly = new Polynomial();
    boolean isPoly = false;
    final Scanner scan = new Scanner(System.in);

    while (true) {
      System.out.print(">");
      final String command = scan.nextLine();
      //System.out.println("这里是第一次修改");
      if (command.toCharArray()[0] != '!') {
        try {
          poly = expression(command);
          System.out.println(poly);
          isPoly = true;
        } catch (ExpressionException ee) {
          System.out.println(ee.getMessage());
          isPoly = false;
        }
        continue;
      }
      
      if (!isPoly) {
        System.out.println("Please Input Polynomial");
        continue;
      }

      try {
        if (command.substring(0, 4).equals("!d/d")) {
          final String var = command.substring(4, command.length());
          final Polynomial ppTmp = derivative(poly, var);
          if (ppTmp.toString().equals("0")) {
            System.out.println("Var Not Found");
          } else {
            poly = ppTmp;
            System.out.println(poly);
          }
          continue;
        } else if (command.substring(0, 9).equals("!simplify")) {
          final Pattern pp1 = Pattern.compile("\\!simplify\\s*");
          final Matcher mm1 = pp1.matcher(command);  
          if (mm1.matches()) {
            System.out.println(poly);
            continue;
          }
          
          final TreeMap<String, Integer> pairs = new TreeMap<String, Integer>();
          
          final Pattern pp2 = Pattern.compile("\\!simplify(\\s+([a-zA-Z]+)\\s*=\\s*(\\d+))+\\s*");
          final Matcher mm2 = pp2.matcher(command);  
          if (!mm2.matches()) {
            System.out.println("Input Error");
            continue;
          }
          
          final Pattern pp3 = Pattern.compile("([a-zA-Z]+)\\s*=\\s*(\\d+)");
          final Matcher mm3 = pp3.matcher(command);  
          if (mm3.find()) {
            pairs.put(mm3.group(1), Integer.valueOf(mm3.group(2)));
          } else {
            System.out.println("Input Error");
            continue;
          }
          
          while (mm3.find()) {
            pairs.put(mm3.group(1), Integer.valueOf(mm3.group(2)));
          }
          
          poly = simplify(poly, pairs);
          System.out.println(poly);
          
          continue;
        } else {
          System.out.println("Unknown Command");
          continue;
        }
      } catch (IndexOutOfBoundsException ee) {
        ee.printStackTrace();
        System.out.println("Input Error");
        continue;
      }
    }
    
  }
  
}
