package Test.blackBoxTesting;

import static org.junit.Assert.*;
import org.junit.Test;

import xyz.luxin.java.secourse.Control;
import xyz.luxin.java.secourse.ExpressionException;

import org.junit.After;
import org.junit.Before;

public class Test6 {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testExpression(){
		/*
		 * ≤‚ ‘”√¿˝6  ≤‚ ‘¥˙¬Î
		 */
		String inputString ="(67fan^IAmNotAnInteger+"+
		"	(888y+  We are not the same variable+(45"+
		"x^2+(13lx^7y^12+(67x*x+(888y*x+(45zoo^9+(13"+
		"x^9y^12+		(67x^11+(888y^2+(45x^5+(13x^"+
		"7y^11+(67x*x*x+   (888y*y*x+(45x^10+(13x^19"+
		"y^12))))))))))))))))";
		try {
		    Control.expression(inputString);
		} catch (ExpressionException e) { 
	        assertTrue(e.getMessage().equals("Format Error")); 
		}
	}	



}
