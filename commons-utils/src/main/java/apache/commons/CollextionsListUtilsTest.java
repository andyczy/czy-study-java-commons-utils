package apache.commons;

import org.apache.commons.collections.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 陈郑游
 * Date: 2018-09-11
 * Time: 9:36
 * Description:
 */
public class CollextionsListUtilsTest {
    public static void main(String[] args) {
        List list  = new ArrayList();
        list.add("a");

        List list2  = new ArrayList();
        list2.add("b");

        List sum = ListUtils.sum(list,list2);
        System.out.println(sum);
    }
}
