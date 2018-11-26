package apache.commons.test;

import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;
import org.junit.Test;

import java.util.Date;

/**
 * @author Chenzhengyou
 * @date 2018-08-31
 * @description:
 */
public class ValidatorTTest {


    @Test
    public void test () {
        // 获取日期验证
        DateValidator validator = DateValidator.getInstance();
        // 验证/转换日期
        Date fooDate = validator.validate("2018/02/18", "yyyy/MM/dd");
        if (fooDate == null) {
            System.out.println("错误 不是日期");
        } else {
            System.out.println("是日期");
        }

        IntegerValidator instance1 = IntegerValidator.getInstance();
        Integer validate = instance1.validate("ss1");
        System.out.println("是否是int：" + validate);


        DoubleValidator instance = DoubleValidator.getInstance();
        boolean value = instance.maxValue(3, 2);
        System.out.println("比较大小" + value);

        boolean emailValidator = EmailValidator.getInstance().isValid("649954910@qq.com");
        System.out.println("邮箱验证" + emailValidator);


        boolean email = GenericValidator.isEmail("649954910@qq.com");
        System.out.println("邮箱验证" + email);
    }
}
