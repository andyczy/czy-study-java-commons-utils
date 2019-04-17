package httpClientUtil;

import java.util.*;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

public class Test {


    /**
     * 测试 ：哗啦啦
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        long timeMillis = System.currentTimeMillis();

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("uuid:" + uuid);

        List<NameValuePair> params = new ArrayList<>();
        //参数
        params.add(new BasicNameValuePair("timestamp", String.valueOf(timeMillis)));
        params.add(new BasicNameValuePair("signature", ""));
        params.add(new BasicNameValuePair("devID", ""));
        params.add(new BasicNameValuePair("merchantsID", ""));
        params.add(new BasicNameValuePair("version", "2.0"));
        params.add(new BasicNameValuePair("requestBody", ""));

        //请求头
        Header[] headers = {
                new BasicHeader("Content-Type", "application/x-www-form-urlencoded"),
                new BasicHeader("traceID", uuid),
                new BasicHeader("groupID", ""),
                new BasicHeader("shopID", "")
        };

        JSONObject jsonStr = HttpUtil.requestPostToJSONObject("https://www-openapi.hualala.com/doc/getOpenFood", params, headers);
        System.out.println("请求结果：" + jsonStr);
    }



}
