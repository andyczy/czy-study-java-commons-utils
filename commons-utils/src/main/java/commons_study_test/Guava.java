package commons_study_test;

import com.google.common.base.Objects;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * @author Chenzhengyou
 * @date 2018-08-30
 * @description:
 */
public class Guava {

    public static void main(String[] args) {
        System.out.println( Objects.equal("a", "a")); // returns true
        System.out.println( Objects.equal(null, "a")); // returns true
        System.out.println( Objects.equal("a", null)); // returns true
        System.out.println(  Objects.equal(null, null)); // returns true

        Date d = new Date();

        System.out.println(DateFormatUtils.format(d, String.valueOf(DateFormatUtils.ISO_DATE_FORMAT)));

    }
}
