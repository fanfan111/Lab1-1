package Test.blackBoxTesting;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xyz.luxin.java.secourse.Control;
import xyz.luxin.java.secourse.ExpressionException;


public class Test3 {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExpression(){
		/*
		 * ≤‚ ‘”√¿˝3  ≤‚ ‘¥˙¬Î
		 */
		String inputString ="-(67fan^(1+3)+	(888y+"+
		"  We are not the same variable+(45x^2+(13l"+
		"x^7y^12+(67x*x+(888y*x+(45zoo^9+(13x^9y^12+"+
		"		(67x^11+(888y^2+(45x^5+(13x^7y^11+"+
		"(67x*x*x+   (888y*y*x+(45x^10+(13x^19y^12))"+
		"))))))))))))))";
		try {
		    Control.expression(inputString);
		} catch (ExpressionException e) { 
	        assertTrue(e.getMessage().equals("Format Error")); 
		}
	}

}
