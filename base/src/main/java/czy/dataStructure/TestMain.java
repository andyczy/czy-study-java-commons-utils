package czy.dataStructure;
import org.junit.Test;

/**
 * @auther 陈郑游
 * @create 2017/4/30 0030
 * @功能
 * @问题
 * @说明
 * @URL地址
 * @进度描述
 */
public class TestMain {


    public void switchmain() {
        int season = 0;
        String strSeason = "Summer";
        switch (strSeason.toLowerCase()) {
            case "spring":
                season = 1;
                break;
            default:
                season = -1;
                break;
        }
        System.out.println(strSeason + "-->" + season);
    }

    @Test
    public void yimain() {
        int n = 2 << 3;

        System.out.println("因为将一个数左移 n 位，就相当于乘以了2的 n 次方:" + n);


        final StringBuffer a = new StringBuffer("immutable");
        //执行如下语句将报告编译期错误：
        //a = new StringBuffer("");
        //但是，执行如下语句则可以通过编译：
        a.append(" broken!");

        System.out.println("Math 类中提供了三个与取整有关的方法： ceil、 floor、 round，这些方法的作用与它们的英\n" +
                "文名称的含义相对应，");
        System.out.println(Math.round(-11.5));
        System.out.println(Math.round(11.5));


//        int x=1;
//        return x==1?true:false;
    }



    public static int staticVar = 0;
    public int instanceVar = 0;

    @Test
    public void VariantTest() {
        staticVar++;
        instanceVar++;
        System.out.println(instanceVar);
        System.out.println(staticVar);
    }
}
