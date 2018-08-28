package czy.baseClass.scanner;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @auther 陈郑游
 * @create 2017/3/21 0021
 * @功能 现在输入n个数字，以逗号，分开；然后可选择升或降序排序
 * @问题
 * @说明
 * @URL地址
 * @进度描述
 */
public class SortNum {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("请输入,需要进行排序的数字(格式 : 233,53,36),回车结束输入.");
        String nextLine = sc.nextLine();

        String[] split = nextLine.split(",");
        Integer[] temp = new Integer[split.length];

        int index = 0;

        try {
            for (String string : split) {
                temp[index++] = Integer.parseInt(string);
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println("请输入正确的内容以及格式.");
            return;
        }

        System.out.println("你输入的是 : " + Arrays.toString(temp));
        System.out.println("请选择,升序(0)还是降序(1)");

        int nextInt = sc.nextInt();
        Arrays.sort(temp);

        if (nextInt == 1) {
            StringBuilder stringBuilder = new StringBuilder(Arrays.toString(temp));
            String substring = stringBuilder.substring(1, stringBuilder.length() - 1);
            StringBuilder sTemp = new StringBuilder(substring).reverse();
            System.out.println("降序 : " + sTemp);
        } else {
            System.out.println("升序 : " + Arrays.toString(temp));
        }
    }

}
