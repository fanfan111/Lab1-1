package xyz.luxin.java.secourse;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Control {

  public static String expression(String exp) throws ExpressionException {

    Polynomial poly = new Polynomial();
    poly.expressionBracket(exp);
    return poly.toString();
  }

  public static String simplify(String polyString, TreeMap<String, Integer> pairs)
      throws ExpressionException {

    Polynomial poly = (new Polynomial(polyString, true)).simplify(pairs);
    return poly.toString();
  }

  public static String derivative(String polyString, String command) throws ExpressionException {

    Pattern p = Pattern.compile("\\s*([a-zA-Z]+)\\s*");
    Matcher m = p.matcher(command.substring(4, command.length()));  
    m.matches();
    String var = m.group(1);
    
    Polynomial poly = (new Polynomial(polyString, true)).derivative(var);
    if (poly.toString().equals("0")) {
      return "Var Not Found";
    } else {
      return poly.toString();
    }
  }
}
