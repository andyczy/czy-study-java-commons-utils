package apache.commons.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * Author: Chen zheng you
 * CreateTime: 2019-02-21 11:25
 * Description:
 */
public class Lang3Test {

    public static void main(String[] args) throws Exception {
        Date date = DateUtils.addDays(new Date(),1);
        System.out.println(date);
        Date days = DateUtils.setDays(new Date(),10);
        System.out.println(days);

        Boolean aBoolean = DateUtils.isSameDay(date,new Date());
        System.out.println(aBoolean);

        System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
        System.out.println(DateUtils.parseDate("2015-05-04", "yyyy-MM-dd"));

        //生成随机六位数字   数字参数可以更改
        System.out.println(RandomStringUtils.randomNumeric(20));
        //生成随机六位字母
        System.out.println(RandomStringUtils.randomAlphabetic(20));
        System.out.println(RandomStringUtils.randomAlphabetic(20));
        System.out.println(RandomStringUtils.randomAlphanumeric(6));//生成随机六位字母数字组合
        System.out.println(RandomStringUtils.random(6, true, true));//六位 第一个boolean 是否包含字母，第二个是否包含数字
        System.out.println(RandomStringUtils.random(6, "abcdefghigkqqqiiiiqq"));//生成六位随机，按给出的字母中取得

    }
}
