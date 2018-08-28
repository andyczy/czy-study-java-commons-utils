package czy.gson.createrObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/*
 * Json Class List 
 * 
 */
public class JsonTest {
    public static void main(String[] args) {

        //创建JsonObject
        JsonObject object = new JsonObject();

        object.addProperty("cat", "it");
        //添加数组
        JsonArray array = new JsonArray();

        //添加元素
        JsonObject obn1 = new JsonObject();
        obn1.addProperty("id", 1);
        obn1.addProperty("name", "Java");
        obn1.addProperty("IDE", "eclipse");
        array.add(obn1);

        //添加元素
        JsonObject obn2 = new JsonObject();
        obn2.addProperty("id", 2);
        obn2.addProperty("name", "C#");
        obn2.addProperty("IDE", "VS");
        array.add(obn2);

        //添加元素
        JsonObject obn3 = new JsonObject();
        obn3.addProperty("id", 3);
        obn3.addProperty("name", "PHP");
        obn3.addProperty("IDE", "XCode");
        array.add(obn3);

        object.addProperty("pop", true);
        //把添加到数组
        object.add("languages", array);
        //输出数据
        System.out.println(object);

    }
}