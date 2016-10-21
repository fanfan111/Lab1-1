package xyz.luxin.java.secourse;
/**
 * .
 * @author .
 */

public class Operator extends AbstractExpression {
  /**
   * .
   */
  public transient char op0;

  /**
   * @ param op.
   * @ throws ExpressionException
   */

  public Operator(final char op0) throws ExpressionException {
    super();
    if (op0 == '+' || op0 == '-' || op0 == '*' || op0 == '^') {
      this.op0 = op0;
    } else {
      throw new ExpressionException("Operator Illegal");
    }
  }
  /**
   * .
   */
  
  @Override
  public String toString() {
    return String.valueOf(op0);
  }
}