package Test;
  
import org.junit.runner.RunWith;  
import org.junit.runners.Suite;  
import org.junit.runners.Suite.SuiteClasses;  
  
//RunWith表示这个类是一个suite的类  
@RunWith(Suite.class)  
//说明这个类包含哪些测试组件 
@SuiteClasses({Test.blackBoxTesting.Test1.class,  
               Test.blackBoxTesting.Test2.class,  
               Test.blackBoxTesting.Test3.class,
               Test.blackBoxTesting.Test4.class,
               Test.blackBoxTesting.Test5.class,
               Test.blackBoxTesting.Test6.class,
               Test.blackBoxTesting.Test7.class,
               Test.blackBoxTesting.Test8.class,
               Test.blackBoxTesting.Test9.class,
               
               Test.whiteBoxTesting.Test1.class,  
               Test.whiteBoxTesting.Test2.class,  
               Test.whiteBoxTesting.Test3.class,
               Test.whiteBoxTesting.Test4.class,
               Test.whiteBoxTesting.Test5.class,})  
public class TestAll {  
  
}  
