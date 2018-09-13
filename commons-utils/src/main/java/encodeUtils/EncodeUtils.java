package encodeUtils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Author: 陈郑游
 * CreateTime: 2018-09-13 9:31
 * Description:
 */
public class EncodeUtils {

    /**
     * md5加密
     * @param str
     * @return
     */
    public static String md5HexUtils(String str) {
        String md5Hex = "";
        try {
            md5Hex = DigestUtils.md5Hex(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return md5Hex;

    }

    /**
     * sha1Hex加密
     * @param str
     * @return
     */
    public static String sha1HexUtils(String str) {
        String sha1Hex = "";
        try {
            sha1Hex = DigestUtils.sha1Hex(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1Hex;
    }

    /**
     * base64编码
     * @param str
     * @return
     */
    public static String encodeTest(String str){
        try {
            Base64 base64 = new Base64();
            str = base64.encodeToString(str.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * base64解码
     * @param str
     * @return
     */
    public static String decodeTest(String str){
        try {
            Base64 base64 = new Base64();
            str = new String(Base64.decodeBase64(str.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
