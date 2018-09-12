package validatorUtil;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Chenzhengyou
 * @date 2018-06-22
 * @description: 校验数字
 */
public class ValidatorUtil {


    private final static Integer MOBILELENGTH = 11;
    private final static Integer PHONELENGTH = 9;
    private final static Integer inlandLENGTH = 15;
    private final static Integer inlandSLENGTH = 18;

    /**
     * 验证手机号是否合法
     *
     * @param mobile
     * @return
     */
    public static boolean isMobilePhone(String mobile) {
        if (mobile.length() != MOBILELENGTH) {
            return false;
        } else {
            /**
             * 移动号段正则表达式
             */
            String patMobile = "^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";
            /**
             * 联通号段正则表达式
             */
            String patUnicom = "^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$";
            /**
             * 电信号段正则表达式
             */
            String patTelecommunications = "^((133)|(153)|(177)|(18[0,1,9])|(149)|(199))\\d{8}$";
            /**
             * 虚拟运营商正则表达式
             */
            String patVirtual = "^((170))\\d{8}|(1718)|(1719)\\d{7}$";

            boolean successM = Pattern.compile(patMobile).matcher(mobile).matches();
            if (successM) {
                return true;
            }
            boolean successU = Pattern.compile(patUnicom).matcher(mobile).matches();
            if (successU) {
                return true;
            }
            boolean successT = Pattern.compile(patTelecommunications).matcher(mobile).matches();
            if (successT) {
                return true;
            }
            boolean successV = Pattern.compile(patVirtual).matcher(mobile).matches();
            if (successV) {
                return true;
            }
            return false;
        }
    }


    /**
     * 电话号码验证
     *
     * @param str
     * @return
     */
    public static boolean isPhone(String str) {
        // 验证带区号的
        String areaCode = "^[0][1-9]{2,3}-[0-9]{5,10}$";
        // 验证没有区号的
        String noAreaCode = "^[1-9]{1}[0-9]{5,8}$";
        Matcher m = null;
        boolean b = false;
        Pattern p1 = Pattern.compile(areaCode);
        Pattern p2 = Pattern.compile(noAreaCode);
        if (str.length() > PHONELENGTH) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 是否数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Boolean success = false;
        String re = "^-{0,1}\\d*\\.{0,1}\\d+$";
        if (StringUtils.isNotBlank(str)) {
            success = Pattern.matches(re, str);
        }
        return success;
    }

    /**
     * 中国大陆与香港身份证正则表达式(15位、18位)、包含x或者大写X
     *
     * @param str
     * @return
     */
    public static boolean isIDCard(String str) {
        Boolean success = false;
        //中国大陆
        String nl = "(^\\d{18}$)|(^\\d{15}$)|(^\\d{17}[\\d|x]$)|(^\\d{17}[\\d|X]$)|(^\\d{14}[\\d|X]$)|(^\\d{14}[\\d|x]$)";
        //中国大陆与香港
        String re = "([A-Za-z](\\d{6})\\(\\d\\))|(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X|x)";
        if (StringUtils.isNotBlank(str)) {
            success = Pattern.matches(re, StringUtils.trim(str));
        }
        return success;
    }

    public static void main(String[] args) {
        System.out.println(isIDCard(" p103678(9)"));
    }

}
