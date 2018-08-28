package czy.conllection.print;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @auther 陈郑游
 * @时间 2017/5/12 0012
 * @功能
 * @问题
 * @博客地址：http://blog.csdn.net/javawebrookie
 * @GitHub地址：https://github.com/AndyCZY
 * @GitBook地址：https://www.gitbook.com/@chenzhengyou
 */
public class EnumerationTest {

    // 输出集合、使用率：4.9%
    public static void main(String[] args) {
        Vector c = new Vector();
        c.add("狗娃");
        c.add("狗剩");
        c.add("铁蛋");
        c.add("美美");

        // enumeration是Vector特有的
        // 获取Enumeration接口对象
        Enumeration enumeration = c.elements();
        // 方法1：和 hasNext() 一样
        while (enumeration.hasMoreElements()){
            // 方法2：获取元素
            System.out.println(enumeration.nextElement());
        }
    }

}
