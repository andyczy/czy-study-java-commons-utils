package lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther 陈郑游
 * @Data 2017/8/21 0021
 * @Description: http://www.cnblogs.com/wangdaijun/p/6287532.html
 * @CSDN:http://blog.csdn.net/javawebrookie
 * @GITHUB:https://github.com/AndyCZY
 */
public class ExampleList {

    private static List<String> items = new ArrayList<>();
    static {
        items.add("A");
        items.add("BC");
        items.add("C");
        items.add("BD");
        items.add("E");
    }
    public static void main(String[] args) {
        //Java8之前操作List
        for(String item:items){
            System.out.println(item);
        }
        //Java8 lambda遍历list
        items.forEach(c-> System.out.println(c));
        items.forEach(item->{
            if("C".equals(item)){
                System.out.println(item);
            }
        });
        System.out.println("--------");
        //先过滤
        items.stream().filter(s->s.contains("B")).forEach(c1-> System.out.println(c1));
    }
}
