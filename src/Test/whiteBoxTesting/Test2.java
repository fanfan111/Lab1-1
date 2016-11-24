package Test.whiteBoxTesting;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xyz.luxin.java.secourse.Control;
import xyz.luxin.java.secourse.ExpressionException;


public class Test2 {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testDerivative() {
    /*
     * ≤‚ ‘”√¿˝2 ≤‚ ‘¥˙¬Î
     */
    String polyInputString = "x*y";
    String commandString = "!d/d z";
    String resultString = "Var Not Found";

    try {
      assertEquals(resultString, Control.derivative(polyInputString, commandString));
    } catch (ExpressionException e) {
      e.printStackTrace();
    }
  }
  
}
