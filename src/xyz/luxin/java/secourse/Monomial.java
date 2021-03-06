package xyz.luxin.java.secourse;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Monomial extends Expression implements Comparable<Monomial> {
	
	public int constVaule;
	public int varNumber;
	public TreeMap<String, Integer> varIndex;
	public int monIndex;
	
	
	public Monomial() {
		constVaule = 1;
		varNumber = 0;
		varIndex = new TreeMap<String, Integer>();
		monIndex = 0;
	}
	
	public Monomial(Monomial o) {
		this.constVaule = o.constVaule;
		this.varNumber = o.varNumber;
		this.varIndex = new TreeMap<String, Integer>(o.varIndex);
		this.monIndex = o.monIndex;
	}
	
	public Monomial(String expString, boolean isExtraNegative, boolean isNegEx) throws ExpressionException {
		
		if (!isNegEx) {
			//check character
			Pattern p0 = Pattern.compile("[^a-zA-Z0-9\\+\\-\\*\\^\\(\\)\\s]");
			Matcher m0 = p0.matcher(expString);	
			if (m0.find()) {
				throw new ExpressionException("Unknown Character");
			}
		}
		
		constVaule = 1;
		varNumber = 0;
		varIndex = new TreeMap<String, Integer>();
		monIndex = 0;
		
		if (isExtraNegative) {
			constVaule = -1;
		}
		
		String pFactor = "(#|(\\d+\\^\\d+)|([a-zA-Z]+\\^\\d+)|(\\d+)|([a-zA-Z]+))";
		String pMonomial = "(" + pFactor + "(\\s*(\\*)?\\s*" + pFactor + ")*)";
		
		Pattern p1 = Pattern.compile(pMonomial);
		Matcher m1 = p1.matcher(expString);
		
		if (!m1.matches()) {
			throw new ExpressionException("Format Error");
		}
		
		
		Pattern p2 = Pattern.compile(pFactor);
		Matcher m2 = p2.matcher(expString);
		
		while (m2.find()) {
			String str = m2.group(0);
			char[] chars = str.toCharArray();
			if (chars[0]=='#') {
				constVaule *= -1;
			} else if (chars[0]>='0' && chars[0]<='9') {
				//number
				if (str.contains("^")) {
					String[] nums = str.split("\\^");
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
					String[] pair = str.split("\\^");
					if (pair.length != 2) {
						throw new ExpressionException("Format Error");
					}
					
					if (varIndex.containsKey(pair[0])) {
						varIndex.replace(pair[0], varIndex.get(pair[0])+Integer.parseInt(pair[1]));
					} else {
						varIndex.put(pair[0], Integer.parseInt(pair[1]));
					}
					
					monIndex += Integer.parseInt(pair[1]);
					
				} else {
					if (varIndex.containsKey(str)) {
						varIndex.replace(str, varIndex.get(str)+1);
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
	
	public Monomial(String expString, boolean isExtraNegative) throws ExpressionException {
		//Does not support "Negative Extension"(#) by default.
		this(expString, isExtraNegative, false);
	}
	
	public Monomial simplify(TreeMap<String, Integer> pairs) {
		
		Monomial result = new Monomial(this);
		
		Iterator<Entry<String, Integer>> it = pairs.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Integer> entry = (Entry<String, Integer>)it.next();
			String key = entry.getKey();
			Integer value = entry.getValue();
			
			if (result.varIndex.containsKey(key)) {
				Integer index = result.varIndex.get(key);
				result.varIndex.remove(key);
				result.varNumber--;
				result.monIndex -= index;
				for (int i=0; i<index; i++) {
					result.constVaule *= value;
				}
			}
		}
		
		return result;
	}
	
	public Monomial derivative(String var) {
		
		Monomial result = new Monomial();
		if (!varIndex.containsKey(var)) {
			result.constVaule = 0;
			return result;
		}	
		result = new Monomial(this);
		if (result.varIndex.get(var)==1) {
			result.varIndex.remove(var);
			result.varNumber--;
			result.monIndex--;
			return result;
		}
		result.monIndex--;
		result.constVaule = result.constVaule * result.varIndex.get(var);
		result.varIndex.replace(var, result.varIndex.get(var)-1);
		return result;
	}
	
	public Monomial multiplication(Monomial a){
		Monomial result1 = new Monomial();
		result1.constVaule = this.constVaule*a.constVaule;
		result1.monIndex = 0;
		TreeMap<String, Integer>result = new TreeMap<String, Integer>();

		Iterator<Entry<String, Integer>> it = this.varIndex.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Integer> entry = (Entry<String, Integer>)it.next();
			String m = entry.getKey();
			int nAll = this.varIndex.get(m);
			result1.monIndex += nAll;
			if (result.containsKey(m)) {
				//Map���Ѵ��ڵĵ���ʽ��get�����ĵ���ʽ����һ���������߽�ϵ����ͬ
				Integer n = result.get(m);
				nAll += n;
				result.remove(m);
			}
			if (nAll != 0) {
				result.put(m, nAll);
			}
		}

		Iterator<Entry<String, Integer>> it1 = a.varIndex.entrySet().iterator();
		while (it1.hasNext()) {
			Entry<String, Integer> entry = (Entry<String, Integer>)it1.next();
			String m = entry.getKey();
			int nAll = a.varIndex.get(m);
			result1.monIndex += nAll;
			if (result.containsKey(m)) {
				//Map���Ѵ��ڵĵ���ʽ��get�����ĵ���ʽ����һ���������߽�ϵ����ͬ
				Integer n = result.get(m);
				nAll += n;
				result.remove(m);
			}
			if (nAll != 0) {
				result.put(m, nAll);
			}
		}
		
		result1.varNumber = result.size();
		result1.varIndex = result;


		return result1;
		
	}
	
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
		
		
		Iterator<Entry<String, Integer>> it = varIndex.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Integer> entry = (Entry<String, Integer>)it.next();
			String key = entry.getKey();
			Integer value = entry.getValue();
			if (value==1) {
				result = result + key + "*";
			} else {
				result = result + key + "^" + String.valueOf(value) + "*";
			}
		}
			
		if ((result.toCharArray())[result.length()-1]=='*') {
			result = result.substring(0, result.length()-1);
		}
		
		return result;
	}

	@Override
	public int compareTo(Monomial o) {
		
		if (this.monIndex > o.monIndex) {
			return 1;
		} else if (this.monIndex < o.monIndex) {
			return -1;
		} else {
			if (this.varNumber > o.varNumber) {
				return 1;
			} else if (this.varNumber < o.varNumber) {
				return -1;
			} else {
				Iterator<Entry<String, Integer>> it1 = this.varIndex.entrySet().iterator();
				Iterator<Entry<String, Integer>> it2 = o.varIndex.entrySet().iterator();
				while (it1.hasNext() && it2.hasNext()) {
					Entry<String, Integer> entry1 = (Entry<String, Integer>)it1.next();
					Entry<String, Integer> entry2 = (Entry<String, Integer>)it2.next();
					String key1 = entry1.getKey();
					String key2 = entry2.getKey();
					if (key1.compareTo(key2) > 0) {
						return 1;
					} else if (key1.compareTo(key2) < 0) {
						return -1;
					}
				}
				
				//fix same monIndex, same varNumber, same vars, different varIndex
				it1 = this.varIndex.entrySet().iterator();
				it2 = o.varIndex.entrySet().iterator();
				while (it1.hasNext() && it2.hasNext()) {
					Entry<String, Integer> entry1 = (Entry<String, Integer>)it1.next();
					Entry<String, Integer> entry2 = (Entry<String, Integer>)it2.next();
					Integer index1 = entry1.getValue();
					Integer index2 = entry2.getValue();
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