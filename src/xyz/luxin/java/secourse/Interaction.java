package xyz.luxin.java.secourse;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interaction {

  @SuppressWarnings("resource")
  public static void run() {

    String polyString = "";
    boolean isPoly = false;

    while (true) {
      System.out.print(">");
      Scanner sc = new Scanner(System.in);
      String command = sc.nextLine();
      
      if (command.length()==0) {
        System.out.println("Please Input Command");
        continue;
      }
      
      if ((command.toCharArray())[0] != '!') {
        try {
          polyString = Control.expression(command);
          System.out.println(polyString);
          isPoly = true;
        } catch (ExpressionException e) {
          System.out.println(e.getMessage());
          isPoly = false;
        }
        continue;
      }

      if (!isPoly) {
        System.out.println("Please Input Polynomial");
        continue;
      }

      try {
        if (command.length() < 5) {
          System.out.println("Input Error");
          continue;
        }
        
        if (command.substring(0, 4).equals("!d/d")) {
          Pattern p = Pattern.compile("\\s*([a-zA-Z]+)\\s*");
          Matcher m = p.matcher(command.substring(4, command.length()));  
          if (!m.matches()) {
            System.out.println("Input Error");
            continue;
          }
          
          try {
            String polyTmp = Control.derivative(polyString, command);
            if (polyTmp.equals("Var Not Found")) {
              System.out.println("Var Not Found");
            } else {
              polyString = polyTmp;
              System.out.println(polyString);
            }
          } catch (ExpressionException e) {
            System.out.println(e.getMessage());
            continue;
          }
          
          continue;
        } else if (command.substring(0, 9).equals("!simplify")) {
          Pattern p1 = Pattern.compile("\\!simplify\\s*");
          Matcher m1 = p1.matcher(command);
          if (m1.matches()) {
            System.out.println(polyString);
            continue;
          }

          TreeMap<String, Integer> pairs = new TreeMap<String, Integer>();

          Pattern p2 = Pattern.compile("\\!simplify(\\s+([a-zA-Z]+)\\s*=\\s*(\\d+))+\\s*");
          Matcher m2 = p2.matcher(command);
          if (!m2.matches()) {
            System.out.println("Input Error");
            continue;
          }

          Pattern p3 = Pattern.compile("([a-zA-Z]+)\\s*=\\s*(\\d+)");
          Matcher m3 = p3.matcher(command);
          if (m3.find()) {
            pairs.put(m3.group(1), Integer.valueOf(m3.group(2)));
          } else {
            System.out.println("Input Error");
            continue;
          }

          while (m3.find()) {
            pairs.put(m3.group(1), Integer.valueOf(m3.group(2)));
          }

          try {
            polyString = Control.simplify(polyString, pairs);
          } catch (ExpressionException e) {
            System.out.println(e.getMessage());
            continue;
          }
          System.out.println(polyString);

          continue;
        } else {
          System.out.println("Unknown Command");
          continue;
        }
      } catch (IndexOutOfBoundsException e) {
        System.out.println("Input Error");
        continue;
      }
    }

  }
}
