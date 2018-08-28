package czy.gson.createrObject;


import com.alibaba.fastjson.JSONObject;

/**
 * 当map来用、json
 */
public class Fun1_JSONObject {

	public static void main(String[] args) {

		JSONObject map = new JSONObject();
		
		map.put("name", "zhangSan");
		map.put("age", 23);
		map.put("sex", "male");
		
		String s = map.toString();
		System.out.println(s);
	}

}
