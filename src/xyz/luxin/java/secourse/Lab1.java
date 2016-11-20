package xyz.luxin.java.secourse;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Lab1 {
	public static Polynomial poly = new Polynomial();
	public static String expression(String exp,Polynomial poly) throws ExpressionException {
		
		poly.expressionBracket(exp);
		return poly.toString();
	}
	
	public static Polynomial simplify(Polynomial poly, TreeMap<String, Integer> pairs) {
			
		return poly.simplify(pairs);
	}
	
	public static String derivative(String polyInput, String command) 
			throws ExpressionException {
		String var = command.substring(5, command.length());
		Polynomial pTmp = poly.derivative(var);
		if (pTmp.toString().equals("0")) {
			return "Var Not Found";
		} else {
			poly = pTmp;
		}
		return poly.toString();
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		boolean isPoly = false;
		while (true) {
			System.out.print(">");
			Scanner sc = new Scanner(System.in);
			String command = sc.nextLine();
			if ((command.toCharArray())[0] != '!') {
				try {
					expression(command,poly);
					System.out.println(poly);
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
				if (command.substring(0, 5).equals("!d/d ")) {
					try {
						System.out.println(derivative(poly.toString(),command));
					} catch (ExpressionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
					continue;
				} else if (command.substring(0, 9).equals("!simplify")) {
					Pattern p1 = Pattern.compile("\\!simplify\\s*");
					Matcher m1 = p1.matcher(command);	
					if (m1.matches()) {
						System.out.println(poly);
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
					
					poly = simplify(poly, pairs);
					System.out.println(poly);
					
					continue;
				} else {
					System.out.println("Unknown Command");
					continue;
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				System.out.println("Input Error");
				continue;
			}
		}
		
	}
	
}
