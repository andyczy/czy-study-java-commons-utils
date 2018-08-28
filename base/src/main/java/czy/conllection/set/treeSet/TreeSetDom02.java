package czy.conllection.set.treeSet;

import java.util.TreeSet;

/**
 TreeSet练习
 将字符串中的数值进行排序。
 例如String str="8 10 15 5 2 7";    2,5,7,8,10,15

 使用 TreeSet完成。
 思路:
 1，将字符串切割。
 2，可以将这些对象存入TreeSet集合。
 因为TreeSet自身具备排序功能。

 */
public class TreeSetDom02 {
    //排序
    public static void main(String[] args) {
        String str = "8 10 15 5 2 7";
        String[] strs = str.split(" ");
        TreeSet ts = new TreeSet();
        for (int x = 0; x < strs.length; x++) {
            int y = Integer.parseInt(strs[x]);
            ts.add(y);
        }
        System.out.println(ts);
    }
}
