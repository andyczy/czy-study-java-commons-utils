package czy.gson.createrGson;

import com.google.gson.Gson;
import czy.gson.jsonList.User;

/**
 * json
 * 把Java的Class对象使用Gson转换生成Json的字符串
 * 仅包含基本数据类型的数据结构
 *
 * @author 陈郑游
 */
public class GsonUserTest {
    public static void main(String[] args) {

        Gson gson = new Gson();
        User user = new User();

        user.id = 1;
        user.Name = "陈郑游";
        user.age = 22;
        user.email = "649954910@qq.com";
//        user.aihao = "打篮球";

        System.out.println(gson.toJson(user));

    }
}