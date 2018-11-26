package json.gson.createrObject;


import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 原来就有一个List，我们需要把List转换成JSONArray
 */

public class Fun4_JSONArray_Person {

	public static void main(String[] args) {

		Person p1 = new Person("zhangSan", 23, "male");
		Person p2 = new Person("liSi", 32, "female");
		List<Person> list = new ArrayList<Person>();
		list.add(p1);
		list.add(p2);

		System.out.println(JSONArray.fromObject(list).toString());

	}

}
