package Test.whiteBoxTesting;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xyz.luxin.java.secourse.ExpressionException;
import xyz.luxin.java.secourse.Lab1;


public class Test3 {
  private static Lab1 testLab1 = new Lab1();

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testDerivative() {
    /*
     * ≤‚ ‘”√¿˝3 ≤‚ ‘¥˙¬Î
     */
    String polyInputString = "x*z";
    String commandString = "!d/d z";
    String resultString = "x";

    try {
      assertEquals(resultString, testLab1.derivative(polyInputString, commandString));
    } catch (ExpressionException e) {
      e.printStackTrace();
    }
  }

}
