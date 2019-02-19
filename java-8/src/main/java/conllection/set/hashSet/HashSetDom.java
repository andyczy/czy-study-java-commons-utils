package conllection.set.hashSet;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class HashSetDom {


    /*
    * 用于存储无序(存入和取出的顺序不一定相同)元素，值不能重复。
    *
    *
    *
    *
    *
    *
    * */
    public static void main(String[] args) {

        //Set 集合存和取的顺序不一致。
        Set<Comparable> hs = new HashSet<Comparable>(6);

        hs.add("abc");
        hs.add(new Date());//在HashSet里增加日期对象
        hs.add(new Integer(12));

        Iterator<Comparable> it = hs.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}


