package Test.whiteBoxTesting;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xyz.luxin.java.secourse.Control;
import xyz.luxin.java.secourse.ExpressionException;


public class Test3 {

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
      assertEquals(resultString, Control.derivative(polyInputString, commandString));
    } catch (ExpressionException e) {
      e.printStackTrace();
    }
  }

}
