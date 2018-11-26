package json.gson.jsonList;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 把Java的Class对象使用Gson转换成Json的字符串
 * 仅包含基本数据类型的数据结构
 * 除了基本数据类型还包含了List集合
 * 除了基本数据类型还包含了List和Map集合
 */
public class GsonArrayBookTest {
    public static void main(String[] args) {
        Gson gson = new Gson();
        User user = new User();
        user.id = 1;
        user.Name = "陈郑游";
        user.age = 22;
        user.email = "649954910@qq.base.io";
        System.out.println("第一次输出JSON数据：" + gson.toJson(user));

        List<String> books = new ArrayList<String>();
        books.add("数学");
        books.add("语文");
        books.add("英语");
        books.add("物理");
        books.add("化学");
        books.add("生物");
        user.books = (ArrayList<String>) books;
        System.out.println("第二次输出JSON数据：" + gson.toJson(user));


    }
}


