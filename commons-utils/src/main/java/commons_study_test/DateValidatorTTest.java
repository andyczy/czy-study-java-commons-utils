package commons_study_test;

import org.apache.commons.validator.routines.DateValidator;

import java.util.Date;

/**
 * @author Chenzhengyou
 * @date 2018-08-31
 * @description:
 */
public class DateValidatorTTest {
    public static void main(String[] args) {
        // 获取日期验证
        DateValidator validator = DateValidator.getInstance();
        // 验证/转换日期
        Date fooDate = validator.validate("2018/02/18", "yyyy/MM/dd");
        if (fooDate == null) {
            System.out.println("错误 不是日期");
        }else {
            System.out.println("是日期");
        }
    }
}
