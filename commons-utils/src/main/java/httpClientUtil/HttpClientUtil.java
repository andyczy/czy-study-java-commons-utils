//package httpClientUtil;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * @auther 陈郑游
// * @create 2016-11-24-19:27
// * @功能描述 HttpClientUtil 工具类
// * @问题
// * @说明
// * @URL地址
// * @进度描述
// */
//public class HttpClientUtil {
//
//
//    /**
//     * Description:get请求处理
//     * @param url
//     * @param param
//     *
//     */
//    public static String doGet(String url, Map<String, String> param) {
//
//        // 创建Httpclient对象
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//
//        String resultString = "";
//        CloseableHttpResponse response = null;
//
//        try {
//            // 创建uri
//            URIBuilder builder = new URIBuilder(url);
//            if (param != null) {
//                for (String key : param.keySet()) {
//                    builder.addParameter(key, param.get(key));
//                }
//            }
//            URI uri = builder.build();
//
//            // 创建http GET请求
//            HttpGet httpGet = new HttpGet(uri);
//
//            // 执行请求
//            response = httpclient.execute(httpGet);
//
//            // 判断返回状态是否为200
//            if (response.getStatusLine().getStatusCode() == 200) {
//                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (response != null) {
//                    response.close();
//                }
//                httpclient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return resultString;
//    }
//
//
//    /**
//     * Description:get请求处理
//     * @param url
//     */
//    public static String doGet(String url) {
//        return doGet(url, null);
//    }
//
//
//    /**
//     * Description:Post请求处理
//     * @param url
//     * @param param
//     */
//    public static String doPost(String url, Map<String, String> param) {
//
//        // 创建Httpclient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        CloseableHttpResponse response = null;
//        String resultString = "";
//
//        try {
//            // 创建Http Post请求
//            HttpPost httpPost = new HttpPost(url);
//
//            // 创建参数列表
//            if (param != null) {
//
//                List<NameValuePair> paramList = new ArrayList<>();
//
//                for (String key : param.keySet()) {
//                    paramList.add(new BasicNameValuePair(key, param.get(key)));
//                }
//
//                // 模拟表单
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
//                httpPost.setEntity(entity);
//            }
//            // 执行http请求
//            response = httpClient.execute(httpPost);
//            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                response.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        return resultString;
//    }
//
//
//    /**
//     * Description:Post请求处理
//     * @param url
//     */
//    public static String doPost(String url) {
//        return doPost(url, null);
//    }
//
//
//
//    /**
//     * Description:PostJson请求处理
//     * @param url
//     * @param json
//     */
//    public static String doPostJson(String url, String json) {
//
//        // 创建Httpclient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        CloseableHttpResponse response = null;
//        String resultString = "";
//
//        try {
//            // 创建Http Post请求
//            HttpPost httpPost = new HttpPost(url);
//            // 创建请求内容
//            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
//            httpPost.setEntity(entity);
//            // 执行http请求
//            response = httpClient.execute(httpPost);
//            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                response.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        return resultString;
//    }
//}
