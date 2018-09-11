package apache.commons;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Author: 陈郑游
 * Date: 2018-09-11
 * Time: 9:33
 * Description:
 */
public class BeanUtilsTest {

    public static void main(String[] args)throws Exception {
        Book book  = new Book();
        book.setIsbn("123");
        Object cloneBean = BeanUtils.cloneBean(book);

        book.setName("czy");


        System.out.println(book);
        System.out.println(cloneBean);
    }
}
