package blackBoxTesting;

import static org.junit.Assert.*;
import org.junit.Test;

import xyz.luxin.java.secourse.ExpressionException;
import xyz.luxin.java.secourse.Lab1;

import org.junit.After;
import org.junit.Before;

public class Test5 {
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
		 * 测试用例5  测试代码
		 */
		String inputString ="(67fan^(1+3)+WeAreNotTheSameVa"+
		"riable+	(888y+  We are not the same variable+("+
		"45x^2+(13lx^7y^12+(67x*x+(888y*x+(45zoo^9+(13x^9y"+
		"^12+		(67x^11+(888y^2+(45x^5+(13x^7y^11+(67x*"+
		"x*x+   (888y*y*x+(45x^10+(13x^19y^12))))))))))))))))";
		String resultString = "WeAreNotTheSameVariable+888*y"+
		"+112*x^2+888*y^2+888*x*y+67*x^3+888*x*y^2+67*fan^4+"+
		"45*x^5+We*are*not*same*the*variable+45*zoo^9+45*x^"+
		"10+67*x^11+13*x^7*y^11+13*lx^7*y^12+13*x^9*y^12+13*"+
		"x^19*y^12";
      try {
			assertEquals(resultString,testLab1
				.expression(inputString,testLab1.poly));
		} catch (ExpressionException e) {
			e.printStackTrace();
		}
	}
	


}
