package apache.commons;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;


import java.util.Arrays;

import static encodeUtils.EncodeUtils.*;

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
        System.out.println("Base64 解码后：" + str);
        System.out.println(encodeTest(str));
        System.out.println(decodeTest(str));


        String md5Hex = md5HexUtils(str);
        System.out.print("MD5 摘要算法 一层：" + md5Hex + "\n");

        String md5Hexs = md5HexUtils(md5HexUtils(str));
        System.out.print("MD5 摘要算法 两层：" + md5Hexs + "\n");

        String ecPsw = sha1HexUtils(str);
        System.out.println("SHA1:" + ecPsw + "\n");
    }
}
