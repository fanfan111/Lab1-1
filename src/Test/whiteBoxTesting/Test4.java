package Test.whiteBoxTesting;

import static org.junit.Assert.*;
import org.junit.Test;

import xyz.luxin.java.secourse.ExpressionException;
import xyz.luxin.java.secourse.Lab1;

import org.junit.After;
import org.junit.Before;


public class Test4 {
  private static Lab1 testLab1 = new Lab1();

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testDerivative() {
    /*
     * ≤‚ ‘”√¿˝4 ≤‚ ‘¥˙¬Î
     */
    String polyInputString = "z*z";
    String commandString = "!d/d z";
    String resultString = "2*z";

    try {
      assertEquals(resultString, testLab1.derivative(polyInputString, commandString));
    } catch (ExpressionException e) {
      e.printStackTrace();
    }
  }

}
