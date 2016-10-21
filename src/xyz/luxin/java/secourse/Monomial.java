package xyz.luxin.java.secourse;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * .
 * @author .
 */

public class Monomial extends AbstractExpression implements Comparable<Monomial> {

  /**
   * .
   */
  public transient int constVaule;
  /**
   * .
   */
  public transient int varNumber;
  /**
   * .
   */
  public transient TreeMap<String, Integer> varIndex;
  /**
   * .
   */
  public transient int monIndex;
  
  /**
   * @ javadoc.
   */
  
  public Monomial() {
    super();
    constVaule = 1;
    varNumber = 0;
    varIndex = new TreeMap<String, Integer>();
    monIndex = 0;
  }
  /**
   * @ param.
   */

  public Monomial(final Monomial ooo) {
    super();
    this.constVaule = ooo.constVaule;
    this.varNumber = ooo.varNumber;
    this.varIndex = new TreeMap<String, Integer>(ooo.varIndex);
    this.monIndex = ooo.monIndex;
  }
  /**
   * .
   * @ param expString
   * @ param isExtraNegative
   * @ param isNegEx
   * @ throws ExpressionException
   */
  
  public Monomial(final String expString,final boolean isExtraNegative,final boolean isNegEx) 
      throws ExpressionException {
    super();
    if (!isNegEx) {
      //check character
      final Pattern pp0 = Pattern.compile("[^a-zA-Z0-9\\+\\-\\*\\^\\(\\)\\s]");
      final Matcher mm0 = pp0.matcher(expString);  
      if (mm0.find()) {
        throw new ExpressionException("Unknown Character");
      }
    }
    //System.out.println("这里是第一次修改");
    constVaule = 1;
    varNumber = 0;
    varIndex = new TreeMap<String, Integer>();
    monIndex = 0;
    
    if (isExtraNegative) {
      constVaule = -1;
    }
    
    final String ppFactor = "(#|(\\d+\\^\\d+)|([a-zA-Z]+\\^\\d+)|(\\d+)|([a-zA-Z]+))";
    final String ppMonomial = "(" + ppFactor + "(\\s*(\\*)?\\s*" + ppFactor + ")*)";
    
    final Pattern pp1 = Pattern.compile(ppMonomial);
    final Matcher mm1 = pp1.matcher(expString);
    
    if (!mm1.matches()) {
      throw new ExpressionException("Format Error");
    }
    
    
    final Pattern pp2 = Pattern.compile(ppFactor);
    final Matcher mm2 = pp2.matcher(expString);
    
    while (mm2.find()) {
      final String str = mm2.group(0);
      final char[] chars = str.toCharArray();
      if (chars[0] == '#') {
        constVaule *= -1;
      } else if (chars[0] >= '0' && chars[0] <= '9') {
        //number
        if (str.contains("^")) {
          final String[] nums = str.split("\\^");
          if (nums.length != 2) {
            throw new ExpressionException("Format Error");
          }
          
          constVaule *= (int)Math.pow(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
        } else {
          constVaule *= Integer.parseInt(str);
        }
      } else {
        //var
        if (str.contains("^")) {
          final String[] pair = str.split("\\^");
          if (pair.length != 2) {
            throw new ExpressionException("Format Error");
          }
          
          if (varIndex.containsKey(pair[0])) {
            varIndex.replace(pair[0], varIndex.get(pair[0]) + Integer.parseInt(pair[1]));
          } else {
            varIndex.put(pair[0], Integer.parseInt(pair[1]));
          }
          
          monIndex += Integer.parseInt(pair[1]);
          
        } else {
          if (varIndex.containsKey(str)) {
            varIndex.replace(str, varIndex.get(str) + 1);
          } else {
            varIndex.put(str, 1);
          }
          
          monIndex++;
        }
      }
    }
    
    varNumber = varIndex.size();
    
    return;
  }
  /**
   * .
   * @param expString .
   * @param isExtraNegative .
   * @throws ExpressionException .
   */
  
  public Monomial(final String expString,final boolean isExtraNegative) throws ExpressionException {
    //Does not support "Negative Extension"(#) by default.
    this(expString, isExtraNegative, false);
  }
  /** .
   * @ param pairs
   * @ return
   */
  
  public Monomial simplify(final TreeMap<String, Integer> pairs) {
    
    Monomial result = new Monomial(this);
    
    final Iterator<Entry<String, Integer>> iit = pairs.entrySet().iterator();
    while (iit.hasNext()) {
      final Entry<String, Integer> entry = (Entry<String, Integer>)iit.next();
      final String key = entry.getKey();
      final Integer value = entry.getValue();
      
      if (result.varIndex.containsKey(key)) {
        final Integer index = result.varIndex.get(key);
        result.varIndex.remove(key);
        result.varNumber--;
        result.monIndex -= index;
        for (int i = 0; i < index; i++) {
          result.constVaule *= value;
        }
      }
    }
    
    return result;
  }
  
  /** .
   * @ param var
   * @ return
   */
  
  public Monomial derivative(final String var) {
    
    Monomial result = new Monomial();

    if (!varIndex.containsKey(var)) {
      result.constVaule = 0;
      return result;
    }
    
    
    result = new Monomial(this);
    
    if (result.varIndex.get(var) == 1) {
      result.varIndex.remove(var);
      result.varNumber--;
      result.monIndex--;
      return result;
    }
    
    result.monIndex--;
    result.constVaule = result.constVaule * result.varIndex.get(var);
    result.varIndex.replace(var, result.varIndex.get(var) - 1);
    return result;
  }
  
  /** .
   * @ param a
   * @ return
   */
  
  public Monomial multiplication(final Monomial aa0) {
    Monomial result1 = new Monomial();
    result1.constVaule = this.constVaule * aa0.constVaule;
    result1.monIndex = 0;
    final TreeMap<String, Integer> result = new TreeMap<String,Integer>();

    final Iterator<Entry<String, Integer>> it0 = this.varIndex.entrySet().iterator();
    while (it0.hasNext()) {
      final Entry<String, Integer> entry = (Entry<String, Integer>)it0.next();
      final String mm0 = entry.getKey();
      int nnAll = this.varIndex.get(mm0);
      result1.monIndex += nnAll;
      if (result.containsKey(mm0)) {
        //Map中已存在的单项式与get参数的单项式不是一个对象，两者仅系数不同
        final Integer nn0 = result.get(mm0);
        nnAll += nn0;
        result.remove(mm0);
      }
      if (nnAll != 0) {
        result.put(mm0, nnAll);
      }
    }

    final Iterator<Entry<String, Integer>> it1 = aa0.varIndex.entrySet().iterator();
    while (it1.hasNext()) {
      final Entry<String, Integer> entry = (Entry<String, Integer>)it1.next();
      final String mm0 = entry.getKey();
      int nnAll = aa0.varIndex.get(mm0);
      result1.monIndex += nnAll;
      if (result.containsKey(mm0)) {
        //Map中已存在的单项式与get参数的单项式不是一个对象，两者仅系数不同
        final Integer nn0 = result.get(mm0);
        nnAll += nn0;
        result.remove(mm0);
      }
      if (nnAll != 0) {
        result.put(mm0, nnAll);
      }
    }
    
    result1.varNumber = result.size();
    result1.varIndex = result;


    return result1;
    
  }
  /**
   * .
   */
  
  @Override
  public String toString() {
    
    if (varIndex.isEmpty()) {
      return String.valueOf(constVaule);
    }
    
    String result = "";
    
    if (constVaule == -1) {
      result += "-";
    } else if (constVaule != 1) {
      result += String.valueOf(constVaule);
      result += "*";
    }
    
    
    final Iterator<Entry<String, Integer>> it0 = varIndex.entrySet().iterator();
    while (it0.hasNext()) {
      final Entry<String, Integer> entry = (Entry<String, Integer>)it0.next();
      final String key = entry.getKey();
      final Integer value = entry.getValue();
      if (value == 1) {
        result = result + key + "*";
      } else {
        result = result + key + "^" + value + "*";
      }
    }
      
    if (result.toCharArray()[result.length() - 1] == '*') {
      result = result.substring(0, result.length() - 1);
    }
    
    return result;
  }
  /**
   * .
   */
  
  @Override
  public int compareTo(final Monomial oo0) {
    
    if (this.monIndex > oo0.monIndex) {
      return 1;
    } else if (this.monIndex < oo0.monIndex) {
      return -1;
    } else {
      if (this.varNumber > oo0.varNumber) {
        return 1;
      } else if (this.varNumber < oo0.varNumber) {
        return -1;
      } else {
        Iterator<Entry<String, Integer>> it1 = this.varIndex.entrySet().iterator();
        Iterator<Entry<String, Integer>> it2 = oo0.varIndex.entrySet().iterator();
        while (it1.hasNext() && it2.hasNext()) {
          final Entry<String, Integer> entry1 = (Entry<String, Integer>)it1.next();
          final Entry<String, Integer> entry2 = (Entry<String, Integer>)it2.next();
          final String key1 = entry1.getKey();
          final String key2 = entry2.getKey();
          if (key1.compareTo(key2) > 0) {
            return 1;
          } else if (key1.compareTo(key2) < 0) {
            return -1;
          }
        }
        
        //fix same monIndex, same varNumber, same vars, different varIndex
        it1 = this.varIndex.entrySet().iterator();
        it2 = oo0.varIndex.entrySet().iterator();
        while (it1.hasNext() && it2.hasNext()) {
          final Entry<String, Integer> entry1 = (Entry<String, Integer>)it1.next();
          final Entry<String, Integer> entry2 = (Entry<String, Integer>)it2.next();
          final Integer index1 = entry1.getValue();
          final Integer index2 = entry2.getValue();
          if (index1 > index2) {
            return 1;
          } else if (index1 < index2) {
            return -1;
          }
        }
        
        return 0;
      }
    }
  }
}