package blackBoxTesting;

import static org.junit.Assert.*;
import org.junit.Test;

import xyz.luxin.java.secourse.ExpressionException;
import xyz.luxin.java.secourse.Lab1;

import org.junit.After;
import org.junit.Before;

public class Test6 {
	private static Lab1 testLab1=new Lab1();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testExpression(){
		/*
		 * 测试用例6  测试代码
		 */
		String inputString ="(67fan^IAmNotAnInteger+"+
		"	(888y+  We are not the same variable+(45"+
		"x^2+(13lx^7y^12+(67x*x+(888y*x+(45zoo^9+(13"+
		"x^9y^12+		(67x^11+(888y^2+(45x^5+(13x^"+
		"7y^11+(67x*x*x+   (888y*y*x+(45x^10+(13x^19"+
		"y^12))))))))))))))))";
		try {
			testLab1.expression(inputString,testLab1.poly);
		} catch (ExpressionException e) { 
	        assertTrue(e.getMessage().equals("Format Error")); 
		}
	}	



}
