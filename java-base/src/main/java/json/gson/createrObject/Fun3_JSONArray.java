package czy.gson.createrObject;


import com.alibaba.fastjson.JSONArray;

/**
 * JSONArray
 */
public class Fun3_JSONArray {

	public static void main(String[] args) {

		Person p1 = new Person("zhangSan", 23, "male");
		Person p2 = new Person("liSi", 32, "female");

		JSONArray list = new JSONArray();
		list.add(p1);
		list.add(p2);

		System.out.println(list.toString());

	}

}
