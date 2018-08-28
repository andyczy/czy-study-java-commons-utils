package czy.conllection.list.list_linked.dome2;

/**
 * @auther 陈郑游
 * @时间 2017/5/13 0013
 * @功能
 * @问题
 * @博客地址：http://blog.csdn.net/javawebrookie
 * @GitHub地址：https://github.com/AndyCZY
 * @GitBook地址：https://www.gitbook.com/@chenzhengyou
 */
public class Client {

    public static void main(String[] args) {

       Link link = new Link();  // 负责所有数据的操作
       link.add("车头");
       link.add("车厢1");
       link.add("车厢2");
       link.print();


    }
}
