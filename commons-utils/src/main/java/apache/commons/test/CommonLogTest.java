package apache.commons.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Chenzhengyou
 * @date 2018-08-31
 * @description:
 */
public class CommonLogTest {
    private static Log log = LogFactory.getLog(CommonLogTest.class);


    //日志打印
    public static void main(String[] args) {
        log.error("ERROR");
        log.debug("DEBUG");
        log.warn("WARN");
        log.info("INFO");
        log.trace("TRACE");
        System.out.println(log.getClass());
    }
}
