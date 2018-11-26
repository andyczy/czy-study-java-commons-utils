package json.gson.jsonMap;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * 把Java的Class对象使用Gson转换成Json的字符串
 * 仅包含基本数据类型的数据结构
 * 除了基本数据类型还包含了List集合
 * Map集合
 * 输出的字符串使用Gson转换成User对象
 */
public class Gson_Get_Test {
    public static void main(String[] args) {
        Gson gson = new Gson();
        User user = new User();
        user.id = 1;
        user.Name = "陈郑游";
        user.age = 22;
        user.email = "649954910@qq.base.io";
        System.out.println("第一次输出JSON数据：" + gson.toJson(user) + "\n");


        HashMap<String, String> booksMap = new HashMap<String, String>();
        //map.put(key, value);
        booksMap.put("1", "数学");
        booksMap.put("2", "语文");
        booksMap.put("3", "英语");
        booksMap.put("4", "物理");
        booksMap.put("5", "化学");
        booksMap.put("6", "生物");
        user.booksMap = booksMap;
        System.out.println("第三次输出JSON数据：" + gson.toJson(user) + "\n");

//		String string = new String();
//		//输出的字符串使用Gson转换成User对象
//		string.format(string, User.class);
//		
//		System.out.println("类名：" + user.getClass());
//		System.out.println("年龄：" + user.age);
//		System.out.println("名字：" + user.Name);
//		System.out.println("邮箱：" +user.email);
//		System.out.println("books:" + user.books);
//		System.out.println("books size :" + user.books.size());
//		System.out.println("booksMap:" + user.booksMap);

    }
}


