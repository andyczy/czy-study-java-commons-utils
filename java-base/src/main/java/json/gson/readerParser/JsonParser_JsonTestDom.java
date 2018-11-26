package json.gson.readerParser;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;


/**
 * Reader  Json Class
 * 全部输出Json数据
 */
public class JsonParser_JsonTestDom {
    public static void main(String[] args) {

        ClassLoader classLoader = JsonParser_JsonTestDom.class.getClassLoader();
        URL resource = classLoader.getResource("json/Test.json");
        String path = resource.getPath();
        System.out.println(path);

        try {
            //创建JsonParser 解析对象
            JsonParser parser = new JsonParser();
            //再创建 JsonObject 和 文件读取流
            FileReader reader = new FileReader(path);
            BufferedReader buffered = new BufferedReader(reader);

            JsonObject object = (JsonObject) parser.parse(buffered);
            //读取ken_vlaue    String 、Boolean 转化不同类型接收不同类型
            System.out.println("cat:" + object.get("cat").getAsString());
            System.out.println("pop:" + object.get("pop").getAsBoolean());

            //获取数组
            JsonArray array = object.get("languages").getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                System.out.println("-------JSON对象如下-------");
                //获取数组单独对象
                JsonObject sub = array.get(i).getAsJsonObject();
                System.out.println("id:" + sub.get("id").getAsInt());
                System.out.println("name:" + sub.get("name").getAsString());
                System.out.println("IDE:" + sub.get("IDE").getAsString());
            }

        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}