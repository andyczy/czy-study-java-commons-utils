package httpClientUtil;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * http 请求工具类：包含get post请求
 *
 */

public class HttpUtil {
	
	private static Integer connectTimeout = 20000;// 20秒超时
	private static Integer socketTimeout = 30000;// 30秒超时
	protected final static Logger logger = LogManager.getLogger(HttpUtil.class);

	/**
	 * 配置请求的超时设置
	 * @return
	 */
	public static RequestConfig getRequestConfig(){
		return RequestConfig.custom()
				.setConnectionRequestTimeout(connectTimeout).setConnectTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout).build();
	}
	
	/**
	 * http get请求方法，返回json字符串 - 推荐
	 * </p>
	 * connect by o.a.http.impl.client.CloseableHttpClient
	 * 
	 * @param urlWithParams
	 * @return
	 * @throws Exception
	 */
	public static String requestGet(String urlWithParams, Header[] headers) throws Exception {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		HttpGet httpget = new HttpGet(urlWithParams);

		// 配置请求的超时设置
		httpget.setConfig(getRequestConfig());
		if(null != headers){
			httpget.setHeaders(headers);
		}

		CloseableHttpResponse response = httpclient.execute(httpget);
		// System.out.println("StatusCode -> " +
		// response.getStatusLine().getStatusCode());

		HttpEntity entity = response.getEntity();
		String jsonStr = EntityUtils.toString(entity);// , "utf-8");

		httpget.releaseConnection();
		
		if (StringUtils.isNotBlank(jsonStr)) {
			return jsonStr;
		}
		return null;
	}
	
	/**
	 * Get请求数据转成JSONObject
	 * @param urlWithParams
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static JSONObject requestGetToJSONObject(String urlWithParams,
                                                    Header[] headers) throws Exception {
		String result = requestGet(urlWithParams, headers);
		if(StringUtils.isBlank(result)){
			return null;
		}
		return JSONObject.parseObject(result);
	}
	
	/**
	 * http post请求方法，返回json字符串 - 推荐
	 * </p>
	 * connect by o.a.http.impl.client.CloseableHttpClient
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String requestPost(String url, List<NameValuePair> params,
			Header[] headers) throws Exception {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httppost = new HttpPost(url);
		
		// 配置请求的超时设置
		httppost.setConfig(getRequestConfig());
		if(null != params){
			httppost.setEntity(new UrlEncodedFormEntity(params));
		}
		if(null != headers){
			httppost.setHeaders(headers);
		}

		CloseableHttpResponse response = httpclient.execute(httppost);
		// System.out.println(response.toString());

		HttpEntity entity = response.getEntity();
		// 返回的有可能是加密的字符串
		String jsonStr = EntityUtils.toString(entity, "UTF-8");
		// System.out.println(jsonStr);
		
		httppost.releaseConnection();
		
		if (StringUtils.isNotBlank(jsonStr)) {
			return jsonStr;
		}
		return null;
	}
	
	/**
	 * Post请求数据转成JSONObject
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static JSONObject requestPostToJSONObject(String url,
                                                     List<NameValuePair> params, Header[] headers) throws Exception {
		String result = requestPost(url, params, headers);
		if(StringUtils.isBlank(result)){
			return null;
		}
		return JSONObject.parseObject(result);
	}
	
	/**
	 * 
	 * @param url
	 * @param jsonStr
	 * @return
	 */
	public static String post(String url,String jsonStr) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setConfig(getRequestConfig());
        post.addHeader("Content-Type", "application/json;charset=UTF-8");
        String result = "";
        
        try {

        	StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            post.setEntity(stringEntity);

            // 发送请求
            HttpResponse httpResponse = httpclient.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //System.out.println("请求服务器成功，做相应处理");
            	//logger.info("请求成功，做相应处理");
                
            } else {
            	logger.error("POST请求服务端失败，errorMsg：" + result);
            }

        } catch(ConnectTimeoutException e){
			// 捕获超时异常 并反馈给调用者
			e.printStackTrace();
			logger.error("POST请求连接超时");
		} catch (Exception e) {
        	e.printStackTrace();
        	logger.error("POST请求异常");
        }
        return result;
    }
	
	/**
	 * post 动态Content-Type
	 * @param url
	 * @param jsonStr
	 * @param ct----Content-Type
	 * @return
	 */
	public static String post(String url,String jsonStr, String ct) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
		post.setConfig(getRequestConfig());
        post.addHeader("Content-Type", ct);
        String result = "";
        
        try {

        	StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            post.setEntity(stringEntity);

            // 发送请求
            HttpResponse httpResponse = httpclient.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //System.out.println("请求服务器成功，做相应处理");
            	//logger.info("请求成功，做相应处理");
                
            } else {
            	logger.info("POST请求服务端失败");
            }

        } catch (Exception e) {
        	logger.error("POST请求异常");
            throw new RuntimeException(e);
        }
        return result;
    }
	
	public static JSONObject postToJSONObject(String url, String jsonStr) throws Exception {
		String result = post(url, jsonStr);
		if(StringUtils.isBlank(result)){
			return null;
		}
		return JSONObject.parseObject(result);
	}
	
}
