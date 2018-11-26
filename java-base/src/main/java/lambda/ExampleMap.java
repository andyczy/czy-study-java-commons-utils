package lambda;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther 陈郑游
 * @Data 2017/8/21 0021
 * @Description:
 * @CSDN:http://blog.csdn.net/javawebrookie
 * @GITHUB:https://github.com/AndyCZY
 */
public class ExampleMap {

    private static Map<String, Integer> items = new HashMap<>();
    static {
        items.put("A", 10);
        items.put("B", 20);
        items.put("C", 30);
        items.put("D", 40);
        items.put("E", 50);
        items.put("F", 60);
    }
    public static void main(String[] args) {
        //Java8之前遍历是这样遍历map
        for(Map.Entry<String,Integer> entry:items.entrySet()){
            System.out.println("key:" + entry.getKey() + " value:" + entry.getValue());
        }
        //Java8遍历map
        items.forEach((key,value)-> System.out.println("key:" + key + " value:" + value));
    }


}
