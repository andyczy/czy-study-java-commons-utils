package json.gson.createrObject;


import net.sf.json.JSONObject;

/*
 * 当你已经有一个Person对象时，可以把Person转换成JSONObject对象
 * 
 */
public class Fun2_JSONObject_Person {

	public static void main(String[] args) {

		Person p = new Person("liSi", 32, "female");
		// 把对象转换成JSONObject类型
		JSONObject map = JSONObject.fromObject(p);
		System.out.println(map.toString());
	}

}
