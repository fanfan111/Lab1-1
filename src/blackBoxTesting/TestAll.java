package blackBoxTesting;
  
import org.junit.runner.RunWith;  
import org.junit.runners.Suite;  
import org.junit.runners.Suite.SuiteClasses;  
  
//import com.lxh.util.TestCalcuateUtils;  
  
//RunWith表示这个类是一个suite的类  
@RunWith(Suite.class)  
//说明这个类包含哪些测试组件  
@SuiteClasses({Test1.class,  
               Test2.class,  
               Test3.class,
               Test4.class,
               Test5.class,
               Test6.class,
               Test7.class,
               Test8.class,
               Test9.class,})  
public class TestAll {  
    /* 
     * 测试原则： 
     * 1、建议创建一个专门的source folder --> test 来编写测试类代码 
     * 2、测试类的包应该保持和需要测试的类一致 
     * 3、测试单元中的每一个测试方法都必须可以独立执行，没有顺序 
     * 4、测试方法之间不能有任何的依赖 
     */  
}  
