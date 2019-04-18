package jsonUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


/**
 * @auther 陈郑游
 * @create 2016/3/26 0020
 * @功能
 * @问题
 * @说明
 * @URL地址
 * @进度描述 于jackson封装的json转换工具类
 */
public class JacksonUtils {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * Description：将对象转换成json字符串
     *
     * @param data json数据
     * @return
     */
    public static String objectToJson(Object data) {

        try {
            String string = MAPPER.writeValueAsString(data);
            return string;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Description：将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Description：将json数据转换成pojo对象list
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);

        try {

            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
