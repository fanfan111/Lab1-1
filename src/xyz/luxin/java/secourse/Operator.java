package xyz.luxin.java.secourse;

public class Operator extends Expression {
	
	public char op;
	
	public Operator(char op) throws ExpressionException {
		if (op=='+' || op=='-' || op=='*' || op=='^') {
			this.op = op;
		} else {
			System.out.println("�����Ƕ�B1���޸�");
			throw new ExpressionException("Operator Illegal");
		}
	}
	
	@Override
	public String toString() {
		return String.valueOf(op);
	}
}