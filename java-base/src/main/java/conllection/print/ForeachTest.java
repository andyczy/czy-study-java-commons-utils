package conllection.print;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther 陈郑游
 * @时间 2017/5/12 0012
 * @功能
 * @问题
 * @博客地址：http://blog.csdn.net/javawebrookie
 * @GitHub地址：https://github.com/AndyCZY
 * @GitBook地址：https://www.gitbook.com/@chenzhengyou
 */
public class ForeachTest {


    // 输出集合、使用率：0.05%
    public static void main(String[] args) {
        List<String> c = new ArrayList<String>();
        c.add("狗娃");
        c.add("狗剩");
        c.add("铁蛋");
        c.add("美美");
        for (String str : c) {
            System.out.println(str);
        }
    }
}
