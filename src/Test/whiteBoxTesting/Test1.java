package Test.whiteBoxTesting;

import static org.junit.Assert.*;
import org.junit.Test;

import xyz.luxin.java.secourse.Control;
import xyz.luxin.java.secourse.ExpressionException;

import org.junit.After;
import org.junit.Before;


public class Test1 {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testDerivative() {
    /*
     * ��������1 ���Դ���
     */
    String polyInputString = "";
    String commandString = "!d/d x";
    String resultString = "Var Not Found";

    try {
      assertEquals(resultString, Control.derivative(polyInputString, commandString));
    } catch (ExpressionException e) {
      e.printStackTrace();
    }
  }

}
