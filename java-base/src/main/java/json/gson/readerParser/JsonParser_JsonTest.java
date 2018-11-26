package czy.gson.readerParser;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

/**
 * Reader  Json Class
 * 全部输出Json数据
 */
public class JsonParser_JsonTest {
	public static void main(String[] args) {

		ClassLoader classLoader = JsonParser_JsonTest.class.getClassLoader();
		URL resource = classLoader.getResource("json/Test.json");
		String path = resource.getPath();
		System.out.println(path);

		try {

			//1.创建JsonParser对象,解析!
			JsonParser parser = new JsonParser();
			//2.创建JsonObject对象
			JsonObject object = (JsonObject) parser.parse(new FileReader(path));
			//3.遍历数组输出						
			JsonObject objectlist = object.getAsJsonObject();
			System.out.println(objectlist);


		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}


}
