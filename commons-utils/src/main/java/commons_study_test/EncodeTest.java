package commons_study_test;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author Chenzhengyou
 * @date 2018-08-31
 * @description:
 */
public class EncodeTest {
    public static void main(String[] args) {
        Base64 base64 = new Base64();
        String str = "czy19940726";
        str = Arrays.toString(Base64.decodeBase64(str));
//        str = new String(Base64.decodeBase64(str));
        System.out.println("Base64 解码后：" + str);
    }
}
