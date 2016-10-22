package xyz.luxin.java.secourse;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * .
 * @author Bacon
 */

public class Polynomial extends AbstractExpression {
  /**
   * .
   */
  private transient TreeMap<Monomial, Integer> mmMonos;
  /**
   * .
   */
  
  public Polynomial() {
    super();
    mmMonos = new TreeMap<Monomial, Integer>();
  }
  /**
   * .
   * @param expString .
   * @param isNegEx .
   * @throws ExpressionException .
   */
  
  public Polynomial(final String expString,final boolean isNegEx) throws ExpressionException {
    super();
    mmMonos = new TreeMap<Monomial, Integer>();
    if (isNegEx) {
      expre1test(expString, true);
    } else {
      expre1test(expString, false);
    }
  }
  /**
   * .
   * @param expString .
   * @throws ExpressionException .
   */
  
  public Polynomial(final String expString) throws ExpressionException {
    //Does not support "Negative Extension"(#) by default.
    this(expString, false);
  }
  /**
   * .
   * @param mm0 .
   */
  
  public Polynomial(final Monomial mm0) {
    super();
    mmMonos = new TreeMap<Monomial, Integer>();
    mmMonos.put(mm0, mm0.constVaule);
  }
  /**
   * .
   * @param monos .
   */
  
  public Polynomial(final TreeMap<Monomial, Integer> monos) {
    super();
    mmMonos = monos;
  }
  
  
  /**
   * .
   * @ param expString
   * @ throws ExpressionException
   */
  public void expressionBracket(final String expString) throws ExpressionException {
    
    char[] chars = expString.toCharArray();

    
    //check character
    final Pattern pp1 = Pattern.compile("[^a-zA-Z0-9\\+\\-\\*\\^\\(\\)\\s]");
    final Matcher mm1 = pp1.matcher(expString);  
    String expString1 = expString;
    if (mm1.find()) {
      throw new ExpressionException("Unknown Character");
    }
    
    
    //check bracket
    int count = 0;
    for (int i = 0; i < expString1.length(); i++) {
      if (chars[i] == '(') {
        count++;
      }
      if (chars[i] == ')') {
        count--;
      }
      if (count < 0) {
        throw new ExpressionException("Bracket Error");
      }
    }
    if (count != 0) {
      throw new ExpressionException("Bracket Error");
    }
    
    
    //fix bracket *
    Pattern pp0;
    Matcher mm0;
    StringBuffer sb0;
    pp0 = Pattern.compile("(\\d+|[a-zA-Z]+)\\s*\\(");
    mm0 = pp0.matcher(expString1);  
    sb0 = new StringBuffer();
    while (mm0.find()) {
      final String str = mm0.group().replaceAll("\\(", "*(");
      mm0.appendReplacement(sb0, str);
    }
    mm0.appendTail(sb0);
    expString1 = sb0.toString();
    chars = expString1.toCharArray();
    
    pp0 = Pattern.compile("\\)\\s*(\\d+|[a-zA-Z]+)");
    mm0 = pp0.matcher(expString1);  
    sb0 = new StringBuffer();
    while (mm0.find()) {
      final String str = mm0.group().replaceAll("\\)", ")*");
      mm0.appendReplacement(sb0, str);
    }
    mm0.appendTail(sb0);
    expString1 = sb0.toString();
    chars = expString1.toCharArray();
    
    pp0 = Pattern.compile("\\)\\s*\\(");
    mm0 = pp0.matcher(expString1);  
    sb0 = new StringBuffer();
    while (mm0.find()) {
      final String str = mm0.group().replaceAll("\\)", ")*");
      mm0.appendReplacement(sb0, str);
    }
    mm0.appendTail(sb0);
    expString1 = sb0.toString();
    chars = expString1.toCharArray();
    
    
    //check - and fix - to +#*
    int realCount = 0;
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '-') {
        realCount++;
      }
    }
    
    int matchCount = 0;
    pp0 = Pattern.compile("(\\d+|[a-zA-Z]+|\\))\\s*\\-\\s*(\\d+|[a-zA-Z]+|\\()");
    mm0 = pp0.matcher(expString1);  
    sb0 = new StringBuffer();
    while (mm0.find()) {
      final String str = mm0.group().replaceAll("\\-", "+#*");
      mm0.appendReplacement(sb0, str);
      matchCount++;
    }
    mm0.appendTail(sb0);
    expString1 = sb0.toString();
    chars = expString1.toCharArray();
    
    if (realCount != matchCount) {
      throw new ExpressionException("Format Error");
    }

    
    final ExpressionTree tree = new ExpressionTree();
    ExpressionTree.createTree(tree, expString1);
    
    this.mmMonos = ExpressionTree.obtainPolynomial(tree).mmMonos;
  }
  
  /**
   * .
   * @ param expString
   * @ param isNegEx
   * @ throws ExpressionException
   */
  
  public final void expre1test(final String expString,final boolean isNegEx) 
      throws ExpressionException {
    
    if (!isNegEx) {
      //check character
      final Pattern pp0 = Pattern.compile("[^a-zA-Z0-9\\+\\-\\*\\^\\(\\)\\s]");
      final Matcher mm0 = pp0.matcher(expString);  
      if (mm0.find()) {
        throw new ExpressionException("Unknown Character");
      }
    }
    
    final String ppFactor = "(#|(\\d+\\^\\d+)|([a-zA-Z]+\\^\\d+)|(\\d+)|([a-zA-Z]+))";
    final String ppMonomial = "(" + ppFactor + "(\\s*(\\*)?\\s*" + ppFactor + ")*)";
    final String ppPolynomial = 
        "(\\s*(" + ppMonomial + "(\\s*[\\+\\-]\\s*" + ppMonomial + ")*)\\s*)";
    
    final Pattern pp0 = Pattern.compile(ppPolynomial);
    final Matcher mm0 = pp0.matcher(expString);  
    
    final Pattern p0p = Pattern.compile("([\\+\\-])");
    final Matcher m0p = p0p.matcher(expString);
    
    if (!mm0.matches()) {
      throw new ExpressionException("Format Error");
    }
    
      
    final Pattern pp1 = Pattern.compile(ppMonomial);
    final Matcher mm1 = pp1.matcher(expString);
    if (mm1.find()) {  
      final Monomial mono = new Monomial(mm1.group(0), false, isNegEx);
      
      if (mmMonos.containsKey(mono)) {
        //Map中已存在的单项式与get参数的单项式不是一个对象，两者仅系数不同
        final Integer nn0 = mmMonos.get(mono);
        mono.constVaule = mono.constVaule + nn0;
        mmMonos.remove(mono);
      }
      if (mono.constVaule != 0) {
        mmMonos.put(mono, mono.constVaule);
      }
    }
      
    while (mm1.find() && m0p.find()) {

      boolean isExtraNegative = false;

      if (m0p.group(0).equals("-")) {
        isExtraNegative = true;
      }
      
      final Monomial mono = new Monomial(mm1.group(0), isExtraNegative, isNegEx);
      
      if (mmMonos.containsKey(mono)) {
        //Map中已存在的单项式与get参数的单项式不是一个对象，两者仅系数不同
        final Integer nn0 = mmMonos.get(mono);
        mono.constVaule = mono.constVaule + nn0;
        mmMonos.remove(mono);
      } 
      if (mono.constVaule != 0) {
        mmMonos.put(mono, mono.constVaule);
      }
    }
  }
  
  
  /**
   * .
   * @ param expString
   * @ throws ExpressionException
   */
  public void expre(final String expString) throws ExpressionException {
    //Does not support "Negative Extension"(#) by default.
    expre1test(expString, false);
  }
  
  /**
   * .
   * @ param pairs
   * @ return
   */
  public Polynomial simplify(final TreeMap<String, Integer> pairs) {
    
    final TreeMap<Monomial, Integer> result = new TreeMap<Monomial, Integer>();
    
    final Iterator<Entry<Monomial, Integer>> it0 = mmMonos.entrySet().iterator();
    while (it0.hasNext()) {
      final Entry<Monomial, Integer> entry = (Entry<Monomial, Integer>)it0.next();
      final Monomial mm0 = entry.getKey().simplify(pairs);

      if (result.containsKey(mm0)) {
        //Map中已存在的单项式与get参数的单项式不是一个对象，两者仅系数不同
        final Integer nn0 = result.get(mm0);
        mm0.constVaule = mm0.constVaule + nn0;
        result.remove(mm0);
      }
      if (mm0.constVaule != 0) {
        result.put(mm0, mm0.constVaule);
      }
    }

    return new Polynomial(result);
  }
  
  /**
   * .
   * @ param var
   * @ return
   */
  public Polynomial derivative(final String var) {

    final TreeMap<Monomial, Integer> result = new TreeMap<Monomial, Integer>();
    
    final Iterator<Entry<Monomial, Integer>> it0 = mmMonos.entrySet().iterator();
    while (it0.hasNext()) {
      final Entry<Monomial, Integer> entry = (Entry<Monomial, Integer>)it0.next();
      final Monomial mm0 = entry.getKey().derivative(var);
      
      if (result.containsKey(mm0)) {
        //Map中已存在的单项式与get参数的单项式不是一个对象，两者仅系数不同
        final Integer nn0 = result.get(mm0);
        mm0.constVaule = mm0.constVaule + nn0;
        result.remove(mm0);
      }
      if (mm0.constVaule != 0) {
        result.put(mm0, mm0.constVaule);
      }
    }

    return new Polynomial(result);
  }
  
  /**
   * .
   * @ param p1
   * @ param p2
   * @ return
   */
  public static Polynomial add(final Polynomial pp1,final Polynomial pp2) {
    
    final TreeMap<Monomial, Integer> result = new TreeMap<Monomial, Integer>();
    
    final Iterator<Entry<Monomial, Integer>> p1it = pp1.mmMonos.entrySet().iterator();
    while (p1it.hasNext()) {
      final Entry<Monomial, Integer> entry = (Entry<Monomial, Integer>)p1it.next();
      final Monomial mm0 = new Monomial(entry.getKey());
      
      if (result.containsKey(mm0)) {
        //Map中已存在的单项式与get参数的单项式不是一个对象，两者仅系数不同
        final Integer nn0 = result.get(mm0);
        mm0.constVaule = mm0.constVaule + nn0;
        result.remove(mm0);
      }
      if (mm0.constVaule != 0) {
        result.put(mm0, mm0.constVaule);
      }
    }
    //System.out.println(p2.toString());
    final Iterator<Entry<Monomial, Integer>> p2it = pp2.mmMonos.entrySet().iterator();
    while (p2it.hasNext()) {
      final Entry<Monomial, Integer> entry = (Entry<Monomial, Integer>)p2it.next();
      final Monomial mm0 = new Monomial(entry.getKey());

      if (result.containsKey(mm0)) {
        //Map中已存在的单项式与get参数的单项式不是一个对象，两者仅系数不同
        final Integer nn0 = result.get(mm0);
        mm0.constVaule = mm0.constVaule + nn0;
        result.remove(mm0);
      }
      if (mm0.constVaule != 0) {
        result.put(mm0, mm0.constVaule);
      }
    }
    return new Polynomial(result);
  }

  /**
   * .
   * @ param p1
   * @ param p2
   * @ return
   */
  public static Polynomial multiplication(final Polynomial pp1,final Polynomial pp2) {
    
    final TreeMap<Monomial, Integer> result = new TreeMap<Monomial, Integer>();
      
    final Iterator<Entry<Monomial, Integer>> p1it = pp1.mmMonos.entrySet().iterator();
    while (p1it.hasNext()) {
      final Entry<Monomial, Integer> p1Entry = (Entry<Monomial, Integer>)p1it.next();
      final Monomial mm1 = p1Entry.getKey();
      //System.out.println(m1.toString());
      final Iterator<Entry<Monomial, Integer>> p2it = pp2.mmMonos.entrySet().iterator();
      while (p2it.hasNext()) {
        final Entry<Monomial, Integer> p2Entry = (Entry<Monomial, Integer>)p2it.next();
        final Monomial mm2 = mm1.multiplication(p2Entry.getKey());

        if (result.containsKey(mm2)) {
          //Map中已存在的单项式与get参数的单项式不是一个对象，两者仅系数不同
          final Integer nn0 = result.get(mm2);
          mm2.constVaule = mm2.constVaule + nn0;
          result.remove(mm2);
        }
        if (mm2.constVaule != 0) {
          result.put(mm2, mm2.constVaule);
        }
      }
    }
    return new Polynomial(result);
  }
  
  /**
   * .
   * @ param p1
   * @ param p2
   * @ param op
   * @ return
   * @ throws ExpressionException
   */
  public static Polynomial arithmetic(final Polynomial pp01,final Polynomial pp2,
      final Operator oop) 
      throws ExpressionException {
    //coding...
    Polynomial p33 = pp2;
    Polynomial pp3 = new Polynomial();
    if (oop.toString().compareTo("+") == 0) {
      pp3 = add(pp01,p33);
    } else if (oop.toString().compareTo("*") == 0) {
      pp3 = multiplication(pp01,p33);
    } else if (oop.toString().compareTo("-") == 0) {
      p33 = multiplication(new Polynomial("1-2"),p33);
      pp3 = add(pp01,p33);
    } else {
      final String integer = "(\\d+)";
      
      final Pattern pp1 = Pattern.compile(integer);
      final Matcher mm0 = pp1.matcher(p33.toString());
      
      if (!mm0.matches()) {
        throw new ExpressionException("Power Non Integer");
      }
      
      final int power = Integer.parseInt(p33.toString());
      Polynomial ppTempt = new Polynomial(pp01.toString());
      
      for (int i = 0 ; i < power - 1; i++) {
        ppTempt = multiplication(ppTempt,pp01);
      }
      pp3 = ppTempt;
    }
    return pp3;
  }
  /**
   * .
   */
  
  @Override
  public String toString() {
    
    if (mmMonos.isEmpty()) {
      return "0";
    }
    
    String result = "";
    
    final Iterator<Entry<Monomial, Integer>> it0 = mmMonos.entrySet().iterator();
    while (it0.hasNext()) {
      final Entry<Monomial, Integer> entry = (Entry<Monomial, Integer>)it0.next();
      final Monomial mm0 = entry.getKey();
      if (mm0.constVaule < 0) {
        result = result + mm0.toString();
      } else if (mm0.constVaule > 0) {
        result = result + "+" + mm0.toString();
      }
    }
    
    if (result.toCharArray()[0] == '+') {
      result = result.substring(1, result.length());
    }
    
    return result;
  }
}
